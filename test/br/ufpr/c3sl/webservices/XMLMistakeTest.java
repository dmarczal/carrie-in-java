package br.ufpr.c3sl.webservices;

import org.junit.Test;

import br.ufpr.c3sl.client.webservices.XMLMistakeDAO;
import br.ufpr.c3sl.model.User;
import br.ufpr.c3sl.session.Session;


public class XMLMistakeTest {
	private static User user;
	
	@org.junit.BeforeClass
	public static void setup(){
		user = new User("dmarczal@gmail.com");
		user.setId(1l);
		Session.setCurrentUser(user);
	}
	@Test
	public void should_getAll(){
		XMLMistakeDAO.getAll("Fractal Simulator");
	}
}
