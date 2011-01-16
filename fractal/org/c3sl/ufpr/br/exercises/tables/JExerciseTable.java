package org.c3sl.ufpr.br.exercises.tables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JViewport;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import net.java.balloontip.BalloonTip;
import net.java.balloontip.TablecellBalloonTip;
import net.java.balloontip.styles.EdgedBalloonStyle;

import org.c3sl.ufpr.br.correction.Correction;
import org.c3sl.ufpr.br.fractal.Drawing;
import org.c3sl.ufpr.br.fractal.Fractal;

import br.ufpr.c3sl.state.SaveState;
import br.ufpr.c3sl.view.footer.paginator.PaginatorEvent;
import br.ufpr.c3sl.view.footer.paginator.PaginatorListener;
import br.ufpr.c3sl.view.principal.JpCarrie;
import br.ufpr.c3sl.virtualkeyboard.events.VirtualKeyBoardEvent;
import br.ufpr.c3sl.virtualkeyboard.formula.ElementOfFormula;
import br.ufpr.c3sl.virtualkeyboard.formula.FormulaInitial;
import br.ufpr.c3sl.virtualkeyboard.listeners.VirtualKeyBoardListener;
import br.ufpr.c3sl.virtualkeyboard.main.VirtualKeyBoardMain;

@SuppressWarnings("serial")
public abstract class JExerciseTable extends JTable implements VirtualKeyBoardListener, SaveState, PaginatorListener{

	private TableModel model = new TableModel();
	private String axiom;
	private String rules;

	private double angle;

	transient protected VirtualKeyBoardMain keyBoard;
	transient protected TablecellBalloonTip balloonMessage;

	protected boolean[][] cellEditable;
	private boolean[][] isRowCollumnCorrect;
	
	private Correction correction;
	
	private int numberRow;
	private int numberColumn;

	public JExerciseTable(String axiom, String rules, double angle, Correction correction,
			int numberRow, int numberColumn) {
		this.axiom = axiom;
		this.rules = rules;
		this.angle = angle;
		this.setModel(model);
		this.correction = correction;
		this.numberColumn = numberColumn;
		this.numberRow = numberRow;
		this.isRowCollumnCorrect = new boolean[numberRow+1][numberColumn+1];
		configureModel();
		configureFractal();
		buildEventsAndTransientvariables();
		configureEditable();
		JpCarrie.getInstance().getPaginator().addPaginatorListener(this);
	}

	public boolean isRowCollumnCorrect(int row, int column){
		return isRowCollumnCorrect[row][column];
	}
	
	public void setRowCollumnCorrect(int row, int column, boolean correct){
		this.isRowCollumnCorrect[row][column] = correct;
	}
	
	public void configureEditable(){
		cellEditable = new boolean[numberRow+1][numberColumn];
		cellEditable[0][2] = true;
	}

	private void configureModel(){
		Enumeration<TableColumn> columns = this.getColumnModel().getColumns();

		 MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer();
		
		while (columns.hasMoreElements()) {
			TableColumn column = columns.nextElement();
			column.setMinWidth(16);
			column.setHeaderRenderer(renderer);
			column.setCellRenderer(new Renderer(getDefaultRenderer(JPanel.class)));
		}

		this.getColumnModel().getColumn(0).setMaxWidth(100);
		this.getColumnModel().getColumn(0).setPreferredWidth(100);

		this.getColumnModel().getColumn(1).setMaxWidth(148);
		this.getColumnModel().getColumn(1).setPreferredWidth(148);

		this.setRowHeight(128);

		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_LAST_COLUMN);
		this.setBackground(new Color(220,226,225));
		this.setSelectionBackground(new Color(220,226,225));

