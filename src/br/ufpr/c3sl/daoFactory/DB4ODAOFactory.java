package br.ufpr.c3sl.daoFactory;

import java.sql.Timestamp;

import br.ufpr.c3sl.dao.MistakeDAO;
import br.ufpr.c3sl.dao.UserDAO;
import br.ufpr.c3sl.daoconcrete.DB4OMistake;
import br.ufpr.c3sl.daoconcrete.DBO4UserDAO;

import com.db4o.Db4oEmbedded;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

public class DB4ODAOFactory extends DAOFactory {
	
	private static final String BD4O = "bd4o.db";
	
	public static EmbeddedObjectContainer getConnection(){
		EmbeddedObjectContainer container = Db4oEmbedded.openFile(
				DB4ODAOFactory.configure(), BD4O); 
		return 	container;
	}
	
	private static EmbeddedConfiguration configure(){
        EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();
            configuration.common().objectClass(Timestamp.class).storeTransientFields(true);
            return configuration;
    }
	
	@Override
	public UserDAO getUserDAO() {
		return new DBO4UserDAO();
	}

	@Override
	public MistakeDAO getMistakeDAO() {
		return new DB4OMistake();
	}

}
