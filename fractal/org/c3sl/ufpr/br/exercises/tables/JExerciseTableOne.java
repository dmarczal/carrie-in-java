package org.c3sl.ufpr.br.exercises.tables;

import org.c3sl.ufpr.br.correction.CorrectionOne;

import br.ufpr.c3sl.virtualkeyboard.main.VirtualKeyBoardMain;

public class JExerciseTableOne extends JExerciseTable{

	private static final long serialVersionUID = 1L;

	public JExerciseTableOne(String axiom, String rules, double angle) {
		super(axiom, rules, angle, new CorrectionOne(), 4, 3);
	}

	@Override
	public void buildEventsAndTransientvariables() {
		this.keyBoard = new VirtualKeyBoardMain(this, false);
		this.keyBoard.addKeyBoardListener(this);
	}
	
	@Override
	public String[] arrayHeader() {
		return new String[] {"Iteração", "Fractal", "Lado"};
	}
	
	@Override
	public int getMaxHeaderLength(){
		return 50;
	}
}
