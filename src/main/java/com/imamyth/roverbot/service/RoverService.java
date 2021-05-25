package com.imamyth.roverbot.service;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.imamyth.roverbot.dto.RoverInput;
import com.imamyth.roverbot.dto.RoverOutput;

public interface RoverService {

	RoverOutput moveRover(RoverInput roverInput) throws JsonGenerationException, JsonMappingException, IOException;

	RoverOutput getRoverPosition() throws FileNotFoundException, IOException;

}
