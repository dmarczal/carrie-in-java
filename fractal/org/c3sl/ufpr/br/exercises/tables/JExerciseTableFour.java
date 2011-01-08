package org.c3sl.ufpr.br.exercises.tables;

import org.c3sl.ufpr.br.correction.CorrectionFour;

import br.ufpr.c3sl.virtualkeyboard.formula.FormulaInitial;
import br.ufpr.c3sl.virtualkeyboard.main.VirtualKeyBoardMain;

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
			
			this.setValueAt("ℓ", 0, 2);
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
				"Área de um triângulo verde", "Número de triângulos Verdes", "Área Verde"};
	}
}
