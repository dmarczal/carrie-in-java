package org.c3sl.ufpr.br.exercises.tables;

import org.c3sl.ufpr.br.correction.CorrectionOne;

import br.ufpr.c3sl.virtualkeyboard.main.VirtualKeyBoardMain;

public class JExerciseTableOne extends JExerciseTable{

	private static final long serialVersionUID = 8618510175150055446L;

	public JExerciseTableOne(String axiom, String rules, double angle) {
		super(axiom, rules, angle, new CorrectionOne(), 4, 3);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void buildEventsAndTransientvariables() {
		this.keyBoard = new VirtualKeyBoardMain(false);
		this.keyBoard.addKeyBoardListener(this);
	}
}
