package com.bjss.cvservice.cv.service;

import com.bjss.cvservice.cv.entity.dao.AdditionalInformation;
import com.bjss.cvservice.cv.entity.dao.Cv;
import com.bjss.cvservice.cv.entity.dto.AdditionalInformationDTO;
import com.bjss.cvservice.cv.repository.AdditionalInformationRepository;
import com.bjss.cvservice.cv.repository.CvRepository;
import com.bjss.cvservice.exception.CVServiceException;
import com.bjss.cvservice.users.entity.User;
import com.bjss.cvservice.users.repository.UserRepository;
import com.bjss.cvservice.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AdditionalInformationServiceTest {
    private static final String TEST_USERNAME = "testUsername";
    private static final String TEST_NAME = "testName";
    private static final String TEST_ID = "0L";

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    private AdditionalInformationService additionalInformationService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.additionalInformationService =
                new AdditionalInformationService(userRepository, userService);
    }

    @Test
    void shouldAddAdditionalInformationWhenAddMethodCalled() {
        List<AdditionalInformationDTO> additionalInformationDTOList = Collections
                .singletonList(getAdditionalInformationDTO());
        User user = getUserDAO(additionalInformationDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        additionalInformationService.addAdditionalInformation(TEST_USERNAME, additionalInformationDTOList);
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldThrowExceptionWhenAddMethodCalled() {
        List<AdditionalInformationDTO> additionalInformationDTOList = Collections
                .singletonList(getAdditionalInformationDTO());
        User user = getUserDAO(additionalInformationDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        doThrow(RuntimeException.class).when(userRepository).save(eq(user));
        assertThrows(CVServiceException.class, () -> additionalInformationService
                .addAdditionalInformation(TEST_USERNAME, additionalInformationDTOList));
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldGetAdditionalInformationWhenGetMethodCalled() {
        List<AdditionalInformationDTO> additionalInformationDTOList = Collections
                .singletonList(getAdditionalInformationDTO());
        User user = getUserDAO(additionalInformationDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        additionalInformationService.getAdditionalInformation(TEST_USERNAME);
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldGetEmptyListWhenGetMethodCalled() {
        List<AdditionalInformationDTO> additionalInformationDTOList = Collections
                .singletonList(getAdditionalInformationDTO());
        User user = User.builder().build();
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        assertTrue(additionalInformationService.getAdditionalInformation(TEST_USERNAME).isEmpty());
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldDeleteAdditionalInformationWhenDeleteMethodCalled() {
        AdditionalInformationDTO additionalInformationDTO = getAdditionalInformationDTO();
        List<AdditionalInformationDTO> additionalInformationDTOList = Collections
                .singletonList(additionalInformationDTO);
        User user = getUserDAO(additionalInformationDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        additionalInformationService.deleteAdditionalInformation(TEST_USERNAME, TEST_NAME);
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldThrowExceptionWhenDeleteMethodCalled() {
        AdditionalInformationDTO additionalInformationDTO = getAdditionalInformationDTO();
        List<AdditionalInformationDTO> additionalInformationDTOList = Collections
                .singletonList(additionalInformationDTO);
        User user = getUserDAO(additionalInformationDTOList);
        AdditionalInformation additionalInformation = user.getCv().getAdditionalInformationList().get(0);
        additionalInformation.setName("test");
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        assertThrows(CVServiceException.class, () -> additionalInformationService.deleteAdditionalInformation(TEST_USERNAME, TEST_NAME));
        verify(userRepository, times(0)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldSaveAdditionalInformationWhenSaveMethodCalled() {
        List<AdditionalInformationDTO> additionalInformationDTOList = Collections
                .singletonList(getAdditionalInformationDTO());
        User user = getUserDAO(additionalInformationDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        additionalInformationService.saveAdditionalInformation(TEST_USERNAME, additionalInformationDTOList);
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldThrowExceptionWhenSaveMethodCalled() {
        List<AdditionalInformationDTO> additionalInformationDTOList = Collections
                .singletonList(getAdditionalInformationDTO());
        User user = getUserDAO(additionalInformationDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        doThrow(RuntimeException.class).when(userRepository).save(eq(user));
        assertThrows(CVServiceException.class, () -> additionalInformationService
                .saveAdditionalInformation(TEST_USERNAME, additionalInformationDTOList));
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    private AdditionalInformationDTO getAdditionalInformationDTO() {
        return AdditionalInformationDTO.builder()
                .name(TEST_NAME)
                .type(AdditionalInformationDTO.Type.COURSE)
                .build();
    }

    private User getUserDAO(List<AdditionalInformationDTO> additionalInformationDTO) {
        User user = User.builder().build();
        Cv cv = Cv.builder()
                .additionalInformationList(additionalInformationDTO.stream()
                        .map(AdditionalInformation::fromDTO)
                        .collect(Collectors.toList()))
                .companyHistoryList(Collections.emptyList())
                .skills(Collections.emptyList())
                .build();
        cv.setId(TEST_ID);
        user.setCv(cv);
        return user;
    }
}