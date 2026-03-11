package com.demo.user.exception.custom;

import com.demo.user.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserAlreadyDeactivatedException extends BaseException {
    public UserAlreadyDeactivatedException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
