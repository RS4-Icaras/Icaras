package nl.rsvier.icaras.dao.cursisttraject;

import nl.rsvier.icaras.core.cursisttraject.Cursist;
import org.springframework.dao.DataAccessException;

public interface ICursistDAO {

	public Cursist findCursistByID(long id) throws DataAccessException ;
    
    public void saveCursist (Cursist cursist) throws DataAccessException ;
    
    public void updateCursist (Cursist cursist) throws DataAccessException ;
    
    public void deleteCursist (Cursist cursist) throws DataAccessException ;

}
