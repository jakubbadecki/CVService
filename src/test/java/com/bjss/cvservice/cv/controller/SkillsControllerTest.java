package com.bjss.cvservice.cv.controller;

import com.bjss.cvservice.cv.entity.dto.SkillDTO;
import com.bjss.cvservice.cv.service.SkillsService;
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

class SkillsControllerTest {
    private static final String TEST_NAME = "testName";
    private static final String TEST_USERNAME = "testUsername";

    @Mock
    private SkillsService skillsService;
    private SkillsController skillsController;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.skillsController = new SkillsController(skillsService);
    }

    @Test
    void shouldCreateSkillsListWhenSaveEndpointIsCalled() {
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        List<SkillDTO> skillsDTOList = Collections
                .singletonList(getSkillsDTO());
        skillsController.saveSkills(skillsDTOList, authentication);
        verify(skillsService, times(1))
                .saveSkills(eq(TEST_USERNAME), eq(skillsDTOList));
    }

    @Test
    void shouldThrowExceptionWhenSaveSkillsEndpointIsCalled() {
        List<SkillDTO> skillsDTOList = Collections
                .singletonList(getSkillsDTO());
        doThrow(CVServiceException.class)
                .when(skillsService)
                .saveSkills(eq(TEST_USERNAME), eq(skillsDTOList));
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        assertThrows(CVServiceException.class, () -> skillsController
                .saveSkills(skillsDTOList, authentication));
        verify(skillsService, times(1))
                .saveSkills(eq(TEST_USERNAME), eq(skillsDTOList));
    }

    @Test
    void shouldDeleteSkillsWhenDeleteSkillsEndpointIsCalled() {
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        skillsController.deleteSkill(TEST_NAME, authentication);
        verify(skillsService, times(1))
                .deleteSkill(eq(TEST_USERNAME), eq(TEST_NAME));
    }

    @Test
    void shouldGetSkillsWhenGetEndpointIsCalled() {
        List<SkillDTO> skillsDTOList = Collections.singletonList(getSkillsDTO());
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        when(skillsService.getSkills(eq(TEST_USERNAME)))
                .thenReturn(skillsDTOList);
        List<SkillDTO> skillsList = skillsController.getSkills(authentication);
        verify(skillsService, times(1)).getSkills(eq(TEST_USERNAME));
        assertEquals(skillsDTOList, skillsList);
    }

    @Test
    void shouldThrowExceptionWhenGetSkillsEndpointIsCalled() {
        doThrow(CVServiceException.class)
                .when(skillsService)
                .getSkills(eq(TEST_USERNAME));
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        assertThrows(CVServiceException.class, () -> skillsController
                .getSkills(authentication));
        verify(skillsService, times(1))
                .getSkills(eq(TEST_USERNAME));
    }

    @Test
    void shouldAddSkillsWhenAddEndpointIsCalled() {
        List<SkillDTO> skillsDTOList = Collections.singletonList(getSkillsDTO());
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        skillsController
                .addSkills(skillsDTOList, authentication);
        verify(skillsService, times(1))
                .addSkills(eq(TEST_USERNAME), eq(skillsDTOList));
    }

    @Test
    void shouldThrowExceptionWhenAddSkillsEndpointIsCalled() {
        List<SkillDTO> skillsDTOList = Collections.singletonList(getSkillsDTO());
        doThrow(CVServiceException.class)
                .when(skillsService)
                .addSkills(eq(TEST_USERNAME), eq(skillsDTOList));
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        assertThrows(CVServiceException.class, () -> skillsController
                .addSkills(skillsDTOList, authentication));
        verify(skillsService, times(1))
                .addSkills(eq(TEST_USERNAME), eq(skillsDTOList));
    }

    private SkillDTO getSkillsDTO() {
        return SkillDTO.builder().build();
    }
}