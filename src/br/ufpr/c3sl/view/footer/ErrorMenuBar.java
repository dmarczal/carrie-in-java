package br.ufpr.c3sl.view.footer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import br.ufpr.c3sl.dao.RetroactionDAO;
import br.ufpr.c3sl.daoFactory.DAOFactory;
import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.Mistake;
import br.ufpr.c3sl.model.Retroaction;
import br.ufpr.c3sl.util.Util;
import br.ufpr.c3sl.view.retroaction.RetroactionFrame;

/**
 * CARRIE Framework
 * class ErrorMenuBar CARRIE framework
 * The Menu to retroaction a Error
 * @author  Diego Marczal
 * @version December 16, 2010
 */ 
@SuppressWarnings("serial")
public class ErrorMenuBar extends JMenuBar {

	private JMenu menu;
	private JMenu subMenuerror;
	private String imagePath = "error_up";

	public ErrorMenuBar() {
		createDefaulMenus();
	}

	private void createDefaulMenus() {
		setLocation(getLocation().x, 10);
		setPreferredSize(new Dimension(32, 32));
		menu = new JMenu("Menu");

		menu.setName("MenuBar");
		menu.setToolTipText("Erros Cometidos");
		menu.addMouseListener(new MenuErrorMouseListener());

		subMenuerror = new JMenu("Erros Cometidos");
		menu.add(subMenuerror);
		this.add(menu);
	}

	public void update(Graphics g) {
		Graphics buffer;
		Image offscreen = null;

		offscreen =  createImage(getSize().width, getSize().height);
		buffer = offscreen.getGraphics();

		paint(buffer);
		g.setColor(getParent().getBackground());
		g.drawImage(offscreen, 0,0, this);
		buffer.dispose();
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g.setColor(getBackground());
		g.fillRect(0, 0, getSize().width, getSize().height);
		g2.drawImage(getToolkit().getImage(Util.getIconURL(getClass(), imagePath)), 0, 0, this);
	}
	
	/**
	 *  Add a error in the menu 
	 *  @param errorInfo A object who has all information about the error
	 */
	public void updateMenu(Mistake mistake){
		JMenuItem item = new JMenuItem(
				"Erro: " + mistake.getExercise() + " - "
				+ Util.getDateTimeFormated(mistake.getCreatedAtTime()));

		item.setToolTipText(mistake.getMistakeInfo().getDescription());
		item.addActionListener(new MenuMistakeItemListener(mistake));

		subMenuerror.add(item);
		SwingUtilities.updateComponentTreeUI(this);
	}

	private class MenuErrorMouseListener implements MouseListener {

		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {
			imagePath = "error_up";
			repaint();
		}				
		public void mousePressed(MouseEvent e) {
			imagePath = "error_down";
			repaint();
		}
		public void mouseReleased(MouseEvent e) {
			imagePath = "error_up";
			repaint();
		}
	}

	private class MenuMistakeItemListener implements ActionListener {

		private Mistake mistake;
		private JFrame frame;

		public MenuMistakeItemListener(Mistake mistake) {
			this.mistake = mistake;
		}

		public void actionPerformed(ActionEvent e) {
			if (frame == null)
				//Schedule a job for the event-dispatching thread:
				//creating and showing this application's GUI.
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						frame = RetroactionFrame.createAndShowGUI(mistake);
						saveRetroaction();
					}
				});
			else{
				if (!frame.isVisible()){
					saveRetroaction();
				}				
				frame.setVisible(!frame.isVisible());
			}
		}
		
		private void saveRetroaction(){
			DAOFactory dao = DAOFactory.getDAOFactory(DAOFactory.DATABASE_CHOOSE);
			RetroactionDAO retroactionDao = dao.getRetroactionDAO();
			
			Retroaction retroaction = new Retroaction();
			retroaction.setMistake(mistake);
			
			try {
				retroactionDao.insert(retroaction);
			} catch (UserException e1) {
				e1.printStackTrace();
			}
		}
	}
}