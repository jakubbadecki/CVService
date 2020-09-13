package com.bjss.cvservice.cv.controller;

import com.bjss.cvservice.cv.entity.dto.CvDTO;
import com.bjss.cvservice.cv.service.CvService;
import com.bjss.cvservice.exception.CVServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CvControllerTest {
    private static final String TEST_USERNAME = "testUsername";

    @Mock
    private CvService cvService;
    private CvController cvController;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.cvController = new CvController(cvService);
    }

    @Test
    void shouldCreateCvListWhenSaveEndpointIsCalled() {
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        CvDTO cvDTO = getCvDTO();
        cvController.updateCv(cvDTO, authentication);
        verify(cvService, times(1)).saveCv(eq(TEST_USERNAME), eq(cvDTO));
    }

    @Test
    void shouldThrowExceptionWhenSaveCvEndpointIsCalled() {
        CvDTO cvDTO = getCvDTO();
        doThrow(CVServiceException.class).when(cvService).saveCv(eq(TEST_USERNAME), eq(cvDTO));
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        assertThrows(CVServiceException.class, () -> cvController.updateCv(cvDTO, authentication));
        verify(cvService, times(1)).saveCv(eq(TEST_USERNAME), eq(cvDTO));
    }

    @Test
    void shouldDeleteCvWhenDeleteCvEndpointIsCalled() {
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        cvController.deleteCv(authentication);
        verify(cvService, times(1)).deleteCv(eq(TEST_USERNAME));
    }

    @Test
    void shouldGetCvWhenGetEndpointIsCalled() {
        CvDTO expectedCv = getCvDTO();
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        when(cvService.getCv(eq(TEST_USERNAME))).thenReturn(expectedCv);
        CvDTO cv = cvController.getCv(authentication);
        verify(cvService, times(1)).getCv(eq(TEST_USERNAME));
        assertEquals(expectedCv, cv);
    }

    @Test
    void shouldThrowExceptionWhenGetCvEndpointIsCalled() {
        doThrow(CVServiceException.class).when(cvService).getCv(eq(TEST_USERNAME));
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        assertThrows(CVServiceException.class, () -> cvController.getCv(authentication));
        verify(cvService, times(1)).getCv(eq(TEST_USERNAME));
    }

    private CvDTO getCvDTO() {
        return CvDTO.builder().build();
    }
}