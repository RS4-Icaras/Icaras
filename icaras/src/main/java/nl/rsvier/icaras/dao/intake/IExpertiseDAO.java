package nl.rsvier.icaras.dao.intake;

import java.util.List;

import nl.rsvier.icaras.core.intake.Expertise;

public interface IExpertiseDAO {

	public Expertise findExpertise (long id);

    public void persistExpertise (Expertise Expertise);
    
    public void updateExpertise (Expertise Expertise);
    
    public void deleteExpertise (Expertise Expertise);
    
    public List<Expertise> getAllExpertise();
}
