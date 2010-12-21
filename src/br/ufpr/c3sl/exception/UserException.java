package br.ufpr.c3sl.exception;

@SuppressWarnings("serial")
public class UserException extends Exception {

	private String error;

	public UserException(String error) {
		super(error);
		this.error = error;
	}
	
	public String getError() {
		return error;
	}
}
