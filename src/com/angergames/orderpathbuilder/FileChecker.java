package com.angergames.orderpathbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class FileChecker {
	
	private HashMap<String, Path> paths = new HashMap<String, Path>();
	private BufferedReader reader;
	
	private void loadPaths() {
		try {
			reader = new BufferedReader(new FileReader(new File("cfg/paths.cfg")));
			String line = "";
			String[] tokens;
			
			while((line = reader.readLine()) != null) {
				if(line.startsWith("#") || line.length() == 0) continue;
				tokens = line.split("=");
				
				paths.put(tokens[0], new Path(tokens[1]));
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
