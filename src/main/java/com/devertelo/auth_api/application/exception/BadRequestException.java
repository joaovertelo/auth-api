package com.devertelo.auth_api.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;

public class BadRequestException extends HttpClientErrorException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public BadRequestException(String message, String error) {
        super(HttpStatus.BAD_REQUEST, message, error.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }
}
