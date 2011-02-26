package br.ufpr.c3sl.webservices;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufpr.c3sl.deepClone.ObjectByteArray;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class Main {
	
	
	public static void main(String[] args) {
		try {
		    // Construct data
		    String data =  URLEncoder.encode("utf8", "UTF-8") + "=" + URLEncoder.encode("✓", "UTF-8");
		    data +=	"&" + URLEncoder.encode("authenticity_token", "UTF-8") + "=" + URLEncoder.encode("7UeGQR0qiZYdQEvRsY1ngqfpRrRs1HRbuqNLGpEDiv0=", "UTF-8");
		    
		    data +=	"&" + URLEncoder.encode("user[name]", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8");
		    //data += "&" + URLEncoder.encode("key2", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8");

			Customer c = new Customer();
//			
			c.setName("Diego");
//			
			byte[] cb = ObjectByteArray.getByteOfArray(c);

			String c64 = Base64.encode(cb);
			
			data += "&" + URLEncoder.encode("user[object]", "UTF-8") + "=" + URLEncoder.encode(c64, "UTF-8");
			
		    //String value = new String(c64);
			
		    //System.out.println("OOi " + c64);
		    
		    //System.out.println(value.getBytes("base64").length);
		    
		    Customer  d = (Customer) ObjectByteArray.getObject(Base64.decode(c64));
		    
		    
		    System.out.println(d.getName());
		    
		    
//		    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.unicentro.br", 8080));
		    
		    // Send data
		    URL url = new URL("http://0.0.0.0:3000/users/new");
		    URLConnection conn = url.openConnection();
		    //conn.setRequestProperty("csrf-param", "authenticity_token");
		    //conn.setRequestProperty("csrf-token", "7UeGQR0qiZYdQEvRsY1ngqfpRrRs1HRbuqNLGpEDiv0=");
		    
//	    <meta name="csrf-param" content="authenticity_token"/> 
//      <meta name="csrf-token" content="7UeGQR0qiZYdQEvRsY1ngqfpRrRs1HRbuqNLGpEDiv0="/>

//		    conn.setDoOutput(true);
//		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//		    wr.write(data);
//		    wr.flush();

		    // Get the response
		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    
		    Pattern pattern = Pattern.compile("<meta name=\"csrf-token\" content=\"(.*?)\"/>");
			
		    System.out.println(pattern.pattern());
		    
		    String line;
		    while ((line = rd.readLine()) != null) {
		    	Matcher matcher = pattern.matcher(rd.readLine());
				
				while(matcher.find()) {
					System.out.println("ola");
				}
		    	//System.out.println(line);
		    }
		    
		    //wr.close();
		    rd.close();
		} catch (Exception e) {
		}
	
	}
	
}
