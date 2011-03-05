package org.c3sl.ufpr.br.main;

import org.c3sl.ufpr.br.constants.Constants;
import org.c3sl.ufpr.br.exercises.ExerciseFive;
import org.c3sl.ufpr.br.exercises.ExerciseFour;
import org.c3sl.ufpr.br.exercises.ExerciseOne;
import org.c3sl.ufpr.br.exercises.ExerciseSix;
import org.c3sl.ufpr.br.exercises.ExerciseThree;
import org.c3sl.ufpr.br.exercises.ExerciseTwo;

import br.ufpr.c3sl.view.applet.JAppletCarrie;

public class Fractal extends JAppletCarrie{
	
	private static final long serialVersionUID = 1L;
	
	public void init() {
		super.init("Fractal Simulator");
		//Nome é importante para conexao com banco de dados
		
		addPageFromHtmlFile(Constants.INTRODUCTION_PATH+"capa.html");
		
		for (int i = 1; i <= 15; i++) {
			addPageFromHtmlFile(Constants.INTRODUCTION_PATH+"introduction"+i+".html");
		}
		
		ExerciseTwo exerTwo = new ExerciseTwo();
		ExerciseThree exerThree = new ExerciseThree();
		ExerciseFour exerFour = new ExerciseFour();
		
		ExerciseFive exerFive = new ExerciseFive();
		ExerciseSix exerSix = new ExerciseSix();
		
		exerTwo.addExerciseListener(exerThree);
		exerTwo.addExerciseListener(exerFour);
		
		exerFive.addExerciseListener(exerSix);
		
		addPanel("Exercício 1", new ExerciseOne());
		addPanel("Exercício 2", exerTwo);
		addPanel("Exercício 3", exerThree);
		addPanel("Exercício 4", exerFour);
		addPanel("Exercício 5", exerFive);
		addPanel("Exercício 6", exerSix);
	}
}
