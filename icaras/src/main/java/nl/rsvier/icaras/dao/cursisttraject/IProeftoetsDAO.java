package nl.rsvier.icaras.dao.cursisttraject;

import nl.rsvier.icaras.core.cursisttraject.Proeftoets;

import org.springframework.dao.DataAccessException;

public interface IProeftoetsDAO {

    public Proeftoets findProeftoetsByID(long id) throws DataAccessException ;
    
    public void saveProeftoets (Proeftoets proeftoets) throws DataAccessException ;
    
    public void updateProeftoets (Proeftoets proeftoets) throws DataAccessException ;
    
    public void deleteProeftoets (Proeftoets proeftoets) throws DataAccessException ;
}