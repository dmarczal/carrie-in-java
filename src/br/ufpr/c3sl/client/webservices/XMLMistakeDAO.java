package br.ufpr.c3sl.client.webservices;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import br.ufpr.c3sl.model.Mistake;
import br.ufpr.c3sl.model.MistakesArray;
import br.ufpr.c3sl.session.Session;

public class XMLMistakeDAO {
	
	public static Mistake save(Mistake mistake){
		Mistake mistake2 = null;
		URL url;
		try {
			url = new URL(HOST.URL+ "/"+Session.getCurrentUser().getId()+"/mistakes.xml");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/xml");
			
			Serializer serializer = new Persister();
			serializer.write(mistake,  conn.getOutputStream());
			
			mistake2 = serializer.read(Mistake.class, conn.getInputStream());
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mistake2;
	}
	
	/**
	 * Get all mistakes from the current user
	 * @return mistakes All mistakes from a current user
	 */
	public static List<Mistake> getAll(String oa){
		URL url;
		List<Mistake> mistakes = new ArrayList<Mistake>();
		try { //TODO: ?oa="+oa
			url = new URL(HOST.URL+ "/"+Session.getCurrentUser().getId()+"/mistakes.xml");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "text/xml");
	        
	        Serializer serializer = new Persister();
	        
	        MistakesArray mArray = serializer.read(MistakesArray.class, conn.getInputStream());
	        mistakes = Arrays.asList(mArray.getMistakes());
	       
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return mistakes;
	}
}
