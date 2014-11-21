package nl.rsvier.icaras.service.relatiebeheer;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.transaction.Transactional;

import nl.rsvier.icaras.core.relatiebeheer.Adres;
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

	// TODO vanwege autowiren van interface (good practice, mogelijkheid om
	// andere implementatie te gebruiken)
	// nu cast nodig op getHibernateTemplate. Hierdoor is overstap op andere
	// implementatie alsnog niet mogelijk
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
	 * verwijderd meegegeven Relatie uit de database
	 * 
	 * @param r
	 *            de te deleten Relatie
	 */
	public void delete(Relatie r) {
		
		relatieDao.delete(r);

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

	public Relatie getByIdMetAdres(int id) {

		Relatie r = relatieDao.getById(id);

		((RelatieDaoHibernate) relatieDao).getHibernateTemplate().initialize(
				r.getAdressen());

		return r;

	}

	/**
	 * @return lijst met alle relaties in db, in elke relatie is ook de
	 *         adreslijst geinstantieerd
	 */
	public List<Relatie> getAllMetAdres() {

		List<Relatie> relatieLijst = relatieDao.getAll();
		ListIterator<Relatie> listitr = relatieLijst.listIterator();

		while (listitr.hasNext()) {
			Relatie r = (Relatie) listitr.next();
			((RelatieDaoHibernate) relatieDao).getHibernateTemplate()
					.initialize(r.getAdressen());
		}

		return relatieLijst;
	}
	
}
