package org.c3sl.ufpr.br.correction;

import java.io.Serializable;

import br.ufpr.c3sl.model.MistakeInfo;
import br.ufpr.c3sl.virtualkeyboard.mathevaluator.MathEvaluator;

public class CorrectionOne implements Correction, Serializable{

	private static final long serialVersionUID = 2412805759758300505L;
	
	private double firstAnswer;
	private double correctAnswer, answerDouble;
	
	transient private MistakeInfo mistake;
	
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
			 saveState(answer, "answer != 0");
			 return false;
		}
		
		if(row == 4){
			math.addVariable("n", 0.8);
			math.setExpression(firstAnswer + "/( 2 ^ n )");	
		}else
			math.setExpression(firstAnswer + "/" + Math.pow(2, row));
		
		correctAnswer = math.getValue();
		
		if (!(correctAnswer == answerDouble)){
			if (row == 4)
				saveState(answer, (firstAnswer + "/( 2 ^ n )"));
			else
				saveState(answer, correctAnswer+"");
			
			return false;
		}
		
		return (correctAnswer == answerDouble);
	}
	
	private void saveState(String answer, String correctAnswer){
		mistake = new MistakeInfo(
				"Exercise One Error", answer, correctAnswer,
				"Erro no ensino de progressões geométrica");
	}
	
	public MistakeInfo getMistakeInfo(){
		return mistake;
	}
}
