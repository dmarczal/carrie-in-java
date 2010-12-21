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

import br.ufpr.c3sl.virtualkeyboard.compositedElements.Division;
import br.ufpr.c3sl.virtualkeyboard.compositedElements.Parentheses;
import br.ufpr.c3sl.virtualkeyboard.compositedElements.Power;
import br.ufpr.c3sl.virtualkeyboard.compositedElements.Root;
import br.ufpr.c3sl.virtualkeyboard.elements.Number;
import br.ufpr.c3sl.virtualkeyboard.elements.Operation;
import br.ufpr.c3sl.virtualkeyboard.elements.Variable;
import br.ufpr.c3sl.virtualkeyboard.main.VirtualKeyBoardMain;


/**
 * The Class FormulaInitial created on Mar 28, 2009.
 * 
 */
@SuppressWarnings("serial")
public class FormulaInitial extends ElementCompositeInitial {

	/* (non-Javadoc)
	 * @see virtualkeyboard.formula.ElementOfFormula#addElement(virtualkeyboard.formula.ElementOfFormula)
	 * This method make several verifications 
	 * Can't add element like this:
	 * Operation be first element e.g. + 10 
	 * Power be first element     e.g. ^ + 10
	 * Variable before number, Parentheses, Root  e.g  l10, l(), l√
	 * number before Variable, Parentheses, Root  e.g  10l, 10(), 10√
	 * Operation before Power, Division     e.g. +^, +/10
	 */
	public boolean addElement(ElementOfFormula element){

		if ((getLastElementAdded() == null && element instanceof Operation)
				||  (getLastElementAdded() == null && element instanceof Power)                  // e.g. ^
				
				||	(getLastElementAdded() instanceof Operation && element instanceof Power)     // e.g. +^9
				||	(getLastElementAdded() instanceof Operation && element instanceof Division)  // e.g.: +/

				||	(getLastElementAdded() instanceof Number && element instanceof Variable)     // ex: 9 l
				||	(getLastElementAdded() instanceof Number && element instanceof Root)         // ex: 9 √
				||	(getLastElementAdded() instanceof Number && element instanceof Parentheses)  // ex: 9 ()

				||	(getLastElementAdded() instanceof Variable && element instanceof Number)      // ex: n 9
				||	(getLastElementAdded() instanceof Variable && element instanceof Root)        // ex: n √
				||	(getLastElementAdded() instanceof Variable && element instanceof Parentheses) // ex: n ()
		){
			
			/*If there is a error the element focus must be set with the last element added */
			if (getLastElementAdded() != null)
				VirtualKeyBoardMain.setElementFocus(getLastElementAdded());
			
			 //System.out.println("Not possible added");
			return false;
		}else 
			if ((getLastElementAdded() instanceof ElementCompositeOfFormula
					&& getLastElementAdded().isValidElement() 
					&& element instanceof Variable)                                  // e.g (10)l 
					
					||  (getLastElementAdded() instanceof ElementCompositeOfFormula 
							&& getLastElementAdded().isValidElement() 
							&& element instanceof Number)                             // e.g (10)10
				
					
					||  (getLastElementAdded() instanceof ElementCompositeOfFormula 
							&& getLastElementAdded().isValidElement() 
							&& element instanceof ElementCompositeOfFormula
							&& !(element instanceof Power && !(getLastElementAdded() instanceof Division))
							&& !(element instanceof Division))){  // e.g 10/10  ^  								
				
				/* if one of case occurs the elements focus must be the father of last element added */
				VirtualKeyBoardMain.setElementFocus(getLastElementAdded().getFather());
					//System.out.println("Not possible added");
				return false;
		}else{
			//System.out.println("Added successfully");
			element.setFather(this);
			this.add(element, (this.getComponentCount()-1));
			return true;
		}
	}

	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormula#isValidElement()
	 * It is valid if the last element is not null and it is valid
	 */
	public boolean isValidElement(){
		if (getLastElementAdded() != null)
			return getLastElementAdded().isValidElement();
		return false;
	}
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#removeLastElement()
	 */
	public boolean removeLastElement() {
		return true;
	}
	
	/* (non-Javadoc)
	 * Method overwritten
	 * @see java.awt.Component#toString()
	 * @return a String with the formula (10^(10)- 20 + 50)
	 */
	public String toString(){
		String s = "";			
		for (Component c : this.getComponents()) {
			if (c instanceof ElementOfFormulaIF)
				s += c.toString();
		}
		return s;
	}

	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#getClone()
	 * @return a deep clone of formula initial
	 */
	public ElementOfFormula getClone(){
		FormulaInitial clone = new FormulaInitial();
		
		for (Component c : this.getComponents()) {
			if (c instanceof ElementOfFormula){
				ElementOfFormula element = ((ElementOfFormula) c).getClone();
				element.setFather(clone);
				clone.add(element,(clone.getComponentCount()-1));
			}
		}
		return clone;
	}
}
