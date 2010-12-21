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

import javax.swing.JLabel;

import br.ufpr.c3sl.virtualkeyboard.formula.ElementOfFormula;


/**
 * The Class Variable created on Mar 28, 2009.
 * 
 * It is a JPanel with the template, which uses a JLabel to display the number
 *
 */
@SuppressWarnings("serial")
public class Variable extends ElementOfFormula{
	
	private JLabel jlVariable = new JLabel();
	
	/**
	 * Construct for a Variable
	 * Initializes a newly Variable with the name 'Variable'.
	 * Add the jlVariable at position (this.getComponentCount()-1) because the
	 * last element is the cursor
	 */
	public Variable(){
		this.setName("Variable");
		this.add(jlVariable, (this.getComponentCount()-1));
	}

	/* (non-Javadoc)
	 * @see virtualkeyboard.formula.ElementOfFormula#addElement(java.lang.String)
	 * Method to add a variable,
	 * @param String variable
	 * return true if added with successfully, otherwise return false
	 */
	public boolean addElement(String variable){
		jlVariable.setText(variable);
		return true;
	}
	
	/**
	 * Checks if is empty.
	 * 
	 * @return true, if is empty
	 */
	public boolean isEmpty(){
		return jlVariable.getText().equals("");
	}
	

	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#removeLastElement()
	 * @see virtualkeyboard.formula.ElementCompositeOfFormula#removeLastElement()
	 * Remove lastElement if the element is empty remove by itself from its father
	 * when it is removed from its father the elementsFocus must be its father
	 * 
	 * @return true if removed with successfully, otherwise return false
	 */
	public boolean removeLastElement() {
		getFather().remove(this);
		this.jlVariable.setText("");
		return true;
	}

	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#getLastElementAdded()
	 * Variable is a simple element, not have a last element added
	 */
	public ElementOfFormula getLastElementAdded() {
		return null;
	}
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormula#isValidElement()
	 */
	public boolean isValidElement(){
		//System.out.println("Variável é válida");
		return true;
	}
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see java.awt.Component#toString()
	 */
	public String toString(){
		return jlVariable.getText();
	}
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#getClone()
	 * @return a deep clone of the instance of Variable 
	 */
	public ElementOfFormula getClone(){
		Variable clone = new Variable();
		clone.add(new JLabel(),(clone.getComponentCount()-1));
		clone.addElement(jlVariable.getText());
		return clone;
	}
}
