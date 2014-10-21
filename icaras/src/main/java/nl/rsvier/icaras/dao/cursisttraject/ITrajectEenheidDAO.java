package nl.rsvier.icaras.dao.cursisttraject;

import nl.rsvier.icaras.core.cursisttraject.TrajectEenheid;
import org.springframework.dao.DataAccessException;

public interface ITrajectEenheidDAO {

    public TrajectEenheid findTrajectEenheidByID (long id) throws DataAccessException ;
    
    public void saveTrajectEenheid (TrajectEenheid trajectEenheid) throws DataAccessException ;
    
    public void updateTrajectEenheid (TrajectEenheid trajectEenheid) throws DataAccessException ;
    
    public void deleteTrajectEenheid (TrajectEenheid trajectEenheid) throws DataAccessException ;

}