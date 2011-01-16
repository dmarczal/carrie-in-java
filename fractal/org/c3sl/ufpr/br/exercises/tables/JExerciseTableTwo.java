package org.c3sl.ufpr.br.exercises.tables;

import org.c3sl.ufpr.br.correction.CorrectionTwo;

import br.ufpr.c3sl.virtualkeyboard.main.VirtualKeyBoardMain;


public class JExerciseTableTwo extends JExerciseTable{

	private static final long serialVersionUID = -161837439302595511L;
	
	public JExerciseTableTwo(String axiom, String rules, double angle) {
		super(axiom, rules, angle, new CorrectionTwo(), 5, 3);
		configureTable();
	}
	
	public void configureTable(){
		this.setValueAt("ℓ", 0, 2);
	}
	
	@Override
	public void configureEditable(){
		cellEditable = new boolean[6][3];
		cellEditable[1][2] = true;
	}
	
	@Override
	public int getMaxHeaderLength(){
		return 60;
	}
	
	@Override
	public void activeCell(boolean _true){
		if (getSelectedRow() != 1){
			cellEditable[getSelectedRow()-1][getSelectedColumn()] = !_true;
		}	
		if (getSelectedRow() < getRowCount()-1)	
			cellEditable[getSelectedRow()+1][getSelectedColumn()] = _true;
	}
	
	@Override
	public void buildEventsAndTransientvariables() {
		this.keyBoard = new VirtualKeyBoardMain(this, true, true, true);
		this.keyBoard.addKeyBoardListener(this);
	}

	@Override
	public String[] arrayHeader() {
		return new String[] {"Iteração", "Fractal", "Tamanho do lado do menor Triângulo"};
	}

}
