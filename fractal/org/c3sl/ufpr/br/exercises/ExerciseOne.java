package org.c3sl.ufpr.br.exercises;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.c3sl.ufpr.br.constants.Constants;

import br.ufpr.c3sl.view.EnunciationHTML.Enunciation;

@SuppressWarnings("serial")
public class ExerciseOne extends JPanel {
	
	private JExerciseTable exerciseTable;
	
	public ExerciseOne() {
		super(new BorderLayout());
		buildTable();
	}
	
	private void buildTable(){
		this.add(new Enunciation(Constants.ENUNCIATION_PATH+"exercise1.html", true),
				BorderLayout.NORTH);
		
		exerciseTable = new JExerciseTable("fXf--ff--ff", "X=--fXf++fXf++fXf--\nf=ff", 60);
		JScrollPane js = new JScrollPane(exerciseTable);
		this.add(js, BorderLayout.CENTER);		
	}
}