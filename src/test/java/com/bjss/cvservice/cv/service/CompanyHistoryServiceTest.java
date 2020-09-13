package com.bjss.cvservice.cv.service;

import com.bjss.cvservice.cv.entity.dao.CompanyHistory;
import com.bjss.cvservice.cv.entity.dao.Cv;
import com.bjss.cvservice.cv.entity.dto.CompanyHistoryDTO;
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

class CompanyHistoryServiceTest {
    private static final String TEST_USERNAME = "testUsername";
    private static final String TEST_NAME = "testName";
    private static final String TEST_ID = "0L";

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    private CompanyHistoryService companyHistoryService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.companyHistoryService =
                new CompanyHistoryService(userRepository, userService);
    }

    @Test
    void shouldAddCompanyHistoryWhenAddMethodCalled() {
        List<CompanyHistoryDTO> companyHistoryDTOList = Collections
                .singletonList(getCompanyHistoryDTO());
        User user = getUserDAO(companyHistoryDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        companyHistoryService.addCompanyHistory(TEST_USERNAME, companyHistoryDTOList);
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldThrowExceptionWhenAddMethodCalled() {
        List<CompanyHistoryDTO> companyHistoryDTOList = Collections
                .singletonList(getCompanyHistoryDTO());
        User user = getUserDAO(companyHistoryDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        doThrow(RuntimeException.class).when(userRepository).save(eq(user));
        assertThrows(CVServiceException.class, () -> companyHistoryService
                .addCompanyHistory(TEST_USERNAME, companyHistoryDTOList));
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldGetCompanyHistoryWhenGetMethodCalled() {
        List<CompanyHistoryDTO> companyHistoryDTOList = Collections
                .singletonList(getCompanyHistoryDTO());
        User user = getUserDAO(companyHistoryDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        companyHistoryService.getCompanyHistory(TEST_USERNAME);
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldGetEmptyListWhenGetMethodCalled() {
        List<CompanyHistoryDTO> additionalInformationDTOList = Collections
                .singletonList(getCompanyHistoryDTO());
        User user = User.builder().build();
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        assertTrue(companyHistoryService.getCompanyHistory(TEST_USERNAME).isEmpty());
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldDeleteCompanyHistoryWhenDeleteMethodCalled() {
        CompanyHistoryDTO companyHistoryDTO = getCompanyHistoryDTO();
        List<CompanyHistoryDTO> companyHistoryDTOList = Collections
                .singletonList(companyHistoryDTO);
        User user = getUserDAO(companyHistoryDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        companyHistoryService.deleteCompanyHistory(TEST_USERNAME, TEST_NAME);
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldThrowExceptionWhenDeleteMethodCalled() {
        CompanyHistoryDTO companyHistoryDTO = getCompanyHistoryDTO();
        List<CompanyHistoryDTO> companyHistoryDTOList = Collections
                .singletonList(companyHistoryDTO);
        User user = getUserDAO(companyHistoryDTOList);
        CompanyHistory companyHistory = user.getCv().getCompanyHistoryList().get(0);
        companyHistory.setCompanyName("test");
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        assertThrows(CVServiceException.class, () -> companyHistoryService.deleteCompanyHistory(TEST_USERNAME, TEST_NAME));
        verify(userRepository, times(0)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldSaveCompanyHistoryWhenSaveMethodCalled() {
        List<CompanyHistoryDTO> companyHistoryDTOList = Collections
                .singletonList(getCompanyHistoryDTO());
        User user = getUserDAO(companyHistoryDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        companyHistoryService.saveCompanyHistory(TEST_USERNAME, companyHistoryDTOList);
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldThrowExceptionWhenSaveMethodCalled() {
        List<CompanyHistoryDTO> companyHistoryDTOList = Collections
                .singletonList(getCompanyHistoryDTO());
        User user = getUserDAO(companyHistoryDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        doThrow(RuntimeException.class).when(userRepository).save(eq(user));
        assertThrows(CVServiceException.class, () -> companyHistoryService
                .saveCompanyHistory(TEST_USERNAME, companyHistoryDTOList));
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    private CompanyHistoryDTO getCompanyHistoryDTO() {
        return CompanyHistoryDTO.builder()
                .companyName(TEST_NAME)
                .build();
    }

    private User getUserDAO(List<CompanyHistoryDTO> companyHistoryDTO) {
        User user = User.builder().build();
        Cv cv = Cv.builder()
                .companyHistoryList(companyHistoryDTO.stream()
                        .map(CompanyHistory::fromDTO)
                        .collect(Collectors.toList()))
                .additionalInformationList(Collections.emptyList())
                .skills(Collections.emptyList())
                .build();
        cv.setId(TEST_ID);
        user.setCv(cv);
        return user;
    }
}