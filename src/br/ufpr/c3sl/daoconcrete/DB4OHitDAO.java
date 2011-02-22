package br.ufpr.c3sl.daoconcrete;

import java.util.Date;

import com.db4o.EmbeddedObjectContainer;

import br.ufpr.c3sl.dao.HitDAO;
import br.ufpr.c3sl.daoFactory.DB4ODAOFactory;
import br.ufpr.c3sl.model.Hit;

public class DB4OHitDAO implements HitDAO{

	@Override
	public Hit insert(Hit hit) {
		EmbeddedObjectContainer dbo = DB4ODAOFactory.getConnection();
		hit.setCreatedAt(new Date().getTime());
		dbo.store(hit);
		return hit;
	}

}
