package br.ufpr.c3sl.model;

import java.sql.Timestamp;
import java.util.Date;

public class User {
	
	private String email;
	private String mode;
	private int id;
	private Long time;
	private boolean newRecord;
	
	public User(){
		time = new Date().getTime();
		newRecord = true;
	}
	
	public Timestamp getCreatedAt() {
		return new Timestamp(time);
	}

	public void setCreatedAt(Long time) {
		this.time = time;
	}

	public User(String email) {
		this();
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMode() {
		return mode;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public boolean isNewRecord(){
		return newRecord;
	}
	
	public void setNotNewRecord(){
		newRecord = false;
	}
	
	public void setNewRecord(){
		newRecord = true;
	}
}
