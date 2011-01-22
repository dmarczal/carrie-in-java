package br.ufpr.c3sl.view.footer;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.ufpr.c3sl.connection.Internet;
import br.ufpr.c3sl.dao.MistakeDAO;
import br.ufpr.c3sl.dao.RetroactionDAO;
import br.ufpr.c3sl.dao.UserDAO;
import br.ufpr.c3sl.daoFactory.DAOFactory;
import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.Mistake;
import br.ufpr.c3sl.model.Retroaction;
import br.ufpr.c3sl.model.User;
import br.ufpr.c3sl.session.Session;
import br.ufpr.c3sl.util.Util;
import br.ufpr.c3sl.view.calculator.Calculator;
import br.ufpr.c3sl.view.footer.paginator.JpPaginator;
import br.ufpr.c3sl.view.glossary.GlossaryGUI;
import br.ufpr.c3sl.view.notepad.NotePad;
import br.ufpr.c3sl.view.principal.JpCarrie;
import br.ufpr.c3sl.view.retroaction.PaginateMistakes;
import br.ufpr.c3sl.view.retroaction.PaginateRetroFrame;
import br.ufpr.c3sl.view.util.ImageButton;

@SuppressWarnings("serial")
public class JpMenuBarFooter extends JPanel {

	private ImageButton imgButtonSendServer;
	private ImageButton imgButtonCalc;
	private ImageButton imgButtonGlossary;
	private ImageButton imgButtonNotePad;
	private ImageButton imgButtonRetroaction;
	
	private JpPaginator jpPaginator;
	
	private PaginateMistakes paginateMistakes;

	private Calculator calc;

	public JpMenuBarFooter() {
		super(true);
		calc = new Calculator();
		createImagesButtons();
		addListeners();
	}

	private JpPaginator paginator() {
		if (jpPaginator == null){
			jpPaginator = new JpPaginator();
			this.add(jpPaginator, FlowLayout.LEFT);
		}
		return jpPaginator;
	}
	
