package br.ufpr.c3sl.model;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementArray;
import org.simpleframework.xml.Root;


@Root
public class MistakesArray {
	
	@Attribute(required=false)
	private String type; 
	
	@ElementArray(required=false)
	private Mistake[] mistakes;
	
	@Element(required=false)
	private User user;

	public Mistake[] getMistakes(){
		updateUser();
		return mistakes;
	}
	
	public void setMistakes(Mistake[] mistakes){
		this.mistakes = mistakes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	private void updateUser(){
		if (mistakes != null && mistakes[0] != null){
			if (mistakes[0].getUser() == null)
				for (Mistake mistake : mistakes) {
					mistake.setUser(user);
				}
		}
	}
}