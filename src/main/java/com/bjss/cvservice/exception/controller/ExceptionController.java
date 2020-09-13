package com.bjss.cvservice.exception.controller;

import com.bjss.cvservice.exception.CVServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@Slf4j
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = CVServiceException.class)
    public ResponseEntity<Object> cvServiceException(CVServiceException exception) {
        String exceptionMessage = exception.getMessage();
        log.error("Exception: {}", exceptionMessage);
        return new ResponseEntity<>(
                MessageFormat.format("Exception: {0}", exceptionMessage),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<Object> bindException(BindException exception) {
        log.error("Exception: {}", exception);
        return new ResponseEntity<>(
                MessageFormat.format("Exception: {0}", exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> runtimeException(RuntimeException exception) {
        log.error("Exception: {}", exception);
        return new ResponseEntity<>(
                MessageFormat.format("Exception: {0}", exception.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
