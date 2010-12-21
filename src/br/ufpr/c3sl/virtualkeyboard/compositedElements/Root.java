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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JSeparator;

import br.ufpr.c3sl.virtualkeyboard.formula.ElementCompositeOfFormula;
import br.ufpr.c3sl.virtualkeyboard.formula.ElementOfFormula;


/**
 * The Class Root created on Mar 28, 2009.
 *                                _______  
 * It Create formula like this:  √(10+50).
 *
 * It is a JPanel with the parentheses template. Between the
 * parentheses it possible to insert the elements
 * 
 */
@SuppressWarnings("serial")
public class Root extends ElementCompositeOfFormula {

	/** The Northwest Constraint. */
	private GridBagConstraints nwConstraint = new GridBagConstraints();
	/** The Northeast Constraint. */
	private GridBagConstraints neConstraint = new GridBagConstraints();
	/** The SouthWest Constraint. */
	private GridBagConstraints swConstraint = new GridBagConstraints();
	/** The SouthEast Constraint. */
	private GridBagConstraints seConstraint = new GridBagConstraints();
	
	private JLabel jlRoot = new JLabel("√");
	private JSeparator jsLineUpRoot = new JSeparator();

	
	/**
	 * Instantiates a new root.
	 */
	public Root() {
		this.setName("Root");
		setLayout();
		addElementForTemplateRoot();
	}
		
	/**
	 * Sets the layout.
	 */
	private void setLayout() {
		this.setLayout(new GridBagLayout());
		nwConstraint.gridx = 0; neConstraint.gridx = 1; swConstraint.gridx = 0; seConstraint.gridx = 1;
		nwConstraint.gridy = 0; neConstraint.gridy = 0; swConstraint.gridy = 1; seConstraint.gridy = 1;

		swConstraint.anchor=GridBagConstraints.FIRST_LINE_END;
		swConstraint.insets = new Insets(-2,0,0,0);
		neConstraint.fill=GridBagConstraints.HORIZONTAL;
		neConstraint.anchor=GridBagConstraints.LAST_LINE_START;

		jsLineUpRoot.setForeground(new java.awt.Color(0, 0, 0));
	}

	/**
	 *  Method to add element in the template for root
	 */
	private void addElementForTemplateRoot() {
		jlRoot.setFont(fONT_FOR_TEMPLATE_ELEMENT);
		this.removeAll();
		this.add(jlRoot, swConstraint);
		this.add(jsLineUpRoot,neConstraint);
		this.add(formulaInside, seConstraint);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 * Method overwritten From super Class
	 * @return a string with the formula, e.g. sqrt(9-10)	
	 */
	public String toString() {
		String s = "sqrt(" + formulaInside+")";
		return s;
	}
	
	
	/* (non-Javadoc)
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#getClone()
	 * Method overwritten From super Class
	 * @return a deep clone of the instance of Root  	
	 */
	public ElementOfFormula getClone(){
		Root clone = new Root();
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
