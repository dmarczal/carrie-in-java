package br.ufpr.c3sl.view.glossary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


@SuppressWarnings("serial")
public class JTextFileldCompleter extends JTextField{

	private ArrayList<String> listOfWords = new ArrayList<String>();
	private ArrayList<String> findedWords = new ArrayList<String>();
	
	public JTextFileldCompleter(int witdh){
		super(witdh);
		new Completer();
	}
	
	public void setLitOflistOfWords(Object[] l){
		this.listOfWords.clear();
		for (int i = 0; i < l.length; i++) {
			this.listOfWords.add((String)l[i]);
		}
	}
	
	public Object[] getFindedWords(){
		return findedWords.toArray();
 	}
	
	
	private class Completer implements DocumentListener{
		 
		private Pattern pattern;
		
		public Completer(){
			getDocument().addDocumentListener(this);
		}
 
		private void findWords(){
			Iterator<String> it = listOfWords.iterator();
 
			pattern = Pattern.compile(getText() + ".+");
 
			while(it.hasNext()){
				String completion = it.next();
				Matcher matcher = pattern.matcher(completion);
 
				if(matcher.matches()){
					findedWords.add(completion);
				} 
			} 
		}  
 
		private void buildListModel(){
			findWords();
			
			if(getText().length() < 1) {
				findedWords.clear();
				findedWords.addAll(listOfWords);
			}
			CompleterNotify.getInstance().changedState();
			findedWords.clear();
		}
 		
		public void changedUpdate(DocumentEvent e) {buildListModel();}
 
		public void insertUpdate(DocumentEvent e) {buildListModel();}
 
		public void removeUpdate(DocumentEvent e) {buildListModel();}
 	}

}
