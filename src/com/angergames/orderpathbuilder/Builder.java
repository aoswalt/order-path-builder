package com.angergames.orderpathbuilder;

import javax.swing.JPanel;

/**
 * Builder.java
 * Purpose: The entry point and main window. 
 * 
 * @author Adam Oswalt
 */
public class Builder extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static String patternPackage = "\\w+P\\d{1}";
	private static String patternMinimum = "\\w+M";
	
	private static String in = "HTFSTL2MP3";
	private static String out;
	
	public static void main(String[] args) {
		out = in.toUpperCase();
		System.out.println("Input: " + out);
		
		if(out.matches(patternPackage)) {
			out = out.substring(0, out.length() - 2);
		}
		
		if(out.matches(patternMinimum)) {
			out = out.substring(0, out.length() - 1);
		}
		
		System.out.println("Searched for: " + out);
	}
}
