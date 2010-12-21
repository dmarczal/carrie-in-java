package br.ufpr.c3sl.daoconcrete;

import java.util.ArrayList;
import java.util.List;

import br.ufpr.c3sl.dao.MistakeDAO;
import br.ufpr.c3sl.daoFactory.DB4ODAOFactory;
import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.Mistake;
import br.ufpr.c3sl.model.User;

import com.db4o.EmbeddedObjectContainer;
import com.db4o.query.Predicate;

public class DB4OMistake implements MistakeDAO{

	@Override
	public int insert(Mistake mistake) throws UserException {
		EmbeddedObjectContainer dbo = DB4ODAOFactory.getConnection();
		dbo.store(mistake);
        dbo.close();
		return 1;
	}

	@Override
	public List<Mistake> getAll(final User user, final String learningObjectString) {
		EmbeddedObjectContainer dbo = DB4ODAOFactory.getConnection();
		
		List<Mistake> results = dbo.query(new Predicate<Mistake>() {
			private static final long serialVersionUID = 7067504373296785410L;

			public boolean match(Mistake mistake){
		        return (mistake.getUser().getEmail().equals(user.getEmail()) 
		        		&& mistake.getLearningObject().equals(learningObjectString));
		    }
		});
				
		ArrayList<Mistake> list = new ArrayList<Mistake>();
		list.addAll(results);
		dbo.close();
		
		return list;
	}

}
