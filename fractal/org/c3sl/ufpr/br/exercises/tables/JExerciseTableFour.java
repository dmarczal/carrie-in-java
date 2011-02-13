package org.c3sl.ufpr.br.exercises.tables;

import org.c3sl.ufpr.br.correction.CorrectionFour;

import br.ufpr.c3sl.virtualkeyboard.compositedElements.Division;
import br.ufpr.c3sl.virtualkeyboard.compositedElements.Power;
import br.ufpr.c3sl.virtualkeyboard.elements.Variable;
import br.ufpr.c3sl.virtualkeyboard.formula.ElementOfFormula;
import br.ufpr.c3sl.virtualkeyboard.formula.FormulaInitial;
import br.ufpr.c3sl.virtualkeyboard.main.VirtualKeyBoardMain;

import br.ufpr.c3sl.virtualkeyboard.elements.Number;

public class JExerciseTableFour extends JExerciseTable{

	private static final long serialVersionUID = 5587952136472209059L;
	private static final int ROW = 5;
	private static final int COLUMN = 5;	

	public JExerciseTableFour(String axiom, String rules, double angle) {
		super(axiom, rules, angle, new CorrectionFour(), ROW, COLUMN);
		configureTable();
	}

	public void configureTable(){
		this.setValueAt("ℓ", 0, 2);

		for (int i = 0; i < ROW+1; i++) {
			setValueAt(new FormulaInitial(), i, 3);
			setValueAt(new FormulaInitial(), i, 4);
			setValueAt(new FormulaInitial(), i, 5);
			setValueAt(getFormula(i+""), i, 2);
		}

		this.setValueAt("ℓ", 0, 2);
		setValueAt(getFormula("n"), ROW, 2);

		this.getColumnModel().getColumn(2).setMaxWidth(140);
		this.getColumnModel().getColumn(2).setPreferredWidth(140);

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
				"Área de um triângulo verde", "Número de triângulos Verdes", "Área Verde"};
	}

	@Override
	public boolean getFractalPaintColor(){
		return true;
	}

	@Override
	public void configureEditable(){
		cellEditable = new boolean[ROW+1][COLUMN+1];
		cellEditable[0][3] = true;
		cellEditable[0][4] = true;
		cellEditable[0][5] = true;
	}

	private boolean isRowCorrect(int line){
		for (int i = 3; i < 6; i++) {
			if (!isRowCollumnCorrect(line, i))
				return false;
		}	

		return true;
	}

	private void nexts(boolean _true){
		if (getSelectedRow() < getRowCount()-1){
			cellEditable[getSelectedRow()+1][3] = _true;
			cellEditable[getSelectedRow()+1][4] = _true;
			cellEditable[getSelectedRow()+1][5] = _true;
		}
	}

	private void previous(boolean _true){
		if (getSelectedRow() > 0){
			cellEditable[getSelectedRow()-1][3] = _true;
			cellEditable[getSelectedRow()-1][4] = _true;
			cellEditable[getSelectedRow()-1][5] = _true;
		}
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

	public ElementOfFormula getFormula(String i){
		Variable label = new Variable();
		label.addElement("ℓ");

		Number number = new Number();
		number.setNumber("2");

		Power power = new Power();

		Number numberI = new Number();
		numberI.setNumber(i);
		power.addElement(numberI);				

		Division division = new Division();
		division.setDividend(label);

		division.addElement(number);
		division.addElement(power);

		return division;
	}
}
