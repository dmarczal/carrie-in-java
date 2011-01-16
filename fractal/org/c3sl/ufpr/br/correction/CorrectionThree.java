package org.c3sl.ufpr.br.correction;

import br.ufpr.c3sl.mistakes.MistakeOccurrence;
import br.ufpr.c3sl.model.MistakeInfo;
import br.ufpr.c3sl.virtualkeyboard.mathevaluator.MathEvaluator;

public class CorrectionThree extends AbstractCorrection{

	private static final long serialVersionUID = 3183228478248324360L;

	@Override
	public boolean isCorrect(String answer, int row, int column) {
		MathEvaluator math = new MathEvaluator();
		math.addVariable("ℓ", 311.43);
		math.addVariable("n", 0.8);
		math.setExpression(answer);

		answerDouble = math.getValue();

		String expression = "";

		switch (row) {
		case 5:
			if (column == 3)
				expression = "( 3 ^ ( n + 1 ) ) * ℓ/ ( 2 ^ n ) ";
			else
				expression = "ℓ/ ( 2 ^ n )";
			break;
		default:
			if (column == 3)
				expression = "3^"+(row+1) + "*" + "ℓ/2^" + row;
			else
				expression = "ℓ/2^" + row;
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
			this.message = MistakeOccurrence.getMistakeMessage("3", 3, Integer.parseInt(row+""+column));
			saveState(answer, expression, row, column);
			return false;
		case -2:
			this.message = MistakeOccurrence.getMistakeMessage("3", 3, Integer.parseInt(row+""+column));
			saveState(answer, expression, row, column);	
			return false;
		default:
			this.message = null;
			return false;
		}
	}
	
	private void saveState(String answer, String correctAnswer, int row, int column){
		mistake = new MistakeInfo(
		"Erro no exercicio 3 Iteração "+ row + " " + column, answer, correctAnswer,
		"Erro no ensino de progressões geométrica no exercício 3");
	}

}
