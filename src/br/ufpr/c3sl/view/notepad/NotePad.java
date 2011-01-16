package br.ufpr.c3sl.view.notepad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.StyledEditorKit;

import br.ufpr.c3sl.util.Util;
import br.ufpr.c3sl.view.util.ImageButton;


@SuppressWarnings("serial")
public class NotePad extends JPanel {
	
	private final int 		SIZE 		= 14;
//	private final String 	NEGRITO    	= "b";
//	private final String 	ITALICO    	= "i";
//	private final String 	SUBLINHADO	= "u";


	public final char DELIMITER_CARACTER = '§';
	
//	private StyledDocument doc;
	private JTextPane editorPane;
	private JPanel jpButtons;
	private Color colors[] = {Color.BLACK, Color.BLUE, Color.DARK_GRAY, Color.GREEN, Color.MAGENTA, Color.PINK, Color.RED};
	
	private ImageButton bold;
	private ImageButton italic;
	private ImageButton underline;
	
	public NotePad(){
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(350,400));
		configEditorPane();
	}

	public void configEditorPane(){
		editorPane = new JTextPane();
		JPanel controls = new JPanel(new GridLayout(1,15));
		
		add(controls, BorderLayout.NORTH);

		bold = new ImageButton(Util.getIconURL(getClass(), "bold_up"), Util.getIconURL(getClass(),"bold_down"));
		bold.setName("Negrito");
		bold.setToolTipText("Negrito");
		controls.add(bold);
		
		Action action = new StyledEditorKit.BoldAction();
		action.putValue(Action.NAME, "");
		bold.addActionListener(action);
		
		
		italic = new ImageButton(Util.getIconURL(getClass(),"italic_up"), Util.getIconURL(getClass(),"italic_down"));
		italic.setName("Itálico");
		italic.setToolTipText("Itálico");
		controls.add(italic);	
		
		action = new StyledEditorKit.ItalicAction();
		action.putValue(Action.NAME, "");
		italic.addActionListener(action);
		
		
		underline = new ImageButton(Util.getIconURL(getClass(),"underline_up"), Util.getIconURL(getClass(),"underline_down"));
		underline.setName("Sublinhado");
		underline.setToolTipText("Sublinhado");
		
		action = new StyledEditorKit.UnderlineAction();
		action.putValue(Action.NAME, "");
		underline.addActionListener(action);
		
		controls.add(underline);


		for (final Color color : colors) {
			ImageButton squareColor = new ImageButton(createColorImage(color, false), createColorImage(color, true));
			action = new StyledEditorKit.ForegroundAction(Action.NAME, color);
			action.putValue(Action.NAME, "");
			squareColor.addActionListener(action);
			controls.add(squareColor);
		}	



		JScrollPane editorScrollPane = new JScrollPane(this.editorPane);
		editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		editorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.editorPane = new JTextPane();
		this.editorPane.addKeyListener(new KeyAdapter() {
			public void keyTyped(final KeyEvent e) {				

				if(e.getKeyChar() == DELIMITER_CARACTER)
					e.setKeyChar('\u0000');			
			}
		});

		editorScrollPane.setViewportView(this.editorPane);
		add(editorScrollPane, BorderLayout.CENTER);


		// Tentativa de PDF
//		this.doc = this.editorPane.getStyledDocument();
//
//		Style style = doc.addStyle("default", null);
//
//		style = doc.addStyle(this.NEGRITO, null);
//		StyleConstants.setBold(style, true);
//
//		style = doc.addStyle(this.COLOR_BLUE, null);
//		StyleConstants.setItalic(style, true);
//
//		style = doc.addStyle(this.ITALICO, null);
//		StyleConstants.setItalic(style, true);
//
//		style = doc.addStyle(this.SUBLINHADO, null);
//		StyleConstants.setUnderline(style, true); 
		
		jpButtons = new JPanel();
		//TODO:Open and Save File this.add(jpButtons, BorderLayout.SOUTH);
		
		JButton openButton = new JButton();
		openButton.setName("Open");
		openButton.setText("Abrir");
		openButton.addActionListener(new MouseEventToOpenFile());
		jpButtons.add(openButton);
		
		JButton saveButton = new JButton();
		saveButton.setName("Save");
		saveButton.setText("Salvar");
		saveButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		jpButtons.add(saveButton);
		
		editorScrollPane.setMinimumSize(new Dimension(10, 10));
		this.add(editorScrollPane, BorderLayout.CENTER);
	}
	
	
	private BufferedImage createColorImage(Color color, boolean pressed) {
		BufferedImage im = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = im.createGraphics();

		g2d.setColor(color);
		g2d.fillRect(1, 1, SIZE, SIZE);

		g2d.setColor(Color.BLACK);
		g2d.drawRect(0, 0, SIZE - 1, SIZE - 1);

		if (pressed){
			g2d.setColor(Color.WHITE);
			g2d.drawRect(1, 1, SIZE-2, SIZE-2);
		}

		return im;
	}

	public static JFrame createAndShowGUI(){
		JFrame frame = new JFrame("Bloco de Anotações");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setContentPane(new NotePad());

		//Display the window.
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		return frame;
	}
	
	private class MouseEventToOpenFile implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			NoteFile nf = new NoteFile();
			editorPane.setText(nf.getText());
		}
	}
	
	public static void main(String[] args) {
		createAndShowGUI();
	}
	
