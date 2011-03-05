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
package br.ufpr.c3sl.virtualkeyboard.compositedElements;

import java.awt.Component;

import javax.swing.JLabel;

import br.ufpr.c3sl.virtualkeyboard.formula.ElementCompositeOfFormula;
import br.ufpr.c3sl.virtualkeyboard.formula.ElementOfFormula;


/**
 * The Class Parentheses created on Mar 27, 2009.
 * It Create formula like this: (10+50).
 * It is a JPanel with the parentheses template. Between the
 * parentheses it possible to insert the elements
 *  
 */
public class Parentheses extends ElementCompositeOfFormula {
	private static final long serialVersionUID = 1L;
	
	private JLabel jlBracketLeft = new JLabel("(");
	private JLabel jlBracketRight = new JLabel(")");
	
	/**
	 * Instantiates a new parentheses.
	 */
	public Parentheses() {
		this.setName("Parentheses");
		addParentheses();
	}
		
	/**
	 * Adds the Parentheses.
	 * And add the formulaInside, which is a variable with will contain the formula inside the
	 * parentheses. It is inherited from {@link ElementCompositeOfFormula}
	 */
	private void addParentheses(){
		this.removeAll();
		jlBracketLeft.setFont(fONT_FOR_TEMPLATE_ELEMENT);
		jlBracketRight.setFont(fONT_FOR_TEMPLATE_ELEMENT);
		this.add(jlBracketLeft);
		this.add(formulaInside); 
		this.add(jlBracketRight);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 * Method overwritten
	 * @return a string with the formula, e.g. (9-10)	
	 */
	public String toString() {
		String s = "";		
		s += "("+formulaInside+")";
		return s;
	}

	/* (non-Javadoc)
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#getClone()
	 * Method overwritten From super Class
	 * @return a deep clone of the instance of parentheses  	
	 */
	public ElementOfFormula getClone(){
		Parentheses clone = new Parentheses();
		for (Component c : this.formulaInside.getComponents()) {
			if (c instanceof ElementOfFormula){
				ElementOfFormula element = ((ElementOfFormula) c).getClone();
				element.setFather(clone);
				clone.formulaInside.add(element,(clone.formulaInside.getComponentCount()-1));
			}
		}
		return clone;
	}
	
}