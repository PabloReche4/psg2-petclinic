package org.springframework.samples.petclinic.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

/**
 * Thrown when a clinic owner tries to change a clinic's plan to one whose
 * usage limits are already exceeded by the clinic's owners (too many pets or
 * too many monthly visits).
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class PlanDowngradeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PlanDowngradeException(String message) {
		super(message);
	}
}