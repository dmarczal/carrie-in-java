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
package br.ufpr.c3sl.virtualkeyboard.formula;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JPanel;

import br.ufpr.c3sl.virtualkeyboard.elements.Cursor;


/**
 * The Class ElementOfFormula created on Mar 28, 2009.
 * 
 */
@SuppressWarnings("serial")
public abstract class ElementOfFormula extends JPanel implements ElementOfFormulaIF{
	
	private Cursor cursor = new Cursor();	
	private ElementOfFormula father;
	protected static final Font fONT_FOR_TEMPLATE_ELEMENT = new Font("Lucida Sans Unicode", 0, 20);
	
	
	/**
	 * Instantiates a new element of formula.
	 * with the background null, layout FlowLayout(FlowLayout.LEFT, 2, 0)
	 * and add the cursor.
	 */
	public ElementOfFormula() {
		this.setBackground(null);
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 0));
		this.add(cursor); 
	}
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#addElement(virtualkeyboard.formula.ElementOfFormula)
	 * Not added because it is a leaf element
	 */
	public boolean addElement(ElementOfFormula element) {
		//System.out.println("Elemento Folha!!");
		return false;
	}
	
	/* (non-Javadoc)
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#addElement(java.lang.String)
	 * Method overwritten
	 * Not added because it is a leaf element
	 */
	public boolean addElement(String element){
		//System.out.println("Operação não permitida!! "+ s);
		return false;
	}

	/**
	 * Sets the father.
	 * 
	 * @param father the new father
	 */
	public void setFather(ElementOfFormula father) {
		this.father = father;
	}

	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#getFather()
	 */
	public ElementOfFormula getFather() {
		return father;
	} 
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#hideCursor()
	 */
	public void hideCursor(){
		cursor.hideCursor();
	}
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#showCursor()
	 */
	public void showCursor(){
		cursor.showCursor();
	}
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#isValidElement()
	 */
	public boolean isValidElement(){
		//System.out.println(this + "is not Valid");
		return false;
	}
}
