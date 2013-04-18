package com.angergames.orderpathbuilder;

public class Path {
	
	private String[] tokens;
	private int[] variables = new int[6];
	
	public Path(String raw) {
		for(int i = 0; i < variables.length; i++) {
			variables[i] = -1;
		}
		
		tokens = raw.split("\\");
		for(int i = 0; i < tokens.length; i++) {
			if(tokens[i].startsWith("$")) {
				
			}
		}
	}
}
