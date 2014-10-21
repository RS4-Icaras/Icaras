package nl.rsvier.icaras.dao.cursisttraject;

import nl.rsvier.icaras.core.cursisttraject.Boek;
import org.springframework.dao.DataAccessException;

public interface IBoekDAO {

    public Boek findBoekByID(long id) throws DataAccessException ;
    
    public void saveBoek (Boek boek) throws DataAccessException ;
    
    public void updateBoek (Boek boek) throws DataAccessException ;
    
    public void deleteBoek (Boek boek) throws DataAccessException ;
}
