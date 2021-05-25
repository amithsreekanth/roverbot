package com.imamyth.roverbot.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Position {

	// Another way is using Enum. Not using it due to time constraint
	@NotEmpty(message = "direction cannot be null")
	@Pattern(regexp="^(N|E|W|S)$", message="Not a valid direction")
	private String direction;
	@NotNull(message = "x cordinate cannot be null")
	private Integer x;
	@NotNull(message = "y cordinate cannot be null")
	private Integer y;
	
}
