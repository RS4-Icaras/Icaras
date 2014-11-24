package nl.rsvier.icaras.dao.relatiebeheer;

import java.util.List;

import nl.rsvier.icaras.core.relatiebeheer.Bedrijf;
import nl.rsvier.icaras.core.relatiebeheer.Leverancier;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

import org.springframework.stereotype.Repository;

/*
 * Annotatie @Repository zorgt ook voor (aanmelding voor) translatie van
 * exceptions naar Spring
 */
@Repository("IOrganisatieDao")
public class OrganisatieDaoHibernate extends GenericDaoHibernate<Organisatie>
		implements IOrganisatieDao {

	public OrganisatieDaoHibernate() {
		super(Organisatie.class);
	}

	@Override
	public List<Organisatie> getAllMetAdressenenNfaLijst() {
		List<Organisatie> organisaties = getAll();
		for (Organisatie organisatie : organisaties) {
			organisatie.getAdressen().size();
			organisatie.getNfaLijst().size();
		}
		return organisaties;
	}

	/**
	 * initialiseert ook de lijst met contactpersonen
	 */
	@Override
	public List<Organisatie> getAllMetRollen() {
		List<Organisatie> organisaties = getAll();
		for (Organisatie organisatie : organisaties) {
			organisatie.getContactpersonen().size();
			if (organisatie.heeftRol(Bedrijf.class)) {
				Bedrijf bedrijf = organisatie.getBedrijf();
				bedrijf.getAanbiedingen().size();
				bedrijf.getArbeidsovereenkomsten().size();
				bedrijf.getMedewerkers().size();
			}
			if (organisatie.heeftRol(Leverancier.class)) {
				organisatie.getLeverancier();
			}
		}
		return organisaties;
	}

	@Override
	public List<Organisatie> getAllCompleet() {
		List<Organisatie> organisaties = getAll();
		for (Organisatie organisatie : organisaties) {
			organisatie.getAdressen().size();
			organisatie.getNfaLijst().size();
			organisatie.getContactpersonen().size();
			if (organisatie.heeftRol(Bedrijf.class)) {
				Bedrijf bedrijf = organisatie.getBedrijf();
				bedrijf.getAanbiedingen().size();
				bedrijf.getArbeidsovereenkomsten().size();
				bedrijf.getMedewerkers().size();
			}
			if (organisatie.heeftRol(Leverancier.class)) {
				organisatie.getLeverancier();
			}
		}
		return organisaties;
	}

	@Override
	public Organisatie getByIdMetAdressenEnNfaLijst(int id) {
		Organisatie organisatie = getById(id);
		organisatie.getAdressen().size();
		organisatie.getNfaLijst().size();
		return organisatie;
	}

	/**
	 * initialiseert ook de lijst met contactpersonen
	 */
	@Override
	public Organisatie getByIdMetRollen(int id) {
		Organisatie organisatie = getById(id);
		organisatie.getContactpersonen().size();
		if (organisatie.heeftRol(Bedrijf.class)) {
			Bedrijf bedrijf = organisatie.getBedrijf();
			bedrijf.getAanbiedingen().size();
			bedrijf.getArbeidsovereenkomsten().size();
			bedrijf.getMedewerkers().size();
		}
		if (organisatie.heeftRol(Leverancier.class)) {
			organisatie.getLeverancier();
		}
		return organisatie;
	}

	@Override
	public Organisatie getByIdCompleet(int id) {
		Organisatie organisatie = getById(id);
		organisatie.getContactpersonen().size();
		organisatie.getAdressen().size();
		organisatie.getNfaLijst().size();
		if (organisatie.heeftRol(Bedrijf.class)) {
			Bedrijf bedrijf = organisatie.getBedrijf();
			bedrijf.getAanbiedingen().size();
			bedrijf.getArbeidsovereenkomsten().size();
			bedrijf.getMedewerkers().size();
		}
		if (organisatie.heeftRol(Leverancier.class)) {
			organisatie.getLeverancier();
		}
		return organisatie;
	}
}
