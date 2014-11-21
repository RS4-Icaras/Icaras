package nl.rsvier.icaras.dao.relatiebeheer;

import java.util.List;
import java.util.ListIterator;

import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.relatiebeheer.Relatie;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

@Repository("IRelatieDao")
public class RelatieDaoHibernate extends GenericDaoHibernate<Relatie> implements
		IRelatieDao {

	public RelatieDaoHibernate() {
		super(Relatie.class);
	}

	/**
	 * @return lijst met alle relaties in db, in elke relatie is ook de
	 *         adreslijst geinstantieerd
	 */
	@Override
	public List<Relatie> getAllMetAdressen() {

		List<Relatie> relatieLijst = this.getAll();
		ListIterator<Relatie> listitr = relatieLijst.listIterator();

		while (listitr.hasNext()) {
			Relatie r = (Relatie) listitr.next();
			r.getAdressen().size();
		}

		return relatieLijst;
	}

	/**
	 * @return Relatie uit database met geinitialiseerde adressenlijst
	 * @param de
	 *            id van de op te vragen relatie
	 */
	@Override
	public Relatie getByIdMetAdres(int id) {
		Relatie r = this.getById(id);
		r.getAdressen().size();
		return r;
	}

}