	public PaginateMistakes getPaginateMistakes(){
		if (imgButtonRetroaction == null){
			paginateMistakes = new PaginateMistakes();
			imgButtonRetroaction = configureImageButton(imgButtonRetroaction, "error", "Retroação a Erros");
			imgButtonRetroaction.addActionListener(new ActionListener() {
				
				private JFrame frame;
				
				public void actionPerformed(ActionEvent e) {
					if (frame == null){
						javax.swing.SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								frame = new PaginateRetroFrame(paginateMistakes);
							}
						});
					}else{
						if (!frame.isVisible()){
						}				
						frame.setVisible(!frame.isVisible());
					}
				}
			});
		}
		
		showButtonServerIfNecessary();
		return paginateMistakes;
	}


	private void createImagesButtons(){
		imgButtonCalc = configureImageButton(imgButtonCalc, "calc", "Calculadora");
		imgButtonGlossary = configureImageButton(imgButtonGlossary, "glossary", "Glossário");
		imgButtonNotePad = configureImageButton(imgButtonNotePad, "notepad", "Bloco de Notas");
	}

	private ImageButton configureImageButton(ImageButton imgButton,
			String image,
			String toolTipText){

		imgButton = new ImageButton(
				Util.getIconURL(getClass(), image+"_down"),
				Util.getIconURL(getClass(), image+"_up"));

		imgButton.setName(toolTipText);
		imgButton.setToolTipText(toolTipText);

		this.add(imgButton);

		return imgButton;
	}

	private void addListeners(){
		imgButtonCalc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calc.setVisible(!calc.isVisible());
				if (calc.isVisible())
					calc.toFront();
			}
		});

		imgButtonGlossary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GlossaryGUI.getInstance().
				setVisible(!GlossaryGUI.getInstance().isVisible());	
				if (GlossaryGUI.getInstance().isVisible())
					GlossaryGUI.getInstance().toFront();
			}
		});

		imgButtonNotePad.addActionListener(new ActionListener() {
			JFrame notePadFrame;

			public void actionPerformed(ActionEvent e) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						if (notePadFrame == null)
							notePadFrame = NotePad.createAndShowGUI();
						else{
							notePadFrame.setVisible(!notePadFrame.isVisible());
							if (notePadFrame.isVisible())
								notePadFrame.toFront();
						}
					}
				});
			}
		});
	}
	
	public void addButtonServer(){
		imgButtonSendServer = configureImageButton(imgButtonSendServer, "server", "Enviar Dados Para Servidor");
		imgButtonSendServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendToServer();
			}
		});
		validate();
	}


	private void sendToServer(){
		if (Internet.isNotReachable()){
			JOptionPane.showMessageDialog(null, "Não foi impossível enviar os dados para o servidor! \n" +
					"Não foi possível estabelecer conexão com a internet \n\nPor favor, tente mais tarde!",
					"Erro de conexão", JOptionPane.ERROR_MESSAGE);
		}else{
			updateServer();	
		}
	}

	private void updateServer() {
		JpCarrie.getInstance().showLoading();
		
		DAOFactory daoServer = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
		MistakeDAO mistakeDaoS = daoServer.getMistakeDAO();
		UserDAO userDao = daoServer.getUserDAO();
		RetroactionDAO retroactionDAOS = daoServer.getRetroactionDAO();

		DAOFactory daoLocal = DAOFactory.getDAOFactory(DAOFactory.DB4O);
		MistakeDAO mistakeDaoL = daoLocal.getMistakeDAO();
		RetroactionDAO retroactionDAOL = daoLocal.getRetroactionDAO();

		try {
			User user = userDao.findOrCreateByEmail(Session.getCurrentUser().getEmail());

			List<Mistake> list = mistakeDaoL.getAll(user,
					JpCarrie.getInstance().getName());
			
			
			for (Mistake mistake : list) {
				List<Retroaction> retroactionList = retroactionDAOL.getAll(mistake);
				mistakeDaoL.delete(mistake);

				mistake.setUser(user);
				mistake = mistakeDaoS.insert(mistake);

				for (Retroaction retroaction : retroactionList) {
					retroactionDAOL.delete(retroaction);

					retroaction.setMistake(mistake);
					retroactionDAOS.insert(retroaction);
				}
			}

			JOptionPane.showMessageDialog(null, "Os dados foram atualizados com sucesso!!!");
			imgButtonSendServer.setVisible(false);
			resetPaginateMistakes();

		} catch (UserException e) {
			e.printStackTrace();
		}
		
		JpCarrie.getInstance().hideLoading();
	}

	private void resetPaginateMistakes(){
		this.remove(imgButtonRetroaction);
		imgButtonRetroaction = null;
		validate();
	}

	/**
	 *  add a panel to the paginator
	 *  @param jPanel to be added
	 */
	public void addPanelToPaginator(JPanel jPanel){
		paginator().addPanel(jPanel);
	}

	/**
	 *  Get the paginator Object
	 *  @return paginator Objetc
	 */
	public JpPaginator getPaginator(){
		return paginator();
	}
	
	/**
	 *  getAllPanels
	 *  @return all panels added at the CARRIE
	 */
	public Collection<JPanel> getAllPanels() {
		return paginator().getAllPanels();
	}

	/**
	 *  Add a error in the menu bar
	 *  @param errorInfo A object who has all information about the error
	 */
	public void addErrorToMenu(Mistake mistake){
		getPaginateMistakes().addMistake(mistake);
	}

	private void showButtonServerIfNecessary() {
		if(DAOFactory.DATABASE_CHOOSE == DAOFactory.DB4O &&
				imgButtonSendServer == null){
			addButtonServer();
		}else if (DAOFactory.DATABASE_CHOOSE == DAOFactory.DB4O && !imgButtonSendServer.isVisible())
			imgButtonSendServer.setVisible(true);
		
		SwingUtilities.updateComponentTreeUI(this);
	}
}