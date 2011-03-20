package br.ufpr.c3sl.connection;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class HTTPClientFactory {

	private static Type proxyType;
	private static String proxyHost;
	private static int proxyPort;

	public static HttpURLConnection getURLConnection(String _url) throws IOException {
		URL url = new URL(_url);		
		HttpURLConnection conn;

		Proxy proxy = getProxy();

		if (proxy == null)
			conn = (HttpURLConnection) url.openConnection();
		else
			conn = (HttpURLConnection) url.openConnection(proxy);

		return conn;
	}


	private static Proxy getProxy(){
		Proxy proxy = null;
		
		if (configureProxy() != null)
			proxy = new Proxy(proxyType, new InetSocketAddress(proxyHost, proxyPort));

		return proxy;
	}

	private static InetSocketAddress configureProxy(){
		System.setProperty("java.net.useSystemProxies", "true"); 
		InetSocketAddress addr = null;
		
		List<?> l = null; 
		
		try { 
			l = ProxySelector.getDefault().select(new URI("http://www.google.com")); 
		}  
		catch (URISyntaxException e) { 
			e.printStackTrace(); 
		} 
		
		if (l != null) { 
			for (Iterator<?> iter = l.iterator(); iter.hasNext();) { 
				java.net.Proxy proxy = (java.net.Proxy) iter.next(); 
				proxyType = proxy.type(); 
				addr = (InetSocketAddress) proxy.address(); 
				
				if (addr == null) {
					System.out.println("No Proxy connection");
					return null;
				} else { 
					proxyHost = addr.getHostName();
					proxyPort = addr.getPort();
					//System.out.println("proxy hostname : " + addr.getHostName()); 
					//System.setProperty("http.proxyHost", addr.getHostName()); 
					//System.out.println("proxy port : " + addr.getPort()); 
					//System.setProperty("http.proxyPort", Integer.toString(addr.getPort())); 
				} 
			} 
		}
		System.setProperty("java.net.useSystemProxies", "false");
		return addr;
	}
}
