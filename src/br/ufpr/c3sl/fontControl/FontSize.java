package br.ufpr.c3sl.fontControl;

import java.util.Observable;

// TODO: Auto-generated Javadoc
/**
 * The Class FontSize created on Mar 31, 2009.
 *  Have the logic for the font size 
 * @author Diego Marczal
 */
public class FontSize extends Observable{
	private static FontSize fsc;

	private static final int INITIAL = 0;
	private static final int INCREMENT = 1;
	private static final int DEINCREMENT = 2;
	private static final int LOWER_LIMIT = 7;
	private static final int UPPER_LIMIT = 40;
	
	private int countSizeChange = 0;
    private int update = -1;
    
    private boolean canDecrement = true;
    private boolean canIncrement = true;

	/**
	 * Gets the single instance of FontSize.
	 * 
	 * @return single instance of FontSize
	 */
	public static FontSize getInstance(){
		if (fsc == null){
			fsc = new FontSize();
			fsc.addObserver(new FontControl());
			return fsc;
		}else
			return fsc;
	}

	/**
	 * Instantiates a new font size.
	 */
	public FontSize(){
		countSizeChange = 0;
	}

	/**
	 * Begin size.
	 */
	public void beginSize() {
		update = INITIAL;
		setChanged();
		notifyObservers();
		countSizeChange = 0;
	}

	/**
	 * Increment size.
	 */
	public void incrementSize() {
		if (!canIncrement)
			return;
		
		update = INCREMENT;
		countSizeChange++;
		setChanged();
		notifyObservers();
	}

	/**
	 * Decrement size.
	 */
	public void decrementSize() {
		if (!canDecrement)
			return;
		
		update = DEINCREMENT;
		countSizeChange--;
		setChanged();
		notifyObservers();
	}

	/**
	 * Gets the size.
	 * 
	 * @return the size
	 */
	public int getSize(int value){
		int num = sumOrSubtractOrKeep(value);
		return num;
	}

	private int sumOrSubtractOrKeep(int value){
		int newValue = 1;
		switch (update) {
		case INCREMENT:
			newValue = value + 1;
			if (newValue > LOWER_LIMIT)
				canDecrement = true;
			if (newValue > UPPER_LIMIT)
				canIncrement = false;
			return newValue;
		case DEINCREMENT:
			newValue = value - 1;
			if (newValue < LOWER_LIMIT)
				canDecrement = false;
			if (newValue < UPPER_LIMIT)
				canIncrement = true;
			return newValue;
		case INITIAL:
			canIncrement = true;
			canDecrement = true;
			newValue = (value - countSizeChange);
			return newValue;
		default:
			return value;
		}
	}
}
