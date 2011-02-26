package br.ufpr.c3sl.webservices;

import java.io.Serializable;


public class Customer implements Serializable{//implements Resource {
	private String name;
	private int id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
//	public static void main(String[] args) {
//		RestClient restClient = new RestClient("http://educacional.c3sl.ufpr.br/site/users",
//				"", "", "http://educacional.c3sl.ufpr.br", 80,
//				"basic", "realm", Formats.XML, false);
//		
//		
//		Customer customer = new Customer();
//		customer.setName("Hari");
//		try {
//			restClient.save(customer);
//		} catch (RestClientException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	

}