package org.c3sl.ufpr.br.main;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.c3sl.ufpr.br.constants.Constants;
import org.c3sl.ufpr.br.exercises.ExerciseOne;

import br.ufpr.c3sl.view.applet.JAppletCarrie;

@SuppressWarnings("serial")
public class Fractal extends JAppletCarrie{
	
	public void init() {
		super.init("Fractal Simulator");
		//Nome é importante para conexao com banco de dados
		
		JPanel p = new JPanel();
		p.add(new JLabel("P1"));
		
		JPanel p2 = new JPanel();
		p2.add(new JLabel("P2"));
		
		JPanel p3 = new JPanel();
		p3.add(new JLabel("P3"));
		
		addPanel("Exercise One", new ExerciseOne());
		addPanel("Painel One", p);
		addPanel("Painel Two", p2);
		addPanel("Painel Thrre", p3);
		
		addPageFromHtmlFile(Constants.INTRODUCTION_PATH+"introduction1.html");
		 
	}
}
