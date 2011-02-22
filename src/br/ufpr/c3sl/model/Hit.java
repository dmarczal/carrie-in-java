package br.ufpr.c3sl.model;

import java.sql.Timestamp;

import br.ufpr.c3sl.dao.HitDAO;
import br.ufpr.c3sl.daoFactory.DAOFactory;
import br.ufpr.c3sl.session.Session;
import br.ufpr.c3sl.view.principal.JpCarrie;

public class Hit {
	
	private Long createdAt;
	private User user;
	private Long id;
	
	private String cell;
	private String learningObject;
	private String exercise;
	
	private String answer;
	private String correctAnswer;
	
	public Hit(){
		this.learningObject = JpCarrie.getInstance().getName();
		user = Session.getCurrentUser();
	}
	
	public Timestamp getCreatedAtTime() {
		return new Timestamp(createdAt);
	}
	
	public Long getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCell() {
		return cell;
	}
	
	public void setCell(String cell) {
		this.cell = cell;
	}
	
	public String getLearningObject() {
		return learningObject;
	}
	
	public void setLearningObject(String learningObject) {
		this.learningObject = learningObject;
	}
	
	public String getExercise() {
		return exercise;
	}
	
	public void setExercise(String exercise) {
		this.exercise = exercise;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	/**
	 * Save this object in database
	 */
	public void save(){
		DAOFactory dao = DAOFactory.getDAOFactory(DAOFactory.DATABASE_CHOOSE);
		HitDAO hitdao = dao.getHitDAO();
		
		hitdao.insert(this);
	}
}
