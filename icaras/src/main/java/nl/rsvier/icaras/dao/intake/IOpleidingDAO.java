package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.Opleiding;

public interface IOpleidingDAO {
	
	public Opleiding findOpleiding (long id);

    public void persistOpleiding (Opleiding a);
    
    public void updateOpleiding (Opleiding a);
    
    public void deleteOpleiding (Opleiding a);

}
