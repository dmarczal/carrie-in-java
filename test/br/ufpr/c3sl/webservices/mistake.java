package br.ufpr.c3sl.webservices;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class mistake {
	
	public static void main(String[] args) {
		try {
			String xmlText  = "<mistake> " +
			"<exercise>Maggie</exercise>" +
			"<description>Maggie</description>" +
			"</mistake>";

			URL url = new URL("http://0.0.0.0:3000/users/1/mistakes.xml");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "text/xml");
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(xmlText);
			wr.flush();

			BufferedReader rd = new BufferedReader(new 
					InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
			System.out.println("Error" + e);
		}
	}
}
