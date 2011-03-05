package br.ufpr.c3sl.client.webservices;

import java.net.HttpURLConnection;
import java.net.URL;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import br.ufpr.c3sl.model.Retroaction;
import br.ufpr.c3sl.session.Session;

public class XMLRetroactionDAO {

	public static Retroaction save(Retroaction retroaction){
		URL url;
		Retroaction r = null;
		try {
			url = new URL(HOST.URL + "/"
					+ Session.getCurrentUser().getId()
					+ "/mistakes/"
					+ retroaction.getMistake().getId()
					+ "/retroactions.xml");

			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/xml");
			
			
			Serializer serializer = new Persister();
			serializer.write(retroaction,  conn.getOutputStream());

			r = serializer.read(Retroaction.class, conn.getInputStream());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
}
