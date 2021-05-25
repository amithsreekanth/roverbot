package com.imamyth.roverbot.dto;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoverInput {

	@Valid
	private Position position;
	
	@NotNull
	private List<Map<String, Integer>> moves;
	
}
