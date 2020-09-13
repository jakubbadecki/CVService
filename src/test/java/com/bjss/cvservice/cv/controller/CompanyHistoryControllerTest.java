package com.bjss.cvservice.cv.controller;

import com.bjss.cvservice.cv.entity.dto.CompanyHistoryDTO;
import com.bjss.cvservice.cv.service.CompanyHistoryService;
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

class CompanyHistoryControllerTest {
    private static final String TEST_NAME = "testName";
    private static final String TEST_USERNAME = "testUsername";

    @Mock
    private CompanyHistoryService companyHistoryService;
    private CompanyHistoryController companyHistoryController;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.companyHistoryController = new CompanyHistoryController(companyHistoryService);
    }

    @Test
    void shouldCreateCompanyHistoryListWhenSaveEndpointIsCalled() {
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        List<CompanyHistoryDTO> companyHistoryDTOList = Collections.singletonList(getCompanyHistoryDTO());
        companyHistoryController.saveCompanyHistory(companyHistoryDTOList, authentication);
        verify(companyHistoryService, times(1))
                .saveCompanyHistory(eq(TEST_USERNAME), eq(companyHistoryDTOList));
    }

    @Test
    void shouldThrowExceptionWhenSaveCompanyHistoryEndpointIsCalled() {
        List<CompanyHistoryDTO> companyHistoryDTOList = Collections.singletonList(getCompanyHistoryDTO());
        doThrow(CVServiceException.class)
                .when(companyHistoryService)
                .saveCompanyHistory(eq(TEST_USERNAME), eq(companyHistoryDTOList));
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        assertThrows(CVServiceException.class, () -> companyHistoryController
                .saveCompanyHistory(companyHistoryDTOList, authentication));
        verify(companyHistoryService, times(1))
                .saveCompanyHistory(eq(TEST_USERNAME), eq(companyHistoryDTOList));
    }

    @Test
    void shouldDeleteCompanyHistoryWhenDeleteCompanyHistoryEndpointIsCalled() {
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        companyHistoryController.deleteCompanyHistory(TEST_NAME, authentication);
        verify(companyHistoryService, times(1))
                .deleteCompanyHistory(eq(TEST_USERNAME), eq(TEST_NAME));
    }

    @Test
    void shouldGetCompanyHistoryWhenGetEndpointIsCalled() {
        List<CompanyHistoryDTO> companyHistoryDTOList = Collections.singletonList(getCompanyHistoryDTO());
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        when(companyHistoryService.getCompanyHistory(eq(TEST_USERNAME))).thenReturn(companyHistoryDTOList);
        List<CompanyHistoryDTO> companyHistoryList = companyHistoryController.getCompanyHistory(authentication);
        verify(companyHistoryService, times(1)).getCompanyHistory(eq(TEST_USERNAME));
        assertEquals(companyHistoryDTOList, companyHistoryList);
    }

    @Test
    void shouldThrowExceptionWhenGetCompanyHistoryEndpointIsCalled() {
        doThrow(CVServiceException.class)
                .when(companyHistoryService)
                .getCompanyHistory(eq(TEST_USERNAME));
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        assertThrows(CVServiceException.class, () -> companyHistoryController.getCompanyHistory(authentication));
        verify(companyHistoryService, times(1)).getCompanyHistory(eq(TEST_USERNAME));
    }

    @Test
    void shouldAddCompanyHistoryWhenAddEndpointIsCalled() {
        List<CompanyHistoryDTO> companyHistoryDTOList = Collections.singletonList(getCompanyHistoryDTO());
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        companyHistoryController.addCompanyHistory(companyHistoryDTOList, authentication);
        verify(companyHistoryService, times(1))
                .addCompanyHistory(eq(TEST_USERNAME), eq(companyHistoryDTOList));
    }

    @Test
    void shouldThrowExceptionWhenAddCompanyHistoryEndpointIsCalled() {
        List<CompanyHistoryDTO> companyHistoryDTOList = Collections.singletonList(getCompanyHistoryDTO());
        doThrow(CVServiceException.class)
                .when(companyHistoryService)
                .addCompanyHistory(eq(TEST_USERNAME), eq(companyHistoryDTOList));
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        assertThrows(CVServiceException.class, () -> companyHistoryController
                .addCompanyHistory(companyHistoryDTOList, authentication));
        verify(companyHistoryService, times(1))
                .addCompanyHistory(eq(TEST_USERNAME), eq(companyHistoryDTOList));
    }

    private CompanyHistoryDTO getCompanyHistoryDTO() {
        return CompanyHistoryDTO.builder().build();
    }
}