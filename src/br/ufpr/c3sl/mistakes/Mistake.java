package br.ufpr.c3sl.mistakes;

import java.util.HashMap;

public class Mistake {
	
	private String id;
	private String msg;
	
	private int numberOfOccurrence;
	private int maxNumberOccurrence;
	
	private HashMap<Integer, Integer> ids = new HashMap<Integer, Integer>();
	
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
	
	public void setNewId(int newid){
		if (!ids.containsKey(newid))
			ids.put(newid, 0);
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
	
	/**
	 * Get the message
	 * @param id the other id for this mistake
	 * @return message if the number of occurrence is equals to max number of occurrence
	 * 				   else return null and increase the number of occurrence
	 */
	public String getMessage(int newid){
		if (ids.get(newid) < this.maxNumberOccurrence-1){
			ids.put(newid, ids.get(newid)+1);
			return null;
		}else
			return msg;
	}
}
