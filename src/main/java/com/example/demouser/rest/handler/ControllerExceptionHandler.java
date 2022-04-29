package com.example.demouser.rest.handler;

import com.example.demouser.exception.EntityNotValidException;
import com.example.demouser.exception.PasswordException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> responseBody = new HashMap<>();

        responseBody.put("timestamp", Instant.now());
        responseBody.put("status", status.value());
        responseBody.put("error", status.getReasonPhrase());
        responseBody.put("massage", ex.getMessage());

        return ResponseEntity.status(status).body(responseBody);
    }

    @ExceptionHandler(Exception.class) // handle all not control api exception
    public ResponseEntity<Object> handleInternalServerError(Exception e, WebRequest request) {
        e.printStackTrace();
        return handleExceptionInternal(e, null, null, INTERNAL_SERVER_ERROR, request);
    }

    // handle custom api exceptions
    @ExceptionHandler(EntityNotValidException.class)
    public ResponseEntity<Object> handleEntityNotValidException(EntityNotValidException e, WebRequest request) {
        return handleExceptionInternal(e, null, null, BAD_REQUEST, request);
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<Object> handlePasswordException(PasswordException e, WebRequest request) {
        return handleExceptionInternal(e, null, null, UNAUTHORIZED, request);
    }

}
