package br.ufpr.c3sl.view.applet;

import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JPanel;

import br.ufpr.c3sl.view.principal.JpCarrie;

@SuppressWarnings("serial")
public class JAppletCarrie extends JApplet {
	
	private JpCarrie carrie = JpCarrie.getInstance();
	
	public void init(String name) {
		super.init();
		setObjetLearningName(name);
		this.setContentPane(carrie);
		this.setSize(new Dimension(800,600));
		carrie.loadDataFromBD();
	}
	
	/**
	 *  Add a panel to the paginator
	 *  @param String the identifier panel's name
	 *  @param JPanel panel to be added
	 */
	public void addPanel(String name, JPanel panel){
		carrie.addPanel(name, panel);
	}
	
	/**
	 *  Set Object Learning name
	 *  @param name Objet learing name
	 */
	public void setObjetLearningName(String name){
		JpCarrie.getInstance().setName(name);
	}
	
	@Override
	public void destroy() {
		JpCarrie.newInstance();
		super.destroy();
	}
}
