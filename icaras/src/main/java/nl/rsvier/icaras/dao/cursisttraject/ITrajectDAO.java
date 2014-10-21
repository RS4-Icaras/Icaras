package nl.rsvier.icaras.dao.cursisttraject;

import nl.rsvier.icaras.core.cursisttraject.Traject;
import org.springframework.dao.DataAccessException;

public interface ITrajectDAO {

    public Traject findTrajectByID (long id) throws DataAccessException ;
    
    public void saveTraject (Traject resultaat) throws DataAccessException ;
    
    public void updateTraject (Traject resultaat) throws DataAccessException ;
    
    public void deleteTraject (Traject resultaat) throws DataAccessException ;

}