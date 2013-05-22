package com.angergames.orderpathbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ConfigManager.java
 * Purpose: Handle loading of config files. 
 * 
 * @author Adam Oswalt
 */
public class ConfigLoader {
	private final File PATHS_FILE = new File("cfg/paths.cfg");
	private final File TRIM_FILE = new File("cfg/trim.cfg");
	
	private HashMap<String, String> paths = new HashMap<String, String>();
	private ArrayList<Trim> trims = new ArrayList<Trim>();
	
	private BufferedReader reader;
	private static String pathRoot;
	
	public ConfigLoader() {
		loadPaths();
		loadTrims();
	}
	
	/**
	 * Makes path root available to outside classes.
	 * @return The root portion of the paths.
	 */
	public static String getPathRoot() {
		return pathRoot;
	}
	
	/**
	 * Get the paths loaded from the config file.
	 * @return The HashMap of paths.
	 */
	public HashMap<String, String> getPaths() {
		return paths;
	}
	
	/**
	 * Get the list of trims from the config file.
	 * @return The list of trim entries.
	 */
	public ArrayList<Trim> getTrims() {
		return trims;
	}

	/**
	 * Load paths from the config file into the hashmap.
	 */
	private void loadPaths() {
		try {
			if(!PATHS_FILE.exists()) {
				Builder.closeWithError();
				return;
			}
			
			reader = new BufferedReader(new FileReader(PATHS_FILE));
			String line = "";
			String[] tokens;
			
			while((line = reader.readLine()) != null) {
				if(line.length() == 0 || line.startsWith("#")) continue;
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
	 * Load trim entries from the config file into the array list.
	 */
	private void loadTrims() {
		try {
			if(!TRIM_FILE.exists()) {
				Builder.closeWithError();
				return;
			}
			
			reader = new BufferedReader(new FileReader(TRIM_FILE));
			String line = "";
			String[] tokens;
			
			while((line = reader.readLine()) != null) {
				if(line.length() == 0 || line.startsWith("#")) continue;
				
				tokens = line.split(":", 2);	// split line only on first ":"
				trims.add(new Trim(Integer.parseInt(tokens[0]), tokens[1]));
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
	 * Write a new entry to the paths config file.
	 * 
	 * @param key The key to be added.
	 * @param path The path to be added.
	 */
	public void writeConfigEntry(String key, String path) {
		try {
			FileWriter writer = new FileWriter(PATHS_FILE, true);
			writer.write("\n" + key + "=" + path);
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ConfigLoader.java
	 * Purpose: A single trim entry.
	 * 
	 * @author Adam Oswalt
	 */
	protected class Trim {
		protected int length;
		protected String pattern;
		
		protected Trim(int length, String pattern) {
			this.length = length;
			this.pattern = pattern;
		}
	}
}
