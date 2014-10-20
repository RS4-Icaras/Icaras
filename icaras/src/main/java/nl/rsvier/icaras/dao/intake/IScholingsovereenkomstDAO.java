package nl.rsvier.icaras.dao.intake;

import java.util.List;

import nl.rsvier.icaras.core.intake.Scholingsovereenkomst;

public interface IScholingsovereenkomstDAO {
	
	public Scholingsovereenkomst findScholingsovereenkomst (long id);

    public void persistScholingsovereenkomst (Scholingsovereenkomst ar);
    
    public void updateScholingsovereenkomst (Scholingsovereenkomst a);
    
    public void deleteScholingsovereenkomst (Scholingsovereenkomst a);
    
    public List<Scholingsovereenkomst> getAllScholingsovereenkomst();

}
