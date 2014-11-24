package nl.rsvier.icaras.service.relatiebeheer;

import java.util.List;

import nl.rsvier.icaras.core.relatiebeheer.Relatie;


public interface IRelatieService  {
	
	public List<Relatie> getAll();
	
	public void save(Relatie r);
	
	public void update(Relatie r);
	
	public Relatie getById(int id);
	//TODO getAllMetCorrespondentieAdres ipv onderstaande.
	public List<Relatie> getAllMetAdres();	
	
	public Relatie getByIdMetAdres(int id);
}
