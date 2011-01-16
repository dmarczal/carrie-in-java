package org.c3sl.ufpr.br.exercises;

import org.c3sl.ufpr.br.exercises.tables.JExerciseTableFive;

public class ExerciseFive extends Exercise{

	private static final long serialVersionUID = 1L;
	
	public ExerciseFive() {
		super(new JExerciseTableFive("f--f--f", "f=f+f--f+f", 60), "exercise5.html");
	}
}
