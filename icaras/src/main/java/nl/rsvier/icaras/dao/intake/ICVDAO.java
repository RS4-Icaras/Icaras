package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.CV;

public interface ICVDAO {
	
	public CV findCV (long id);

    public void persistCV (CV a);
    
    public void updateCV (CV a);
    
    public void deleteCV (CV a);

}
