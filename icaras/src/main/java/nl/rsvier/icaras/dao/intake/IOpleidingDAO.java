package nl.rsvier.icaras.dao.intake;

import java.util.List;

import nl.rsvier.icaras.core.intake.Opleiding;

public interface IOpleidingDAO {
	
	public Opleiding findOpleiding (long id);

    public void persistOpleiding (Opleiding a);
    
    public void updateOpleiding (Opleiding a);
    
    public void deleteOpleiding (Opleiding a);
    
    public List<Opleiding> getAllOpleiding();

}
