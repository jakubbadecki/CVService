package com.bjss.cvservice.config;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AuthEntryPointTest {
    private final AuthEntryPoint authEntryPoint = new AuthEntryPoint();

    @Test
    void shouldSendErrorWithErrorMessageWhenBasicAuthCommenceIsCalled() throws IOException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        AuthenticationException exception = Mockito.mock(AuthenticationException.class);

        authEntryPoint.commence(request, response, exception);

        verify(response, times(1))
                .sendError(eq(HttpStatus.UNAUTHORIZED.value()), eq(HttpStatus.UNAUTHORIZED.getReasonPhrase()));
        verify(exception, times(1)).getMessage();
    }

    @Test
    void shouldSetUpRealmNameWhenAfterPropertiesSetCalled() {
        authEntryPoint.afterPropertiesSet();
        assertEquals(AuthEntryPoint.CVSERVICE_REALM, authEntryPoint.getRealmName());
    }
}