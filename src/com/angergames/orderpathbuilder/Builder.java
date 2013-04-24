package com.angergames.orderpathbuilder;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Builder.java
 * Purpose: The entry point and main window. 
 * 
 * @author Adam Oswalt
 */
public class Builder extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static final String TITLE = "Path Builder";
	
	private HashMap<String, JTextField> fields = new HashMap<String, JTextField>();
	private String[] orderData = new String[7];
	private int fieldWidth = 8;
	private static JButton findButton = new JButton("Find");
	
	private FileChecker checker;
	
	public Builder() {
		checker = new FileChecker(true);
		setupUI();
	}
	
	/**
	 * Add elements to the interface.
	 */
	private void setupUI() {
		this.setLayout(new GridBagLayout());
		
		findButton.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				String result = checker.findFile(copyFieldsToArray());
				if(result != null) {
					JOptionPane.showMessageDialog(null, new JTextArea(result), "Path", JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 2, 2, 2);
		JLabel label;
		JTextField field;
		
		label = new JLabel("Item:");
		c.gridwidth = 1;
		c.weightx = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 0;
		this.add(label, c);
		field = new JTextField(fieldWidth);
		c.gridwidth = 3;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		this.add(field, c);
		fields.put("item", field);
		
		label = new JLabel("Size:");
		c.gridwidth = 1;
		c.weightx = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 1;
		this.add(label, c);
		field = new JTextField(fieldWidth);
		c.gridwidth = 3;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		this.add(field, c);
		fields.put("size", field);
		
		label = new JLabel("Spec:");
		c.gridwidth = 1;
		c.weightx = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 2;
		this.add(label, c);
		field = new JTextField(fieldWidth);
		c.gridwidth = 3;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		this.add(field, c);
		fields.put("spec", field);
		
		
		//words
		label = new JLabel("Word1:");
		c.gridwidth = 1;
		c.weightx = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 3;
		this.add(label, c);
		field = new JTextField(fieldWidth);
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		this.add(field, c);
		fields.put("word1", field);

		label = new JLabel("Word2:");
		c.weightx = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 2;
		c.gridy = 3;
		this.add(label, c);
		field = new JTextField(fieldWidth);
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 3;
		this.add(field, c);
		fields.put("word2", field);

		label = new JLabel("Word3:");
		c.weightx = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 4;
		this.add(label, c);
		field = new JTextField(fieldWidth);
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		this.add(field, c);
		fields.put("word3", field);

		label = new JLabel("Word4:");
		c.weightx = 0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 2;
		c.gridy = 4;
		this.add(label, c);
		field = new JTextField(fieldWidth);
		c.weightx = 0.5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 3;
		this.add(field, c);
		fields.put("word4", field);
		
		c.anchor = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 5;
		c.fill = GridBagConstraints.NONE;
		this.add(findButton, c);
	}
	
	/**
	 * Copy text from fields to the orderData array.
	 * 
	 * @return The orderData array.
	 */
	private String[] copyFieldsToArray() {
		//ids for the array of order data
		int item = 0;
		int size = 1;
		int spec = 2;
		int word1 = 3;
		int word2 = 4;
		int word3 = 5;
		int word4 = 6;
		
		orderData[item] = fields.get("item").getText();
		orderData[size] = fields.get("size").getText();
		orderData[spec] = fields.get("spec").getText();
		orderData[word1] = fields.get("word1").getText();
		orderData[word2] = fields.get("word2").getText();
		orderData[word3] = fields.get("word3").getText();
		orderData[word4] = fields.get("word4").getText();
		
		return orderData;
	}
	
	/**
	 * Program entry point.
	 * 
	 * @param args If supplied with appropriate arguments, runs by command-line. Otherwise, runs with GUI.
	 */
	public static void main(String[] args) {
		if(args.length == 7) {	// if appropriate number of arguments, launch as commandline
			FileChecker checker = new FileChecker(false);
			
			String result = checker.findFile(args);
			if(result != null) {
				System.out.println(result);
			}
		} else if(args.length > 0 && args.length < 7) {	//if incorrect number of arguments, identify usage
			System.out.println("!Usage: builder [item size spec word1 word2 word3 word4]");
		} else {	// by default, launch with gui
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					JFrame frame = new JFrame(TITLE);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.add(new Builder());
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.getRootPane().setDefaultButton(findButton);
					frame.setVisible(true);
				}
			});
		}
	}
	
	/**
	 * Show error message for config file not found and exit the program.
	 */
	public static void closeWithError() {
		System.err.println("!ERROR: cfg/paths.cfg not found.");
		JOptionPane.showMessageDialog(null, "Config file not found.", "Missing Config", JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}
}
