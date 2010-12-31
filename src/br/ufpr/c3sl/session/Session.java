package br.ufpr.c3sl.session;

import br.ufpr.c3sl.model.User;

public class Session {

	private static User currentUser = null;
	private static String mode = null;
	
	
	/**
	 * get current mode
	 * @return mode the actual mode
	 */ 
	public static String getMode() {
		return mode;
	}

	/**
	 * set current mode (local or Server)
	 * @param mode the actual model
	 */ 
	
	public static void setMode(String mode) {
		Session.mode = mode;
	}

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
