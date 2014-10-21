package nl.rsvier.icaras.dao.cursisttraject;

import nl.rsvier.icaras.core.cursisttraject.Examen;
import org.springframework.dao.DataAccessException;

public interface IExamenDAO {

    public Examen findExamenByID (long id) throws DataAccessException ;
    
    public void saveExamen (Examen examen) throws DataAccessException ;
    
    public void updateExamen (Examen examen) throws DataAccessException ;
    
    public void deleteExamen (Examen examen) throws DataAccessException ;

}