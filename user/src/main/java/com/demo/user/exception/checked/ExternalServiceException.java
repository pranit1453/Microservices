package com.demo.user.exception.checked;

import com.demo.user.exception.BaseCheckedException;
import org.springframework.http.HttpStatus;

public class ExternalServiceException extends BaseCheckedException {
    public ExternalServiceException(String message) {
        super(message, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
