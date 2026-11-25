package org.springframework.samples.petclinic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateVoteException extends RuntimeException {

    private static final long serialVersionUID = -4526917788919980784L;

    public DuplicateVoteException(String message) {
        super(message);
    }
}
