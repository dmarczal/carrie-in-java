package br.ufpr.c3sl.model;

import java.sql.Timestamp;

import org.simpleframework.xml.Element;

import br.ufpr.c3sl.client.webservices.XMLHitDAO;
import br.ufpr.c3sl.session.Session;
import br.ufpr.c3sl.view.principal.JpCarrie;

public class Hit {
	
	@Element(required=false)
	private Timestamp created_at;
	@Element(required=false)
	private Long id;
	@Element
	private String cell;
	@Element
	private String oa;
	@Element
	private String exercise;
	@Element
	private String answer;
	@Element
	private String correct_answer;
	
	@Element
	private Long user_id;
	
	public Hit(){
		this.oa = JpCarrie.getInstance().getName();
		this.user_id = Session.getCurrentUser().getId();
	}
	
	public Timestamp getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(Timestamp created_at) {
		this.created_at = created_at;
	}

	public Long getId() {
		return id;
	}

	public String getCell() {
		return cell;
	}

	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getOa() {
		return oa;
	}

	public void setOa(String oa) {
		this.oa = oa;
	}

	public String getExercise() {
		return exercise;
	}

	public void setExercise(String exercise) {
		this.exercise = exercise;
	}

	public String getAnswer() {
		return answer.replaceAll("l","ℓ");
	}


	public void setAnswer(String answer) {
		this.answer = answer.replaceAll("ℓ","l");
	}


	public String getCorrectAnswer() {
		return correct_answer.replaceAll("l","ℓ");
	}

	public void setCorrectAnswer(String correct_answer) {
		this.correct_answer = correct_answer.replaceAll("ℓ","l");
	}

	public Long getUserId() {
		return user_id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Save this object in database
	 */
	public void save(){
		Thread save = new Thread(new Runnable() {
			@Override
			public void run() {
				XMLHitDAO.save(Hit.this);
			}
		});
		save.start();
	}
}
