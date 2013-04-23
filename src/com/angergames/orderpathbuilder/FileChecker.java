package com.angergames.orderpathbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

/**
 * FileChecker.java
 * Purpose: Handle reading and building paths and checking for files. 
 * 
 * @author Adam Oswalt
 */
public class FileChecker {
	
	private boolean isGUI;
	
	private File configFile = new File("cfg/paths.cfg");
	
	private String[] orderData;
	private HashMap<String, String> paths = new HashMap<String, String>();
	private BufferedReader reader;
	
	private static String pathRoot;
	private String patternSoldSeparately = "\\w+-SS";
	private String patternPackage = "\\w+P\\d";
	private String patternMinimum = "\\w+M";
	private String patternLayers = "\\w+\\d";
	
	private String key;
	
	public FileChecker(boolean isGUI) {
		this.isGUI = isGUI;
		loadPaths();
	}
	
	/**
	 * Makes path root available to outside classes.
	 * @return The root portion of the paths.
	 */
	public static String getPathRoot() {
		return pathRoot;
	}
	
	/**
	 * Load paths from the config file into the hashmap.
	 */
	private void loadPaths() {
		try {
			if(!configFile.exists()) {
				Builder.closeWithError();
				return;
			}
			
			reader = new BufferedReader(new FileReader(configFile));
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
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
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
					writeConfigEntry(newKey, newPath);
				}
			} else {
				System.err.println("!ERROR: Path was not found for " + key);
			}
			return null;
		} else {
			path = pathRoot + path;
			path = replaceVars(path);

			if(new File(path).exists()) {
				//System.out.println("File exists!");
				return path;
			} else {
				return "!WARNING: File not found at " + path;
			}
		}
	}
	
	/**
	 * Attempt to pull a path from the config and remove modifiers if it fails.
	 * 
	 * @return Path from config.
	 */
	private String makePath() {
		key = orderData[0].toUpperCase();
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
	
	/**
	 * Write a new entry to the config file.
	 * 
	 * @param key The key to be added.
	 * @param path The path to be added.
	 */
	private void writeConfigEntry(String key, String path) {
		try {
			FileWriter writer = new FileWriter(configFile, true);
			writer.write("\n" + key + "=" + path);
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
