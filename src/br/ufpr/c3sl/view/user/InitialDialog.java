package br.ufpr.c3sl.view.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import br.ufpr.c3sl.connection.Internet;
import br.ufpr.c3sl.dao.UserDAO;
import br.ufpr.c3sl.daoFactory.DAOFactory;
import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.User;
import br.ufpr.c3sl.session.Session;
import br.ufpr.c3sl.util.Util;

public class InitialDialog extends JDialog {

	private static final long serialVersionUID = 6616103282260587283L;
	private JPanel jpHeader;
	private JPanel jpBody;
	private JPanel jpFooter;
	private JPanel jpMain;

	private JButton jbOK;

	private ButtonGroup group;

	private JRadioButton jrbServer;
	private JRadioButton jrbLocal;

	private JTextField jtfEmail;
	private JLabel lbErrors;

	public InitialDialog() {
		setTitle("Configuraçào Inicial");
		setModal(true);	
		buildDialog();
	}

	private void buildDialog(){
		jpMain = new JPanel(new BorderLayout());
		jpMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(jpMain, BorderLayout.CENTER);

		jpHeader = new JPanel();
		jpHeader.add(new JLabel("Configurações Iniciais do Carrie"));
		jpMain.add(jpHeader, BorderLayout.NORTH);

		jpBody = new JPanel();
		jpBody.setLayout(new BoxLayout(jpBody, BoxLayout.Y_AXIS));
		jpMain.add(jpBody, BorderLayout.CENTER);

		jpBody.add(lbErrors = new JLabel());
		lbErrors.setForeground(Color.red);

		jpBody.add(new JLabel("Por favor escolha o modo de execução"));

		jrbServer = new JRadioButton("Server Mode");
		jrbLocal = new JRadioButton("Local Mode");

		group = new ButtonGroup();
		group.add(jrbLocal);
		group.add(jrbServer);

		jpBody.add(jrbServer);
		jpBody.add(jrbLocal);

		jpBody.add(new JLabel("Por favor entre com seu email?"));
		jtfEmail = new JTextField("diego@gmail.com");

		jpBody.add(jtfEmail);

		jpFooter = new JPanel();
		jbOK = new JButton("OK");
		jbOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmdOK();
			}
		});

		jpFooter.add(jbOK);
		jpMain.add(jpFooter, BorderLayout.SOUTH);

		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	private void cmdOK() {
		validateAnwser();
	}

	private void validateAnwser(){
		ArrayList<String> errors = new ArrayList<String>();
		String msgs = "";

		if(!jrbLocal.isSelected() && !jrbServer.isSelected()){
			errors.add("Você deve selecionar um dos modos");
		}

		if(!Util.validateEmail(jtfEmail.getText())){
			errors.add("Email Inválido");
		}

		if (errors.size() > 0){
			for (int i = 0; i < errors.size(); i++) 
				msgs +=  errors.get(i) + "<br />";
			lbErrors.setBorder(BorderFactory.createTitledBorder("Erro"));
			lbErrors.setText("<html>" + msgs);
		}else{
			configureSession();
		}	
		this.pack();
	}

	private void configureSession(){
		DAOFactory.DATABASE_CHOOSE = jrbLocal.isSelected() ? DAOFactory.DB4O : DAOFactory.MYSQL;

		Internet.verifyConnection("Foi verificado que não existe conexão com a internet," +
		"\n seu modo de execução foi alterado para local");

		System.out.println(DAOFactory.DATABASE_CHOOSE);

		DAOFactory bd = DAOFactory.getDAOFactory(DAOFactory.DATABASE_CHOOSE);
		UserDAO userDAO = bd.getUserDAO();

		String email = jtfEmail.getText(); 

		try {
			User user = userDAO.findOrCreateByEmail(email);
			Session.setMode(jrbLocal.isSelected() ? "Local" : "Server");
			Session.setCurrentUser(user);
		} catch (UserException e) {
			e.printStackTrace();
		}
		this.dispose();
	}

	public static void showDialog(){
		InitialDialog dialog = new InitialDialog();
		dialog.setLocationRelativeTo(null);
		dialog.pack();
		dialog.setVisible(true);
	}
}