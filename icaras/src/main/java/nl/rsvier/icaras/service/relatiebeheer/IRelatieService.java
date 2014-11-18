package nl.rsvier.icaras.service.relatiebeheer;

import java.util.List;
import java.util.Set;

import nl.rsvier.icaras.core.relatiebeheer.Adres;
import nl.rsvier.icaras.core.relatiebeheer.Relatie;
import nl.rsvier.icaras.dao.relatiebeheer.IRelatieDao;
import nl.rsvier.icaras.dao.relatiebeheer.RelatieDaoHibernate;


public interface IRelatieService  {
	
	public List<Relatie> getAll();
	
	public void save(Relatie r);
	
	public void delete(Relatie r);
	
	public void update(Relatie r);
	
	public Relatie getById(int id);
	
	public IRelatieDao getDao();
	
	public void setDao(RelatieDaoHibernate dao);
	
	public List<Relatie> getAllMetAdres();
	
	public Set<Adres> getRelatieAdressen(Relatie r);
	
	public Relatie getByIdMetAdres(int id);
}
