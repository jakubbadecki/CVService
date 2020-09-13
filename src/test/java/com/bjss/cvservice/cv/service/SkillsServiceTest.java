package com.bjss.cvservice.cv.service;

import com.bjss.cvservice.cv.entity.dao.Cv;
import com.bjss.cvservice.cv.entity.dao.Skill;
import com.bjss.cvservice.cv.entity.dto.SkillDTO;
import com.bjss.cvservice.cv.repository.CvRepository;
import com.bjss.cvservice.cv.repository.SkillsRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SkillsServiceTest {
    private static final String TEST_USERNAME = "testUsername";
    private static final String TEST_NAME = "testName";
    private static final String TEST_ID = "0L";
    private static final String TEST_DESCRIPTION = "testDescription";

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    private SkillsService skillsService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.skillsService =
                new SkillsService(userRepository, userService);
    }

    @Test
    void shouldGetSkillsWhenGetMethodCalled() {
        List<SkillDTO> companyHistoryDTOList = Collections
                .singletonList(getSkillsDTO());
        User user = getUserDAO(companyHistoryDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        skillsService.getSkills(TEST_USERNAME);
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldGetEmptyListWhenGetMethodCalled() {
        List<SkillDTO> additionalInformationDTOList = Collections
                .singletonList(getSkillsDTO());
        User user = User.builder().build();
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        assertTrue(skillsService.getSkills(TEST_USERNAME).isEmpty());
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldAddSkillsWhenAddMethodCalled() {
        List<SkillDTO> skillsDTOList = Collections
                .singletonList(getSkillsDTO());
        User user = getUserDAO(skillsDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        skillsService.addSkills(TEST_USERNAME, skillsDTOList);
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldThrowExceptionWhenAddMethodCalled() {
        List<SkillDTO> skillsDTOList = Collections
                .singletonList(getSkillsDTO());
        User user = getUserDAO(skillsDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        doThrow(RuntimeException.class).when(userRepository).save(eq(user));
        assertThrows(CVServiceException.class, () -> skillsService
                .addSkills(TEST_USERNAME, skillsDTOList));
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldDeleteSkillsWhenDeleteMethodCalled() {
        SkillDTO skillsDTO = getSkillsDTO();
        List<SkillDTO> skillsDTOList = Collections
                .singletonList(skillsDTO);
        User user = getUserDAO(skillsDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        skillsService.deleteSkill(TEST_USERNAME, TEST_NAME);
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldThrowExceptionWhenDeleteMethodCalled() {
        SkillDTO skillsDTO = getSkillsDTO();
        List<SkillDTO> skillsDTOList = Collections
                .singletonList(skillsDTO);
        User user = getUserDAO(skillsDTOList);
        Skill skills = user.getCv().getSkills().get(0);
        skills.setId("2L");
        skills.setName("test");
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        assertThrows(CVServiceException.class, () -> skillsService.deleteSkill(TEST_USERNAME, TEST_NAME));
        verify(userRepository, times(0)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldSaveSkillsWhenSaveMethodCalled() {
        List<SkillDTO> skillsDTOList = Collections
                .singletonList(getSkillsDTO());
        User user = getUserDAO(skillsDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        skillsService.saveSkills(TEST_USERNAME, skillsDTOList);
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    @Test
    void shouldThrowExceptionWhenSaveMethodCalled() {
        List<SkillDTO> skillsDTOList = Collections
                .singletonList(getSkillsDTO());
        User user = getUserDAO(skillsDTOList);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(user);
        doThrow(RuntimeException.class).when(userRepository).save(eq(user));
        assertThrows(CVServiceException.class, () -> skillsService
                .saveSkills(TEST_USERNAME, skillsDTOList));
        verify(userRepository, times(1)).save(eq(user));
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
    }

    private SkillDTO getSkillsDTO() {
        return SkillDTO.builder()
                .name(TEST_NAME)
                .description(TEST_DESCRIPTION)
                .build();
    }

    private User getUserDAO(List<SkillDTO> skillsDTO) {
        User user = User.builder().build();
        Cv cv = Cv.builder()
                .skills(skillsDTO.stream()
                        .map(Skill::fromDTO)
                        .collect(Collectors.toList()))
                .companyHistoryList(Collections.emptyList())
                .additionalInformationList(Collections.emptyList())
                .build();
        cv.setId(TEST_ID);
        user.setCv(cv);
        return user;
    }
}