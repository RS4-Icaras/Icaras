package nl.rsvier.icaras.dao.intake;


import java.util.List;

import nl.rsvier.icaras.core.intake.Aanmelder;
import nl.rsvier.icaras.core.intake.Arbeidsovereenkomst;

public interface IArbeidsovereenkomstDAO {
	
	public Arbeidsovereenkomst findArbeidsovereenkomst (long id);

    public void persistArbeidsovereenkomst (Arbeidsovereenkomst a);
    
    public void updateArbeidsovereenkomst (Arbeidsovereenkomst a);
    
    public void deleteArbeidsovereenkomst (Arbeidsovereenkomst a);
    
    public List<Arbeidsovereenkomst> getAllArbeidsovereenkomst();

}
