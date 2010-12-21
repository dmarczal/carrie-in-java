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
 * The Class ElementCompositeInitial created on Mar 28, 2009.
 * 
 */
@SuppressWarnings("serial")
public abstract class ElementCompositeInitial 
	extends ElementOfFormula implements ElementOfFormulaIF{

	/* (non-Javadoc)
	 * @see virtualkeyboard.formula.ElementOfFormulaIF#getLastElementAdded()
	 * return the last Element Added at the formula. The last added is 
	 * last but one, because the last is the cursor
	 */
	public ElementOfFormula getLastElementAdded() {
		int numbersOfElements = this.getComponentCount()-2;
		if (numbersOfElements < 0)
			return null;
		else
			return (ElementOfFormula) this.getComponent(numbersOfElements);
	}
}
