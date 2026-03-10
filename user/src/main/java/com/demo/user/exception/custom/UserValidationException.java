package com.demo.user.exception.custom;

import com.demo.user.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserValidationException extends BaseException {
    public UserValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
