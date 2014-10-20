package nl.rsvier.icaras.dao.intake;

import java.util.List;
import nl.rsvier.icaras.core.intake.Werkervaringseenheid;

public interface IWerkervaringseenheidDAO {
	
	public Werkervaringseenheid findWerkervaringseenheid (long id);

    public void persistWerkervaringseenheid (Werkervaringseenheid a);
    
    public void updateWerkervaringseenheid (Werkervaringseenheid a);
    
    public void deleteWerkervaringseenheid (Werkervaringseenheid a);
    
    public List<Werkervaringseenheid> getAllWerkervaringseenheid();
	

}
