package br.ufpr.c3sl.daoconcrete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.ufpr.c3sl.dao.RetroactionDAO;
import br.ufpr.c3sl.daoFactory.MysqlDAOFactory;
import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.Mistake;
import br.ufpr.c3sl.model.Retroaction;

public class MysqlRetroactionDAO implements RetroactionDAO{

	private static final String INSERT = "INSERT INTO retroactions " +
	"(mistake_id, created_at) VALUES (?, ?)";
	
	@Override
	public Retroaction insert(Retroaction retroaction) throws UserException {
		Connection c = MysqlDAOFactory.createConnection();
		if (retroaction.getCreatedAt() == null)
			retroaction.setCreatedAt(new Date().getTime());
		PreparedStatement pstmt;
		try {
			pstmt = c.prepareStatement(INSERT);
			pstmt.setLong(1, retroaction.getMistake().getId());
			pstmt.setTimestamp(2, retroaction.getCreatedAtTime());

			pstmt.executeUpdate();
			
			ResultSet rset = pstmt.getGeneratedKeys();
			rset.next();
			Long idGenerated = rset.getLong(1);
			retroaction.setId(idGenerated);
			
			pstmt.close();
			c.close();
			return retroaction;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException(e.getMessage());
		}
	}

	@Override
	public List<Retroaction> getAll(final Mistake mistake) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Retroaction retroaction) {
		// TODO Auto-generated method stub
		return false;
	}
}
