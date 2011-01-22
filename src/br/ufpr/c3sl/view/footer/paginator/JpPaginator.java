package br.ufpr.c3sl.view.footer.paginator;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import br.ufpr.c3sl.util.Util;
import br.ufpr.c3sl.view.principal.JpCarrie;

/**
 * CARRIE Framework
 * class JpPaginator the paginator panel of CARRIE framework
 * @author  Diego Marczal
 * @version Outubro 16, 2010
 */
@SuppressWarnings("serial")
public class JpPaginator extends JPanel {

	private static final int MAX_SIZE_OF_ITEM_NAME = 35;

	private JButton jbNext;
	private JButton jbPrevious;

	private JComboBox jcbList;

	private ArrayList<String> toolTips;
	private ArrayList<String> names;

	private LinkedHashMap<String, JPanel> list = new LinkedHashMap<String, JPanel>();

	private List<PaginatorListener> listeners;

	public JpPaginator() {
		inicializeVariables();
		setListeners();
		listeners = new ArrayList<PaginatorListener>();
	}

	private void inicializeVariables(){
		list = new LinkedHashMap<String, JPanel>();
		toolTips = new ArrayList<String>();
		names = new ArrayList<String>();

		jbPrevious = new JButton(Util.getImageIcon(getClass(), "backward_nav"));
		this.add(jbPrevious);
		jbNext = new JButton(Util.getImageIcon(getClass(), "forward_nav"));
		this.add(jbNext);

		jcbList = new JComboBox();
		jcbList.setRenderer(new MyComboBoxRenderer());
		this.add(jcbList);
	}

	private void setListeners(){
		jbNext.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				moveAhead();
			}
		});

		jbPrevious.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				moveBack();
			}
		});

		jcbList.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				int index = cb.getSelectedIndex();

				if (index >= 0){
					String key = names.get(index);
					JpCarrie.getInstance().updateMainPanel(list.get(key));
					//br.ufpr.c3sl.condigital.app.model.PaginationChangeNotify.getInstance().changed(l.get(key));
				}
			}
		});
	}

	private String createTootips(String s){
		String toolTip = s;
		if (s.length() > MAX_SIZE_OF_ITEM_NAME){
			toolTip = s.substring(0, MAX_SIZE_OF_ITEM_NAME)+"...";
		}
		toolTips.add(toolTip);
		names.add(s);

		return toolTip;
	}

	/**
	 *  add a panel to the paginator
	 *  @param jPanel to be added
	 */
	public void addPanel(JPanel jPanel){
		list.put(jPanel.getName(), jPanel);
		jcbList.addItem(createTootips(jPanel.getName()));
	}

	/**
	 *  move the paginator ahead
	 */
	public void moveAhead() {
		int x = jcbList.getSelectedIndex();

		if (++x < jcbList.getItemCount()){
			jcbList.setSelectedIndex(x);
			paginatorDispacher("AHEAD");
		}
	};

	/**
	 *  move the paginator back
	 */
	public void moveBack () {
		int x = jcbList.getSelectedIndex();
		if (x > 0 && x <= jcbList.getItemCount() ){
			jcbList.setSelectedIndex(--x);
			paginatorDispacher("BACK");
		}
	};

	/**
	 *  move the paginator to a determinate index
	 *  @param index index of the panel
	 */
	public void moveToIndex(int index) {
		if (index >= 0 && index < jcbList.getItemCount()){
			jcbList.setSelectedIndex(index);
			paginatorDispacher("TO:"+ index);
		}

	}

	/**
	 *  move the paginator to the last panel
	 */
	public void moveToLast() {
		jcbList.setSelectedIndex(jcbList.getItemCount()-1);
		paginatorDispacher("LAST");
	}

	/**
	 *  getAllPanels
	 *  @return all panels added at the CARRIE
	 */
	public Collection<JPanel> getAllPanels() {
		return list.values();
	}


	//Listeners
	public synchronized void addPaginatorListener(PaginatorListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public synchronized void removePaginatorListener(PaginatorListener listener) {
		listeners.remove(listener);
	}

	private void paginatorDispacher(Object object) {
		PaginatorEvent<Object> evento = new PaginatorEvent<Object>(object);

		for (PaginatorListener listener : getListenerClone()) {
			listener.paginated(evento);
		}
	}

	@SuppressWarnings("unchecked")
	private List<PaginatorListener> getListenerClone(){
		synchronized (this) {
			// Clonar para evitar problemas de sincronização
			// durante a propagação
			return (List<PaginatorListener>) ((ArrayList<PaginatorListener>) listeners).clone();
		}
	}

	private class MyComboBoxRenderer extends BasicComboBoxRenderer {
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {

			if (list != null){
				if (isSelected) {
					setBackground(list.getSelectionBackground());
					setForeground(list.getSelectionForeground());
					if (-1 < index) {
						list.setToolTipText(toolTips.get(index));
					}
				} else {
					setBackground(list.getBackground());
					setForeground(list.getForeground());
				}

				setFont(list.getFont());
			}
			setText((value == null) ? "" : value.toString());
			return this;
		}

	}

}

