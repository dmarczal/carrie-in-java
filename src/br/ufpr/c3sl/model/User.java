package br.ufpr.c3sl.model;

import java.sql.Timestamp;


public class User {
	
	private String email;
	private Long id;
	private Long createdAt;
	
	public User(String email) {
		super();
		this.email = email;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Timestamp getCreateAtTime(){
		return new Timestamp(createdAt);
	}
	
	@Override
	public String toString() {
		return email+"/"+id+"/"+createdAt;
	}
}
