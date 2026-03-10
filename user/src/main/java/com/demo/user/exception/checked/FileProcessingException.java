package com.demo.user.exception.checked;

import com.demo.user.exception.BaseCheckedException;
import org.springframework.http.HttpStatus;

public class FileProcessingException extends BaseCheckedException {
    public FileProcessingException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
