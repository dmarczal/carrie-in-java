package br.ufpr.c3sl.view.retroaction;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import javax.swing.text.View;

import br.ufpr.c3sl.model.Mistake;

public class PaginateMistakes extends JPanel {

	private static final long serialVersionUID = 1L;
	private RadioButtonUI ui = new RadioButtonUI();
	private int pageSize = 5;

	private MistakesTableModel model = new MistakesTableModel();
	private TableRowSorter<MistakesTableModel> sorter = new TableRowSorter<MistakesTableModel>(model);
	private Box box = Box.createHorizontalBox();

	public PaginateMistakes() {
		super(new BorderLayout());

		JTable table = new JTable(model) {
			private static final long serialVersionUID = -2359027001994434574L;

			public Component prepareRenderer(TableCellRenderer tcr,
					int row, int column) {

				Component c = super.prepareRenderer(tcr, row, column);

				if (isRowSelected(row)) {
					c.setForeground(getSelectionForeground());
					c.setBackground(getSelectionBackground());
				} else {
					c.setForeground(getForeground());
					c.setBackground((row % 2 == 0) ? new Color(240, 255, 250)
					: Color.WHITE);
				}

				return c;
			}
		};
		
		//invisible column
		table.getColumnModel().getColumn(0).setMaxWidth(10);
		table.getColumnModel().getColumn(0).setPreferredWidth(10);

		//table.setIntercellSpacing(new Dimension());
		table.setShowGrid(false);
		table.setRowSorter(sorter);
		table.getTableHeader().setBackground(Color.white);
		add(new JScrollPane(table));
		add(box, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(550, 208));
		showPages(10, 1);
	}
	
	public boolean contaisMistakes(){
		return model.contaisMistakes();
	}
	
	public void addMistake(Mistake mistake){
		model.addMistake(mistake);
	}
	
	public void addMistakes(List<Mistake> mistakesList){
		model.addMistakes(mistakesList);
	}
	
	public void showFirstPage(){
		showPages(10, 1);
	}
	
	
	@SuppressWarnings("unchecked")
	private void showPages(final int itemsPerPage, final int currentPageIndex) {
		sorter.setRowFilter(filter(itemsPerPage, currentPageIndex - 1));
		ArrayList<JRadioButton> l = new ArrayList<JRadioButton>();

		int startPageIndex = currentPageIndex - pageSize;
		if (startPageIndex <= 0)
			startPageIndex = 1;
		int maxPageIndex = (model.getRowCount() / itemsPerPage) + 1;
		int endPageIndex = currentPageIndex + pageSize - 1;
		if (endPageIndex > maxPageIndex)
			endPageIndex = maxPageIndex;

		if (currentPageIndex > 1)
			l.add(createRadioButtons(itemsPerPage, currentPageIndex - 1,
			"Prev"));
		for (int i = startPageIndex; i <= endPageIndex; i++)
			l.add(createLinks(itemsPerPage, currentPageIndex, i - 1));
		if (currentPageIndex < maxPageIndex)
			l.add(createRadioButtons(itemsPerPage, currentPageIndex + 1,
			"Next"));

		box.removeAll();
		ButtonGroup bg = new ButtonGroup();
		box.add(Box.createHorizontalGlue());
		for (JRadioButton r : l) {
			box.add(r);
			bg.add(r);
		}
		box.add(Box.createHorizontalGlue());
		box.revalidate();
		box.repaint();
		l.clear();
	}

