package br.ufpr.c3sl.fontControl;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.ufpr.c3sl.view.PageHTML.JPanelHTML;
import br.ufpr.c3sl.view.principal.JpCarrie;

public class FontControl implements Observer{

	private FontSize fontSize = FontSize.getInstance();
	private static ArrayList<Container> externalContainersForControlFont = new ArrayList<Container>();

	//TODO:Fazer font size no static
	private void changeFontSize(Container c) {
		Font f;

		if (c instanceof JPanelHTML) {
			JPanelHTML compPane = (JPanelHTML) c;
			if (compPane.getTextualContent() != null) {
				Pattern pattern = Pattern.compile("font-size: (.*?)px;");
				Matcher matcher = pattern.matcher(compPane.getTextualContent());
				String text = compPane.getTextualContent();
				int offset = 0;

				while(matcher.find()) {
					Integer size = Integer.parseInt(matcher.group(1));
					Integer newSize = fontSize.getSize(size);
					text = text.substring(0, matcher.start() + offset) + "font-size: " + newSize + "px;" + text.substring(matcher.end()  + offset);
					offset += newSize.toString().length() - size.toString().length();
				}

				compPane.setTextualContent(text);
				compPane.refreshPane();
			}
		}

		for (Component	comp : c.getComponents()) {
			f = new Font(comp.getFont().getFamily(),
					comp.getFont().getStyle(),
					fontSize.getSize(comp.getFont().getSize()));

			if (comp instanceof JMenu) {
				int count = ((JMenu) comp).getItemCount();
				for (int i = 0; i < count; i++) {
					((JMenu) comp).getItem(i).setFont(f);
				}
			}

			if (comp instanceof JLabel) {
				JLabel compLabel = (JLabel) comp;
				if (compLabel.getText() != null) {
					Pattern pattern = Pattern.compile("font-size: (.*?)px;");
					Matcher matcher = pattern.matcher(compLabel.getText());
					String text = compLabel.getText();
					int offset = 0;

					while(matcher.find()) {
						Integer size = Integer.parseInt(matcher.group(1));
						Integer newSize = fontSize.getSize(size);
						text = text.substring(0, matcher.start() + offset) + "font-size: " + newSize + "px;" + text.substring(matcher.end()  + offset);
						offset += newSize.toString().length() - size.toString().length();
					}

					compLabel.setText(text);
				}
			}

			comp.setFont(f);
			if (!(comp instanceof Canvas))
				changeFontSize((Container)comp);
		}
	}

	public static void controlFontForExternalContainer(Container c){
		externalContainersForControlFont.add(c);
	}

	public static void removeControlFontForExternalContainer(Container c){
		externalContainersForControlFont.remove(c);
	}

	@Override
	public void update(Observable o, Object arg) {

		Collection<JPanel> list = JpCarrie.getInstance().getPaginator().getAllPanels();

//		JPanel main = JpCarrie.getInstance().getMainPanel();
//		changeFontSize(main);
//		SwingUtilities.updateComponentTreeUI(main);

		final Iterator<JPanel> panels = list.iterator();

		while (panels.hasNext()) {
			JPanel panel = panels.next();

			changeFontSize(panel);
			SwingUtilities.updateComponentTreeUI(panel);
		}

		// TODO Auto-generated method stub
		for (Container c : externalContainersForControlFont) {
			changeFontSize(c);
			SwingUtilities.updateComponentTreeUI(c);
		}
	}
}
