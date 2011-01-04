package br.ufpr.c3sl.view.retroaction;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import br.ufpr.c3sl.deepClone.ObjectByteArray;
import br.ufpr.c3sl.model.Mistake;
import br.ufpr.c3sl.util.Util;
import br.ufpr.c3sl.view.principal.JpCarrie;

public class RetroactionFrame extends JFrame{

	private static final long serialVersionUID = -4715230985143190020L;
	private JPanel mainPanel;
	private JPanel mistakePanel;
	
	private Mistake mistake;
	
	public RetroactionFrame(Mistake mistake){
		super("Retroação: " + mistake.getMistakeInfo().getTitle() + " : " + mistake.getCreatedAtTime());
		this.mistake = mistake;
		this.mistakePanel = (JPanel) ObjectByteArray.getObject(mistake.getObject());
		this.mainPanel = new JPanel();
		
		Util.updateStaticFields(this.mistakePanel);
		
		this.mistakePanel.setPreferredSize(this.mistakePanel.getSize());
		
		mainPanel.setLayout(new BorderLayout());
		setContentPane(mainPanel);
		initComponents();
	}
	
	public void initComponents(){
		setupMenu();
		mistakePanel.setPreferredSize(mistakePanel.getPreferredSize());
		mainPanel.add(mistakePanel, BorderLayout.CENTER);
	}
	
	private void setupMenu(){
		JMenuBar jmenubar = new JMenuBar();
		
		JMenu menu = new JMenu("Aplicação"); 
		jmenubar.add(menu);
		
		JMenuItem itemShowErDescription = new JMenuItem("Descrição do Erro");
		itemShowErDescription.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JOptionPane.showMessageDialog(RetroactionFrame.this,
						"Retroação: " + mistake.getMistakeInfo().getTitle() + " : " + mistake.getCreatedAtTime()+"\n\n"+
						 mistake.getMistakeInfo().getTitle()+"\n\n"
						 + mistake.getMistakeInfo().getDescription() + "\n\n",
						 mistake.getMistakeInfo().getTitle() + " : "+ mistake.getCreatedAtTime(),
                         JOptionPane.INFORMATION_MESSAGE);

			}
		});
		menu.add(itemShowErDescription);
		
		JMenuItem itemResetState = new JMenuItem("Voltar ao estado inicial deste erro");
		
		itemResetState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetState();
			}
		});
		
		menu.add(itemResetState);
		
		JMenuItem itemExit = new JMenuItem("Fechar");
		
		itemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RetroactionFrame.this.dispose();
			}
		});
		
		menu.add(itemExit);
		
		mainPanel.add(jmenubar, BorderLayout.NORTH);
	}
	
	private void resetState() {
		mainPanel.remove(this.mistakePanel);
		this.mistakePanel = (JPanel) ObjectByteArray.getObject(mistake.getObject());
		Util.updateStaticFields(this.mistakePanel);
		mainPanel.add(this.mistakePanel);
		mainPanel.revalidate();
	}
	
	public void setVisible(boolean arg){
		this.resetState();
		super.setVisible(arg);
	}

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static JFrame createAndShowGUI(Mistake mistake) {
        //Create and set up the window.
        JFrame frame = new RetroactionFrame(mistake);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(JpCarrie.getInstance());
        frame.setVisible(true);
        return frame;
    }
}
