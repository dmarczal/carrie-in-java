package br.ufpr.c3sl.daoFactory;

import br.ufpr.c3sl.dao.MistakeDAO;
import br.ufpr.c3sl.dao.UserDAO;

//Abstract class DAO Factory
public abstract class DAOFactory {

  public static final int MYSQL = 1;
  public static final int DB4O = 2;
  public static int DATABASE_CHOOSE;
  
  /* 
   * There will be a method for each DAO that can be
   * created. The concrete factories will have to
   * implement these methods.
   */
  public abstract UserDAO getUserDAO();
  public abstract MistakeDAO getMistakeDAO();

  public static DAOFactory getDAOFactory(
      int whichFactory) {
  
    switch (whichFactory) {
      case MYSQL: 
          return new MysqlDAOFactory();
      case DB4O : 
          return new DB4ODAOFactory();      
      default   : 
          return null;
    }
  }
}
