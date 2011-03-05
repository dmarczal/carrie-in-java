package br.ufpr.c3sl.daoconcrete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.JOptionPane;

import br.ufpr.c3sl.dao.UserDAO;
import br.ufpr.c3sl.daoFactory.MysqlDAOFactory;
import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.User;

public class MysqlUserDAO implements UserDAO{

	private static final String INSERT = "INSERT INTO users (email, created_at) VALUES (?, ?)";
	private static final String FIND_BY_EMAIL = "SELECT * FROM users WHERE email LIKE ?";
	
	/**
	 * insert
	 * @param user
	 * @return user The mistake inserted	
	 */
	@Override
	public User insert(User user) throws UserException {
		Connection c = MysqlDAOFactory.createConnection();
		
		if (user.getCreatedAt() == null)
			user.setCreatedAt(new Timestamp(new Date().getTime()));
		
		PreparedStatement pstmt;
		try {
			pstmt = c.prepareStatement(INSERT);
			pstmt.setString(1, user.getEmail());
			pstmt.setTimestamp(2, user.getCreatedAt());
			
			pstmt.executeUpdate();
			
			ResultSet rset = pstmt.getGeneratedKeys();
			rset.next();
			Long idGenerated = rset.getLong(1);
			user.setId(idGenerated);
			pstmt.close();
			c.close();
			return user;
		} catch (SQLException e) {
			throw new UserException(e.getMessage());
		}
	}
	
	@Override
	public User findByEmail(String email){
		Connection c = MysqlDAOFactory.createConnection();
		PreparedStatement pstmt = null;
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
		}finally{
			try {
				pstmt.close();
				c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private User createUser(ResultSet rset) throws SQLException{
		User user = new User(rset.getString("email"));
		user.setCreatedAt(rset.getTimestamp("created_at"));
		user.setId(rset.getLong("id"));
		return user;
	}
	
	@Override
	public User findOrCreateByEmail(String email) throws UserException {
		User user = findByEmail(email); 
		
		if(user == null){
			user = new User(email);
			user = insert(user);
			JOptionPane.showMessageDialog(null, "Novo cadastro no servidor realizado com sucesso");
		}
		 
		return user;
	}
}

