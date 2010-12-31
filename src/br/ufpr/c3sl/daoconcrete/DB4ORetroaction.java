package br.ufpr.c3sl.daoconcrete;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufpr.c3sl.dao.RetroactionDAO;
import br.ufpr.c3sl.daoFactory.DB4ODAOFactory;
import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.Mistake;
import br.ufpr.c3sl.model.Retroaction;

import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Predicate;

public class DB4ORetroaction implements RetroactionDAO{

	@Override
	public Retroaction insert(Retroaction retroaction) throws UserException {
		EmbeddedObjectContainer dbo = DB4ODAOFactory.getConnection();
		retroaction.setCreatedAt(new Date().getTime());
		dbo.store(retroaction);
		return retroaction;
	}

	@Override
	public List<Retroaction> getAll(final Mistake mistake) {
		EmbeddedObjectContainer dbo = DB4ODAOFactory.getConnection();

		List<Retroaction> results = dbo.query(new Predicate<Retroaction>() {
			private static final long serialVersionUID = 7067504373296785410L;

			public boolean match(Retroaction retroaction){
				return (retroaction.getUser().getEmail().equals(mistake.getUser().getEmail()) &&
						retroaction.getMistake() == mistake);
			}
		});

		ArrayList<Retroaction> list = new ArrayList<Retroaction>();
		list.addAll(results);

		return list;
	}

	@Override
	public boolean delete(Retroaction retroaction) {
		try {
			EmbeddedObjectContainer dbo = DB4ODAOFactory.getConnection();

			Retroaction found = null;
			Retroaction r = new Retroaction();
			
			r.setCreatedAt(retroaction.getCreatedAt());
			r.setUser(retroaction.getUser());
			r.setMistake(null);
			ObjectSet<Retroaction> result = dbo.queryByExample(r);
			
			found = result.next();
			System.out.println("Foound "+ found);
			
			dbo.delete(found);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
