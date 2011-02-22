package org.c3sl.ufpr.br.correction;

import br.ufpr.c3sl.mistakes.MistakeOccurrence;
import br.ufpr.c3sl.virtualkeyboard.mathevaluator.MathEvaluator;

public class CorrectionSix extends AbstractCorrection {

	public CorrectionSix() {
		super(6);
	}

	private static final long serialVersionUID = -1456505415418648388L;

	@Override
	public boolean isCorrect(String answer, int row, int column) {
		MathEvaluator math = new MathEvaluator();
		math.addVariable("ℓ", 311.43);
		math.addVariable("n", 0.8);
		math.setExpression(answer);

		answerDouble = math.getValue();

		String expression = "";

		switch (column) {
		case 3:
			if (row == 5)
				expression = " ( ( ( ℓ/ (3 ^ n)  ) ^ 2 ) * sqrt ( 3 ) ) / 4 ";
			else
				expression = " ( ( ( ℓ/ (3 ^ " + row + " )  ) ^ 2 )) * sqrt ( 3 ) / 4 ";
			break;
		case 4:
			if (row == 5)
				expression = " 3 * ( 4 ^ ( n - 1 ) )";
			else
				if (row == 0)
					expression = "1";
				else
					expression = "3 * (4 ^ (" + row + "-1 ))";
			break;
		case 5:
			if (row == 5)
				expression = "3 * (4 ^ (n -1 )) * ( ( ( ℓ/ (3 ^ n) ) ^ 2 ) * sqrt ( 3 ) ) / 4 ";
			else
				if (row == 0)
					expression = "( ( ℓ ) ^ 2 ) * sqrt ( 3 )  / 4";
				else
					expression = "3 * (4 ^ (" + row + "-1 ))" + " * ( ( ( ℓ/3^" + row + " ) ^ 2 ) * sqrt ( 3 ) ) / 4 ";
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
			saveHit(answer, expression, row, column);
			return true;
		case -1:
			this.message = MistakeOccurrence.getMistakeMessage("4", 3, Integer.parseInt(row+""+column));
			saveState(answer, expression, row, column);
			return false;
		case -2:
			this.message = MistakeOccurrence.getMistakeMessage("4", 3, Integer.parseInt(row+""+column));
			saveState(answer, expression, row, column);	
			return false;
		default:
			this.message = null;
			return false;
		}
	}
}
