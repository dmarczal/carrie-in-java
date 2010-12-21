package br.ufpr.c3sl.model;

import java.sql.Timestamp;
import java.util.Date;

public class Mistake {
	
	private byte[] Object;
	
	private String exercise;
	private String learningObject;
	
	private MistakeInfo mistakeInfo;
	
	private Long createdAt;
	
	private User user;
	
	private int id;
	
	public Mistake(){
		createdAt = new Date().getTime();
		mistakeInfo = new  MistakeInfo("", "", "", "");
	}
	
	public Timestamp getCreatedAt() {
		return new Timestamp(createdAt);
	}
	
	public void setCreatedAt(Long time) {
		this.createdAt = time;
	}
	
	public byte[] getObject() {
		return Object;
	}
	
	public void setObject(byte[] Object) {
		this.Object = Object;
	}
	
	public void setMistakeInfo(MistakeInfo mistakeInfo) {
		this.mistakeInfo = mistakeInfo;
	}
	
	public String getTitle() {
		return mistakeInfo.getTitle();
	}
	
	public void setTitle(String title) {
		this.mistakeInfo.setTitle(title);
	}
	
	public String getExercise() {
		return exercise;
	}
	
	public void setExercise(String exercise) {
		this.exercise = exercise;
	}
	
	public String getLearningObject() {
		return learningObject;
	}
	
	public void setLearningObject(String learningObject) {
		this.learningObject = learningObject;
	}
	
	public String getDescription() {
		return mistakeInfo.getDescription();
	}
	
	public void setDescription(String description) {
		this.mistakeInfo.setDescription(description);
	}
	
	public String getAnswer() {
		return mistakeInfo.getAnswer();
	}
	
	public void setAnswer(String answer) {
		this.mistakeInfo.setAnswer(answer);
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getCorrectAnswer() {
		return mistakeInfo.getCorrectAnswer();
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.mistakeInfo.setCorrectAnswer(correctAnswer);
	}
}
