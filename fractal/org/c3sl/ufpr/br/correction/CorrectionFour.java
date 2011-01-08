package org.c3sl.ufpr.br.correction;

import br.ufpr.c3sl.model.MistakeInfo;

public class CorrectionFour extends AbstractCorrection{

	@Override
	public boolean isCorrect(String answer, int row, int column) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void saveState(String answer, String correctAnswer, int row, int column){
		mistake = new MistakeInfo(
		"Erro no exercicio 4 Iteração "+ row + " " + column, answer, correctAnswer,
		"Erro no ensino de progressões geométrica no exercício 4");
	}

}
