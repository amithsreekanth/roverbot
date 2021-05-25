package com.imamyth.roverbot.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.imamyth.roverbot.constant.RoverConstants;
import com.imamyth.roverbot.dto.Position;
import com.imamyth.roverbot.dto.RoverInput;
import com.imamyth.roverbot.dto.RoverOutput;
import com.imamyth.roverbot.exception.InvalidRoverInputException;
import com.imamyth.roverbot.service.RoverService;
import com.imamyth.roverbot.utility.CompassUtility;

@Service
public class RoverServiceImpl implements RoverService {

	@Override
	public RoverOutput moveRover(RoverInput roverInput) throws JsonGenerationException, JsonMappingException, IOException {
		List<Map<String, Integer>> moves = roverInput.getMoves();
		Map<Integer, String> compassMap;
		Integer x = roverInput.getPosition().getX();
		Integer y = roverInput.getPosition().getY();
		int degree;
		String movement;
		String finalDirection = roverInput.getPosition().getDirection();
		
		for(Map<String, Integer> move: moves) {
			Set<String> moveSet = move.keySet();
			
			Optional<String> turnOptional = moveSet.stream().filter(m -> m.equals(RoverConstants.LEFT) || m.equals(RoverConstants.RIGHT)).findFirst();
			String turn = turnOptional.orElseThrow(() -> new InvalidRoverInputException("turn value not preset"));

			switch(turn) {
			case RoverConstants.LEFT :   compassMap = CompassUtility.getCompass(finalDirection, "L");
						degree = move.get(turn);
						finalDirection = compassMap.get(degree);
						movement = moveSet.stream().filter(m -> m.equals(RoverConstants.FORWARD) || m.equals(RoverConstants.BACKWARD)).findFirst().orElseThrow(() -> new InvalidRoverInputException("Movement value not preset"));
						
						if(RoverConstants.FORWARD.equals(movement)  && RoverConstants.WEST.equals(finalDirection)) {
							x=moveX(x, movement, move, false);
						}
						else if(RoverConstants.BACKWARD.equals(movement)  && RoverConstants.WEST.equals(finalDirection)) {
							x = moveX(x, movement, move, true);
						}
						else if(RoverConstants.FORWARD.equals(movement)  && RoverConstants.SOUTH.equals(finalDirection)) {
							y=moveY(y, movement, move, false);
						}
						else if(RoverConstants.BACKWARD.equals(movement)  && RoverConstants.SOUTH.equals(finalDirection)) {
							y = moveY(y, movement, move, true);
						}
						else if(RoverConstants.FORWARD.equals(movement)  && RoverConstants.EAST.equals(finalDirection)) {
							x = moveX(x, movement, move, true);
						}
						else if(RoverConstants.BACKWARD.equals(movement)  && RoverConstants.EAST.equals(finalDirection)) {
							x=moveX(x, movement, move, false);
						}
						else if(RoverConstants.FORWARD.equals(movement)  && RoverConstants.NORTH.equals(finalDirection)) {
							y = moveY(y, movement, move, true);
						}
						else {
							y=moveY(y, movement, move, false);
						}
						break;
						
			case RoverConstants.RIGHT :    compassMap = CompassUtility.getCompass(finalDirection, "R");
						degree = move.get(turn);
						finalDirection = compassMap.get(degree);
						movement = moveSet.stream().filter(m -> m.equals(RoverConstants.FORWARD) || m.equals(RoverConstants.BACKWARD)).findFirst().orElseThrow(() -> new InvalidRoverInputException("Movement value not preset"));
						
						if(RoverConstants.FORWARD.equals(movement) && RoverConstants.EAST.equals(finalDirection)) {
							x = moveX(x, movement, move, true);
						}
						else if(RoverConstants.BACKWARD.equals(movement) && RoverConstants.EAST.equals(finalDirection)) {
							x= moveX(x, movement, move, false);
						}
						else if(RoverConstants.FORWARD.equals(movement) && RoverConstants.SOUTH.equals(finalDirection)) {
							y = moveY(y, movement, move, false);
						}
						else if(RoverConstants.BACKWARD.equals(movement) && RoverConstants.SOUTH.equals(finalDirection)) {
							y = moveY(y, movement, move, true);
						}
						else if(RoverConstants.FORWARD.equals(movement) && RoverConstants.NORTH.equals(finalDirection)) {
							y = moveY(y, movement, move, true);
						}
						else if(RoverConstants.BACKWARD.equals(movement) && RoverConstants.NORTH.equals(finalDirection)) {
							y = moveY(y, movement, move, false);
						}
						else if(RoverConstants.FORWARD.equals(movement) && RoverConstants.WEST.equals(finalDirection)) {
							x=moveX(x, movement, move, false);
						}
						else {
							x = moveX(x, movement, move, true);
						}
						break;
				default: throw new InvalidRoverInputException();
			}
		}
		
		Position finalRoverPosition = new Position(finalDirection, x, y);
		RoverOutput roverOutput = new RoverOutput(finalRoverPosition);
		saveToXml(roverOutput);
		return roverOutput;
	}

	private Integer moveY(Integer y, String movement, Map<String, Integer> move, Boolean movDirection) {
		if(movDirection) {
			y=y+move.get(movement);
		}
		else {
			y=y-move.get(movement);
		}
		
		return y;
	}

	private Integer moveX(Integer x, String movement, Map<String, Integer> move, Boolean movDirection) {
		if(movDirection) {
			x=x+move.get(movement);
		}
		else {
			x=x-move.get(movement);
		}
		return x;
	}
	
	private void saveToXml(RoverOutput roverOutput) throws JsonGenerationException, JsonMappingException, IOException {
		XmlMapper xmlMapper = new XmlMapper();
		xmlMapper.writeValue(new File("src/main/resources/static/rover_output.xml"), roverOutput);
	}

	@Override
	public RoverOutput getRoverPosition() throws FileNotFoundException, IOException {
		File file = new File("src/main/resources/static/rover_output.xml");
	    XmlMapper xmlMapper = new XmlMapper();
	    String xml = inputStreamToString(new FileInputStream(file));
	    RoverOutput roverOutput = xmlMapper.readValue(xml, RoverOutput.class);
		return roverOutput;
	}
	
	private String inputStreamToString(InputStream is) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    String line;
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    while ((line = br.readLine()) != null) {
	        sb.append(line);
	    }
	    br.close();
	    return sb.toString();
	}

	
}
