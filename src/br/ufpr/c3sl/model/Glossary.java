package br.ufpr.c3sl.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.TreeMap;
import java.util.regex.Pattern;

import br.ufpr.c3sl.util.Util;

// TODO: Auto-generated Javadoc
/**
 * The Class Glossary created on Mar 31, 2009.
 * 
 * @author Diego Marczal
 */
public class Glossary {

	/** The glossary list. */
	private TreeMap<String, String> glossaryList = new TreeMap<String, String>();

	/** The pattern word. */
	private Pattern patternWord = Pattern.compile("[(.)[^\\s]].+:");

	/** The pattern explain. */
	private Pattern patternExplain = Pattern.compile("\\s{2}[(.)[^\\s]].*");

	/** The word. */
	private String word="";

	/** The explain. */
	private String explain="";

	/** The line number. */
	private int lineNumber=1;

	/** The exist glossary. */
	private boolean existGlossary;

	/**
	 * Exist glossary.
	 * 
	 * @return true, if successful
	 */
	public boolean existGlossary() {
		return existGlossary;
	}

	/**
	 * Instantiates a new glossary.
	 */
	public Glossary(){

//		new Thread (new Runnable()  {  
//
//			public void run () {  
				// 	if (System.getSecurityManager() == null) {
				//	    System.setSecurityManager(new MySecurityManager());
				//	}
				//	AccessController.doPrivileged(new PrivilegedAction() {  
				//		public Object run() {            
				try {
					readFile();
					existGlossary = true;
				} catch (Exception e) {
					existGlossary = false;
					//e.printStackTrace();
					System.err.println("Glossary Not Specified ");
				}

				//	return new Object();              
				//	}  
				//	});  

//			}}).start();
	}

	/**
	 * Gets the keys.
	 * 
	 * @return the keys
	 */
	public Object[] getKeys(){
		return glossaryList.keySet().toArray();
	}

	/**
	 * Gets the value of key.
	 * 
	 * @param key the key
	 * 
	 * @return the value of key
	 */
	public String getValueOfKey(String key){
		return glossaryList.get(key);
	}

	/**
	 * Gets the path.
	 * 
	 * @param path the path
	 * 
	 * @return the path
	 */
	public java.net.URL getPath(String path){
		java.net.URL fileURL = Glossary.class.getResource(path);
		if (fileURL != null) {
			return fileURL;
		} else {
			new Exception("Couldn't find file: " + path).printStackTrace();
			return null;
		}
	}

	/**
	 * Read file.
	 * 
	 * @throws Exception the exception
	 */
	public void readFile() throws Exception{

		URL url = getPath(Util.GLOSSARY_PATH);
		URLConnection urlc = url.openConnection();

		BufferedReader bufRead = new BufferedReader(new InputStreamReader(urlc.getInputStream(), "UTF-8"));

		processFile(bufRead);

		bufRead.close();
	}

	/**
	 * Process file.
	 * 
	 * @param r the r
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void processFile(BufferedReader r) throws IOException {
		// d.available() returns 0 if the file does not have more lines.
		boolean notError=false;
		String line = r.readLine();
		// Read through file one line at time. Print line # and line
		while (line != null){		
			if (!(notError=processLine(line))){
				System.out.println("Erro na linha: "+lineNumber);
				glossaryList.clear();
				break;
			}

			lineNumber++;
			line = r.readLine();
		}

		if(notError)
			glossaryList.put(word, explain);
	}

	/**
	 * Process line.
	 * 
	 * @param s the s
	 * 
	 * @return true, if successful
	 */
	private boolean processLine(String s){
		if(patternWord.matcher(s).matches()){
			if(lineNumber != 1)
				glossaryList.put(word, explain);
			word = s.replace(':', ' ');
			explain = "";
		}else
			if(patternExplain.matcher(s).matches()){
				explain += s;
			}else
				if(s.trim().equals("")){
				}else
					return false;
		return true;		
	}
}
