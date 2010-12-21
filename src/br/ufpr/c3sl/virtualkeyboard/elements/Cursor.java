/* Copyright (c) 2008-2009 Centro de Computacao Cientifica e Software Livre
 * Departamento de Informatica - Universidade Federal do Parana - C3SL/UFPR
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.
 */
package br.ufpr.c3sl.virtualkeyboard.elements;

import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * The Class Cursor created on Mar 27, 2009.
 * It uses a JLabel to display the Cursor 
 * It show and hidden the JLabel to simulate a cursor
 */
@SuppressWarnings("serial")
public class Cursor extends JLabel {
	
	private boolean shown;
	
	private String text;
	
	private Timer timer;
	
	/**
	 * Construct for a Cursor
	 * Initializes a newly Cursor with the name 'Cursor'.
	 * The default is to the cursor hidden
	 */
	public Cursor(){
		this.setName("Cursor");
		setCursor("|");
	
		timer = new Timer(250, new ActionListener() {  
			public void actionPerformed(java.awt.event.ActionEvent e) {  
				switchView();
			}  
		});
		shown = true;
		timer.start();
		hideCursor();
	}
	
	/**
	 * Method to set the a new Cursor.
	 * 
	 * @param cursor the cursor
	 */
	public void setCursor(String cursor){
		text = cursor;
		this.setText(cursor);
	}
	
	/**
	 * Switch view.
	 * Change text of JLabel to simulate the cursor
	 */
	public void switchView(){
		shown = !shown;
		if (shown)
			this.setText(" ");
		else
			this.setText(text);
	}

	/**
	 * Show cursor.
	 */
	public void showCursor(){
		this.setVisible(true);
		timer.start();
	}
	
	/**
	 * Hide cursor.
	 */
	public void hideCursor(){
		this.setVisible(false);
		timer.stop();
	}
}
