package nl.rsvier.icaras.dao.relatiebeheer;

import java.util.List;

import nl.rsvier.icaras.core.relatiebeheer.Adres;

public interface IAdresDao {

	public void save(Adres adres);
	public void update(Adres adres);
	public void delete(Adres adres);
	public Adres getById(int adresId);
	public List<Adres> getAll();
	
}
