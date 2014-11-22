package nl.rsvier.icaras.dao.relatiebeheer;

import java.util.List;

import nl.rsvier.icaras.core.relatiebeheer.Relatie;
import nl.rsvier.icaras.dao.IGenericDao;


public interface IRelatieDao extends IGenericDao<Relatie> {
	
	/**
	 * @return lijst met alle relaties in db, in elke relatie is ook de
	 *         adreslijst geinstantieerd
	 */
	public List<Relatie> getAllMetAdressen();
	//TODO methodeNaam aanpassen Adressen wordt Adreslijst
	//ook voor onderstaande methode en dus bijbehorende 
	// implementatie en ook in servicelaag
	/**
	 * @return Relatie uit database met geinitialiseerde adressenlijst
	 * @param de id van de op te vragen relatie
	 */
	public Relatie getByIdMetAdres(int id);

}
