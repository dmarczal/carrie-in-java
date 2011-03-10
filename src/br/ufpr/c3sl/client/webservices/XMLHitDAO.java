package br.ufpr.c3sl.client.webservices;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.JOptionPane;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import br.ufpr.c3sl.model.Hit;
import br.ufpr.c3sl.session.Session;

public class XMLHitDAO {
	
	public static Hit save(Hit hit){
		URL url;
		Hit h = null;
		try {
			url = new URL(HOST.URL + "/"
					+ Session.getCurrentUser().getId()
					+ "/hits.xml");

			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/xml");
			
			
			Serializer serializer = new Persister();
			serializer.write(hit,  conn.getOutputStream());

			h = serializer.read(Hit.class, conn.getInputStream());
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "" +
					"Erro na comunicação com o servidor! O software pode não \n" +
					"Funcionar corretamente");
			e.printStackTrace();
		}
		return h;
	}
}
