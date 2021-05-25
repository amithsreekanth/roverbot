package com.imamyth.roverbot.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.imamyth.roverbot.dto.RoverInput;
import com.imamyth.roverbot.dto.RoverOutput;
import com.imamyth.roverbot.exception.ExceptionMessageDTO;
import com.imamyth.roverbot.exception.InvalidRoverInputException;
import com.imamyth.roverbot.service.RoverService;

@RestController
@RequestMapping("/api/v1")
public class RoverController {
	
	@Autowired
	private RoverService roverService;
	
	@PostMapping("rover/move")
	public ResponseEntity<RoverOutput> moveRover(@Valid @RequestBody RoverInput roverInput) throws JsonGenerationException, JsonMappingException, IOException {
		
		RoverOutput roverOutput = roverService.moveRover(roverInput);
		
		return ResponseEntity.ok(roverOutput);
		
	}
	
	@GetMapping("rover")
	public ResponseEntity<RoverOutput> getRoverPosition() throws JsonGenerationException, JsonMappingException, IOException {
		
		RoverOutput roverOutput = roverService.getRoverPosition();
		
		return ResponseEntity.ok(roverOutput);
		
	}
	
	@ExceptionHandler(InvalidRoverInputException.class)
    public final ResponseEntity<ExceptionMessageDTO> handleInvalidInputExceptions(InvalidRoverInputException ex) {
        return new ResponseEntity<>(new ExceptionMessageDTO(ex), HttpStatus.NOT_FOUND);
    }

	@ExceptionHandler(JsonGenerationException.class)
    public final ResponseEntity<ExceptionMessageDTO> handleJsonGenerationExceptions(JsonGenerationException ex) {
        return new ResponseEntity<>(new ExceptionMessageDTO(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(JsonMappingException.class)
    public final ResponseEntity<ExceptionMessageDTO> handleJsonMappingExceptions(JsonMappingException ex) {
        return new ResponseEntity<>(new ExceptionMessageDTO(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(IOException.class)
    public final ResponseEntity<ExceptionMessageDTO> handleIOExceptions(IOException ex) {
        return new ResponseEntity<>(new ExceptionMessageDTO(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	@ExceptionHandler(FileNotFoundException.class)
    public final ResponseEntity<ExceptionMessageDTO> handleIOExceptions(FileNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionMessageDTO(ex), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionMessageDTO> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
		 return new ResponseEntity<>(new ExceptionMessageDTO(ex), HttpStatus.BAD_REQUEST);
	}
}
