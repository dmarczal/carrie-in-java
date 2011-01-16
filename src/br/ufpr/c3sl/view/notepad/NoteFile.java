package br.ufpr.c3sl.view.notepad;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.JFileChooser;

public class NoteFile {

	public String getText() {
		try {
			return readFile (getFile());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private File getFile() {
		JFileChooser fc = new JFileChooser();
		fc.showOpenDialog(fc);
		File file = fc.getSelectedFile();
		if (file != null && file.length() > 100000)
			return null;
		
		return file;
	}
	
	private String readFile(File f) throws Exception {
				
		String text = "";
		
		if (f == null)
			return text;
		
		URL url = f.getAbsoluteFile().toURI().toURL();

		URLConnection urlc = url.openConnection();

		BufferedReader bufRead = new BufferedReader(new InputStreamReader(urlc.getInputStream(), "UTF-8"));

		String line = bufRead.readLine();
		// Read through file one line at time. Print line # and line
		while (line != null){
			text += line + "\n";
			line = bufRead.readLine();
		}

		bufRead.close();
		
		return text;
	}
}
