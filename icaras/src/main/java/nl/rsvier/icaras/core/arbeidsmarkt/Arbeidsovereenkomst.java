package nl.rsvier.icaras.core.arbeidsmarkt;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import nl.rsvier.icaras.core.IEntity;
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

	public Arbeidsovereenkomst(Aanbieding aanbieding) {
		setAanbieding(aanbieding);
		//volgende twee worden aangeroepen in setAanbieding
//		setPersoon(aanbieding.getPersoon());
//		setOrganisatie(aanbieding.getOrganisatie());
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
//		System.out.println("Aanvang setPersoon AO");
//		if (persoon != null) {
//			System.out.println("Voor persoon: " + persoon);
//			Werknemer w = null;// hulp variabele
//			// kijk of de meegegeven persoonsinstantie een werknemersrol heeft
//			w = persoon.getWerknemer();
//			if (w != null) {
//				System.out.println("werknemer is niet null");
//				for (Arbeidsovereenkomst arbeidsovereenkomst : w
//						.getArbeidsovereenkomsten()) {
//
//					if (arbeidsovereenkomst == this) {// Kan niet worden
//														// gebruikt binnen
//														// Hibernate (mogelijk
//														// meerdere instanties/proxies?)
//						// ken dan deze persoon toe
//						this.persoon = persoon;
//					}
//				}
//			}
//		}
//
//	}

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
	 * deze aanbieding een persoon en een organisatie heeft.
	 * 
	 * @param aanbieding
	 */
	private void setAanbieding(Aanbieding aanbieding) {
		if (aanbieding.getPersoon() != null && aanbieding.getOrganisatie() != null) {
			this.aanbieding = aanbieding;
			this.setPersoon(aanbieding.getPersoon());
			setOrganisatie(aanbieding.getOrganisatie());
		}
	}

}