	private JRadioButton createLinks(final int itemsPerPage, final int current,
			final int target) {
		JRadioButton radio = new JRadioButton("" + (target + 1)) {

			private static final long serialVersionUID = 7825727201629193035L;

			protected void fireStateChanged() {
				ButtonModel model = getModel();
				if (!model.isEnabled()) {
					setForeground(Color.GRAY);
				} else if (model.isPressed() && model.isArmed()) {
					setForeground(Color.GREEN);
				} else if (model.isSelected()) {
					setForeground(Color.RED);
				}
				super.fireStateChanged();
			}
		};
		radio.setForeground(Color.BLACK);
		radio.setUI(ui);
		if (target + 1 == current) {
			radio.setSelected(true);
		}
		radio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPages(itemsPerPage, target + 1);
			}
		});
		return radio;
	}

	private JRadioButton createRadioButtons(final int itemsPerPage,
			final int target, String title) {
		JRadioButton radio = new JRadioButton(title);
		radio.setForeground(Color.black);
		radio.setUI(ui);
		radio.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showPages(itemsPerPage, target);
			}
		});
		return radio;
	}

	@SuppressWarnings("rawtypes")
	private RowFilter filter(final int itemsPerPage, final int target) {
		return new RowFilter() {
			public boolean include(Entry entry) {
				int ei = (Integer) entry.getIdentifier();
				return (target * itemsPerPage <= ei && ei < target * itemsPerPage + itemsPerPage);
			}
		};
	}
}

/***********************************************************************/

class RadioButtonUI extends BasicRadioButtonUI {
	public Icon getDefaultIcon() {
		return null;
	}

	private static Dimension size = new Dimension();
	private static Rectangle rec1 = new Rectangle();
	private static Rectangle rec2 = new Rectangle();
	private static Rectangle rec3 = new Rectangle();

	public synchronized void paint(Graphics g, JComponent c) {
		AbstractButton b = (AbstractButton) c;
		ButtonModel model = b.getModel();
		Font f = c.getFont();
		g.setFont(f);
		FontMetrics fm = c.getFontMetrics(f);

		Insets i = c.getInsets();
		size = b.getSize(size);
		rec1.x = i.left;
		rec1.y = i.top;
		rec1.width = size.width - (i.right + rec1.x);
		rec1.height = size.height - (i.bottom + rec1.y);
		rec2.x = rec2.y = rec2.width = rec2.height = 0;
		rec3.x = rec3.y = rec3.width = rec3.height = 0;

		String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(),
				null, b.getVerticalAlignment(), b.getHorizontalAlignment(), b
				.getVerticalTextPosition(), b
				.getHorizontalTextPosition(), rec1, rec2, rec3, 0);

		if (c.isOpaque()) {
			g.setColor(b.getBackground());
			g.fillRect(0, 0, size.width, size.height);
		}
		if (text == null)
			return;
		g.setColor(b.getForeground());

		if (!model.isSelected() && !model.isPressed() && !model.isArmed()
				&& b.isRolloverEnabled() && model.isRollover()) {
			g.drawLine(rec1.x, rec1.y + rec1.height, rec1.x + rec1.width,
					rec1.y + rec1.height);
		}

		View v = (View) c.getClientProperty(BasicHTML.propertyKey);

		if (v != null) {
			v.paint(g, rec3);
		} else {
			paintText(g, b, rec3, text);
		}
	}
}

/***********************************************************************/

class MistakesTableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1118824776778151336L;
	private Hashtable<Integer, Mistake> map;
	
	private int mistakeID; //this is necessary because BD4O doesn't support id
	
	MistakesTableModel() {

		addColumn("");
		addColumn("Exercise");
		addColumn("Resposta");
		addColumn("Titulo do Erro");
		addColumn("Ocorrido em:");
		
		map = new Hashtable<Integer, Mistake>();
	}

	public boolean contaisMistakes(){
		return !map.isEmpty();
	}
	
	public void addMistake(Mistake mistake){
		map.put(mistakeID, mistake);
		insertRow(0, new Object[] {mistakeID, mistake.getExercise(), 
				mistake.getMistakeInfo().getAnswer(),
				mistake.getMistakeInfo().getTitle(),
				mistake.getCreatedAtTime(),});
		mistakeID++;
	}
	
	public void addMistakes(List<Mistake> list){
		for (Mistake mistake : list) {
			addMistake(mistake);
		}
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		final Mistake mistake = map.get(getValueAt(row, 0));

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				RetroactionFrame.createAndShowGUI(mistake);
			}
		});
		
		return false;
	}
}