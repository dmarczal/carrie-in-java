package br.ufpr.c3sl.webservices;

import org.junit.Test;

import br.ufpr.c3sl.client.webservices.XMLHitDAO;
import br.ufpr.c3sl.model.Hit;
import br.ufpr.c3sl.model.User;
import br.ufpr.c3sl.session.Session;


public class xmlHitTest {
	
	private static User user;
	
	@org.junit.BeforeClass
	public static void setup(){
		user = new User("dmarczal@gmail.com");
		user.setId(1l);
		Session.setCurrentUser(user);
	}
	@Test
	public void should_save(){
		Hit hit = new Hit();
		hit.setAnswer("3");
		hit.setCell("1 2");
		hit.setCorrectAnswer("6/3");
		hit.setExercise("Exercício 1");
		hit.setOa("Fractal Simulator");
		XMLHitDAO.save(hit);
	}
}
