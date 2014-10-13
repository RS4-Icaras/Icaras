package nl.rsvier.icaras.dao.relatiebeheer;

import java.util.List;

import nl.rsvier.icaras.core.relatiebeheer.Adres;

public interface IAdresDao {

	public void saveAdres(Adres adres);
	public void updateAdres(Adres adres);
	public void deleteAdres(Adres adres);
	public Adres getAdresById(int adresId);
	public List<Adres> getAllAdressen();
	
}
