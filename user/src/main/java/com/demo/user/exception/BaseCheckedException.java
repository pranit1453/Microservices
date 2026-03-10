package com.demo.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseCheckedException extends Exception {

    private final HttpStatus status;

    public BaseCheckedException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}