		this.getTableHeader().setReorderingAllowed(false);        
		this.getTableHeader().setBackground(Color.white);
	}

	public boolean getFractalPaintColor(){
		return false;
	}
	
	private void configureFractal(){
		for (int i = 0; i < numberRow; i++) {
			Fractal fractal = new Fractal(this.axiom, this.rules, this.angle, i);

			Drawing drawing = new Drawing(true);
			drawing.setPaintWithColor(getFractalPaintColor());
			drawing.setFractal(fractal);

			JPanel jpFractal = new JPanel(new BorderLayout());
			jpFractal.add(drawing, BorderLayout.CENTER);

			model.addRow(new Object[] { i, jpFractal });
			model.setValueAt(new FormulaInitial(), i, 2);
		}

		model.addRow(new Object[] { "n", "figura limite"});
		model.setValueAt(new FormulaInitial(), numberRow, 2);
	}

	public abstract String[] arrayHeader();
	
	private class TableModel extends DefaultTableModel{

		public TableModel() {
			super(new Object[][]{}, arrayHeader());
		}

		@Override
		public boolean isCellEditable(int row, int column) {
			if(cellEditable[row][column]){
				showKeyBoard(row, column);
			}else 
				keyBoard.setVisible(false);

			return false;
		}
	}



	@Override
	public void formulaSended(VirtualKeyBoardEvent<JPanel> formula) {
		if (correction.isCorrect(formula.getSource().toString(), getSelectedRow(), getSelectedColumn())){
			processCorrectAnswer(formula.getSource());
		}else{
			processWrongAnswer(formula.getSource());
		}

		keyBoard.setVisible(false);
	}

	private void processCorrectAnswer(JPanel answer){
		setValueAt(answer, getSelectedRow(), getSelectedColumn());

		setRowCollumnCorrect(getSelectedRow(), getSelectedColumn(), true);
		activeCell(true);		
		answer.setLayout(new FlowLayout());
		answer.setBorder(BorderFactory.createTitledBorder(
				new LineBorder(new Color(0, 150, 0), 5), "Correto",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER, getFont(), Color.black));
		
	}

	private void processWrongAnswer(JPanel answer){
		setRowCollumnCorrect(getSelectedRow(), getSelectedColumn(), false);
		activeCell(false);
		answer.setLayout(new FlowLayout());
		setValueAt(answer, getSelectedRow(), getSelectedColumn());
		answer.setBorder(BorderFactory.createTitledBorder(
				new LineBorder(new Color(250, 0, 0), 5), "Incorreto",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.CENTER, getFont(), Color.black));

		showMessageErrorIfNecessary();		
		JpCarrie.getInstance().saveState(correction.getMistakeInfo());
	}

	private void showMessageErrorIfNecessary() {
		if (correction.thereIsMessage()) {

			JTextPane paneMsg = new JTextPane();
			paneMsg.setContentType("text/html");
			paneMsg.setBackground(Color.RED);
			paneMsg.setText(correction.getErrorMessage());
			paneMsg.setEditable(false);
			
			balloonMessage = new TablecellBalloonTip(this, paneMsg,
					getSelectedRow(), getSelectedColumn()-1,
					new EdgedBalloonStyle(Color.RED, Color.WHITE),  
					BalloonTip.Orientation.RIGHT_ABOVE,
					BalloonTip.AttachLocation.NORTHEAST,
					60, 20,
					true);
			
			balloonMessage.setViewport((JViewport) this.getParent());
		}
	}

	private void closeBallonTip(){
		if (balloonMessage != null){
			balloonMessage.closeBalloon();
			balloonMessage = null;
		}

	}

	public void activeCell(boolean _true){
		if (getSelectedRow() != 0){
			cellEditable[getSelectedRow()-1][getSelectedColumn()] = !_true;
		}	
		if (getSelectedRow() < getRowCount()-1)	
			cellEditable[getSelectedRow()+1][getSelectedColumn()] = _true;
	} 

	private void showKeyBoard(int row, int column){
		closeBallonTip();

		if(row == numberRow)
			keyBoard.enableVariableN();

		this.keyBoard.setFormula((ElementOfFormula) getValueAt(row, column));
		this.keyBoard.setVisible(true);
	}


	@Override
	public void paginated(PaginatorEvent<Object> event) {
		System.out.println(event);
		this.keyBoard.setVisible(false);
		closeBallonTip();
	}
	
	public int getMaxHeaderLength(){
		return 20;
	}
	
	class Renderer implements TableCellRenderer, Serializable {
		private DefaultTableCellRenderer __defaultRenderer;

		public Renderer(TableCellRenderer renderer) {
			__defaultRenderer = (DefaultTableCellRenderer) renderer;
			__defaultRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			if(value instanceof Component) {
				((Component)value).setBackground(null);
				return (Component)value;
			}
			return __defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}
	
	class MultiLineHeaderRenderer extends JList implements TableCellRenderer, Serializable {
		private static final long serialVersionUID = 1L;

		public MultiLineHeaderRenderer() {
			
			setOpaque(true);
			setForeground(UIManager.getColor("TableHeader.foreground"));
			setBackground(UIManager.getColor("TableHeader.background"));
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			
			ListCellRenderer renderer = getCellRenderer();
			 ((JLabel) renderer).setHorizontalAlignment(SwingConstants.CENTER);
			 
			setCellRenderer(renderer);
		}
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			setFont(table.getFont());
			String str = (value == null) ? "" : value.toString(), line = "";
			String[] words = str.split(" ");
			int maxLen = getMaxHeaderLength();
			Vector<String> v = new Vector<String>();
			try {
				for (int i = 0; i < words.length; i++) {
					int len = line.length() + words[i].length(); 
					if (len < maxLen) {
						line += words[i] + " ";
					}
					else {
						v.addElement(line);
						line = words[i] + " ";
					}
				}
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			v.addElement(line);
			setListData(v);
			return this;
		}
	}
}