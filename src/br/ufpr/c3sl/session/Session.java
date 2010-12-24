package br.ufpr.c3sl.session;

import br.ufpr.c3sl.model.User;

public class Session {

	private static User currentUser = null;
	
	/**
	 * set current user
	 * @param user the actual user
	 */ 
	public static void setCurrentUser(User user){
		currentUser = user;
	}

	/**
	 * get current user
	 * @return currentUser the actual user
	 */ 
	public static User getCurrentUser(){
		return currentUser;
	}
}
