package com.bjss.cvservice.cv.service;

import com.bjss.cvservice.cv.entity.dao.Cv;
import com.bjss.cvservice.cv.entity.dto.AddressDTO;
import com.bjss.cvservice.cv.entity.dto.CvDTO;
import com.bjss.cvservice.cv.entity.dto.ProfileDTO;
import com.bjss.cvservice.exception.CVServiceException;
import com.bjss.cvservice.users.entity.User;
import com.bjss.cvservice.users.repository.UserRepository;
import com.bjss.cvservice.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CvServiceTest {
    private static final String TEST_USERNAME = "testUsername";
    private static final String TEST_ID = "0L";

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    private CvService cvService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.cvService =
                new CvService(userRepository, userService);
    }

    @Test
    void shouldSaveCvWhenSaveMethodCalled() {
        CvDTO cvDTO = getCvDTO();
        User user = getUserDAO(cvDTO);
        Cv cv = user.getCv();
        cv.setId(null);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        cvService.saveCv(TEST_USERNAME, cvDTO);
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldThrowExceptionWhenSaveMethodCalled() {
        CvDTO cvDTO = getCvDTO();
        User user = getUserDAO(cvDTO);
        Cv cv = user.getCv();
        cv.setId(null);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        doThrow(RuntimeException.class).when(userRepository).save(eq(user));
        assertThrows(CVServiceException.class, () -> cvService.saveCv(TEST_USERNAME, cvDTO));
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldGetCvWhenGetMethodCalled() {
        CvDTO cvDTO = getCvDTO();
        User user = getUserDAO(cvDTO);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        when(userRepository.findById(eq(TEST_ID))).thenReturn(Optional.of(user));
        cvService.getCv(TEST_USERNAME);
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldThrowExceptionWhenGetMethodCalled() {
        CvDTO cvDTO = getCvDTO();
        User user = User.builder().build();
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        assertThrows(CVServiceException.class, () -> cvService.getCv(TEST_USERNAME));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldDeleteCvWhenDeleteMethodCalled() {
        CvDTO cvDTO = getCvDTO();
        User user = getUserDAO(cvDTO);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        cvService.deleteCv(TEST_USERNAME);
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldThrowExceptionWhenDeleteMethodCalled() {
        CvDTO cvDTO = getCvDTO();
        User user = getUserDAO(cvDTO);
        Cv cv = user.getCv();
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        doThrow(RuntimeException.class).when(userRepository).save(eq(user));
        assertThrows(CVServiceException.class, () -> cvService.deleteCv(TEST_USERNAME));
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    private CvDTO getCvDTO() {
        return CvDTO.builder()
                .profile(ProfileDTO.builder()
                        .address(AddressDTO.builder().build())
                        .build())
                .skills(Collections.emptyList())
                .companyHistoryList(Collections.emptyList())
                .additionalInformationList(Collections.emptyList())
                .build();
    }

    private User getUserDAO(CvDTO cvDTO) {
        User user = User.builder().build();
        Cv cv = Cv.fromDTO(cvDTO);
        cv.setId(TEST_ID);
        user.setCv(cv);
        return user;
    }
}