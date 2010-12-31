package br.ufpr.c3sl.model;

import java.sql.Timestamp;

public class Retroaction {
	
	private Long id;
	private Long createdAt;
	private User user;
	private Mistake mistake;
	
	public Retroaction(){
		user = null;
		mistake = null;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Timestamp getCreatedAtTime() {
		return new Timestamp(createdAt);
	}
	
	public Long getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Mistake getMistake() {
		return mistake;
	}
	public void setMistake(Mistake mistake) {
		this.mistake = mistake;
	}
}
