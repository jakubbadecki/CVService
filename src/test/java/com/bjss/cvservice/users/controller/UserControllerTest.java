package com.bjss.cvservice.users.controller;

import com.bjss.cvservice.exception.CVServiceException;
import com.bjss.cvservice.users.entity.User;
import com.bjss.cvservice.users.entity.UserDTO;
import com.bjss.cvservice.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserControllerTest {
    private static final String TEST_USERNAME = "testUsername";
    private static final String TEST_PASSWORD = "testPassword";

    @Mock
    private UserService userService;
    private UserController userController;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.userController = new UserController(userService);
    }

    @Test
    void shouldCreateUserWhenSaveEndpointIsCalled() {
        userController.createUser(getUserDTO());
        verify(userService, times(1)).createUser(eq(TEST_USERNAME), eq(TEST_PASSWORD));
    }

    @Test
    void shouldThrowExceptionWhenSaveEndpointIsCalled() {
        UserDTO userDTO = getUserDTO();
        doThrow(DataIntegrityViolationException.class)
                .when(userService).createUser(eq(TEST_USERNAME), eq(TEST_PASSWORD));
        assertThrows(CVServiceException.class, () -> userController.createUser(userDTO));
        verify(userService, times(1)).createUser(eq(TEST_USERNAME), eq(TEST_PASSWORD));
    }

    @Test
    void shouldDeleteUserWhenDeleteEndpointIsCalled() {
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        userController.deleteUser(authentication);
        verify(userService, times(1)).deleteUser(eq(TEST_USERNAME));
    }

    @Test
    void shouldGetUserWhenGetEndpointIsCalled() {
        when(authentication.getName()).thenReturn(TEST_USERNAME);
        when(userService.loadUserByUsername(eq(TEST_USERNAME))).thenReturn(getUserDAO());
        UserDTO user = userController.getUser(authentication);
        verify(userService, times(1)).loadUserByUsername(eq(TEST_USERNAME));
        assertEquals(getUserDTO(), user);
    }

    private UserDTO getUserDTO() {
        return UserDTO.builder()
                .username(TEST_USERNAME)
                .password(TEST_PASSWORD)
                .build();
    }

    private User getUserDAO() {
        return User.builder()
                .username(TEST_USERNAME)
                .password(TEST_PASSWORD)
                .build();
    }
}