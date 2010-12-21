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


/**
 * The Interface ElementOfFormulaIF created on Mar 28, 2009.
 * 
 */
public interface ElementOfFormulaIF extends Cloneable {
	
	/**
	 * Adds the element.
	 * 
	 * @param element the element
	 * 
	 * @return true, if successful
	 */
	boolean addElement(ElementOfFormula element);
	
	/**
	 * Adds the element.
	 * 
	 * @param element the element
	 * 
	 * @return true, if successful
	 */
	boolean addElement(String element);
	
	/**
	 * Removes the last element.
	 * 
	 * @return true, if successful
	 */
	boolean removeLastElement();
	
	/**
	 * Checks if is valid element.
	 * 
	 * @return true, if is valid element
	 */
	boolean isValidElement();
	
	/**
	 * Gets the father.
	 * 
	 * @return the father
	 */
	ElementOfFormula getFather();
	
	/**
	 * Gets the last element added.
	 * 
	 * @return the last element added
	 */
	ElementOfFormula getLastElementAdded();
	
	/**
	 * Gets the clone.
	 * 
	 * @return the clone
	 */
	ElementOfFormula getClone();
	
	/**
	 * Hide cursor.
	 */
	void hideCursor();
	
	/**
	 * Show cursor.
	 */
	void showCursor();
}
