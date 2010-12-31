package br.ufpr.c3sl.dao;

import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.User;

public interface UserDAO {

  User insert(User user) throws UserException;
  User findByEmail(String email);
  User findOrCreateByEmail(String email) throws UserException;
}
