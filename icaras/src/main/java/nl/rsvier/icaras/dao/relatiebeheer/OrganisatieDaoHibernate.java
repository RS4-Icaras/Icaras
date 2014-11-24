package nl.rsvier.icaras.dao.relatiebeheer;

import java.util.List;

import nl.rsvier.icaras.core.relatiebeheer.Bedrijf;
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



	@Override
	public List<Organisatie> getAllMetRollen() {
		List<Organisatie> organisaties = getAll();
		for (Organisatie organisatie : organisaties) {
			Bedrijf bedrijf = organisatie.getBedrijf();
			bedrijf.getAanbiedingen().size();
			bedrijf.getArbeidsovereenkomsten().size();
			bedrijf.getMedewerkers().size();
			organisatie.getLeverancier();
		}
		return organisaties;
	}

	@Override
	public List<Organisatie> getAllCompleet() {
		List<Organisatie> organisaties = getAll();
		for (Organisatie organisatie : organisaties) {
			organisatie.getAdressen().size();
			organisatie.getNfaLijst().size();
			Bedrijf bedrijf = organisatie.getBedrijf();
			bedrijf.getAanbiedingen().size();
			bedrijf.getArbeidsovereenkomsten().size();
			bedrijf.getMedewerkers().size();
			organisatie.getLeverancier();
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

	@Override
	public Organisatie getByIdMetRollen(int id) {
		Organisatie organisatie = getById(id);
		Bedrijf bedrijf = organisatie.getBedrijf();
		bedrijf.getAanbiedingen().size();
		bedrijf.getArbeidsovereenkomsten().size();
		bedrijf.getMedewerkers().size();
		organisatie.getLeverancier();
		return organisatie;
	}
	
	@Override
	public Organisatie getByIdCompleet(int id) {
		Organisatie organisatie = getById(id);
		organisatie.getAdressen().size();
		organisatie.getNfaLijst().size();
		Bedrijf bedrijf = organisatie.getBedrijf();
		bedrijf.getAanbiedingen().size();
		bedrijf.getArbeidsovereenkomsten().size();
		bedrijf.getMedewerkers().size();
		organisatie.getLeverancier();
		return organisatie;
	}
}
