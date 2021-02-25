package com.safetynet.alerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistingException extends RuntimeException {

    public AlreadyExistingException() {

    }

    public AlreadyExistingException(String s) {
        super(s);
    }
}