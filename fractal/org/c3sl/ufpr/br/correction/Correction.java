package org.c3sl.ufpr.br.correction;

import br.ufpr.c3sl.model.MistakeInfo;

public interface Correction {
	
	boolean isCorrect(String answer, int row, int column);
	boolean thereIsMessage();
	MistakeInfo getMistakeInfo();
	String getErrorMessage();
	
}
