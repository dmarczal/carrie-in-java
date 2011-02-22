package org.c3sl.ufpr.br.correction;

import java.io.Serializable;

import br.ufpr.c3sl.model.Hit;
import br.ufpr.c3sl.model.MistakeInfo;

public abstract class AbstractCorrection implements Correction, Serializable {

	private static final long serialVersionUID = 7354268779520647241L;

	protected double correctAnswer, answerDouble;
	transient protected MistakeInfo mistake;
	protected String message = null;
	
	private String exercise;
	
	public AbstractCorrection(int exercise){
		this.exercise = "Exercício "+exercise;
	}
	
	public MistakeInfo getMistakeInfo(){
		return mistake;
	}

	public boolean thereIsMessage(){
		if (this.message != null)
			return true;
		else
			return false;
	}

	public String getErrorMessage(){
		return this.message;
	}
	
	/** 
	 * verify if the answer is similar to the answer
	 * @param correctAnswer The answer expected
	 * @param answer The answer entered
	 * @param error The error accepted
	 * @return 1 If correct
	 * @return -1 If size smaller than the correct size
	 * @return -2 If size bigger than the correct size
	 */
	public int compareDouble(double correctAnswer, double answer, double error){
		double diference = Math.abs(answer - correctAnswer);
		if (diference == 0)
			return 1;
		else
			if (answer < correctAnswer)
				return -1;
			else
				return -2;
	}
	
	public void saveState(String answer, String correctAnswer, int row, int column){
		mistake = new MistakeInfo(
				"Erro no Exercício " + exercise + ", na iteração "+ row + " " + column,
				 answer, correctAnswer,
				"Ensino de progressões geométrica");
		mistake.setCell(row + " " + column);
	}
	
	public void saveHit(String answer, String correctAnswer, int row, int column){
		Hit hit = new Hit();
		hit.setAnswer(answer);
		hit.setCell(row+" "+column);
		hit.setCorrectAnswer(correctAnswer);
		hit.setExercise(exercise);
		hit.save();
	}
}
