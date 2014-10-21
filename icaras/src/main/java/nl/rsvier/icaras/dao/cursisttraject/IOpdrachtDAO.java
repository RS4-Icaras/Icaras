package nl.rsvier.icaras.dao.cursisttraject;

import nl.rsvier.icaras.core.cursisttraject.Opdracht;
import org.springframework.dao.DataAccessException;

public interface IOpdrachtDAO {

    public Opdracht findOpdrachtByID (long id) throws DataAccessException ;
    
    public void saveOpdracht (Opdracht opdracht) throws DataAccessException ;
    
    public void updateOpdracht (Opdracht opdracht) throws DataAccessException ;
    
    public void deleteOpdracht (Opdracht opdracht) throws DataAccessException ;

}
