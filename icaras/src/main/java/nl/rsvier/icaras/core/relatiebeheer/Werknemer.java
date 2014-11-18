package nl.rsvier.icaras.core.relatiebeheer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import nl.rsvier.icaras.core.arbeidsmarkt.Arbeidsovereenkomst;

/**
 * Deze klasse representeert de werknemersRol. Houdt een lijst van
 * arbeidsovereenkomsten bij. Zie klasse Arbeidsovereenkomst.
 */
@Entity
public class Werknemer extends PersoonsRol {

	private static final long serialVersionUID = 1L;
	private Set<Arbeidsovereenkomst> arbeidsovereenkomsten = new HashSet<Arbeidsovereenkomst>();

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
	public Set<Arbeidsovereenkomst> getArbeidsovereenkomsten() {
		return arbeidsovereenkomsten;
	}

	public boolean heeftArbeidsovereenkomst(
			Arbeidsovereenkomst arbeidsovereenkomst) {
		return getArbeidsovereenkomsten().contains(arbeidsovereenkomst);
	}

	@SuppressWarnings("unused")
	// wordt wel gebruikt door Hibernate
	private void setArbeidsovereenkomsten(
			Set<Arbeidsovereenkomst> arbeidsovereenkomsten) {
		this.arbeidsovereenkomsten = arbeidsovereenkomsten;
	}

	/**
	 * Kijkt of het opgegeven bedrijf voorkomt in de set van
	 * arbeidsovereenkomsten
	 * 
	 * @param bedrijf
	 *            bedrijf waarvan gevraagd wordt of deze werknemer daar werkzaam
	 *            is.
	 * @return True als het opgegeven bedrijf de bedrijfsrol is van een van de
	 *         organisaties van de arbeidsovereenkomsten in de lijst, anders
	 *         false.
	 */
	public boolean werktBijBedrijf(Bedrijf bedrijf) {
		for (Arbeidsovereenkomst a : getArbeidsovereenkomsten()) {
			if (a.getOrganisatie().getBedrijf() == (bedrijf)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Voegt de gegeven arbeidsoverenkomst toe aan deze werknemer. De
	 * arbeidsovereenkomst wordt alleen toegevoegd deze niet null is, nog niet
	 * voorkomt in de lijst en aan zowel een organisatie met bedrijfsrol als aan
	 * de juiste persoon refereert. TODO controle of persoon.kandidaat de
	 * aanbieding bevat!!!
	 * 
	 * Voegt de arbeidsovereenkomst en medewerker ook toe aan het betreffende
	 * bedrijf indien niet aanwezig.
	 * 
	 * @param Arbeidsovereenkomst
	 *            De toe te voegen arbeidsovereenkomst die een aanbieding
	 *            toegekend heeft gekregen.
	 * @return True als na afloop de arbeidsovereenkomst voorkomt in zowel de
	 *         lijst van deze werknemer als in die van het betreffende bedrijf.
	 *         Tevens moet de persoon die houder is van deze werknemer(rol)
	 *         medewerker zijn bij dat bedrijf.
	 */
	// boolean isGetekend geen eis voor toevoegen
	public synchronized boolean addArbeidsovereenkomst(
			Arbeidsovereenkomst arbeidsovereenkomst) {
		// als de arbeidsovereenkomst al aanwezig is return false
		if (arbeidsovereenkomst == null
				|| arbeidsovereenkomst.getAanbieding() == null
				|| heeftArbeidsovereenkomst(arbeidsovereenkomst)) {
			return false;
		}
		Persoon persoon = arbeidsovereenkomst.getAanbieding().getPersoon();
		Organisatie organisatie = arbeidsovereenkomst.getAanbieding()
				.getOrganisatie();
		if (persoon != null && organisatie != null
		// controleer of het de juiste persoon is
				&& persoon.getWerknemer() == this) {
			Bedrijf bedrijf = organisatie.getBedrijf();
			if (bedrijf != null
					&& getArbeidsovereenkomsten().add(arbeidsovereenkomst)) {
				bedrijf.addArbeidsovereenkomst(arbeidsovereenkomst);
				bedrijf.addMedewerker(persoon);// doet niets als deze al in de
												// lijst voorkomt
				// return of de arbeidsovereenkomst in de lijst van
				// zowel het bedrijf en de werknemer voorkomt
				return heeftArbeidsovereenkomst(arbeidsovereenkomst)
						&& bedrijf.isMedewerker(persoon)
						&& bedrijf
								.heeftArbeidsovereenkomst(arbeidsovereenkomst);
			}
		}
		return false;
	}

	/**
	 * Verwijderd de arbeidsoverenkomst alleen als deze voorkomt in de lijst van
	 * arbeidsovereenkomsten van deze werknemer en niet null is.
	 * 
	 * Verwijderd de arbeidsovereenkomst ook bij het betreffende bedrijf indien
	 * aanwezig. Als na verwijdering van de gegeven arbeidsovereenkomst deze
	 * werknemer niet meer werkt bij dat bedrijf, wordt tevens de medewerker bij
	 * het bedrijf verwijderd.
	 * 
	 * @param Arbeidsovereenkomst
	 *            De te verwijderen arbeidsovereenkomst die een aanbieding
	 *            toegekend heeft gekregen.
	 * @return True als de arbeidsovereenkomst kan worden verwijderd en de
	 *         arbeidsovereenkomst en werknemer na afloop ook niet meer
	 *         voorkomen bij het betreffende bedrijf. bij het betreffende
	 */
	public synchronized boolean removeArbeidsovereenkomst(
			Arbeidsovereenkomst arbeidsovereenkomst) {
		if (arbeidsovereenkomst != null
				&& heeftArbeidsovereenkomst(arbeidsovereenkomst)
				&& arbeidsovereenkomst.getAanbieding() != null
				&& arbeidsovereenkomst.getPersoon() != null
				&& arbeidsovereenkomst.getOrganisatie() != null
				&& arbeidsovereenkomst.getOrganisatie().getBedrijf() != null) {
			Bedrijf bedrijf = arbeidsovereenkomst.getOrganisatie().getBedrijf();
			// verwijder de arbeidsovereenkomst op beide plaatsen
			getArbeidsovereenkomsten().remove(arbeidsovereenkomst);
			bedrijf.removeArbeidsovereenkomst(arbeidsovereenkomst);
			bedrijf.removeMedewerker(arbeidsovereenkomst.getPersoon());// controleert
																		// zelf
																		// of
																		// dit
																		// van
																		// toepassing
																		// is
			return !heeftArbeidsovereenkomst(arbeidsovereenkomst)
					&& !bedrijf.heeftArbeidsovereenkomst(arbeidsovereenkomst);
		}
		return false;
	}
}
