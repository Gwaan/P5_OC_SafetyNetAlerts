package com.safetynet.alerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Already existing exception.
 *
 * @author Gwen
 * @version 1.0
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistingException extends RuntimeException {


    /**
     * Instantiates a new Already existing exception.
     *
     * @param s Error message to display
     */
    public AlreadyExistingException(String s) {
        super(s);
    }
}
