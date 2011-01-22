package org.c3sl.ufpr.br.main;

import org.c3sl.ufpr.br.constants.Constants;
import org.c3sl.ufpr.br.exercises.ExerciseFive;
import org.c3sl.ufpr.br.exercises.ExerciseOne;
import org.c3sl.ufpr.br.exercises.ExerciseSix;
import org.c3sl.ufpr.br.exercises.ExerciseThree;
import org.c3sl.ufpr.br.exercises.ExerciseTwo;
import org.c3sl.ufpr.br.exercises.tables.ExerciseFour;

import br.ufpr.c3sl.view.applet.JAppletCarrie;

@SuppressWarnings("serial")
public class Fractal extends JAppletCarrie{
	
	public void init() {
		super.init("Fractal Simulator");
		//Nome é importante para conexao com banco de dados
		
		addPageFromHtmlFile(Constants.INTRODUCTION_PATH+"capa.html");
		
		for (int i = 1; i <= 15; i++) {
			addPageFromHtmlFile(Constants.INTRODUCTION_PATH+"introduction"+i+".html");
		}
		
		addPanel("Exercício 1", new ExerciseOne());
		addPanel("Exercício 2", new ExerciseTwo());
		addPanel("Exercício 3", new ExerciseThree());
		addPanel("Exercício 4", new ExerciseFour());
		addPanel("Exercício 5", new ExerciseFive());
		addPanel("Exercício 6", new ExerciseSix());
	}
}
