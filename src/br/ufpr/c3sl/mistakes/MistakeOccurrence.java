package br.ufpr.c3sl.mistakes;

public class MistakeOccurrence {

	private static MistakesMessages messages = new MistakesMessages();
	
	/**
	 * 
	 * @param id
	 * @param maxNumberOfOccureence
	 * @return Message if the number of occurrence of the mistake achieve the
	 * 				   maxNumberOfOccurrence
	 */
	public static String getMistakeMessage(String id, int maxNumberOfOccurrence){

		Mistake mistake = messages.getValueOfKey(id);
		
		if (mistake == null)
			System.err.println("Message for error "+ id +" not found\n");
		else{
			mistake.setMaxNumberOfOccurrence(maxNumberOfOccurrence);
			return mistake.getMessage();
		}
		return null;
	}
}
