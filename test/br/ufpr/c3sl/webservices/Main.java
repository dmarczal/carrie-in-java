package br.ufpr.c3sl.webservices;

import java.util.Date;

import br.ufpr.c3sl.client.webservices.XMLRetroactionDAO;
import br.ufpr.c3sl.model.Mistake;
import br.ufpr.c3sl.model.Retroaction;
import br.ufpr.c3sl.model.User;
import br.ufpr.c3sl.session.Session;

public class Main {
	
	
	public static void main(String[] args) {
//		try {
//		    // Construct data
//		    String data =  URLEncoder.encode("utf8", "UTF-8") + "=" + URLEncoder.encode("✓", "UTF-8");
//		    data +=	"&" + URLEncoder.encode("authenticity_token", "UTF-8") + "=" + URLEncoder.encode("7UeGQR0qiZYdQEvRsY1ngqfpRrRs1HRbuqNLGpEDiv0=", "UTF-8");
//		    
//		    data +=	"&" + URLEncoder.encode("user[name]", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8");
//		    //data += "&" + URLEncoder.encode("key2", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8");
//
//			Customer c = new Customer();
////			
//			c.setName("Diego");
////			
//			byte[] cb = ObjectByteArray.getByteOfArray(c);
//
//			String c64 = Base64.encode(cb);
//			
//			data += "&" + URLEncoder.encode("user[object]", "UTF-8") + "=" + URLEncoder.encode(c64, "UTF-8");
//			
//		    //String value = new String(c64);
//			
//		    //System.out.println("OOi " + c64);
//		    
//		    //System.out.println(value.getBytes("base64").length);
//		    
//		    Customer  d = (Customer) ObjectByteArray.getObject(Base64.decode(c64));
//		    
//		    
//		    System.out.println(d.getName());
//		    
//		    
////		    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.unicentro.br", 8080));
//		    
//		    // Send data
//		    URL url = new URL("http://0.0.0.0:3000/users/new");
//		    URLConnection conn = url.openConnection();
//		    //conn.setRequestProperty("csrf-param", "authenticity_token");
//		    //conn.setRequestProperty("csrf-token", "7UeGQR0qiZYdQEvRsY1ngqfpRrRs1HRbuqNLGpEDiv0=");
//		    
////	    <meta name="csrf-param" content="authenticity_token"/> 
////      <meta name="csrf-token" content="7UeGQR0qiZYdQEvRsY1ngqfpRrRs1HRbuqNLGpEDiv0="/>
//
////		    conn.setDoOutput(true);
////		    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
////		    wr.write(data);
////		    wr.flush();
//
//		    // Get the response
//		    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//		    
//		    Pattern pattern = Pattern.compile("<meta name=\"csrf-token\" content=\"(.*?)\"/>");
//			
//		    System.out.println(pattern.pattern());
//		    
//		    String line;
//		    while ((line = rd.readLine()) != null) {
//		    	Matcher matcher = pattern.matcher(rd.readLine());
//				
//				while(matcher.find()) {
//					System.out.println("ola");
//				}
//		    	//System.out.println(line);
//		    }
//		    
//		    //wr.close();
//		    rd.close();
//		} catch (Exception e) {
//		}
		
//		Mistake m = new Mistake();
//		
//		m.save();
//		
//	    JAXBContext context;
//		try {
//			context = JAXBContext.newInstance(Mistake.class);
//
//	        Marshaller marshaller = context.createMarshaller();
//	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//	        
//	        marshaller.marshal(m, new FileWriter("person.xml"));
//	        
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//getAll("Fractal Simulator");
		User u = new User("dmarczal@gmail.com");
		u.setId(1l);
		Session.setCurrentUser(u);
		Retroaction r = new Retroaction();
		Mistake m = new Mistake();
		m.setId(13l);
		
		r.setMistake(m);
		
		System.out.println(new Date());
		XMLRetroactionDAO.save(r);	
		System.out.println(new Date());
	}
}
