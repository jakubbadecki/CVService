package com.bjss.cvservice.config;

import com.bjss.cvservice.users.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class WebSecurityConfigTest {

    @Mock
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AuthEntryPoint authEntryPoint;

    private WebSecurityConfig webSecurityConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        webSecurityConfig = new WebSecurityConfig(userService, passwordEncoder, authEntryPoint);
    }

    @Test
    void shouldReturnDaoAuthenticationProviderWhenAuthenticationProviderIsCalled() {
        AuthenticationProvider authenticationProvider = webSecurityConfig.authenticationProvider();
        assertEquals(DaoAuthenticationProvider.class, authenticationProvider.getClass());
    }

    @Test
    void shouldAddAuthenticationProviderToAuthManagerBuilderWhenConfigureIsCalled() {
        AuthenticationManagerBuilder mockAuthenticationManagerBuilder = mock(AuthenticationManagerBuilder.class);
        webSecurityConfig.configure(mockAuthenticationManagerBuilder);
        verify(mockAuthenticationManagerBuilder, times(1))
                .authenticationProvider(any(AuthenticationProvider.class));
    }
}