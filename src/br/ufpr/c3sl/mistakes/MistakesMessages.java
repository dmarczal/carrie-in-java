package br.ufpr.c3sl.mistakes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * The Mistake Messages
 * @author Diego Marczal
 */

public class MistakesMessages {

	private TreeMap<String, Mistake> messagesMap = new TreeMap<String, Mistake>();

	private Pattern patternComment = Pattern.compile("/{2}.*");
	private Pattern patternId = Pattern.compile("[(.)[^\\s]]+:");
	private Pattern patternMessage = Pattern.compile("\\s{2}[(.)[^\\s]].*");

	private String id="";
	private String message="";

	private int lineNumber=1;

	private boolean existMessagesFile;

	/**
	 * Exist Message File.
	 * @return true if it exists 
	 */
	public boolean existMessageFile() {
		return existMessagesFile;
	}

	/**
	 * Instantiates a new Messages Files.
	 */
	public MistakesMessages(){
		try {
			readFile();
			existMessagesFile = true;
		} catch (Exception e) {
			existMessagesFile = false;
			//e.printStackTrace();
			System.err.println("Messages File Not Specified ");
		}
	}


	/**
	 * Gets the value of key.
	 * @param key the key
	 * @return the value of key
	 */
	public Mistake getValueOfKey(String key){
		return messagesMap.get(key);
	}

	/**
	 * Gets the path.
	 * @param path the path
	 * @return the path
	 */
	public java.net.URL getPath(String path){
		java.net.URL fileURL = MistakesMessages.class.getResource(path);
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
		URL url = getPath("/org/c3sl/ufpr/br/mistakes/messages");
		URLConnection urlc = url.openConnection();

		BufferedReader bufRead = new BufferedReader(new InputStreamReader(urlc.getInputStream(), "UTF-8"));

		processFile(bufRead);
		bufRead.close();
	}

	/**
	 * Process file.
	 * @param r the BufferedReader
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void processFile(BufferedReader r) throws IOException {
		// d.available() returns 0 if the file does not have more lines.
		boolean notError=false;
		String line = r.readLine();
		
		// Read through file one line at time. Print line # and line
		while (line != null){		
			if (!(notError=processLine(line))){
				System.err.println("Erro na linha: "+ lineNumber);
				messagesMap.clear();
				break;
			}

			lineNumber++;
			line = r.readLine();
		}
		if(notError)
			messagesMap.put(id, new Mistake(id, message));
	}

	/**
	 * Process line.
	 * @param s the s
	 * @return true, if successful
	 */
	private boolean processLine(String s){
		if (patternComment.matcher(s).matches())
			return true;
		if(patternId.matcher(s).matches()){
			if(lineNumber != 1)
				messagesMap.put(id, new Mistake(id, message));
			id = s.substring(0, s.length()-1);
			message = "";
		}else
			if(patternMessage.matcher(s).matches()){
				message += s;
			}else
				if(s.trim().equals("")){
				}else
					return false;
		return true;		
	}
}

