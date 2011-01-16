package br.ufpr.c3sl.view.glossary;

/** Class CompleterNofity,
 * Use to identify changed on jtfieldCompleter
 * @author Diego Marczal
 */

import java.util.Observable;

public class CompleterNotify extends Observable{
	
	private static CompleterNotify completer;
	
	/** Construct for a CompleterNotify
	 *  is set as private to use the Singleton  
	 */
	private CompleterNotify(){}
	
	/** Method to get a singleton
	 * 	instance of CompleterNotify
	 */
	public static CompleterNotify getInstance(){
		if (completer == null){
			completer = new CompleterNotify();
			return completer;
		}else
			return completer;
	}
	
	/** Method to set changed
	 */
	public void changedState(){
		setChanged();
		notifyObservers();
	}
}
