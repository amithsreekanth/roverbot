package com.imamyth.roverbot.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExceptionMessageDTO {
	private String message;
	private String exceptionClassName;
	
	public ExceptionMessageDTO(Throwable ex) {
		message = ex.getMessage();
		exceptionClassName = ex.getClass().getName();
	}
}