//	public String getText(){	
//		return this.editorPane.getText();
//	}
//
//	public String getTextPersist(){
//		StringBuffer strText = new StringBuffer();
//
//		strText.append(this.editorPane.getText());
//
//		if( strText.length() == 0 )
//			return "";
//
//		while (true){			
//			int index = strText.indexOf(new Character(this.DELIMITER_CARACTER).toString());		
//
//			if( index != -1)
//				strText.deleteCharAt(index);
//			else
//				break;
//		}
//
//		List styleList = this.styleVectorMap();
//
//		if( styleList.isEmpty() )
//			return strText.toString();
//
//		strText.append(new Character(this.DELIMITER_CARACTER).toString());
//
//		for (int i = 0; i < styleList.size(); i++) 
//			strText.append(styleList.get(i)).append(";");
//
//		strText.replace(strText.lastIndexOf(";"), strText.toString().length() , "");
//
//		return strText.toString();
//	}
//
//	public String getTextPDF() throws Exception{
//		TextPDF textPDF = new TextPDF(this);
//		
//		return textPDF.getPDFText();
//	}	
//
//
//	public void setText(String text){
//
//		if(text == null)
//			return;
//
//		int index = text.indexOf(new Character(this.DELIMITER_CARACTER).toString());
//
//		if ( index == -1 ){
//			this.editorPane.setText(text);
//			return;
//		}
//
//		this.editorPane.setText(text.substring(0,index));
//
//		text = text.substring(index + 1, text.length());
//
//		if( text.indexOf(";") < 0 )
//			return;
//
//		String[] vectorStyle = text.substring(0, text.length()).split(";");				
//
//		for(int i = 0; i < vectorStyle.length; i++ ){
//
//			int index2 = this.getTextIndex(vectorStyle[i]);			
//
//			this.editorPane.select(index2, index2);
//
//			String estilo = this.getTextStyle(vectorStyle[i]);
//
//			this.applyStyle(estilo);
//		}
//
//	}
//
//	//	public final void setPdfFontName(String pdfFontName) {
//	//		this.fontName = pdfFontName;
//	//	}
//
//	private int getTextIndex(String content){
//		StringBuffer index = new StringBuffer();
//
//		for(int i = 0; i < content.length(); i++){
//			String character = content.substring(i, i + 1);
//
//			try{
//				new Integer(character);
//				index.append(character);				
//			}catch(Exception e){
//				break;
//			}
//		}		
//		return Integer.parseInt(index.toString());
//	}
//
//	private String getTextStyle(String content){
//		StringBuffer returnValue = new StringBuffer();
//
//		for(int i = 0; i < content.length(); i++){
//			String character = content.substring(i, i + 1);
//
//			try{
//				new Integer(character);								
//			}catch(Exception e){
//				returnValue.append(character);
//			}
//		}		
//		return returnValue.toString();
//	}
//
//	private List styleVectorMap(){
//
//		List styleList = new ArrayList();
//
//		for(int i = 0; i < this.editorPane.getText().length(); i++){
//			this.editorPane.setCaretPosition(i);
//
//			AttributeSet atribute = this.editorPane.getCharacterAttributes();
//
//			int acumulator = 0;
//
//			StringBuffer content = new StringBuffer(new Integer(i).toString());
//
//			Enumeration e = atribute.getAttributeNames();
//
//			while(e.hasMoreElements()) {            
//				Object key = e.nextElement();
//				Object attr = atribute.getAttribute(key);
//
//				if( attr == null) continue;        	
//
//				if( new Boolean(attr.toString()).booleanValue() )        		
//					content.append(key.toString().substring(0,1));
//
//				acumulator++;
//			}
//
//			if( acumulator > 0 )
//				styleList.add(content);
//		}
//
//		return styleList;
//	}
//
//	private void applyStyle(String style){
//
//		int sectionStart = this.editorPane.getSelectionStart();
//		int sectionEnd  = this.editorPane.getSelectionEnd() - sectionStart;		
//
//		if( sectionEnd < 1 ) sectionEnd = 1;
//
//		for(int i = 0; i < style.length(); i++)		
//			this.doc.setCharacterAttributes(sectionStart ,  sectionEnd,
//					this.editorPane.getStyle(style.substring(i, i + 1)), false);
//	}
//
//
//	private class TextPDF{
//		
//		private final FieldTextPane fieldTextPane;	
//		
//		private List listTextPDF = new LinkedList();
//		public TextPDF(FieldTextPane campoTextPane){
//			this.fieldTextPane = campoTextPane;
//		}
//		public String getPDFText() throws Exception{
//						
//			String content = this.fieldTextPane.getText();
//
//			for(int i = 0; i < content.length(); i++)
//				this.listTextPDF.add(new Character(content.charAt(i)).toString());
//						
//			List styleVector = this.fieldTextPane.styleVectorMap();								
//			
//			for(int i = 0; i < styleVector.size(); i++ ){
//				int index = this.fieldTextPane.getTextIndex(styleVector.get(i).toString());			
//				String character = this.listTextPDF.get(index).toString();
//				String style = this.fieldTextPane.getTextStyle(styleVector.get(i).toString());			
//				this.listTextPDF.set(index, this.applyPDFStyle(style, character));
//			}
//			
//			StringBuffer textPDF = new StringBuffer(); 
//			for (Iterator iterator = this.listTextPDF.iterator(); iterator.hasNext();) 
//				textPDF.append((String) iterator.next());			
//			
//			return textPDF.toString();		
//		}
//		
//		private String applyPDFStyle(String style, String character)throws Exception{
//			StringBuffer contentPDF = new StringBuffer("<style ");
//			
//			try{			
//				for(int i = 0; i < style.length(); i++)
//					if( style.substring(i, i + 1).equals(this.fieldTextPane.NEGRITO) )
//						contentPDF.append(" isBold=\"true\" ");
//					else if( style.substring(i, i + 1).equals(this.fieldTextPane.ITALICO) )
//						contentPDF.append(" isItalic=\"true\" ");
//					else
//						contentPDF.append(" isUnderline=\"true\" ");
//	
//				StringBuffer pdfFontName = new StringBuffer(this.fieldTextPane.fontName);
//				
//				if( style.indexOf(this.fieldTextPane.ITALICO) > -1 && style.indexOf(this.fieldTextPane.NEGRITO) > -1 ) 
//					pdfFontName.append("-BoldOblique");
//				else if( style.indexOf(this.fieldTextPane.NEGRITO) > -1 ) 
//					pdfFontName.append("-Bold");
//				else if( style.indexOf(this.fieldTextPane.ITALICO) > -1 ) 
//					pdfFontName.append("-Oblique");			
//				
//				contentPDF.append(" pdfFontName=\"").append(pdfFontName).append("\" >");
//				
//				contentPDF.append(character).append("</style>");
//			}catch(Exception e){
//				throw new Exception("Falha ao aplicar o estilo PDF no texto");
//			}
//			return contentPDF.toString();
//		}		
//	}
}