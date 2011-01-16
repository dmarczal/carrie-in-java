package br.ufpr.c3sl.view.retroaction;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import br.ufpr.c3sl.view.principal.JpCarrie;

public class PaginateRetroFrame extends JFrame{

	private static final long serialVersionUID = -9196697855924265573L;

	private PaginateMistakes paginateMistakes;
	
	public PaginateRetroFrame(PaginateMistakes paginateMistakes){
		super("Página de Retroação a Erros");
		this.paginateMistakes = paginateMistakes;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().add(paginateMistakes);
		pack();
		setLocationRelativeTo(JpCarrie.getInstance());
		setVisible(true);
		paginateMistakes.showFirstPage();
		addWindowListener();
	}
	
	private void addWindowListener(){
		this.addWindowListener(new WindowListener() {

			public void windowOpened(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {
				setVisible(false);
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				setVisible(false);
			}
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				setVisible(false);
			}

			@Override
			public void windowActivated(WindowEvent e) {}
		});
	}
	
	@Override
	public void setVisible(boolean b) {
		if(b)
			paginateMistakes.showFirstPage();
		super.setVisible(b);
	}
}
