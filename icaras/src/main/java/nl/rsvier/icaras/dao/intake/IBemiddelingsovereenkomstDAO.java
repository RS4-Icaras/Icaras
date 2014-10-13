package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.Bemiddelingsovereenkomst;

public interface IBemiddelingsovereenkomstDAO {
	
	public Bemiddelingsovereenkomst findBemiddelingsovereenkomst (long id);

    public void persistBemiddelingsovereenkomst (Bemiddelingsovereenkomst a);
    
    public void updateBemiddelingsovereenkomst (Bemiddelingsovereenkomst a);
    
    public void deleteBemiddelingsovereenkomst (Bemiddelingsovereenkomst a);

}
