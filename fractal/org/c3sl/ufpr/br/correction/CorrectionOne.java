package org.c3sl.ufpr.br.correction;

import br.ufpr.c3sl.mistakes.MistakeOccurrence;
import br.ufpr.c3sl.virtualkeyboard.mathevaluator.MathEvaluator;

public class CorrectionOne extends AbstractCorrection{

	private static final long serialVersionUID = 1L;
	
	public CorrectionOne() {
		super(1);
	}

	private double firstAnswer;

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

		String expr = "";
		
		if(row == 4){
			expr = firstAnswer + "/ 2 ^ n";	
		}else
			expr = firstAnswer + "/ 2 ^ " + row;
		
		math.addVariable("n", 0.8);
		math.setExpression(expr);
		
		correctAnswer = math.getValue();

		int comparationReturn = compareDouble(correctAnswer, answerDouble, 0.001);

		String stVCorrectAnswer = ""; 
		if (row == 4)
			stVCorrectAnswer = (firstAnswer + "/( 2 ^ n )");
		else
			stVCorrectAnswer = correctAnswer+"";
		
		switch (comparationReturn) {
		case 1:
			this.message = null;
			saveHit(answer, stVCorrectAnswer, row, column);
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
}