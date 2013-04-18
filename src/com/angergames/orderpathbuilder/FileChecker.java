package com.angergames.orderpathbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * FileChecker.java
 * Purpose: Handle reading and building paths and checking for files. 
 * 
 * @author Adam Oswalt
 */
public class FileChecker {
	
	private HashMap<String, JTextField> fields;
	private HashMap<String, String> paths = new HashMap<String, String>();
	private BufferedReader reader;
	
	private String pathRoot;
	private String patternSoldSeparately = "\\w+-SS";
	private String patternPackage = "\\w+P\\d";
	private String patternMinimum = "\\w+M";
	private String patternLayers = "\\w+\\d";
	
	public FileChecker(HashMap<String, JTextField> fields) {
		this.fields = fields;
		loadPaths();
	}
	
	/**
	 * Load paths from the config file into the hashmap.
	 */
	private void loadPaths() {
		try {
			reader = new BufferedReader(new FileReader(new File("cfg/paths.cfg")));
			String line = "";
			String[] tokens;
			
			while((line = reader.readLine()) != null) {
				if(line.startsWith("#") || line.length() == 0) continue;
				tokens = line.split("=");
				
				if(tokens[0].equals("root")) {
					pathRoot = tokens[1];
					continue;
				}
				
				paths.put(tokens[0], tokens[1]);
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
	
	/**
	 * Attempt to build the path and find the file specified by the order.
	 * 
	 * @return The path to the file.
	 */
	public String findFile() {
		String path = pathRoot + makePath();
		path = replaceVars(path);
		
		
		if(new File(path).exists()) {
			return path;
		} else {
			JOptionPane.showInputDialog("Enter path using variables: ");
			// and to hashmap and save to config
		}
		
		return path;
	}
	
	/**
	 * Attempt to pull a path from the config and remove modifiers if it fails.
	 * 
	 * @return Path from config.
	 */
	private String makePath() {
		String key = fields.get("item").getText().toUpperCase();
		String out = "";
		
		if((out = paths.get(key)) != null) {
			return out;
		}
		
		// try to remove sold separately designation
		if(key.matches(patternSoldSeparately)) {
			key = key.substring(0, key.length() - 3);
			
			if((out = paths.get(key)) != null) {
				return out;
			}
		}
		
		// try to remove package designation
		if(key.matches(patternPackage)) {
			key = key.substring(0, key.length() - 2);
			
			if((out = paths.get(key)) != null) {
				return out;
			}
		}
		
		// try to remove minimum designation
		if(key.matches(patternMinimum)) {
			key = key.substring(0, key.length() - 1);
			
			if((out = paths.get(key)) != null) {
				return out;
			}
		}
		
		// remove layer count designation
		if(key.matches(patternLayers)) {
			key = key.substring(0, key.length() - 1);
			
			if((out = paths.get(key)) != null) {
				return out;
			}
		}
		
		return null;
	}
	
	/**
	 * Replace variables in the path with values from the fields.
	 * 
	 * @param path The path containing variables.
	 * @return The path with inserted values.
	 */
	private String replaceVars(String path) {
		// $size, $spec, $word1, $word2, $word3, $word4
		path = path.replace("$size", Integer.parseInt(fields.get("size").getText()) + "");
		path = path.replace("$spec", String.format("%.1f", Double.parseDouble(fields.get("spec").getText())));
		path = path.replace("$word1", fields.get("word1").getText().toUpperCase());
		path = path.replace("$word2", fields.get("word2").getText().toUpperCase());
		path = path.replace("$word3", fields.get("word3").getText().toUpperCase());
		path = path.replace("$word4", fields.get("word4").getText().toUpperCase());
		path = path.replace("$ay", Double.parseDouble(fields.get("spec").getText()) >= 10 ? "ADULT" : "YOUTH");
		
		return path;
	}
}
