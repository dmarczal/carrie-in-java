package br.ufpr.c3sl.view.header;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.ufpr.c3sl.model.User;
import br.ufpr.c3sl.session.Session;
import br.ufpr.c3sl.view.fontControl.FontControlButtons;
import br.ufpr.c3sl.view.util.LoadingPanel;

public class HeaderPane extends JPanel {
	
	private static final long serialVersionUID = -8792075543311677476L;
	private LoadingPanel loadingPane;
	private JPanel jpFontButtons;

	private static Font FONT = new Font("Arial", Font.PLAIN, 11);
	private static Font FONT_TITLE = new Font("Arial", Font.BOLD, 16);
	
	private JLabel lbTitle;
	
	public HeaderPane(){
		setLayout(new BorderLayout());
		jpFontButtons = new FontControlButtons();
		loadingPane = new LoadingPanel();
		addComponents();
		configTitle();
		//configureHeader();
	}
	
	/**
	 *  update Title of the application
	 *  @param title The new Title
	 */
	public void updateTitle(String title){
		lbTitle.setFont(FONT_TITLE);
		lbTitle.setText(title);
	}
	
	/**
	 *  Show loading Message on the top of application
	 */
	public void showLoading(){
		loadingPane.startLoading();
	}

	/**
	 *  Hidden loading message
	 */
	public void hideLoading(){
		loadingPane.stopLoading();
	}
	
	private void configTitle() {
		JPanel jpTitle = new JPanel();
		lbTitle = new JLabel("Default Title");
		jpTitle.add(lbTitle);
		this.add(jpTitle, BorderLayout.CENTER);
	}
	
	private void addComponents(){
		this.add(loadingPane, BorderLayout.NORTH);
		this.add(jpFontButtons, BorderLayout.EAST);
	}
	
	public void configureHeader(){
		JPanel jpInfo = new JPanel();
		jpInfo.setLayout(new BoxLayout(jpInfo, BoxLayout.Y_AXIS));

		String email = "";
		String mode =  "";

		User user = Session.getCurrentUser();
		if(user != null){
			email = user.getEmail();
			mode =  Session.getMode();	
		}

		JLabel lbemail = new JLabel();
		lbemail.setFont(FONT);
		lbemail.setForeground(Color.red);
		lbemail.setText("Olá " + email);
		
		JLabel lbmode = new JLabel();	
		lbmode.setFont(FONT);
		lbmode.setForeground(Color.red);
		lbmode.setText("Modo de execução: " + mode);
		
		jpInfo.add(lbemail);
		jpInfo.add(lbmode);
		
		this.add(jpInfo, BorderLayout.WEST);
	}
}
