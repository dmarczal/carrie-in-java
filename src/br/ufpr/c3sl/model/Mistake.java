package br.ufpr.c3sl.model;

import java.sql.Timestamp;

public class Mistake {
	
	private byte[] Object;
	
	private String exercise;
	private String learningObject;
	
	private MistakeInfo mistakeInfo;
	
	private Long createdAt;
	
	private User user;
	
	private Long id;
	
	public Mistake(){
		mistakeInfo = null;
		user = null;
	}
	
	public Timestamp getCreatedAtTime() {
		return new Timestamp(createdAt);
	}
	
	public void setCreatedAt(Long time) {
		this.createdAt = time;
	}
	
	public Long getCreatedAt(){
		return this.createdAt;
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
	
	public MistakeInfo getMistakeInfo(){
		return this.mistakeInfo;
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
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
}
