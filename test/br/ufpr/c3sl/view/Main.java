package br.ufpr.c3sl.view;

import java.net.URL;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import br.ufpr.c3sl.mistakes.MistakeOccurrence;
import br.ufpr.c3sl.view.user.InitialDialog;



public class Main {
	//private static final String QUERY = "INSERT INTO mistakes (appletexercise, learningObject, description, answer, correctAnswer, title, user_id, created_at) VALUES ('Exercise One', 'Fractal Simulator', 'Erro no ensino de progressões geométrica', '0', 'answer != 0', 'Exercise One Error', 7, '2010-12-29 12:18:59')";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//String m = MistakeOccurrence.getMistakeMessage("1", 1);
		
		//System.out.println(m);
		
		URL url = Main.class.getResource("/org/c3sl/ufpr/br/introduction/introductionOne.html");
		
		System.out.println(url);
		
		//Pattern patternComment = Pattern.compile("/{2}.*");
		
		//System.out.println(patternComment.matcher("// adf").matches());
		
//		JFrame frame = new JFrame("Oi");
//		 //Make sure we have nice window decorations.
//        JFrame.setDefaultLookAndFeelDecorated(true);
//
//        //Create and set up the window.
//        InitialDialog innerframe = new InitialDialog();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        //Display the window.
//        innerframe.setVisible(true);
		
		
//		    frame.add(innerframe);
//	        try {
//	           // innerframe.setSelected(true);
//	        } catch (java.beans.PropertyVetoException e) {}
//		
		
//		frame.setSize(200,200);
//		frame.setVisible(true);
//		DAOFactory daoLocal = DAOFactory.getDAOFactory(DAOFactory.DB4O);
		
		//MistakeDAO mistakeDaoL = daoLocal.getMistakeDAO();
		//RetroactionDAO retroactionDAOL = daoLocal.getRetroactionDAO();
		
//		UserDAO uDao = daoLocal.getUserDAO();
//		
//		User user = uDao.findByEmail("diego@gmail.com");
//		
//		System.out.println(user.getEmail());
//		System.out.println(user.getCreatedAt());
		
		//Temp t = new Temp();
		//t.setUser(user);
		
		//EmbeddedObjectContainer dbo = DB4ODAOFactory.getConnection();
		//dbo.store(t);
		//dbo.close();
		
//		List<Mistake> list = mistakeDaoL.getAll(user,
//				"Fractal Simulator");
//		
//		Mistake m = list.get(0);
//		
//		List<Retroaction> retroactionList = retroactionDAOL.getAll(m);
//		
//		for (Retroaction retroaction : retroactionList) {
//			System.out.println(retroaction.getCreatedAt());
//		}
	}

}
