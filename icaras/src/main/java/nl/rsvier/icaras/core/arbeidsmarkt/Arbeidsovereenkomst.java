package nl.rsvier.icaras.core.arbeidsmarkt;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import nl.rsvier.icaras.core.IEntity;
import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.relatiebeheer.Bedrijf;
import nl.rsvier.icaras.core.relatiebeheer.Kandidaat;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.core.relatiebeheer.Werknemer;

/**
 * De klasse die Arbeidsovereekomst beheert. Heeft een referentie terug naar de
 * persoon die deze arbeidsovereenkomst heeft. Heeft een referentie naar de
 * organisatie waarbij deze overeenkomst is afgesloten. Heeft een referentie
 * naar de aanbieding waaruit de overeenkomst is voortgekomen.
 * 
 */
// TODO Organisatie attribuut met bijbehorende javadoc en methodes toevoegen.

@Entity
public class Arbeidsovereenkomst implements IEntity {

	private static final long serialVersionUID = 1L;

	private int id;
	private Persoon persoon;
	private Organisatie organisatie;
	private Aanbieding aanbieding;

	@SuppressWarnings("unused")
	// voor Hibernate
	private Arbeidsovereenkomst() {
	}

	public Arbeidsovereenkomst(Aanbieding aanbieding)
			throws InvalidBusinessKeyException {
		if (aanbieding != null) {
			setAanbieding(aanbieding);// setPersoon en setOrganisatie worden
										// daarin aangeroepen
		} else {
			throw new InvalidBusinessKeyException(
					"De arbeidsovereenkomst kon niet worden gecre�erd op basis van de opgegeven aanbieding");
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@OneToOne
	public Persoon getPersoon() {
		return persoon;
	}

	/**
	 * Stelt in welke persoon bij deze arbeidsovereenkomst hoort. Accepteert
	 * alleen een persoon die zowel niet null is als een werknemerrol heeft. Als
	 * deze arbeidsovereenkomst hetzelfde object is als de arbeidsovereenkomst
	 * van de werknemerrol, dan wordt de persoon toegevoegd.
	 * 
	 * @param persoon
	 */
	private void setPersoon(Persoon persoon) {
		this.persoon = persoon;
	}

	@OneToOne
	public Organisatie getOrganisatie() {
		return organisatie;
	}

	private void setOrganisatie(Organisatie organisatie) {
		this.organisatie = organisatie;
	}

	@OneToOne
	public Aanbieding getAanbieding() {
		return aanbieding;
	}

	/**
	 * Voegt een aanbieding toe aan deze arbeidsovereenkomst. Voorwaarde is dat
	 * deze aanbieding een persoon en een organisatie heeft, die respectievelijk
	 * een kandidaat en bedrijf hebben met een referentie naar de gegeven
	 * aanbieding.
	 * 
	 * @param aanbieding
	 *            De toe te voegen aanbieding.
	 * @return True als het toevoegen van de arbeidsovereenkomst op basis van de
	 *         gegeven aanbieding aan de betreffende werknemer is gelukt.
	 */
	private synchronized boolean setAanbieding(Aanbieding aanbieding) {
		Persoon persoon = aanbieding.getPersoon();
		Organisatie organisatie = aanbieding.getOrganisatie();
		if (persoon != null && organisatie != null) {
			Kandidaat kandidaat = persoon.getKandidaat();
			Werknemer werknemer = persoon.getWerknemer();
			Bedrijf bedrijf = organisatie.getBedrijf();
			if (kandidaat != null && werknemer != null && bedrijf != null
					&& kandidaat.heeftAanbieding(aanbieding)
					&& bedrijf.heeftAanbieding(aanbieding))
				this.aanbieding = aanbieding;
			setPersoon(aanbieding.getPersoon());
			setOrganisatie(aanbieding.getOrganisatie());
			return werknemer.addArbeidsovereenkomst(this);
		}
		return false;
	}

}
