package br.ufpr.c3sl.view.EnunciationHTML;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import br.ufpr.c3sl.pageHTML.Html;
import br.ufpr.c3sl.state.SaveState;
import br.ufpr.c3sl.util.Util;
import br.ufpr.c3sl.view.PageHTML.JPanelHTML;


public class Enunciation extends JPanel implements SaveState {

	private static final long serialVersionUID = 3743623610914220124L;

	private JCheckBox jcbShowHideEnun;
	private JPanelHTML panel;
	private String text;

	private static final Font FONT_SHOW_HIDE_TEXT = new Font("Arial", Font.ITALIC, 12); 
	private static final String HIDE_ENUNCIATION = "↑ [Clique Aqui para Esconder o Texto]";
	private static final String SHOW_ENUNCIATION = "↓ [Clique Aqui para Aparecer o Texto]";

	public Enunciation(String filepath) {
		this(filepath, true);
	}

	public Enunciation(String filepath, boolean showRadioButton) {
		setMinimumSize(new Dimension(0,0));
		jcbShowHideEnun = new JCheckBox();
		jcbShowHideEnun.setFont(FONT_SHOW_HIDE_TEXT);

		this.text = Util.getTextFromFile(getClass(), filepath);
		
		setLayout(new BorderLayout());

		if (showRadioButton)
			addComponents();
		
		createComonents();
	}

	private void createComonents() {
		panel = new JPanelHTML();
		
		Html html = new Html(text);
		panel.setTextualContent(html.getCode());
		panel.setTitle(html.getTitle());
		panel.create();

		add(panel, BorderLayout.CENTER);
	}

	public void addComponents() {
		add(jcbShowHideEnun, java.awt.BorderLayout.NORTH);

		jcbShowHideEnun.setText(HIDE_ENUNCIATION);
		jcbShowHideEnun.setFocusable(false);
		buildEventsAndTransientvariables();
	}

	//	public boolean enunciationIsVisiable(){
	//		return panel.isVisible();
	//	}
	//	
	//	public void setEnuciationVisiable(boolean visible){
	//		panel.setVisible(visible);
	//		jcbShowHideEnun.setSelected(!visible);
	//	}

	//	public StyleSheet getStyleSheetFromEnun(){
	//		return panel.getStyleSheetFromEditorPane();
	//	}

	private class ActionListenerjrbShowHideEnun implements ActionListener{
		public void actionPerformed(java.awt.event.ActionEvent evt) {
			if (panel.isVisible()) {
				panel.setVisible(false);
				jcbShowHideEnun.setText(SHOW_ENUNCIATION);
			} else {
				jcbShowHideEnun.setText(HIDE_ENUNCIATION);
				panel.setVisible(true);
			}
		}	
	}

	@Override
	public void buildEventsAndTransientvariables() {
		if (panel != null)
			remove(panel);
		
		createComonents();
		jcbShowHideEnun.addActionListener(new ActionListenerjrbShowHideEnun());
	}
}
