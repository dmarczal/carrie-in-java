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

import javax.swing.JSeparator;

import br.ufpr.c3sl.virtualkeyboard.formula.ElementCompositeOfFormula;
import br.ufpr.c3sl.virtualkeyboard.formula.ElementOfFormula;
import br.ufpr.c3sl.virtualkeyboard.formula.FormulaInitial;
import br.ufpr.c3sl.virtualkeyboard.main.VirtualKeyBoardMain;


/**
 * The Class Division created on Mar 27, 2009.
 * create formula like this: ẕ
 *                           1
 * It is a JPanel with the division template. Inside the
 * division (on the divisor) is possible to insert the elements 
 * 
 */
@SuppressWarnings("serial")
public class Division extends ElementCompositeOfFormula {

	private GridBagConstraints constraintLine = new GridBagConstraints();
	private GridBagConstraints constraintDivisor = new GridBagConstraints();
	private GridBagConstraints constraintDividend = new GridBagConstraints();
	
	
	private JSeparator jsLineBetweenDivisorAndDividend = new JSeparator();

	private ElementOfFormula formulaDivend = new FormulaInitial();
	
	/**
	 * Construct for a newly Division.
	 */
	public Division(){
		this.setName("Division");
		setLayout();
		addElementForTemplateDivision();
	}
	
	/**
	 * Method to create the layout for division.
	 */
	private void setLayout() {
		this.setLayout(new GridBagLayout());
		constraintDividend.gridx = 0; constraintLine.gridx = 0; constraintDivisor.gridx = 0;
		constraintDividend.gridy = 0; constraintLine.gridy = 1; constraintDivisor.gridy = 2;

		constraintLine.fill = GridBagConstraints.HORIZONTAL;
		constraintLine.anchor = GridBagConstraints.PAGE_START;

		constraintDivisor.anchor = GridBagConstraints.PAGE_START;
		constraintDividend.anchor = GridBagConstraints.PAGE_END;
		
		jsLineBetweenDivisorAndDividend.setForeground(new java.awt.Color(0, 0, 0));
	}

	/**
	 * Adds the elements for template division.
	 */
	private void addElementForTemplateDivision() {
		this.removeAll();
		this.add(jsLineBetweenDivisorAndDividend, constraintLine);
		this.add(formulaInside, constraintDivisor);
	}	
	
	/**
	 * Sets the dividend.
	 * 
	 * @param formulaDivend the new dividend
	 */
	public void setDividend(ElementOfFormula formulaDivend){
		this.formulaDivend = formulaDivend;	
		this.add(this.formulaDivend, constraintDividend);
	}
	
	/** 
	 * @see br.ufpr.c3sl.virtualkeyboard.formula.ElementCompositeOfFormula#removeLastElement()
	 * Remove lastElement if the element is empty remove by itself from its father
	 * when it is removed from its father the elementsFocus must be its father
	 * 
	 * @return true if removed with successfully, otherwise return false
	 */
	public boolean removeLastElement() {
		this.getFather().addElement(this.formulaDivend);
		VirtualKeyBoardMain.setElementFocus(this.getFather());
		getFather().remove(this);
		return true;
	}
	
	/**
	 * Gets the divend.
	 * 
	 * @return the divend
	 */
	public ElementOfFormula getDivend(){
		return formulaDivend;
	}
	
	/**
	 * Gets the divisor.
	 * 
	 * @return the divisor
	 */
	public ElementOfFormula getDivisor(){
		return formulaInside;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 * Method overwritten From super Class
	 * @return a string with the formula, e.g. 10/(9-10)	
	 */
	public String toString() {
		String s = formulaDivend.toString();		
		s += "/("+ formulaInside + ")";
		return s;
	}
	
	/* (non-Javadoc)
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#getClone()
	 * Method overwritten From super Class
	 * @return a deep clone of the instance of Division 	
	 */
	public ElementOfFormula getClone(){
		Division clone = new Division();
		
		for (Component c : this.formulaInside.getComponents()) {
			if (c instanceof ElementOfFormula){
				ElementOfFormula element = ((ElementOfFormula) c).getClone();
				element.setFather(clone);
				clone.formulaInside.add(element,(clone.formulaInside.getComponentCount()-1));
			}
		}
		
		clone.setDividend(formulaDivend.getClone());
		
		return clone;
	}
}
