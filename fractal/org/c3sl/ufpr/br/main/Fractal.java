package org.c3sl.ufpr.br.main;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.c3sl.ufpr.br.exercises.ExerciseOne;
import org.c3sl.ufpr.br.exercises.ExerciseTwo;

import br.ufpr.c3sl.view.applet.JAppletCarrie;

@SuppressWarnings("serial")
public class Fractal extends JAppletCarrie{
	
	public void init() {
		super.init("Fractal Simulator");
		//Nome é importante para conexao com banco de dados
		
		JPanel p = new JPanel();
		p.add(new JLabel("P1"));
		
		
		addPanel("Exercício 1", new ExerciseOne());
		addPanel("Exercício 2", new ExerciseTwo());
		
		//addPageFromHtmlFile(Constants.INTRODUCTION_PATH+"introduction1.html");
		 
	}
}
