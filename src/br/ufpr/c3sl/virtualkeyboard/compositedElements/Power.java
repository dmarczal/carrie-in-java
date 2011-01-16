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
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import br.ufpr.c3sl.virtualkeyboard.formula.ElementCompositeOfFormula;
import br.ufpr.c3sl.virtualkeyboard.formula.ElementOfFormula;
import br.ufpr.c3sl.virtualkeyboard.formula.FormulaInitial;
import br.ufpr.c3sl.virtualkeyboard.main.VirtualKeyBoardMain;


/**
 * The Class Power created on Mar 27, 2009.
 *                                     10
 * It Create formula like this: (10+50)
 * It is a JPanel with the power template. Up the
 * the element is possible to insert other elements
 * 
 */

@SuppressWarnings("serial")
public class Power extends ElementCompositeOfFormula {

	/** The Northwest Constraint. */
	private GridBagConstraints nwConstraint = new GridBagConstraints();
	/** The SouthEast Constraint. */
	private GridBagConstraints seConstraint = new GridBagConstraints();
	
	private ElementOfFormula firstElement = new FormulaInitial();
	private JPanel powerPanel = new JPanel();
	
	/**
	 * Construct for a newly Power.
	 */	
	public Power() {
		this.setName("Power");
		setLayout();
		addElementForTemplatePower();
	}

	
	/**
	 * Method to create the layout for power.
	 */
	private void setLayout() {
		powerPanel.setLayout(new GridBagLayout());
		this.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		nwConstraint.gridy = 0;  seConstraint.gridy = 1;
		nwConstraint.gridx = 0; seConstraint.gridx = 1;
		
		powerPanel.setBorder(null);
	}

	/**
	 * Method to add the elements to make template for power.
	 */
	private void addElementForTemplatePower() {
		this.removeAll();
		formulaInside.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		powerPanel.add(formulaInside, nwConstraint);
		powerPanel.add(new JLabel(" "), seConstraint);
		
		this.add(powerPanel);
		powerPanel.setBackground(getBackground());
	}
	
	public void setFirstElementOfPower(ElementOfFormula firstElement){
		this.firstElement = firstElement;	
		this.add(this.firstElement,0);
	}
	
	public boolean removeLastElement() {
		this.getFather().addElement(this.firstElement);
		VirtualKeyBoardMain.setElementFocus(this.getFather());
		getFather().remove(this);
		return true;
	}
	
	/**
	 * Gets the divend.
	 * 
	 * @return the divend
	 */
	public ElementOfFormula getFirstElement(){
		return firstElement;
	}
	
	public ElementOfFormula getFormulaInside(){
		return formulaInside;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 * Method overwritten From super Class
	 * @return a string with the formula, e.g. ^(9-10)	
	 */
	public String toString() {
		String s = "("+firstElement+"^("+ formulaInside + "))";
		return s;
	}
	
	/* (non-Javadoc)
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#getClone()
	 * Method overwritten From super Class
	 * @return a deep clone of the instance of Power  	
	 */
	public ElementOfFormula getClone(){
		Power clone = new Power();
		for (Component c : this.formulaInside.getComponents()) {
			if (c instanceof ElementOfFormula){
				ElementOfFormula element = ((ElementOfFormula) c).getClone();
				element.setFather(clone);
				clone.formulaInside.add(element,(clone.formulaInside.getComponentCount()-1));
			}
		}
		
		clone.setFirstElementOfPower(this.firstElement.getClone());
		
		return clone;
	}

}
