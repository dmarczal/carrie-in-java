package br.ufpr.c3sl.daoconcrete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufpr.c3sl.dao.UserDAO;
import br.ufpr.c3sl.daoFactory.MysqlDAOFactory;
import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.User;

public class MysqlUserDAO implements UserDAO{

	private static final String INSERT = "INSERT INTO users (email, created_at) VALUES (?, ?)";
	private static final String FIND_BY_EMAIL = "SELECT * FROM users WHERE email LIKE ?";
	
	@Override
	public int insert(User user) throws UserException {
		Connection c = MysqlDAOFactory.createConnection();

		PreparedStatement pstmt;
		try {
			pstmt = c.prepareStatement(INSERT);
			pstmt.setString(1, user.getEmail());
			pstmt.setTimestamp(2, user.getCreatedAt());
			int rowAfecteds = pstmt.executeUpdate();
			return rowAfecteds;
		} catch (SQLException e) {
			throw new UserException(e.getMessage());
		}
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public User findByEmail(String email){
		Connection c = MysqlDAOFactory.createConnection();
		PreparedStatement pstmt;
		try {
			pstmt = c.prepareStatement(FIND_BY_EMAIL);
			pstmt.setString(1, email);
			ResultSet rset = pstmt.executeQuery();
			if (rset.next())
				return createUser(rset);
			
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private User createUser(ResultSet rset) throws SQLException{
		User user = new User(rset.getString("email"));
		user.setCreatedAt(rset.getTimestamp("created_at").getTime());
		user.setId(rset.getInt("id"));
		user.setNotNewRecord();
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

