package com.demo.user.exception.custom;

import com.demo.user.exception.BaseException;
import org.springframework.http.HttpStatus;

public class UserServiceException extends BaseException {
    public UserServiceException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
