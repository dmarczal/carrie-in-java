package br.ufpr.c3sl.view.applet;

import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.ufpr.c3sl.daoFactory.DB4ODAOFactory;
import br.ufpr.c3sl.view.config.ConfigurationPane;
import br.ufpr.c3sl.view.principal.JpCarrie;

@SuppressWarnings("serial")
public abstract class JAppletCarrie extends JApplet {
	
	private JpCarrie carrie;
	
	public void init(final String name) {
		//Execute a job on the event-dispatching thread; creating this applet's GUI.
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI(name);
                }
            });
        } catch (Exception e) { 
            System.err.println("createGUI didn't complete successfully");
            e.printStackTrace();
        }
    }
	
	public void createGUI(String name){
		carrie = JpCarrie.getInstance();
		carrie.setName(name);
		JPanel pane = new ConfigurationPane();
		pane.setOpaque(true);
		this.setContentPane(pane);
		this.setSize(new Dimension(710, 540));
	}
	
	@Override
	public void destroy() {
		DB4ODAOFactory.getConnection().close();
		System.exit(0);
		super.destroy();
	}
	
	@Override
	public void stop() {
		DB4ODAOFactory.getConnection().close();
		System.exit(0);
		super.stop();
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
}
