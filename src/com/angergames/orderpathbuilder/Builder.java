package com.angergames.orderpathbuilder;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	private int fieldWidth = 8;
	private static JButton findButton = new JButton("Find");
	
	private FileChecker checker = new FileChecker(fields);
	
	public Builder() {
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
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(checker.findFile());
			}
		});
		
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(2, 2, 2, 2);
		JLabel label;
		JTextField field;
		
		label = new JLabel("Item: ");
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 0;
		this.add(label, c);
		field = new JTextField(fieldWidth);
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		this.add(field, c);
		fields.put("item", field);
		
		label = new JLabel("Size: ");
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 1;
		this.add(label, c);
		field = new JTextField(fieldWidth);
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		this.add(field, c);
		fields.put("size", field);
		
		label = new JLabel("Spec: ");
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 2;
		this.add(label, c);
		field = new JTextField(fieldWidth);
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		this.add(field, c);
		fields.put("spec", field);
		
		
		//words
		label = new JLabel("Word1: ");
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 3;
		this.add(label, c);
		field = new JTextField(fieldWidth);
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		this.add(field, c);
		fields.put("word1", field);

		label = new JLabel("Word2: ");
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 0;
		c.gridy = 4;
		this.add(label, c);
		field = new JTextField(fieldWidth);
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 1;
		this.add(field, c);
		fields.put("word2", field);

		label = new JLabel("Word3: ");
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 2;
		c.gridy = 3;
		this.add(label, c);
		field = new JTextField(fieldWidth);
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 3;
		this.add(field, c);
		fields.put("word3", field);

		label = new JLabel("Word4: ");
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 2;
		c.gridy = 4;
		this.add(label, c);
		field = new JTextField(fieldWidth);
		c.anchor = GridBagConstraints.WEST;
		c.gridx = 3;
		this.add(field, c);
		fields.put("word4", field);
		
		c.gridx = 1;
		c.gridy = 5;
		this.add(findButton, c);
	}
	
	/**
	 * Program entry point.
	 * 
	 * @param args Not used.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Builder());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.getRootPane().setDefaultButton(findButton);
		frame.setVisible(true);

	}
}
