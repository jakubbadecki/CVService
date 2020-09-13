package com.bjss.cvservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class AuthEntryPoint extends BasicAuthenticationEntryPoint {

    public static final String CVSERVICE_REALM = "CVSERVICE REALM";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        log.error(e.getMessage());
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName(CVSERVICE_REALM);
        super.afterPropertiesSet();
    }
}