package nl.rsvier.icaras.dao.relatiebeheer;

import java.util.List;

import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

@Repository("IPersoonDao")
public class PersoonDaoHibernate extends GenericDaoHibernate<Persoon> implements
		IPersoonDao {

	public PersoonDaoHibernate() {
		super(Persoon.class);
	}
	
	@Override
	public Persoon getByIdMetAdressenEnNfaLijst(int id) {
			Persoon persoon = getById(id);
			persoon.getAdressen().size();
			persoon.getNfaLijst().size();
			return persoon;
	}

	@Override
	public Persoon getByIdMetRollen(int id) {
		Persoon persoon = getById(id);
		persoon.getKandidaat().getAanbiedingen().size();
		persoon.getWerknemer().getArbeidsovereenkomsten().size();
		persoon.getContactpersoon().getOrganisaties().size();
		return persoon;
	}
	
	@Override
	public Persoon getByIdCompleet(int id) {
		Persoon persoon = getById(id);
		persoon.getAdressen().size();
		persoon.getNfaLijst().size();
		persoon.getKandidaat().getAanbiedingen().size();
		persoon.getWerknemer().getArbeidsovereenkomsten().size();
		persoon.getContactpersoon().getOrganisaties().size();
		return persoon;
	}

	@Override
	public List<Persoon> getAllMetAdressenEnNfaLijst() {
		List<Persoon> personenlijst = getAll();
		for (Persoon persoon : personenlijst) {
			persoon.getAdressen().size();
			persoon.getNfaLijst().size();
		}
		return personenlijst;
	}
	
	@Override
	public List<Persoon> getAllMetRollen() {
		List<Persoon> personenlijst = getAll();
		for (Persoon persoon : personenlijst) {
			persoon.getKandidaat().getAanbiedingen().size();
			persoon.getWerknemer().getArbeidsovereenkomsten().size();
			persoon.getContactpersoon().getOrganisaties().size();
		}
		return personenlijst;
	}

	@Override
	public List<Persoon> getAllCompleet() {
		List<Persoon> personenlijst = getAll();
		for (Persoon persoon : personenlijst) {
			persoon.getAdressen().size();
			persoon.getNfaLijst().size();
			persoon.getKandidaat().getAanbiedingen().size();
			persoon.getWerknemer().getArbeidsovereenkomsten().size();
			persoon.getContactpersoon().getOrganisaties().size();
		}
		return personenlijst;
	}

}
