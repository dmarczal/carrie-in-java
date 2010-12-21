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
import br.ufpr.c3sl.virtualkeyboard.main.VirtualKeyBoardMain;


/**
 * The Class NumberPanel created on Mar 28, 2009.
 * 
 */
@SuppressWarnings("serial")
public class Number extends ElementOfFormula {

	private JLabel jlNumber = new JLabel();
	
	/** The Constant COMMA. */
	private static final String COMMA = ",";

	/**
	 * Construct for a Number
	 * Initializes a newly Number with the name 'Number'.
	 * Add the jlNumber at position (this.getComponentCount()-1) because the
	 * last element is the cursor
	 */
	public Number(){
		this.setName("Number");
		this.add(jlNumber, (this.getComponentCount()-1));
	}

	/**
	 * Method to clear the number.
	 */
	public void clearNumber(){
		setNumber("");
	}

	/**
	 * Method to set the a new number.
	 * 
	 * @param number the number
	 */
	public void setNumber(String number){
		this.jlNumber.setText(number);
	}

	/**
	 * Method to get the actual number.
	 * 
	 * @return the number
	 */
	public String getNumber(){
		return this.jlNumber.getText();
	}

	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormula#addElement(java.lang.String)
	 * Method to add a number,
	 * @param String number
	 * return true if added with successfully, otherwise return false
	 */
	public boolean addElement(String number){
		if(number.equals(COMMA) && !contaisComma() && isEmpty()){
			setNumber("0" + number);
			return true;
		}else
			if(number.equals(COMMA) && !contaisComma()){
				setNumber(getNumber() + number);
				return true;
			}else
				if(!number.equals(COMMA)){
					setNumber(getNumber() + number);
					return true;
				}else
					return false;
	}

	/**
	 * Method to to check if number is not empty.
	 * 
	 * @return true if is empty and false if not
	 */
	public boolean isEmpty(){
		return getNumber().equals("");
	}

	/**
	 * Contains comma.
	 * 
	 * @return true, if contains comma, otherwise return false
	 */
	public boolean contaisComma(){
		return getNumber().contains(",");
	}

	/**
	 * Correct comma if necessary.
	 * if number 10, change to 10 
	 */
	public void correctCommaIfNecessary(){
		if (contaisComma() && 
				getNumber().indexOf(COMMA) == getNumber().length()-1){
			setNumber(getNumber().replace(COMMA, ""));
		}
	}

	/*
	 * @see virtualkeyboard.formula.ElementCompositeOfFormula#removeLastElement()
	 * Remove lastElement if the element is empty remove by itself from its father
	 * when it is removed from its father the elementsFocus must be its father
	 * 
	 * @return true if removed with successfully, otherwise return false
	 */
	public boolean removeLastElement(){

		String str = getNumber();
		if (str.length() > 1 )
		{
			StringBuilder w = new StringBuilder(str);  
			str = String.valueOf( w.deleteCharAt(str.length() - 1) );	
			setNumber(str);
		}else
		{
			VirtualKeyBoardMain.setElementFocus(this.getFather());
			this.clearNumber();
			this.getFather().remove(this);
			return true;
		}
		return false;
	}


	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormula#isValidElement()
	 */
	public boolean isValidElement(){
		//System.out.println("Number is valid");
		return !(contaisComma() && 
				getNumber().indexOf(COMMA) == getNumber().length()-1);
	}

	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#getLastElementAdded()
	 * Number is a simple element, not have a last element added
	 */
	public ElementOfFormula getLastElementAdded() {
		return null;
	}

	/* (non-Javadoc)
	 * Method overwritten
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#getClone()
	 * @return a deep clone of the instance of Number 
	 */
	public ElementOfFormula getClone(){
		Number clone = new Number();
		clone.add(new JLabel(), (clone.getComponentCount()-1));
		clone.addElement(getNumber());
		return clone;
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 * Method overwritten
	 * @return a string with the formula, e.g. 999	
	 */
	public String toString(){
		String s = getNumber().replace(',','.');
		return s;
	}
}
