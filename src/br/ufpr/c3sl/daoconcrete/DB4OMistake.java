package br.ufpr.c3sl.daoconcrete;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufpr.c3sl.dao.MistakeDAO;
import br.ufpr.c3sl.daoFactory.DB4ODAOFactory;
import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.Mistake;
import br.ufpr.c3sl.model.User;

import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

public class DB4OMistake implements MistakeDAO{

	@Override
	public Mistake insert(Mistake mistake) throws UserException {
		EmbeddedObjectContainer dbo = DB4ODAOFactory.getConnection();
		mistake.setCreatedAt(new Date().getTime());
		dbo.store(mistake);
		return mistake;
	}

	@Override
	public List<Mistake> getAll(final User user, final String learningObjectString) {
		EmbeddedObjectContainer dbo = DB4ODAOFactory.getConnection();

		//TODO: ORDER BY created_at DESC
		List<Mistake> results = dbo.query(new Predicate<Mistake>() {
			private static final long serialVersionUID = 7067504373296785410L;

			public boolean match(Mistake mistake){
				return (mistake.getUser().getEmail().equals(user.getEmail()) 
						&& mistake.getLearningObject().equals(learningObjectString));
			}
		});

		ArrayList<Mistake> list = new ArrayList<Mistake>();
		list.addAll(results);

		return list;
	}

	/**
	 * delete a mistake from DB40
	 * @param mistake
	 */
	public boolean delete(Mistake mistake) {
		try {
			EmbeddedObjectContainer dbo = DB4ODAOFactory.getConnection();
			
			Mistake found = null;
			Mistake m = new Mistake();
			
			m.setCreatedAt(mistake.getCreatedAt());
			m.setLearningObject(mistake.getLearningObject());
			m.setUser(mistake.getUser());
			m.setMistakeInfo(null);
			ObjectSet<Mistake> result = dbo.queryByExample(m);
			
			found = result.next();
			dbo.delete(found);
			dbo.delete(found.getMistakeInfo());
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}







