package org.c3sl.ufpr.br.exercises.tables;

import org.c3sl.ufpr.br.correction.CorrectionFive;

import br.ufpr.c3sl.virtualkeyboard.formula.FormulaInitial;
import br.ufpr.c3sl.virtualkeyboard.main.VirtualKeyBoardMain;

public class JExerciseTableFive extends JExerciseTable{

	private static final long serialVersionUID = 1L;

	private static final int ROW = 5;
	private static final int COLUMN = 4;
	
	public JExerciseTableFive(String axiom, String rules, double angle) {
		super(axiom, rules, angle, new CorrectionFive(), ROW, COLUMN);
		configureTable();
	}

	@Override
	public void configureEditable(){
		cellEditable = new boolean[6][4];
		cellEditable[0][3] = true;
		cellEditable[5][3] = true;
		setRowCollumnCorrect(0, 2, true);
	}
	
	
	public void configureTable(){

		for (int i = 0; i < ROW+1; i++) {
			setValueAt(new FormulaInitial(), i, 2);
			setValueAt(new FormulaInitial(), i, 3);
		}

		this.setValueAt("ℓ", 0, 2);
	}
	
	private void nexts(boolean _true){
		if (getSelectedRow() < getRowCount()-1){
			cellEditable[getSelectedRow()+1][2] = _true;
			cellEditable[getSelectedRow()+1][3] = _true;
		}
	}

	private void previous(boolean _true){
		if (getSelectedRow() > 0){
			if (getSelectedRow() != 1)
				cellEditable[getSelectedRow()-1][2] = _true;
			cellEditable[getSelectedRow()-1][3] = _true;
		}
	}

	private boolean isRowCorrect(int line){
		for (int i = 2; i < 4; i++) {
			if (!isRowCollumnCorrect(line, i))
				return false;
		}	

		return true;
	}
	
	@Override
	public void activeCell(boolean _true){
		if (isRowCorrect(getSelectedRow())){
			nexts(true);
			previous(false);
		}else
		{
			nexts(false);
			previous(true);
		}
	}
	
	@Override
	public int getMaxHeaderLength(){
		return 20;
	}

	@Override
	public void buildEventsAndTransientvariables() {
		this.keyBoard = new VirtualKeyBoardMain(this, true, true, true);
		this.keyBoard.addKeyBoardListener(this);
	}

	@Override
	public String[] arrayHeader() {
		return new String[] {"Iteração", "Fractal", "Lado",
				"Perímetro"};
	}
}
