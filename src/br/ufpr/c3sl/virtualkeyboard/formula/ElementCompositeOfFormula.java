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

import java.awt.Component;

import br.ufpr.c3sl.virtualkeyboard.elements.Operation;
import br.ufpr.c3sl.virtualkeyboard.main.VirtualKeyBoardMain;


/**
 * The Class ElementCompositeOfFormula created on Mar 28, 2009.
 * 
 */
public abstract class ElementCompositeOfFormula 
				extends ElementOfFormula implements ElementOfFormulaIF{

	private static final long serialVersionUID = 1L;
	
	/** The formula inside all the element composite have a formula inside. */
	protected ElementOfFormula formulaInside = new FormulaInitial();
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormula#addElement(virtualkeyboard.formula.ElementOfFormula)
	 * If added with successfully return true, otherwise false
	 * 
	 */
	public boolean addElement(ElementOfFormula element) {
		if (formulaInside.addElement(element)){
			element.setFather(this);
			return true;
		}
		
		return false;
	}	
	

	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#removeLastElement()
	 * Remove lastElement if the element is empty remove by itself from its father
	 * when it is removed from its father the elementsFocus must be its father
	 */
	public boolean removeLastElement() {
		VirtualKeyBoardMain.setElementFocus(this.getFather());
		getFather().remove(this);
		return true;
	}
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see java.awt.Container#remove(java.awt.Component)
	 */
	public void remove(Component c) {
		formulaInside.remove(c);
	}

	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#getLastElementAdded()
	 */
	public ElementOfFormula getLastElementAdded() {
		return formulaInside.getLastElementAdded();
	}
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormula#hideCursor()
	 */
	public void hideCursor(){
		formulaInside.hideCursor();
	}
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormula#showCursor()
	 */
	public void showCursor(){
		formulaInside.showCursor();
	}
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormula#isValidElement()
	 * it is valid if the last element is not null and is not a Operation
	 */
	public boolean isValidElement(){
		if (this.getLastElementAdded() == null  || this.getLastElementAdded() instanceof Operation ){
			//System.out.println(this + " is not valid");
			return false;
		}else{
			//System.out.println(this + " is not valid");
			return true;	
		}	
	}
	
}
