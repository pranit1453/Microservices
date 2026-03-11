package com.demo.user.exception.custom;

import com.demo.user.exception.BaseException;
import org.springframework.http.HttpStatus;

public class FileProcessingException extends BaseException {
    public FileProcessingException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
