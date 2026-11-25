package org.springframework.samples.petclinic.booking;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Concurrent booking detected")
public class ConcurrentBookingException extends RuntimeException {

    public ConcurrentBookingException() {
        super("Concurrent booking detected. Please try again.");
    }
    public ConcurrentBookingException(String message) {
        super(message);
    }

}
