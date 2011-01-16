package br.ufpr.c3sl.view.glossary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Position;

import br.ufpr.c3sl.model.Glossary;
import br.ufpr.c3sl.view.principal.JpCarrie;


// TODO: Auto-generated Javadoc
/**
 * The Class GlossaryGUI created on Mar 31, 2009.
 * 
 * @author Diego Marczal
 */
@SuppressWarnings("serial")
public class GlossaryGUI extends JFrame implements Observer{

	private JPanel jpMain;
	private JPanel jpLeft;
	private JPanel jpRight;

	private static JList list;

	private DefaultListModel listModel = new DefaultListModel();

	private JLabel textExplain;

	private Glossary glossary;

	private static GlossaryGUI glossaryGui;

	private JTextFileldCompleter jtfCompleter;

	private JScrollPane listScrollPane;



	/**
	 * Gets the single instance of GlossaryGUI.
	 * 
	 * @return single instance of GlossaryGUI
	 */
	public static GlossaryGUI getInstance(){
		if (glossaryGui == null){
			glossaryGui = new GlossaryGUI();
			return glossaryGui;
		}else
			return glossaryGui;
	}

	/**
	 * Instantiates a new glossary gui.
	 */
	private GlossaryGUI(){
		initializeVariables();
		CompleterNotify.getInstance().addObserver(this);
	}

	/**
	 * Initialize variables.
	 */
	private void initializeVariables(){
		this.setLayout(new BorderLayout());
		this.setFocusableWindowState(true);

		glossary = new Glossary();

		jpMain = new JPanel();
		jpMain.setLayout(new BorderLayout());
		jpMain.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder("Glossário"),
				BorderFactory.createEmptyBorder(5,5,5,5)));

		jpMain.setPreferredSize(new Dimension(600, 200));

		jpLeft = new JPanel();
		jpLeft.setBorder(BorderFactory.createEtchedBorder());
		jpLeft.setLayout(new BorderLayout());

		jpRight = new JPanel(new BorderLayout()){
			public Dimension getPreferredSize(){
				Dimension d = new Dimension();
				d.width = 300;
				return d;
			}
		}; 
		jpRight.setBorder(BorderFactory.createEtchedBorder());
		jpRight.setLayout(new BorderLayout());
		jpRight.setBackground(Color.WHITE);

		textExplain = new JLabel();

		list = new JList(listModel); //data has type Object[]
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					if (list.getSelectedValue() != null){
						StringBuffer conteudo = new StringBuffer();
						conteudo.append("<html>")
						.append("<div style=\"padding:5px\" align=\"justify\">")
						.append("<p style=\"text-indent: 15px;\">")
						.append(glossary.getValueOfKey(list.getSelectedValue().toString()))
						.append("</p>")
						.append("</div>")
						.append("<html>");

						textExplain.setText(conteudo.toString());
					}else
						textExplain.setText("");
				}
			}
		});

		list.setVisibleRowCount(8);

		listScrollPane = new JScrollPane(list);
		//JScrollPane jpRightScrollPane = new JScrollPane(jpRight);//,
		//JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		updateListModel(glossary.getKeys());



		jpRight.add(textExplain, BorderLayout.NORTH);

		jpMain.add(jpLeft, BorderLayout.WEST);
		jpMain.add(jpRight, BorderLayout.CENTER);


		jtfCompleter = new JTextFileldCompleter(15);
		jtfCompleter.setLitOflistOfWords(listModel.toArray());


		jpLeft.add(jtfCompleter, BorderLayout.NORTH);
		jpLeft.add(listScrollPane,BorderLayout.CENTER);

		String initialText = "<html>\n" +
		"<p style='padding:5px;text-align: justify;'>Glossário de Termos</p>"+
		"</html>\n";

		textExplain.setText(initialText);


		this.add(jpMain,BorderLayout.CENTER);

		this.pack();
		this.setTitle("Glossário");
		//this.setResizable(false);
		this.setLocationRelativeTo(JpCarrie.getInstance());
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	/**
	 * Update list model.
	 * 
	 * @param Object[]
	 */
	private void updateListModel(Object[] ob){
		listModel.clear();
		for (int i = 0; i < ob.length; i++) {
			listModel.addElement(ob[i]);
		}

		list.setSelectedIndex(0);
	}

	/**
	 * show the key "s" explanation of glossary in the screen
	 * 
	 * @param String
	 */
	@SuppressWarnings("static-access")
	public static void updateHyperLink(String s) {
		int index = GlossaryGUI.getInstance().list.getNextMatch(s, 0, Position.Bias.Forward );
		GlossaryGUI.getInstance().list.setSelectedIndex(index);
		list.ensureIndexIsVisible(index);
		if (GlossaryGUI.getInstance().isVisible()){
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						GlossaryGUI.getInstance().setVisible(false);
						Thread.sleep(200);
						GlossaryGUI.getInstance().setVisible(true);
						GlossaryGUI.getInstance().toFront();
						GlossaryGUI.getInstance().requestFocus();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}else
			GlossaryGUI.getInstance().setVisible(true);
	}


	/**
	 * Checks if is ok.
	 * 
	 * @return true, if is ok
	 */
	public boolean isOk(){
		return glossary.existGlossary();
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
		updateListModel(jtfCompleter.getFindedWords());
	}

	//	public static void createAndShowGUI() throws Exception {
	//		//Create and set up the window.
	//		MainGlossary frame = new MainGlossary();
	//		frame.pack();
	//		frame.setTitle("Glossário");
	//		frame.setResizable(false);
	//		frame.setLocationRelativeTo(null);
	//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//		frame.setVisible(true);
	//	}
	//
	//
	//	public static void main(String[] args)  {
	//		javax.swing.SwingUtilities.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					MainGlossary.createAndShowGUI();
	//				} catch (Exception e) {
	//					// TODO Auto-generated catch block
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}
}
