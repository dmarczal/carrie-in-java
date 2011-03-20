package org.c3sl.ufpr.br.correction;

import java.io.Serializable;

import br.ufpr.c3sl.model.Hit;
import br.ufpr.c3sl.model.MistakeInfo;

public abstract class AbstractCorrection implements Correction, Serializable {
	
	private static final long serialVersionUID = 1L;

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
		
		int greaterOrSmaller = (answer < correctAnswer)?-1:-2;

		final double TOLERANCE = 0.00001;
		final double TRUCAMENTO = 10000;
		
		if (Math.abs(correctAnswer - answer) <= TOLERANCE) {
			return 1;  
		}
		
		correctAnswer = correctAnswer * TRUCAMENTO;

		int changeValue = (int) (correctAnswer);
		correctAnswer = (double) changeValue / TRUCAMENTO;
				
		answer = answer * TRUCAMENTO;
		changeValue = (int) (answer);
		answer = (double) changeValue / TRUCAMENTO;
		
//		System.out.println("C "+ correctAnswer);
//		System.out.println("A "+ answer);
		
		if ((correctAnswer ==  answer) || Math.abs(correctAnswer - answer) <= TOLERANCE) {
			return 1;  
		}
		return greaterOrSmaller;
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
