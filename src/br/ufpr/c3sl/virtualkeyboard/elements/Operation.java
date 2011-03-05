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
 * The Class Operation created on Mar 28, 2009.
 * 
 * It is a JPanel with the template, which uses a JLabel to display the number
 * 
 */
public class Operation extends ElementOfFormula {

	private static final long serialVersionUID = 1L;
	
	private JLabel jlOperation;

	/**
	 * Instantiates a new Operation panel Operation with the name 'Operation'.
	 * Add the jlOperation at position (this.getComponentCount()-1) because the
	 * last element is the cursor
	 */
	public Operation() {
		jlOperation = new JLabel();
		this.add(jlOperation, (this.getComponentCount()-1));
	}	

	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormula#addElement(java.lang.String)
	 */
	public boolean addElement(String s){
		jlOperation.setText(s);
		return true;
	}

	
	/**
	 * Checks if is empty.
	 * 
	 * @return true, if is empty
	 */
	public boolean isEmpty(){
		return jlOperation.getText().equals("");
	}


	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#removeLastElement()
	 */
	public boolean removeLastElement() {
		getFather().remove(this);
		this.jlOperation.setText("");
		return true;
	}

	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#getLastElementAdded()
	 */
	public ElementOfFormula getLastElementAdded() {
		return null;
	}
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormula#isValidElement()
	 * Method overwritten From super Class
	 * By default operation is not valid, because it can be the last element 
	 * inside the formula
	 */
	public boolean isValidElement(){
		//System.out.println("Operation is not true");
		return false;
	}
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see java.awt.Component#toString()
	 * @return a string with the formula, e.g. *,-,/,+ 	
	 * Its need to change x by *, ÷, / because it need this to calculate
	 * the result of the formula
	 */
	public String toString(){
		if (jlOperation.getText().equals("x"))
			return "*";
		else
			if (jlOperation.getText().equals("÷"))
				return "/";
			else
				return jlOperation.getText();
	}
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#getClone()
	 * @return a deep clone of the instance of Operation 	
	 */
	public ElementOfFormula getClone(){
		Operation clone = new Operation();
		clone.add(new JLabel(),(clone.getComponentCount()-1));
		clone.addElement(jlOperation.getText());
		return clone;
	}
}
