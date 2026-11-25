package org.springframework.samples.petclinic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidImageException extends RuntimeException {

    private static final long serialVersionUID = -2460825321908429064L;

    public InvalidImageException(String message) {
        super(message);
    }
}
