package nl.rsvier.icaras.service.relatiebeheer;

import java.util.List;
import javax.transaction.Transactional;

import nl.rsvier.icaras.core.relatiebeheer.Relatie;
import nl.rsvier.icaras.dao.relatiebeheer.IRelatieDao;
import nl.rsvier.icaras.dao.relatiebeheer.RelatieDaoHibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicelaag voor Relatie klasse Let op: klasse zelf kan niet ge-autowired
 * worden door Spring, ivm Transactional en proxy pattern. Wil je deze klasse
 * autowiren, Autowire dan de interface IRelatieService. Deze heeft dezelfde
 * methoden als RelatieService.
 * 
 * @author Gerben
 *
 */
@Service("IRelatieService")
@Transactional
public class RelatieService implements IRelatieService {

	@Autowired
	private IRelatieDao relatieDao;

	/**
	 * @return relatieDao
	 */

	public IRelatieDao getDao() {
		
		return relatieDao;
		
	}

	/**
	 * 
	 */
	public void setDao(RelatieDaoHibernate dao) {
		
		this.relatieDao = dao;
		
	}

	/**
	 * @return lijst met all relatieObjecten uit de database
	 */
	public List<Relatie> getAll() {

		return relatieDao.getAll();
		
	}

	/**
	 * saved meegegeven Relatie attribuut in de database Controleert eerst of de
	 * adres en nfa Sets null zijn als dit zo is wordt lege HashSet aan dit
	 * attribuut toegekend zodat dataIntegrity gehandhaafd blijft
	 * 
	 * @param r
	 *            de te saven relatie
	 */
	public void save(Relatie r) {
		
		relatieDao.save(r);

	}

	/**
	 * update meegegeven relatie
	 */
	public void update(Relatie r) {
		
		relatieDao.update(r);

	}

	/**
	 * geeft relatie met meegegeven id
	 * 
	 * @return de gevonden relatie met gegeven id, zonder initialisatie van
	 *         lijsten dit is null als geen relatie gevonden wordt
	 */
	public Relatie getById(int id) {

		return relatieDao.getById(id);
	}

	/**
	 * @return Relatie uit database met geinitialiseerde adressenlijst
	 * @param de id van de op te vragen relatie
	 */
	public Relatie getByIdMetAdres(int id) {

		return relatieDao.getByIdMetAdres(id);
	}

	/**
	 * @return lijst met alle relaties in db, in elke relatie is ook de
	 *         adreslijst geinstantieerd
	 */
	public List<Relatie> getAllMetAdres() {

		return relatieDao.getAllMetAdressen();
	}
	
}
