package nl.rsvier.icaras.dao.cursisttraject;

import nl.rsvier.icaras.core.cursisttraject.Resultaat;
import org.springframework.dao.DataAccessException;

public interface IResultaatDAO {

    public Resultaat findResultaatByID (long id) throws DataAccessException ;
    
    public void saveResultaat (Resultaat resultaat) throws DataAccessException ;
    
    public void updateResultaat (Resultaat resultaat) throws DataAccessException ;
    
    public void deleteResultaat (Resultaat resultaat) throws DataAccessException ;

}
