package br.ufpr.c3sl.daoFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.ufpr.c3sl.dao.MistakeDAO;
import br.ufpr.c3sl.dao.RetroactionDAO;
import br.ufpr.c3sl.dao.UserDAO;
import br.ufpr.c3sl.daoconcrete.MysqlMistakeDAO;
import br.ufpr.c3sl.daoconcrete.MysqlRetroactionDAO;
import br.ufpr.c3sl.daoconcrete.MysqlUserDAO;

public class MysqlDAOFactory extends DAOFactory {

//	private static String url = "jdbc:mysql://127.0.0.1:3306/";
//	private static String database = "carrie";
//	private static String driver = "com.mysql.jdbc.Driver";
//	private static String user = "root";
//	private static String password = "";

	private static String url = "jdbc:mysql://mysql04.maxiambiental.com/";
	private static String database = "maxiambiental3";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String user = "maxiambiental3";
	private static String password = "abc123654";
	
	
	// TODO: Recommend connection pool implementation/usage
	public static Connection createConnection() {   
		try {
			Class.forName(driver).newInstance();
			Connection connection = DriverManager.getConnection(url + database, user, password);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception ex){
			ex.printStackTrace();
		}
		
		return null;
	}

	public UserDAO getUserDAO() {
		return new MysqlUserDAO();
	}

	public MistakeDAO getMistakeDAO() {
		return new MysqlMistakeDAO();
	}

	@Override
	public RetroactionDAO getRetroactionDAO() {
		// TODO Auto-generated method stub
		return new MysqlRetroactionDAO();
	}
}