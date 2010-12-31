package br.ufpr.c3sl.view;

import br.ufpr.c3sl.dao.UserDAO;
import br.ufpr.c3sl.daoFactory.DAOFactory;
import br.ufpr.c3sl.model.User;


public class Main {
	//private static final String QUERY = "INSERT INTO mistakes (exercise, learningObject, description, answer, correctAnswer, title, user_id, created_at) VALUES ('Exercise One', 'Fractal Simulator', 'Erro no ensino de progressões geométrica', '0', 'answer != 0', 'Exercise One Error', 7, '2010-12-29 12:18:59')";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DAOFactory daoLocal = DAOFactory.getDAOFactory(DAOFactory.DB4O);
		
		//MistakeDAO mistakeDaoL = daoLocal.getMistakeDAO();
		//RetroactionDAO retroactionDAOL = daoLocal.getRetroactionDAO();
		
		UserDAO uDao = daoLocal.getUserDAO();
		
		User user = uDao.findByEmail("diego@gmail.com");
		
		System.out.println(user.getEmail());
		System.out.println(user.getCreatedAt());
		
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
