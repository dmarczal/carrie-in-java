package org.c3sl.ufpr.br.correction;

import br.ufpr.c3sl.mistakes.MistakeOccurrence;
import br.ufpr.c3sl.model.MistakeInfo;
import br.ufpr.c3sl.virtualkeyboard.mathevaluator.MathEvaluator;

public class CorrectionFive extends AbstractCorrection {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean isCorrect(String answer, int row, int column) {
		MathEvaluator math = new MathEvaluator();
		math.addVariable("ℓ", 311.43);
		math.addVariable("n", 0.8);
		math.setExpression(answer);

		answerDouble = math.getValue();

		String expression = "";

		switch (column) {
		case 2:
			if (row == 5)
				expression = "ℓ/ (3 ^ n)";
			else
				expression = "ℓ/3^" + row;
			break;
		case 3:
			if (row == 5)
				expression = "( 4 ^ n ) * 3 * (ℓ/ 3 ^ n )";
			else
				expression = "(4^" + row + ")* 3 * (ℓ/3^" + row +")";
			break;
		default:
			break;
		}
		
		math.setExpression(expression);

		correctAnswer = math.getValue();

		int comparationReturn = super.compareDouble(correctAnswer, answerDouble, 0.009);

		switch (comparationReturn) {
		case 1:
			this.message = null;
			return true;
		case -1:
			this.message = MistakeOccurrence.getMistakeMessage("5", 3, Integer.parseInt(row+""+column));
			saveState(answer, expression, row, column);
			return false;
		case -2:
			this.message = MistakeOccurrence.getMistakeMessage("5", 3, Integer.parseInt(row+""+column));
			saveState(answer, expression, row, column);	
			return false;
		default:
			this.message = null;
			return false;
		}
	}

	private void saveState(String answer, String correctAnswer, int row, int column){
		mistake = new MistakeInfo(
		"Erro no exercicio 5 Iteração "+ row + " " + column, answer, correctAnswer,
		"Erro no ensino de progressões geométrica no exercício 5");
	}
}

