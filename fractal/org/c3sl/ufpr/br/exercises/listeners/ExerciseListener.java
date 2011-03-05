package org.c3sl.ufpr.br.exercises.listeners;

import javax.swing.JPanel;

import org.c3sl.ufpr.br.exercises.events.ExerciseEvent;

public interface ExerciseListener {
	
	void changedValue(ExerciseEvent<JPanel> event);
}

