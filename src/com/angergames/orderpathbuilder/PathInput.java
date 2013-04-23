package com.angergames.orderpathbuilder;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * PathInput.java
 * Purpose: To show a dialog for inputting a new path.
 * 
 * @author Adam Oswalt
 */
public class PathInput extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final static int FIELD_WIDTH = 8;
	
	private static String[] newEntry = new String[2];
	private static JTextField keyField = new JTextField(FIELD_WIDTH);
	private static JTextField pathField = new JTextField(FIELD_WIDTH);
	
	/**
	 * Show input dialog for adding a new path to the config file.
	 * @param key The key for the new path (from what was attempted to be found).
	 * @return A pair of Strings representing [key, path].
	 */
	public static String[] showDialog(String key) {
		int result = JOptionPane.showConfirmDialog(null, getPanel(key), "Input New Path", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		
		if(result == JOptionPane.OK_OPTION) {
			newEntry[0] = keyField.getText();
			newEntry[1] = pathField.getText();
			return newEntry;
		} else {
			return null;
		}
	}
	
	/**
	 * Build a panel for inputting the path.
	 * @param key The key for the new path (from what was attempted to be found).
	 * @return The panel to be shown.
	 */
	private static JPanel getPanel(String key) {
		JPanel panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 2, 2, 2);
		
		c.gridwidth = 3;
		//c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(new JLabel("<html>A path could not be found for the style entered<br>" +
							 "Enter a new path to add to the config file.<br><br>" +
							 "Use the following for variables: <br>" +
							 "$size, $spec, $word1, $word2, $word3, $word4, $ay"), c);
		c.gridwidth = 1;
		c.weightx = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 1;
		panel.add(new JLabel("Key: "), c);
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = GridBagConstraints.RELATIVE;
		keyField.setText(key);
		panel.add(keyField, c);
		
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 2;
		panel.add(new JLabel("Path: "), c);
		c.anchor = GridBagConstraints.EAST;
		c.gridx = GridBagConstraints.RELATIVE;
		panel.add(new JLabel(FileChecker.getPathRoot()), c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = GridBagConstraints.RELATIVE;
		panel.add(pathField, c);
		
		return panel;
	}
}
