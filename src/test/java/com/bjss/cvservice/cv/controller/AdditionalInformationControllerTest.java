package com.bjss.cvservice.cv.controller;

import com.bjss.cvservice.cv.entity.dto.AdditionalInformationDTO;
import com.bjss.cvservice.cv.service.AdditionalInformationService;
import com.bjss.cvservice.exception.CVServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AdditionalInformationControllerTest {
    private static final String TEST_NAME = "testName";
    private static final String TEST_USERNAME = "testUsername";

    @Mock
    private AdditionalInformationService additionalInformationService;
    private AdditionalInformationController additionalInformationController;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.additionalInformationController = new AdditionalInformationController(additionalInformationService);
    }

    @Test
    void shouldCreateAdditionalInformationListWhenSaveEndpointIsCalled() {
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        List<AdditionalInformationDTO> companyHistoryDTOList = Collections
                .singletonList(getAdditionalInformationDTO());
        additionalInformationController.saveAdditionalInformation(companyHistoryDTOList, authentication);
        verify(additionalInformationService, times(1))
                .saveAdditionalInformation(eq(TEST_USERNAME), eq(companyHistoryDTOList));
    }

    @Test
    void shouldThrowExceptionWhenSaveAdditionalInformationEndpointIsCalled() {
        List<AdditionalInformationDTO> companyHistoryDTOList = Collections
                .singletonList(getAdditionalInformationDTO());
        doThrow(CVServiceException.class)
                .when(additionalInformationService)
                .saveAdditionalInformation(eq(TEST_USERNAME), eq(companyHistoryDTOList));
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        assertThrows(CVServiceException.class, () -> additionalInformationController
                .saveAdditionalInformation(companyHistoryDTOList, authentication));
        verify(additionalInformationService, times(1))
                .saveAdditionalInformation(eq(TEST_USERNAME), eq(companyHistoryDTOList));
    }

    @Test
    void shouldDeleteAdditionalInformationWhenDeleteAdditionalInformationEndpointIsCalled() {
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        additionalInformationController.deleteAdditionalInformation(TEST_NAME, authentication);
        verify(additionalInformationService, times(1))
                .deleteAdditionalInformation(eq(TEST_USERNAME), eq(TEST_NAME));
    }

    @Test
    void shouldGetAdditionalInformationWhenGetEndpointIsCalled() {
        List<AdditionalInformationDTO> additionalInformationDTOList = Collections.singletonList(getAdditionalInformationDTO());
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        when(additionalInformationService.getAdditionalInformation(eq(TEST_USERNAME)))
                .thenReturn(additionalInformationDTOList);
        List<AdditionalInformationDTO> additionalInformationList = additionalInformationController.getAdditionalInformation(authentication);
        verify(additionalInformationService, times(1)).getAdditionalInformation(eq(TEST_USERNAME));
        assertEquals(additionalInformationDTOList, additionalInformationList);
    }

    @Test
    void shouldThrowExceptionWhenGetAdditionalInformationEndpointIsCalled() {
        doThrow(CVServiceException.class)
                .when(additionalInformationService)
                .getAdditionalInformation(eq(TEST_USERNAME));
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        assertThrows(CVServiceException.class, () -> additionalInformationController
                .getAdditionalInformation(authentication));
        verify(additionalInformationService, times(1))
                .getAdditionalInformation(eq(TEST_USERNAME));
    }

    @Test
    void shouldAddAdditionalInformationWhenAddEndpointIsCalled() {
        List<AdditionalInformationDTO> additionalInformationDTOList = Collections.singletonList(getAdditionalInformationDTO());
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        additionalInformationController
                .addAdditionalInformation(additionalInformationDTOList, authentication);
        verify(additionalInformationService, times(1))
                .addAdditionalInformation(eq(TEST_USERNAME), eq(additionalInformationDTOList));
    }

    @Test
    void shouldThrowExceptionWhenAddAdditionalInformationEndpointIsCalled() {
        List<AdditionalInformationDTO> companyHistoryDTOList = Collections
                .singletonList(getAdditionalInformationDTO());
        doThrow(CVServiceException.class)
                .when(additionalInformationService)
                .addAdditionalInformation(eq(TEST_USERNAME), eq(companyHistoryDTOList));
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        assertThrows(CVServiceException.class, () -> additionalInformationController
                .addAdditionalInformation(companyHistoryDTOList, authentication));
        verify(additionalInformationService, times(1))
                .addAdditionalInformation(eq(TEST_USERNAME), eq(companyHistoryDTOList));
    }

    private AdditionalInformationDTO getAdditionalInformationDTO() {
        return AdditionalInformationDTO.builder().build();
    }
}