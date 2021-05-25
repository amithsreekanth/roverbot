package com.imamyth.roverbot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid input")
@NoArgsConstructor
public class InvalidRoverInputException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidRoverInputException(String message) {
		super(message);
	}
	
	

}
