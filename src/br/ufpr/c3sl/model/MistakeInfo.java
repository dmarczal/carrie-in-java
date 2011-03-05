package br.ufpr.c3sl.model;

public class MistakeInfo {

	private static final long serialVersionUID = 1L;
	
	private String description;
	private String answer;
	private String correctAnswer;
	private String title;
	private String cell;
	
	public MistakeInfo(String title, String answer, String correctAnswer, String description) {
		super();
		this.description = description;
		this.answer = answer;
		this.correctAnswer = correctAnswer;
		this.title = title;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title; // maker softwell
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
	public void setCell(String cell) {
		this.cell = cell;
	}

	public String getCell() {
		return this.cell;
	}
}
