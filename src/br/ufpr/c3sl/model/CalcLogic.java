package br.ufpr.c3sl.model;

// TODO: Auto-generated Javadoc
/**
 * The Class CalcLogic created on 2008-2009.
 * @author Diego Marczal
 */
public class CalcLogic {	
	
	/** The current number. */
	private double currentNumber;
	
	/** The is set current number. */
	private boolean isSetCurrentNumber;
	
	/**
	 * Method to determine if the currentNumber is active.
	 * @return true, if checks if is set current number
	 */
	public boolean isSetCurrentNumber() {
		return isSetCurrentNumber;
	}

	/**
	 * Method to set state of current number.
	 * @param isSetCurrentNumber the is set current number
	 */
	public void setSetCurrentNumber(boolean isSetCurrentNumber) {
		this.isSetCurrentNumber = isSetCurrentNumber;
	}

	/**
	 * Method to return the current number.
	 * @return current Number :Double
	 */
	public double getCurrentNumber() {
		return currentNumber;
	}

	/**
	 * Method to set the value of current Number.
	 * @param number the number
	 */
	public void setCurrentNumber(String number) {
		this.currentNumber = convertToDouble(number);
	}

	/**
	 * Method to convert a String to a double.
	 * @param number the number
	 * @return Double number
	 */
	public double convertToDouble(String number){
		return Double.parseDouble(number);
	}
	
	/**
	 * Method to sum a value to the current number.
	 * @param number the number
	 * @return Double  (currentNumber + number)
	 */
	public double sum(String number){
		return (currentNumber += convertToDouble(number));
	}
	
	/**
	 * Method to subtract a value to the current number.
	 * @param number the number
	 * @return Double  (currentNumber - number)
	 */
	public double subtract(String number){
		return (currentNumber -= convertToDouble(number));
	}
	
	/**
	 * Method to divide the current number per a value.
	 * @param number the number
	 * @return Double (currentNumber/number)
	 */
	public double divide(String number){
		return (currentNumber /= convertToDouble(number));
	}
	
	/**
	 * Method to multiply the current number per a value.
	 * @param number the number
	 * @return Double (currentNumber/number)
	 */
	public double multiply(String number){
		return (currentNumber *= convertToDouble(number));
	}

	/**
	 * Method to raised a number per 2 and set this as a current number.
	 * @param number the number
	 * @return Double (currentNumber= pow(number,2))
	 */
	public double pow(String number){
		return (currentNumber = Math.pow(convertToDouble(number),2));
	}

	/**
	 * Method to take the square root of number and set this as a current number.
	 * @param s the s
	 * @return Double (currentNumber= sqrt(number))
	 */
	
	public double powY(String number) {
		return (currentNumber = Math.pow(currentNumber, convertToDouble(number)));
	}
	
	public double sqrtY(String number) {
		return (currentNumber = Math.pow(currentNumber, 1/convertToDouble(number)));
	}
	
	public double sqrt(String s){
		return (currentNumber = Math.sqrt(convertToDouble(s)));
	}
}
