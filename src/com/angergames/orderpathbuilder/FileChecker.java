package com.angergames.orderpathbuilder;

import java.io.File;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.angergames.orderpathbuilder.ConfigLoader.Trim;

/**
 * FileChecker.java
 * Purpose: Handle reading and building paths and checking for files. 
 * 
 * @author Adam Oswalt
 */
public class FileChecker {
	
	private boolean isGUI;
	
	private ConfigLoader config = new ConfigLoader();
	private String pathRoot = ConfigLoader.getPathRoot();
	private HashMap<String, String> paths;
	
	private String[] orderData;
	private String key;
	
	public FileChecker(boolean isGUI) {
		this.isGUI = isGUI;
		
		paths = config.getPaths();
	}
	
	/**
	 * Attempt to build the path and find the file specified by the order.
	 * 
	 * @param An array of strings containing the order data.
	 * @return The path to the file.
	 */
	public String findFile(String[] orderData) {
		this.orderData = orderData;
		
		String path = makePath();
		if(path == null) {
			String[] enteredPath;
			if(isGUI) {
				if((enteredPath = PathInput.showDialog(key)) != null) {
					String newKey = enteredPath[0];
					String newPath = enteredPath[1];
					
					paths.put(newKey, newPath);
					config.writeConfigEntry(newKey, newPath);
				}
			} else {
				System.err.println("!ERROR: Path was not found for " + key);
			}
			return null;
		} else {
			path = pathRoot + path;
			
			//if a required field is empty, do not attempt to find a file
			if((path = replaceVars(path)) == null) {
				return null;
			}

			if(new File(path).exists()) {
				//System.out.println("File exists!");
				return path;
			} else {
				return "!WARNING: File not found at " + path;
			}
		}
	}
	
	/**
	 * Attempt to pull a path from the config and remove pricing additions if it fails.
	 * 
	 * @return Path from config.
	 */
	private String makePath() {
		key = orderData[0].toUpperCase();
		String out = "";
		
		if((out = paths.get(key)) != null) {
			return out;
		}
		
		// try removing pricing additions and check for a path each time
		for(Trim trim: config.getTrims()) {
			if(key.matches(trim.pattern)) {
				key = key.substring(0, key.length() - trim.length);
				
				if((out = paths.get(key)) != null) {
					return out;
				}
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
		// variables = $size, $spec, $word1, $word2, $word3, $word4, $ay
		
		//ids for the array of order data
		//int item = 0;
		int size = 1;
		int spec = 2;
		int word1 = 3;
		int word2 = 4;
		int word3 = 5;
		int word4 = 6;
		
		//test path for each variable and check if field is empty
		if(path.contains("$size")) {
			if(!orderData[size].equals("")) {
				path = path.replace("$size", Integer.parseInt(orderData[size]) + "");
			} else {
				showError("Size was empty.");
				return null;
			}
		}
		
		if(path.contains("$spec")) {
			if(!orderData[spec].equals("")) {
				path = path.replace("$spec", String.format("%.1f", Double.parseDouble(orderData[spec])));
			} else {
				showError("Spec was empty.");
				return null;
			}
		}
		
		if(path.contains("$word1")) {
			if(!orderData[word1].equals("")) {
				path = path.replace("$word1", orderData[word1].toUpperCase());
			} else {
				showError("Word1 was empty.");
				return null;
			}
		}
		
		if(path.contains("$word2")) {
			if(!orderData[word2].equals("")) {
				path = path.replace("$word2", orderData[word2].toUpperCase());
			} else {
				showError("Word2 was empty.");
				return null;
			}
		}
		
		if(path.contains("$word3")) {
			if(!orderData[word3].equals("")) {
				path = path.replace("$word3", orderData[word3].toUpperCase());
			} else {
				showError("Word3 was empty.");
				return null;
			}
		}
		
		if(path.contains("$word4")) {
			if(!orderData[word4].equals("")) {
				path = path.replace("$word4", orderData[word4].toUpperCase());
			} else {
				showError("Word4 was empty.");
				return null;
			}
		}
		
		if(path.contains("$ay")) {
			if(!orderData[spec].equals("")) {
				path = path.replace("$ay", Double.parseDouble(orderData[spec]) >= 10 ? "ADULT" : "YOUTH");
			} else {
				showError("Spec was empty.");
				return null;
			}
		}
		
		return path;
	}
	
	/**
	 * Display an error message. System out if not GUI, JOptionPane if is GUI.
	 * 
	 * @param message The message to display.
	 */
	private void showError(String message) {
		if(isGUI) {
			JOptionPane.showMessageDialog(null, message, "Empty Field", JOptionPane.ERROR_MESSAGE);
		} else {
			System.err.println("!ERROR: " + message);
		}
	}
}
