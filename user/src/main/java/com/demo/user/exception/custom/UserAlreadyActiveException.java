package com.demo.user.exception.custom;

import com.demo.user.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserAlreadyActiveException extends BaseException {
    public UserAlreadyActiveException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
