package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.Werkervaringseenheid;

public interface IWerkervaringseenheidDAO {
	
	public Werkervaringseenheid findWerkervaringseenheid (long id);

    public void persistWerkervaringseenheid (Werkervaringseenheid a);
    
    public void updateWerkervaringseenheid (Werkervaringseenheid a);
    
    public void deleteWerkervaringseenheid (Werkervaringseenheid a);
	

}
