package org.c3sl.ufpr.br.exercises;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.c3sl.ufpr.br.constants.Constants;
import org.c3sl.ufpr.br.exercises.tables.JExerciseTable;

import br.ufpr.c3sl.view.EnunciationHTML.Enunciation;

public class Exercise extends JPanel{

	private static final long serialVersionUID = 1050322933172734059L;
	
	public Exercise(JExerciseTable exerciseTable, String enunFile) {
		super(new BorderLayout());
		
		this.add(new Enunciation(Constants.ENUNCIATION_PATH+enunFile, true),
				BorderLayout.NORTH);
		
		JScrollPane js = new JScrollPane(exerciseTable);
		this.add(js, BorderLayout.CENTER);		
	}
}
