package br.ufpr.c3sl.daoconcrete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufpr.c3sl.dao.MistakeDAO;
import br.ufpr.c3sl.daoFactory.MysqlDAOFactory;
import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.Mistake;
import br.ufpr.c3sl.model.User;
import br.ufpr.c3sl.session.Session;


public class MysqlMistakeDAO implements MistakeDAO{

	private static final String INSERT = "INSERT INTO mistakes " +
	"(object, exercise, oa, description, answer, " +
	"correctAnswer, title, user_id, created_at, cell) " +
	"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String FIND_BY_USER = "SELECT * FROM mistakes " +
			"WHERE user_id LIKE ? and learningObject LIKE ? ORDER BY created_at ASC";

	/**
	 * insert
	 * @param mistake
	 * @return mistake The mistake inserted	
	 */
	public Mistake insert(Mistake mistake) throws UserException {
		Connection c = MysqlDAOFactory.createConnection();
		PreparedStatement pstmt;
		
		if (mistake.getCreatedAt() == null)
			mistake.setCreatedAt(new Timestamp(new Date().getTime()));
		
		try {
			pstmt = c.prepareStatement(INSERT);
			pstmt.setBytes(1, mistake.getObject());
			pstmt.setString(2, mistake.getExercise());
			pstmt.setString(3, mistake.getOa());
			pstmt.setString(4, mistake.getDescription());
			pstmt.setString(5, 
					mistake.getAnswer().replaceAll("ℓ", "l"));
			pstmt.setString(6, 
					mistake.getCorrectAnswer().replaceAll("ℓ", "l"));
			pstmt.setString(7, mistake.getTitle());
			pstmt.setLong(8, mistake.getUser().getId());
			pstmt.setTimestamp(9, mistake.getCreatedAt());
			pstmt.setString(10, mistake.getCell());
			
			System.out.println(pstmt);
			
			pstmt.executeUpdate();
			ResultSet rset = pstmt.getGeneratedKeys();
			rset.next();
			Long idGenerated = rset.getLong(1);
			mistake.setId(idGenerated);
			pstmt.close();
			c.close();
			return mistake;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException(e.getMessage());
		}
	}

	public List<Mistake> getAll(User user, String learningObject) {
		ArrayList<Mistake> list = new ArrayList<Mistake>();
		ResultSet rset;

		Connection c = MysqlDAOFactory.createConnection();
		PreparedStatement pstmt;
		try {
			pstmt = c.prepareStatement(FIND_BY_USER);
			pstmt.setLong(1, user.getId());
			pstmt.setString(2, learningObject);
			rset = pstmt.executeQuery();
			
			while (rset.next()){
				Mistake m = createMistake(rset);
				m.setUser(user);
				list.add(m);
			}
			pstmt.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list; 
	}

	private Mistake createMistake(ResultSet rset) throws SQLException {
		Mistake mistake = new Mistake();
		mistake.setId(rset.getLong("id"));
		mistake.setObject(rset.getBytes("object"));
		mistake.setExercise(rset.getString("exercise"));
		mistake.setOa(rset.getString("oa"));
		mistake.setCreatedAt(rset.getTimestamp("created_at"));
		mistake.setTitle(rset.getString("title"));
		mistake.setAnswer(rset.getString("answer").replaceAll("l","ℓ"));
		mistake.setCorrectAnswer(rset.getString("correctAnswer").replaceAll( "l","ℓ"));
		mistake.setDescription(rset.getString("description"));
		mistake.setCell(rset.getString("cell"));
		mistake.setUser(Session.getCurrentUser());
		
		return mistake;
	}
	
	@Override
	public boolean delete(Mistake mistake) {
		// TODO Auto-generated method stub
		return false;
	}
}
