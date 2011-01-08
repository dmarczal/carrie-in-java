package org.c3sl.ufpr.br.main;

import org.c3sl.ufpr.br.constants.Constants;
import org.c3sl.ufpr.br.exercises.ExerciseOne;
import org.c3sl.ufpr.br.exercises.ExerciseThree;
import org.c3sl.ufpr.br.exercises.ExerciseTwo;

import br.ufpr.c3sl.view.applet.JAppletCarrie;

@SuppressWarnings("serial")
public class Fractal extends JAppletCarrie{
	
	public void init() {
		super.init("Fractal Simulator");
		//Nome é importante para conexao com banco de dados
		
		addPageFromHtmlFile(Constants.INTRODUCTION_PATH+"introduction1.html");
		
		addPanel("Exercício 1", new ExerciseOne());
		addPanel("Exercício 2", new ExerciseTwo());
		addPanel("Exercício 3", new ExerciseThree());
		
	}
}
