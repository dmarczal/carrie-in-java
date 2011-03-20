package br.ufpr.c3sl.view.principal;

import java.awt.BorderLayout;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import br.ufpr.c3sl.deepClone.Compressor;
import br.ufpr.c3sl.deepClone.ObjectByteArray;
import br.ufpr.c3sl.model.Mistake;
import br.ufpr.c3sl.model.MistakeInfo;
import br.ufpr.c3sl.pageHTML.Html;
import br.ufpr.c3sl.session.Session;
import br.ufpr.c3sl.util.Util;
import br.ufpr.c3sl.view.PageHTML.JPanelHTML;
import br.ufpr.c3sl.view.footer.JpMenuBarFooter;
import br.ufpr.c3sl.view.footer.paginator.JpPaginator;
import br.ufpr.c3sl.view.header.HeaderPane;
import br.ufpr.c3sl.view.util.EnableDisable;

/**
 * CARRIE Framework
 * class JpCarrie the main panel of CARRIE framework
 * @author  Diego Marczal
 * @version Outubro 16, 2010
 */ 
@SuppressWarnings("serial")
public class JpCarrie extends JPanel{


	private JPanel jpBody;
	private JPanel jpMain;
	private JPanel jpFooter;
	private JPanel paneToSave = null;

	private JpMenuBarFooter jpMenuFooter;
	private HeaderPane jpHeader;

	private static final Border BORDER = BorderFactory.createEtchedBorder();
	private static JpCarrie carrie = new JpCarrie();

	public static JpCarrie getInstance(){
		return carrie;
	}

	public static void newInstance(){
		String name = null;
		if (carrie != null)
			name = carrie.getName();

		carrie = new JpCarrie();
		carrie.setName(name);
	}

	/**
	 * Private Constructor , initialize variables
	 */ 
	private JpCarrie(){
		jpHeader = new HeaderPane();
		jpBody = new JPanel(new BorderLayout());
		jpFooter  = new JPanel();
		jpMenuFooter = new JpMenuBarFooter();
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
		configureBorder(jpBody);
		configureBorder(jpFooter);
	}

	private void configureHeader(){
		jpHeader.configureHeader();
		configureBorder(jpHeader);
	}

	public void updateHeader(){
		jpHeader.updateHeader();
	}

	private void updateTitle(){
		String parte = jpMain.getName().split(":")[0];
		jpHeader.updateTitle(this.getName() + " - " + parte);
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

	/*----------------------------------------*/

	/**
	 *  After page of configure this method must be
	 *  executed;
	 *  This will update the header and load the DATa
	 */
	public void finalConfiguration(){
		configureHeader();
		loadDataFromBD();
	}

	/**
	 *  get main panel
	 *  @return JPanel the main panel
	 */
	public JPanel getMainPanel(){
		return jpMain;
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
	 *  add a panel to the paginator from a html file
	 *  @param String filepath path
	 */
	public void addPageFromHtmlFile(String filepath) {
		addHtmlContent(Util.getTextFromFile(getClass(), filepath));
	}


	/**
	 *  Show loading Message on the top of application
	 */
	public void showLoading(){
		jpHeader.showLoading();
		EnableDisable.setComponentsEnabled(this, false);
	}

	/**
	 *  Hidden loading message
	 */
	public void hideLoading(){
		jpHeader.hideLoading();
		EnableDisable.setComponentsEnabled(this, true);
	}

	/*----------------------------------------*/

	private void addHtmlContent(String code) {
		JPanelHTML panel = new JPanelHTML();
		Html html = new Html(code);
		panel.setTextualContent(html.getCode());
		panel.setTitle(html.getTitle());
		panel.create();
		addPanel(html.getLegend(), panel);
	}


	/**
	 *  Save the main panel state 
	 *  @param mistake information about the mistake
	 */
	public void saveState(final MistakeInfo mistakeInfo){
		//Internet.verifyConnection();

		final Mistake mistake = new Mistake();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				final JPanel toSave;
				if (paneToSave == null)
					toSave = jpMain;
				else
					toSave = paneToSave;

				byte[] obj;
				
				synchronized (toSave) {
					obj = ObjectByteArray.getByteOfArray(toSave);
				}

				try {
					mistake.setObject(Compressor.compress(obj));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				mistake.setExercise(toSave.getName());
				mistake.setOa(JpCarrie.this.getName());
				mistake.setUser(Session.getCurrentUser());

				mistake.setAnswer(mistakeInfo.getAnswer());
				mistake.setCell(mistakeInfo.getCell());
				mistake.setCorrectAnswer(mistakeInfo.getCorrectAnswer());
				mistake.setDescription(mistakeInfo.getDescription());
				mistake.setTitle(mistakeInfo.getTitle());
				mistake.setCreatedAt(new Timestamp(new Date().getTime()));
				mistake.setUpdatedAt(new Timestamp(new Date().getTime()));

				Thread sendToBd = new Thread(new Runnable() {
					@Override
					public void run() {
						jpMenuFooter.addErrorToMenu(mistake.save());
					}
				});

				sendToBd.start();
			}
		});
	}

	public void setPanelToSave(JPanel paneToSave){
		this.paneToSave = paneToSave;
	}

	private void loadDataFromBD() {
		Thread loadData = new Thread(new Runnable() {
			@Override
			public void run() {
				List<Mistake> list = Mistake.all(JpCarrie.this.getName());
				if(list.size() > 0)
					jpMenuFooter.getPaginateMistakes().addMistakes(list);			
			}
		});

		loadData.start();
	}
}
