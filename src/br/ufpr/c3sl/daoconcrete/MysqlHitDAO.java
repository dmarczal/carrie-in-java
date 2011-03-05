package br.ufpr.c3sl.daoconcrete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import br.ufpr.c3sl.dao.HitDAO;
import br.ufpr.c3sl.daoFactory.MysqlDAOFactory;
import br.ufpr.c3sl.model.Hit;

public class MysqlHitDAO implements HitDAO{

	private static final String INSERT = "INSERT INTO hits " +
	"(exercise, learningObject, user_id, created_at, cell, answer, correctAnswer ) " +
	"VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	@Override
	public Hit insert(Hit hit) {
		Connection c = MysqlDAOFactory.createConnection();
		PreparedStatement pstmt = null;
		
		if (hit.getCreatedAt() == null)
			hit.setCreatedAt(new Timestamp(new Date().getTime()));
		
		
			try {
				pstmt = c.prepareStatement(INSERT);
				pstmt.setString(1, hit.getExercise());
				pstmt.setString(2, hit.getOa());
				pstmt.setLong(3, hit.getUserId());
				pstmt.setTimestamp(4, hit.getCreatedAt());
				pstmt.setString(5, hit.getCell());
				pstmt.setString(6, 
						hit.getAnswer().replaceAll("ℓ", "l"));
				pstmt.setString(7, 
						hit.getCorrectAnswer().replaceAll("ℓ", "l"));
				
				pstmt.executeUpdate();
				ResultSet rset = pstmt.getGeneratedKeys();
				rset.next();
				Long idGenerated = rset.getLong(1);
				hit.setId(idGenerated);
				pstmt.close();
				c.close();
				return hit;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return null;
	}

}
