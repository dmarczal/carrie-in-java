package br.ufpr.c3sl.view.footer;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
import br.ufpr.c3sl.view.footer.paginator.JpPaginator;
import br.ufpr.c3sl.view.principal.JpCarrie;
import br.ufpr.c3sl.view.util.ImageButton;

@SuppressWarnings("serial")
public class JpMenuBarFooter extends JPanel {

	private ImageButton imgButtonSendServer;
	private ImageButton imgButtonCalc;
	private ImageButton imgButtonGlossary;
	private ImageButton imgButtonNotePad;
	private JpPaginator jpPaginator;
	private ErrorMenuBar errorMenuBar;

	public JpMenuBarFooter() {
		super(true);
		//setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
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

	private ErrorMenuBar getErrorMenuBar() {
		if (errorMenuBar == null){
			errorMenuBar = new ErrorMenuBar();
			this.add(errorMenuBar);
		}
		return errorMenuBar;
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

			}
		});

		imgButtonGlossary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

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
					
					retroaction.setUser(user);
					retroaction.setMistake(mistake);
					retroactionDAOS.insert(retroaction);
				}
			}

			JOptionPane.showMessageDialog(null, "Os dados foram atualizados com sucesso!!!");
			imgButtonSendServer.setVisible(false);
			resetMenuBar();

		} catch (UserException e) {
			e.printStackTrace();
		}
	}

	private void resetMenuBar(){
		this.remove(errorMenuBar);
		errorMenuBar = null;
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
	 *  Add a error in the menu bar
	 *  @param errorInfo A object who has all information about the error
	 */
	public void addErrorToMenu(Mistake mistake){
		if(DAOFactory.DATABASE_CHOOSE == DAOFactory.DB4O &&
				imgButtonSendServer == null){
			addButtonServer();
		}else if (DAOFactory.DATABASE_CHOOSE == DAOFactory.DB4O && !imgButtonSendServer.isVisible())
			imgButtonSendServer.setVisible(true);

		getErrorMenuBar().updateMenu(mistake);
	}
}

