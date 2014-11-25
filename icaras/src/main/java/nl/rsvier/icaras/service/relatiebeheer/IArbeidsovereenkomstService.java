package nl.rsvier.icaras.service.relatiebeheer;

import java.util.List;

import nl.rsvier.icaras.core.arbeidsmarkt.Arbeidsovereenkomst;

public interface IArbeidsovereenkomstService {

	public Arbeidsovereenkomst getById(int id);

	public List<Arbeidsovereenkomst> getAll();
}
