package com.safetynet.alerts.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Not found exception.
 *
 * @author Gwen
 * @version 1.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    /**
     * Instantiates a new Not found exception.
     *
     * @param s Error message to display
     */
    public NotFoundException(String s) {
        super(s);
    }
}
