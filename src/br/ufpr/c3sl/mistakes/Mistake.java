package br.ufpr.c3sl.mistakes;

public class Mistake {
	
	private String id;
	private String msg;
	
	private int numberOfOccurrence;
	private int maxNumberOccurrence;
	
	public Mistake(String id, String msg) {
		super();
		this.id = id;
		this.msg = msg;
	}
	
	public void setMaxNumberOfOccurrence(int maxNumberOfOccurrence){
		this.maxNumberOccurrence = maxNumberOfOccurrence;
	}
	
	public String getId(){
		return id;
	}
	
	/**
	 * Get the message
	 * @return message if the number of occurrence is equals to max number of occurrence
	 * 				   else return null and increase the number of occurrence
	 */
	public String getMessage(){
		if (this.maxNumberOccurrence-1 > numberOfOccurrence){
			numberOfOccurrence++;
			return null;
		}else
			return msg;
	}
}
