package com.demo.user.exception.custom;

import com.demo.user.exception.BaseException;
import org.springframework.http.HttpStatus;

public class ExternalServiceException extends BaseException {
    public ExternalServiceException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
