package nl.rsvier.icaras.service.relatiebeheer;

import java.util.List;

import nl.rsvier.icaras.core.arbeidsmarkt.Aanbieding;

public interface IAanbiedingService {

	public Aanbieding getById(int id);

	public List<Aanbieding> getAll();
}
