package br.ufpr.c3sl.daoFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.ufpr.c3sl.dao.MistakeDAO;
import br.ufpr.c3sl.daoconcrete.MysqlMistakeDAO;
import br.ufpr.c3sl.daoconcrete.MysqlUserDAO;

public class MysqlDAOFactory extends DAOFactory {

	//private static String url = "jdbc:mysql://127.0.0.1:3306/";
	private static String url = "jdbc:mysql://mysql04.maxiambiental.com/";
	private static String database = "maxiambiental3";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String user = "maxiambiental3"; //"root";
	private static String password = "abc123654"; //"";

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

	public MysqlUserDAO getUserDAO() {
		return new MysqlUserDAO();
	}

	public MistakeDAO getMistakeDAO() {
		return new MysqlMistakeDAO();
	}
}