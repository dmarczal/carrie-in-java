package br.ufpr.c3sl.view.footer;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.ufpr.c3sl.daoFactory.DAOFactory;
import br.ufpr.c3sl.model.Mistake;
import br.ufpr.c3sl.util.Util;
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
	
	private JpPaginator getPaginator() {
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
				JOptionPane.showMessageDialog(null, "Está Funcionalidade Encontra-se em Construção");
			}
		});
		validate();
	}
	
	/**
	 *  add a panel to the paginator
	 *  @param jPanel to be added
	 */
	public void addPanelToPaginator(JPanel jPanel){
		getPaginator().addPanel(jPanel);
	}
	
	/**
	 *  Add a error in the menu bar
	 *  @param errorInfo A object who has all information about the error
	 */
	public void addErrorToMenu(Mistake mistake){
		if(DAOFactory.DATABASE_CHOOSE == DAOFactory.DB4O &&
				imgButtonSendServer == null){
			addButtonServer();
		} 
		getErrorMenuBar().updateMenu(mistake);
	}
}

