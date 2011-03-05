package br.ufpr.c3sl.model;

import java.sql.Timestamp;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import br.ufpr.c3sl.client.webservices.XMLRetroactionDAO;

@Root
public class Retroaction {
	
	@Element(required=false)
	private Long id;
	
	@Element(required=false)
	private Timestamp created_at;
	
	@SuppressWarnings("unused")
	@Element
	private Long mistake_id;
	
	private Mistake mistake;
	
	public Retroaction(){
		mistake = null;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Timestamp getCreatedAt() {
		return created_at;
	}
	
	public void setCreatedAt(Timestamp createdAt) {
		this.created_at = createdAt;
	}

	public Mistake getMistake() {
		return mistake;
	}
	public void setMistake(Mistake mistake) {
		this.mistake_id = mistake.getId();
		this.mistake = mistake;
	}
	
	/**
	 * Save the mistake
	 * @return Mistake saved
	 */
	public Retroaction save(){
		return XMLRetroactionDAO.save(this);
	}
	
}
