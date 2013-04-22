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
		String path = makePath();
		if(path == null) {
			return null;
		} else {
			path = pathRoot + path;
		}
		
		path = replaceVars(path);
		
		if(path != null) {
			if(new File(path).exists()) {
				return path;
			} else {
				//JOptionPane.showInputDialog("Enter path using variables: ");
				// and to hashmap and save to config
				return path;
			}
		} else {
			return null;
		}
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
	 * @return The path with inserted values or null if a required field is empty.
	 */
	private String replaceVars(String path) {
		// $size, $spec, $word1, $word2, $word3, $word4, $ay
		
		//test path for each variable and check if field is empty
		if(path.contains("$size")) {
			if(!fields.get("size").getText().equals("")) {
				path = path.replace("$size", Integer.parseInt(fields.get("size").getText()) + "");
			} else {
				JOptionPane.showMessageDialog(null, "Size was empty.", "Empty Field", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		
		if(path.contains("$spec")) {
			if(!fields.get("spec").getText().equals("")) {
				path = path.replace("$spec", String.format("%.1f", Double.parseDouble(fields.get("spec").getText())));
			} else {
				JOptionPane.showMessageDialog(null, "Spec was empty.", "Empty Field", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		
		if(path.contains("$word1")) {
			if(!fields.get("word1").getText().equals("")) {
				path = path.replace("$word1", fields.get("word1").getText().toUpperCase());
			} else {
				JOptionPane.showMessageDialog(null, "Word1 was empty.", "Empty Field", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		
		if(path.contains("$word2")) {
			if(!fields.get("word2").getText().equals("")) {
				path = path.replace("$word2", fields.get("word2").getText().toUpperCase());
			} else {
				JOptionPane.showMessageDialog(null, "Word2 was empty.", "Empty Field", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		
		if(path.contains("$word3")) {
			if(!fields.get("word3").getText().equals("")) {
				path = path.replace("$word3", fields.get("word3").getText().toUpperCase());
			} else {
				JOptionPane.showMessageDialog(null, "Word3 was empty.", "Empty Field", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		
		if(path.contains("$word4")) {
			if(!fields.get("word4").getText().equals("")) {
				path = path.replace("$word4", fields.get("word4").getText().toUpperCase());
			} else {
				JOptionPane.showMessageDialog(null, "Word4 was empty.", "Empty Field", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		
		if(path.contains("$ay")) {
			if(!fields.get("$ay").getText().equals("")) {
				path = path.replace("$ay", Double.parseDouble(fields.get("spec").getText()) >= 10 ? "ADULT" : "YOUTH");
			} else {
				JOptionPane.showMessageDialog(null, "Spec was empty.", "Empty Field", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		}
		
		return path;
	}
}
