package br.ufpr.c3sl.daoconcrete;

import java.sql.Timestamp;
import java.util.Date;

import javax.swing.JOptionPane;

import br.ufpr.c3sl.dao.UserDAO;
import br.ufpr.c3sl.daoFactory.DB4ODAOFactory;
import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.User;

import com.db4o.EmbeddedObjectContainer;
import com.db4o.ObjectSet;

public class DB4OUserDAO implements UserDAO{

	public User insert(User user) throws UserException {
		EmbeddedObjectContainer dbo = DB4ODAOFactory.getConnection();
		user.setCreatedAt(new Timestamp(new Date().getTime()));
		dbo.store(user);
		return findByEmail(user.getEmail());
	}

	@Override
	public User findByEmail(final String email) {
		EmbeddedObjectContainer dbo = DB4ODAOFactory.getConnection();
		User user = new User(email); 
		ObjectSet<User> result = dbo.queryByExample(user);
		
		if (result.hasNext()){
			User _user = result.next();
			return _user;
		}
		return null;
	}

	@Override
	public User findOrCreateByEmail(String email) throws UserException {
		User user = findByEmail(email); 
		
		if(user == null){
			user = new User(email);
			user = insert(user);
			JOptionPane.showMessageDialog(null, "Novo Cadastro Realizado Com Sucesso");
		}
		 
		return user;
	}

}
