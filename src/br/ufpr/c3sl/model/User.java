package br.ufpr.c3sl.model;

import java.sql.Timestamp;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class User {
	
	@Element
	private String email;
	@Element
	private Long id;
	@Element
	private Timestamp created_at;
	
	public User(){}
	
	public User(String email) {
		super();
		this.email = email;
	}

	public Timestamp getCreatedAt() {
		return created_at;
	}

	public void setCreatedAt(Timestamp created_at) {
		this.created_at = created_at;
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
	
	@Override
	public String toString() {
		return email+"/"+id+"/"+created_at;
	}
}
