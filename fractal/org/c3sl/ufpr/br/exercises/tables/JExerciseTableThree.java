package org.c3sl.ufpr.br.exercises.tables;

import org.c3sl.ufpr.br.correction.CorrectionThree;

import br.ufpr.c3sl.virtualkeyboard.formula.FormulaInitial;
import br.ufpr.c3sl.virtualkeyboard.main.VirtualKeyBoardMain;

public class JExerciseTableThree extends JExerciseTable {

	private static final long serialVersionUID = 2636022967748655631L;

	private static final int ROW = 5;
	private static final int COLUMN = 3;
	
	public JExerciseTableThree(String axiom, String rules, double angle) {
		super(axiom, rules, angle, new CorrectionThree(), ROW, COLUMN);
		configureTable();
	}

	public void configureTable(){
		this.setValueAt("ℓ", 0, 2);
		for (int i = 0; i < ROW+1; i++) {
			setValueAt(new FormulaInitial(), i, 3);
		}
	}
	
	@Override
	public void configureEditable(){
		cellEditable = new boolean[6][4];
		cellEditable[0][3] = true;
	}
	
	@Override
	public void activeCell(boolean _true){
		if (getSelectedRow() != 0 && getSelectedColumn() == COLUMN){
				if (getSelectedRow() != 1)
					cellEditable[getSelectedRow()-1][getSelectedColumn()-1] = !_true;
				cellEditable[getSelectedRow()-1][getSelectedColumn()] = !_true;
		}	
		if (getSelectedRow() < getRowCount()-1 && getSelectedColumn() == COLUMN){	
			cellEditable[getSelectedRow()+1][getSelectedColumn()-1] = _true;
			cellEditable[getSelectedRow()+1][getSelectedColumn()] = _true;
		}
	}
	
	@Override
	public void buildEventsAndTransientvariables() {
		this.keyBoard = new VirtualKeyBoardMain(true, true, true);
		this.keyBoard.addKeyBoardListener(this);
	}

	@Override
	public String[] arrayHeader() {
		return new String[] {"Iteração", "Fractal", "Tamanho do lado do menor Triângulo",
				"Perímetro"};
	}
}
