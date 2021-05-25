package com.imamyth.roverbot.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.imamyth.roverbot.constant.RoverConstants;

public final class CompassUtility {
	
	private static Map<Integer, String> compass;
	private static List<String> directions;
	
	private CompassUtility() {
		
	}
	
	private static void init() {
		compass = new HashMap<Integer, String>();
		directions = new ArrayList<String>();
		
		compass.put(0, "N");
		compass.put(90, "E");
		compass.put(180, "S");
		compass.put(270, "W");
		
		directions.add("N");
		directions.add("E");
		directions.add("S");
		directions.add("W");
	}
	
	public static Map<Integer, String> getCompass(String direction, String turn) {
		init();

		int index = 0;

		if (turn.equals(RoverConstants.RIGHT)) {
			index = directions.indexOf(direction) + 1;
			if (index > directions.size() - 1) {
				index = 0;
			}
			int degree = 0;
			compass.put(degree, direction);
			while (!directions.get(index).equals(direction)) {
				degree = degree + 90;
				compass.put(degree, directions.get(index));
				index++;
				if (index > directions.size() - 1) {
					index = 0;
				}
			}
		} else {
			index = directions.indexOf(direction) - 1;
			if (index <= 0) {
				index = directions.size() - 1;
			}
			int degree = 0;
			compass.put(degree, direction);
			while (!directions.get(index).equals(direction)) {
				degree = degree + 90;
				compass.put(degree, directions.get(index));
				index--;
				if (index < 0) {
					index = directions.size() - 1;
				}
			}
		}
		return compass;
	}
}
