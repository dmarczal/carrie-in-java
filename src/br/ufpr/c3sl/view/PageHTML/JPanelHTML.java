package br.ufpr.c3sl.view.PageHTML;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

//import br.ufpr.c3sl.condigital.app.view.glossary.GlossaryGUI;

public class JPanelHTML extends JPanel{
	
	private static final long serialVersionUID = -2837566491870483686L;
	
	private String textualContent = null;
	private String title = null;
	private JEditorPane content = new JEditorPane();
	private JPanel mainPanel = null;

	public JPanelHTML() {
		this.setLayout(new BorderLayout());
	}
	
	public String getTextualContent() {
		return textualContent;
	}

	public void setTextualContent(String textualContent) {
		this.textualContent = textualContent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public void refreshPane() {
		if (getTextualContent() != null) {
			content = new JEditorPane();
			content.setContentType("text/html");
			content.setText(getTextualContent());
			content.setEditable(false);
			content.setBackground(new Color(238, 238, 238));
			mainPanel.add(content, BorderLayout.CENTER);
		}
	}

	@SuppressWarnings("serial")
	public void create() {
		mainPanel = new JPanel(new BorderLayout()){
			public Dimension getPreferredSize(){
				Dimension d = super.getPreferredSize();
				d.width = 5;
				return d;
			}
		};
		
		JScrollPane scroll = new JScrollPane(mainPanel);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		
		content.addHyperlinkListener(new JPanelHTMLHyperlinkListener());
		
		if (title != null){
			JPanel jpanelTitle = new JPanel();

			JLabel labelTitle = new JLabel();
			labelTitle.setFont(new Font("Verdana",Font.BOLD,14));		
			labelTitle.setText(title);
			jpanelTitle.add(labelTitle);

			mainPanel.add(jpanelTitle, BorderLayout.NORTH);
		}

		refreshPane();
		add(mainPanel, BorderLayout.CENTER);
	}
	
	public StyleSheet getStyleSheetFromEditorPane(){
		javax.swing.text.html.HTMLEditorKit kit = (HTMLEditorKit) content.getEditorKit();
        return kit.getStyleSheet();
	}
	
	private class JPanelHTMLHyperlinkListener implements HyperlinkListener {
		
		public void hyperlinkUpdate(HyperlinkEvent e) {
		
			if (HyperlinkEvent.EventType.ACTIVATED == e.getEventType()){
				if (e.getURL() != null){
					String url = e.getURL().toString();
					java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
		            try {
		                java.net.URI uri = new java.net.URI(url);
		                desktop.browse(uri);
		            }
		            catch (Exception err) {
		                System.err.println(err.getMessage());
		            }
				} else{
					String info[] = e.getDescription().split(":");
					System.out.println(info);
					//if (info[0].compareTo("glossary") == 0)
						//TODO: GlossaryGUI.updateHyperLink(info[1]);
					//else if (info[0].compareTo("page") == 0)
						//TODO: JPanelCARRIE.getInstance().moveToIndex(Integer.parseInt(info[1]));
				}
			}
		}
	}
}
