package org.c3sl.ufpr.br.exercises.events;

import java.util.EventObject;

public class ExerciseEvent<T> extends EventObject {
	
	private static final long serialVersionUID = 1L;
	private int row;
	private int column;
	
	public ExerciseEvent(Object source) {
		super(source);
	}
	
	public ExerciseEvent(Object source, int row, int column) {
		this(source);
		this.row = row;
		this.column = column;
	}

	@SuppressWarnings("unchecked")
	public T getSource(){
		return ((T) super.getSource());
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
}
