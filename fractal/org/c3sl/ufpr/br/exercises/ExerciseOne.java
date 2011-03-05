package org.c3sl.ufpr.br.exercises;

import org.c3sl.ufpr.br.exercises.tables.JExerciseTableOne;

public class ExerciseOne extends Exercise {
	
	private static final long serialVersionUID = 1L;
	
	public ExerciseOne() {
		super(new JExerciseTableOne("fXf--ff--ff", "X=--fXf++fXf++fXf--\nf=ff", 60), "exercise1.html");
	}

}