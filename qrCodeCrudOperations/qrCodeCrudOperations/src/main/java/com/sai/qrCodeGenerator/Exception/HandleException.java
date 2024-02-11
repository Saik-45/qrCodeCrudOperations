package com.sai.qrCodeGenerator.Exception;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HandleException extends RuntimeException {

    public HandleException(String message) {
        super(message);
    }
}
