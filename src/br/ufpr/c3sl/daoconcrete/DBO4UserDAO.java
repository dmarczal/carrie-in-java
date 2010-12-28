package br.ufpr.c3sl.daoconcrete;

import br.ufpr.c3sl.dao.UserDAO;
import br.ufpr.c3sl.daoFactory.DB4ODAOFactory;
import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.User;

import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectSet;

public class DBO4UserDAO implements UserDAO{

	public int insert(User user) throws UserException {
		EmbeddedObjectContainer dbo = DB4ODAOFactory.getConnection();
		dbo.store(user);
		dbo.close();
		return 1;
	}

	@Override
	public boolean delete(int id) {
		return false;
	}

	@Override
	public User findByEmail(String email) {
		User user = null;
		EmbeddedObjectContainer dbo = DB4ODAOFactory.getConnection();
		User _user = new User(email);
		_user.setCreatedAt(null);
		ObjectSet<User> result = dbo.queryByExample(_user);
		
		if (result.hasNext()){
			user = result.next();
			user.setNotNewRecord();
		}
		dbo.close();
		

		return user;
	}

	@Override
	public User findOrCreateByEmail(String email) throws UserException {
		User user = findByEmail(email); 
		
		if(user == null){
			user = new User(email);
			insert(user);
			user = findByEmail(email);
			user.setNewRecord();
		}
		 
		return user;
	}

}
