package org.c3sl.ufpr.br.exercises;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.c3sl.ufpr.br.constants.Constants;
import org.c3sl.ufpr.br.exercises.events.ExerciseEvent;
import org.c3sl.ufpr.br.exercises.listeners.ExerciseListener;
import org.c3sl.ufpr.br.exercises.tables.JExerciseTable;

import br.ufpr.c3sl.view.EnunciationHTML.Enunciation;

public class Exercise extends JPanel implements ExerciseListener{

	private static final long serialVersionUID = 1L;
	private JExerciseTable exerciseTable;
	
	public Exercise(JExerciseTable exerciseTable, String enunFile) {
		super(new BorderLayout());
		
		this.add(new Enunciation(Constants.ENUNCIATION_PATH+enunFile, true),
				BorderLayout.NORTH);
		
		this.exerciseTable = exerciseTable;
		JScrollPane js = new JScrollPane(exerciseTable);
		this.add(js, BorderLayout.CENTER);		
	}
	
	/**** Listener ****/
	public synchronized void addExerciseListener(ExerciseListener listener) {
		exerciseTable.addExerciseListener(listener);
	}

	public synchronized void removeExerciseListener(ExerciseListener listener) {
		exerciseTable.removeExerciseListener(listener);
	}

	@Override
	public void changedValue(ExerciseEvent<JPanel> event) {
		exerciseTable.changedValue(event);
	}
}
