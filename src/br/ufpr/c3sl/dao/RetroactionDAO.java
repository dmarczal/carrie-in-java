package br.ufpr.c3sl.dao;

import java.util.List;

import br.ufpr.c3sl.exception.UserException;
import br.ufpr.c3sl.model.Mistake;
import br.ufpr.c3sl.model.Retroaction;

public interface RetroactionDAO {

	Retroaction insert(Retroaction retroaction) throws UserException;
	List<Retroaction> getAll(Mistake mistake);
	boolean delete(Retroaction retroaction);
}
