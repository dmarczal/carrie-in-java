package br.ufpr.c3sl.view.fontControl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import br.ufpr.c3sl.fontControl.FontSize;
import br.ufpr.c3sl.util.Util;
import br.ufpr.c3sl.view.util.ImageButton;

public class FontControlButtons extends JPanel {

	private static final long serialVersionUID = -7262763066445152050L;
	
	private ImageButton upSizeLetter;
	private ImageButton downSizeLetter;
	private ImageButton originalSizeLetter;

	public FontControlButtons(){
		addFontSizeButtos();
		setJButtosListeners();
	}
	
	private void addFontSizeButtos() {
		originalSizeLetter = new ImageButton(Util.getIconURL(getClass(), "normal_up"),
				Util.getIconURL(getClass(), "normal_down"));
		originalSizeLetter.setName("originalSizeLetter");
		originalSizeLetter.setToolTipText("Restaurar fonte padrão");
		this.add(originalSizeLetter);

		downSizeLetter = new ImageButton(Util.getIconURL(getClass(), "minus_up"),
				Util.getIconURL(getClass(), "minus_down"));
		downSizeLetter.setName("downSizeLetter");
		downSizeLetter.setToolTipText("Diminuir fonte");
		this.add(downSizeLetter);

		upSizeLetter = new ImageButton(Util.getIconURL(getClass(), "plus_up"),
				Util.getIconURL(getClass(), "plus_down"));
		upSizeLetter.setName("upSizeLetter");
		upSizeLetter.setToolTipText("Aumentar fonte");
		this.add(upSizeLetter);

	}
	
	private void setJButtosListeners() {
		upSizeLetter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				FontSize.getInstance().incrementSize();
			}
		});
		
		downSizeLetter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				FontSize.getInstance().decrementSize();
			}
		});

		originalSizeLetter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				FontSize.getInstance().beginSize();
			}
		});
	}
}