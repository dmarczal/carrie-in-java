package br.ufpr.c3sl.view.principal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import br.ufpr.c3sl.connection.Internet;
import br.ufpr.c3sl.dao.MistakeDAO;
import br.ufpr.c3sl.daoFactory.DAOFactory;
import br.ufpr.c3sl.deepClone.ObjectByteArray;
import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.Mistake;
import br.ufpr.c3sl.model.MistakeInfo;
import br.ufpr.c3sl.model.User;
import br.ufpr.c3sl.session.Session;
import br.ufpr.c3sl.util.Util;
import br.ufpr.c3sl.view.footer.JpMenuBarFooter;
import br.ufpr.c3sl.view.footer.paginator.JpPaginator;
import br.ufpr.c3sl.view.user.InitialDialog;
import br.ufpr.c3sl.view.util.ImageButton;

/**
 * CARRIE Framework
 * class JpCarrie the main panel of CARRIE framework
 * @author  Diego Marczal
 * @version Outubro 16, 2010
 */ 
@SuppressWarnings("serial")
public class JpCarrie extends JPanel{

	private JPanel jpHeader;
	private JPanel jpBody;
	private JPanel jpMain;
	private JPanel jpFooter;
	private JPanel jpFontSize;

	private JpMenuBarFooter jpMenuFooter;

	private static final Border BORDER = BorderFactory.createEtchedBorder();

	private static JpCarrie carrie = new JpCarrie(true);

	private static Font FONT = new Font("Arial", Font.PLAIN, 16);
	private static Font FONT_TITLE = new Font("Arial", Font.BOLD, 16);
	private JLabel lbTitle;

	private ImageButton upSizeLetter;
	private ImageButton downSizeLetter;
	private ImageButton originalSizeLetter;

	public static JpCarrie getInstance(){
		return carrie;
	}

	public static void newInstance(){
		carrie = new JpCarrie(false);
	}
	
	/**
	 * Private Constructor , initialize variables
	 */ 
	private JpCarrie(boolean showDialogMode){
		if (showDialogMode)
			InitialDialog.showDialog();
		jpHeader = new JPanel(new BorderLayout());
		jpBody = new JPanel(new BorderLayout());
		jpFooter  = new JPanel();
		jpMenuFooter = new JpMenuBarFooter();
		jpFontSize = new JPanel();
		create();
	}

	/*
	 * Create Component JpCarrie
	 */
	private void create(){
		configure();
		addComponents();
		addBorder();
	}

	/*
	 * Configure layout of JpCarrie
	 */
	private void configure(){
		setLookAndFell();
		this.setLayout(new BorderLayout());
		configureFooter();
		configureHeader();
		configTitle();
		addFontSizeButtos();
	}

	/*
	 * Add Components in JpCarrie
	 */
	private void addComponents(){
		this.add(jpHeader, BorderLayout.NORTH);
		this.add(jpBody, BorderLayout.CENTER);
		this.add(jpFooter, BorderLayout.SOUTH);
	}


	private void addBorder(){
		configureBorder(this);
		configureBorder(jpHeader);
		configureBorder(jpBody);
		configureBorder(jpFooter);
	}
	
	private void configureHeader(){
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
		lbmode.setText("Modo e execução " + mode);
		

		jpInfo.add(lbemail);
		jpInfo.add(lbmode);
		
		jpHeader.add(jpInfo, BorderLayout.WEST);
	}

	private void configTitle() {
		JPanel jpTitle = new JPanel();
		lbTitle = new JLabel("Default Title");
		jpTitle.add(lbTitle);
		jpHeader.add(jpTitle, BorderLayout.CENTER);
	}

	private void updateTitle(){
		String parte = jpMain.getName().split(":")[0];
		lbTitle.setFont(FONT_TITLE);
		lbTitle.setText(this.getName() + " - " + parte);
	}

	private void addFontSizeButtos() {
		originalSizeLetter = new ImageButton(Util.getIconURL(getClass(), "normal_up"),
				Util.getIconURL(getClass(), "normal_down"));
		originalSizeLetter.setName("originalSizeLetter");
		originalSizeLetter.setToolTipText("Restaurar fonte padrão");
		jpFontSize.add(originalSizeLetter);

		downSizeLetter = new ImageButton(Util.getIconURL(getClass(), "minus_up"),
				Util.getIconURL(getClass(), "minus_down"));
		downSizeLetter.setName("downSizeLetter");
		downSizeLetter.setToolTipText("Diminuir fonte");
		jpFontSize.add(downSizeLetter);

		upSizeLetter = new ImageButton(Util.getIconURL(getClass(), "plus_up"),
				Util.getIconURL(getClass(), "plus_down"));
		upSizeLetter.setName("upSizeLetter");
		upSizeLetter.setToolTipText("Aumentar fonte");
		jpFontSize.add(upSizeLetter);

		jpHeader.add(jpFontSize, BorderLayout.EAST);
	}

	/*
	 * Configure border of a JPanel
	 * @param JPanel jpanel
	 */
	private void configureBorder(JPanel jpanel){
		jpanel.setBorder(BORDER);
	}

	private void configureFooter(){
		jpFooter.add(jpMenuFooter);
	}

	private void setLookAndFell() {
		try {
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *  update main panel
	 *  @param JPanel newPanel new main panel
	 */
	public void updateMainPanel(JPanel newPanel){
		if (jpMain != null)
			this.jpBody.remove(jpMain);

		this.jpMain = newPanel;
		this.jpBody.add(jpMain, BorderLayout.CENTER);

		updateTitle();
		SwingUtilities.updateComponentTreeUI(jpBody);		
	}
	
	/**
	 *  Get the paginator Object
	 *  @return paginator Objetc
	 */
	public JpPaginator getPaginator(){
		return jpMenuFooter.getPaginator();
	}

	/**
	 *  add a panel to the paginator
	 *  @param String the identifier panel's name
	 *  @param JPanel panel to be added
	 */
	public void addPanel(String name, JPanel panel){
		panel.setName(name);
		jpMenuFooter.addPanelToPaginator(panel);
	}
		
	/**
	 *  Save the main panel state 
	 *  @param mistake information about the mistake
	 */
	public void saveState(final MistakeInfo mistakeInfo){
		Internet.verifyConnection();
		
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				Mistake mistake = new Mistake();
				mistake.setExercise(jpMain.getName());
				mistake.setLearningObject(JpCarrie.this.getName());
				mistake.setMistakeInfo(mistakeInfo);
				mistake.setObject(ObjectByteArray.getByteOfArray(jpMain));
				mistake.setUser(Session.getCurrentUser());

				DAOFactory dao = DAOFactory.getDAOFactory(DAOFactory.DATABASE_CHOOSE);
				MistakeDAO mistakedao = dao.getMistakeDAO();

				try {
					mistake = mistakedao.insert(mistake);
					jpMenuFooter.addErrorToMenu(mistake);
				} catch (UserException e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void loadDataFromBD() {
		DAOFactory dao = DAOFactory.getDAOFactory(DAOFactory.DATABASE_CHOOSE);
		MistakeDAO mistakedao = dao.getMistakeDAO();

		List<Mistake> list = mistakedao.getAll(Session.getCurrentUser(), this.getName());

		for (Mistake mistake : list) {
			jpMenuFooter.addErrorToMenu(mistake);
		}
	}

}
