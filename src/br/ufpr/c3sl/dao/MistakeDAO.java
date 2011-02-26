package br.ufpr.c3sl.dao;

import java.util.List;

import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.Mistake;
import br.ufpr.c3sl.model.User;

public interface MistakeDAO {

	Mistake insert(Mistake mistake) throws UserException;

	List<Mistake> getAll(User user, String learningObjectString);

	boolean delete(Mistake mistake);
}
