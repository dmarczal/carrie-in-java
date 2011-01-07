package org.c3sl.ufpr.br.correction;

import br.ufpr.c3sl.mistakes.MistakeOccurrence;
import br.ufpr.c3sl.model.MistakeInfo;
import br.ufpr.c3sl.virtualkeyboard.mathevaluator.MathEvaluator;

public class CorrectionTwo extends AbstractCorrection {

	private static final long serialVersionUID = 8032322462671188618L;

	@Override
	public boolean isCorrect(String answer, int row, int column) {
		MathEvaluator math = new MathEvaluator();
		math.addVariable("ℓ", 311.43);
		math.addVariable("n", 0.8);
		math.setExpression(answer);

		answerDouble = math.getValue();

		String expression = "";

		if (row == 5)
			expression = "ℓ/ ( 2 ^ n )";
		else
			expression = "ℓ/2^" + row;

		math.setExpression(expression);

		correctAnswer = math.getValue();

		int comparationReturn = super.compareDouble(correctAnswer, answerDouble, 0.009);

		switch (comparationReturn) {
		case 1:
			this.message = null;
			return true;
		case -1:
			this.message = MistakeOccurrence.getMistakeMessage("3", 3, row);
			saveState(answer, expression, row, column);
			return false;
		case -2:
			this.message = MistakeOccurrence.getMistakeMessage("3", 3, row);
			saveState(answer, expression, row, column);	
			return false;
		default:
			this.message = null;
			return false;
		}
	}
	
	private void saveState(String answer, String correctAnswer, int row, int column){
		mistake = new MistakeInfo(
		"Erro no exercicio 2 Iteração "+ row + " " + column, answer, correctAnswer,
		"Erro no ensino de progressões geométrica no exercício 2");
	}

}
