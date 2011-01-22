package br.ufpr.c3sl.view.applet;

import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JPanel;

import br.ufpr.c3sl.daoFactory.DB4ODAOFactory;
import br.ufpr.c3sl.view.principal.JpCarrie;
import br.ufpr.c3sl.view.user.ConfigurationPane;

@SuppressWarnings("serial")
public abstract class JAppletCarrie extends JApplet {
	
	private JpCarrie carrie = JpCarrie.getInstance();
	
	public void init(String name) {
		super.init();
		carrie.setName(name);
		this.setContentPane(new ConfigurationPane());
		this.setSize(new Dimension(710, 540));
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
	 *  add a panel to the paginator from a html file
	 *  @param String filepath path
	 */
	public void addPageFromHtmlFile(String filepath) {
		carrie.addPageFromHtmlFile(filepath);
	}
	
	@Override
	public void destroy() {
		JpCarrie.newInstance();
		DB4ODAOFactory.getConnection().close();
		super.destroy();
	}
}
