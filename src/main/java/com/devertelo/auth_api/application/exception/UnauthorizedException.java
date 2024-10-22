package com.devertelo.auth_api.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;

public class UnauthorizedException extends HttpClientErrorException {

    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public UnauthorizedException(String message, String error) {
        super(HttpStatus.UNAUTHORIZED, message, error.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }
}
