package org.c3sl.ufpr.br.correction;

import java.io.Serializable;

import br.ufpr.c3sl.mistakes.MistakeOccurrence;
import br.ufpr.c3sl.model.MistakeInfo;
import br.ufpr.c3sl.virtualkeyboard.mathevaluator.MathEvaluator;

public class CorrectionOne implements Correction, Serializable{

	private static final long serialVersionUID = 2412805759758300505L;

	private double firstAnswer;
	private double correctAnswer, answerDouble;

	transient private MistakeInfo mistake;

	private String message = null;

	@Override
	public boolean isCorrect(String answer, int row, int column) {
		MathEvaluator math = new MathEvaluator();
		math.addVariable("n", 0.8);
		math.setExpression(answer);
		answerDouble = math.getValue();
		math.reset();

		if (row == 0)
			if (answerDouble != 0)
				firstAnswer = answerDouble;
			else{
				saveState(answer, "answer != 0", row, column);
				return false;
			}

		if(row == 4){
			math.addVariable("n", 0.8);
			math.setExpression(firstAnswer + "/( 2 ^ n )");	
		}else
			math.setExpression(firstAnswer + "/" + Math.pow(2, row));

		correctAnswer = math.getValue();

		int comparationReturn = compareDouble(correctAnswer, answerDouble, 0.009);

		String stVCorrectAnswer = ""; 
		if (row == 4)
			stVCorrectAnswer = (firstAnswer + "/( 2 ^ n )");
		else
			stVCorrectAnswer = correctAnswer+"";
		
		switch (comparationReturn) {
		case 1:
			this.message = null;
			return true;
		case -1:
			this.message = MistakeOccurrence.getMistakeMessage("1", 0);
			saveState(answer, stVCorrectAnswer, row, column);
			return false;
		case -2:
			this.message = MistakeOccurrence.getMistakeMessage("2", 0);
			saveState(answer, stVCorrectAnswer, row, column);	
			return false;
		default:
			this.message = null;
			return false;
		}
	}


	/* 
	 * verify if the answer is similar to the answer
	 * @param correctAnswer The answer expected
	 * @param answer The answer entered
	 * @param error The error accepted
	 * @return 1 If correct
	 * @return -1 If size smaller than the correct size
	 * @return -2 If size bigger than the correct size
	 */
	private int compareDouble(double correctAnswer, double answer, double error){
		correctAnswer = correctAnswer * 1000;

		int changeValue = (int) (correctAnswer);
		correctAnswer = (double) changeValue / 1000;

		answer = answer * 1000;
		changeValue = (int) (answer);
		answer = (double) changeValue / 1000;

		int greaterOrSmaller = (answer < correctAnswer)?-1:-2;

		if (correctAnswer == answer)
			return 1;
		if (correctAnswer + 0.001 == answer)
			return 1;    	


		correctAnswer = correctAnswer * 100;
		changeValue = (int) (correctAnswer);
		correctAnswer = (double) changeValue / 100;

		if (correctAnswer == answer)
			return 1;
		if (correctAnswer + 0.01 == answer)
			return 1;    	

		correctAnswer = correctAnswer * 10;
		changeValue = (int) (correctAnswer);
		correctAnswer = (double) changeValue / 10;
		if (correctAnswer == answer)
			return 1;
		if (correctAnswer + 0.1 == answer)
			return 1;

		return greaterOrSmaller;
	}



	private void saveState(String answer, String correctAnswer, int row, int column){
				mistake = new MistakeInfo(
				"Exercise na Iteração "+ row + " " + column, answer, correctAnswer,
		"Erro no ensino de progressões geométrica");
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
}