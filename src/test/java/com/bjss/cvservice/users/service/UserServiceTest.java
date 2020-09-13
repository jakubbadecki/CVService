package com.bjss.cvservice.users.service;

import com.bjss.cvservice.exception.CVServiceException;
import com.bjss.cvservice.users.entity.User;
import com.bjss.cvservice.users.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private static final String TEST_PASSWORD = "testPassword";
    private static final String TEST_USERNAME = "testUsername";
    private static final String ENCODED_PASSWORD = "encodedPassword";

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.userService = new UserService(userRepository, bCryptPasswordEncoder);
    }

    @Test
    void shouldCreateUserWhenCreateUserMethodCalled() {

        User user = User.builder()
                .role(User.Role.USER)
                .username(TEST_USERNAME)
                .password(ENCODED_PASSWORD)
                .build();
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(bCryptPasswordEncoder.encode(eq(TEST_PASSWORD))).thenReturn(ENCODED_PASSWORD);

        userService.createUser(TEST_USERNAME, TEST_PASSWORD);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldDeleteUserWhenDeleteUserMethodCalled() {

        User user = User.builder()
                .role(User.Role.USER)
                .username(TEST_USERNAME)
                .password(ENCODED_PASSWORD)
                .build();
        when(userRepository.findByUsername(eq(TEST_USERNAME))).thenReturn(Optional.of(user));

        userService.deleteUser(TEST_USERNAME);

        verify(userRepository, times(1)).findByUsername(eq(TEST_USERNAME));
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findByUsername(eq(TEST_USERNAME))).thenReturn(Optional.empty());
        assertThrows(CVServiceException.class, () -> userService.deleteUser(TEST_USERNAME));
        verify(userRepository, times(1)).findByUsername(eq(TEST_USERNAME));
    }
}