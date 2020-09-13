package com.bjss.cvservice.exception.controller;

import com.bjss.cvservice.exception.CVServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExceptionControllerTest {
    private static final String TEST_MESSAGE = "testMessage";

    private ExceptionController exceptionController = new ExceptionController();

    @Test
    void shouldReturnResponseEntityWhenCvServiceException() {
        ResponseEntity<Object> objectResponseEntity = exceptionController
                .cvServiceException(new CVServiceException(TEST_MESSAGE));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, objectResponseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(objectResponseEntity.getBody()).toString().endsWith(TEST_MESSAGE));
    }

    @Test
    void shouldReturnResponseEntityWhenCvBindException() {
        ResponseEntity<Object> objectResponseEntity = exceptionController
                .bindException(new BindException(new Object(), TEST_MESSAGE));
        assertEquals(HttpStatus.BAD_REQUEST, objectResponseEntity.getStatusCode());
    }

    @Test
    void shouldReturnResponseEntityWhenRuntimeException() {
        ResponseEntity<Object> objectResponseEntity = exceptionController
                .runtimeException(new CVServiceException(TEST_MESSAGE));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, objectResponseEntity.getStatusCode());
        assertTrue(Objects.requireNonNull(objectResponseEntity.getBody()).toString().endsWith(TEST_MESSAGE));
    }
}