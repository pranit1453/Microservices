package com.demo.user.exception.custom;

import com.demo.user.exception.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidUserDataException extends BaseException {
    public InvalidUserDataException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
