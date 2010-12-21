package br.ufpr.c3sl.dao;

import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.User;

public interface UserDAO {

  int insert(User user) throws UserException;
  boolean delete(int id);
  User findByEmail(String email);
  
}
