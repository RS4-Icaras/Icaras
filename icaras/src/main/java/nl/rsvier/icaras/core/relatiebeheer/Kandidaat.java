package nl.rsvier.icaras.core.relatiebeheer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import nl.rsvier.icaras.core.arbeidsmarkt.Aanbieding;
import nl.rsvier.icaras.core.arbeidsmarkt.CvGenerator;

/**
 * Deze klasse representeert de KandidaatRol. Houdt een lijst van
 * aanbiedingen bij. Zie klasse Aanbieding.
 *
 */
@Entity
public class Kandidaat extends PersoonsRol {

	private static final long serialVersionUID = 1L;

	private Set<Aanbieding> aanbiedingen = new HashSet<Aanbieding>();

	private CvGenerator cvGenerator = new CvGenerator();

	/*
	 * Roep Destructotron aan om alle referenties naar deze Vacature
	 * verwijderen. Vervolgens kan de Vacature worden gedelete zonder dat er
	 * data achterblijft in de database die verwijst naar deze Vacature die niet
	 * langer bestaat.
	 */
	public boolean destructotron(Aanbieding aanbieding) {
		// TODO: Leroy, thomas, en gordon leveren deze klasse aan
		return false;
	}

	@OneToMany(orphanRemoval = true, cascade = javax.persistence.CascadeType.ALL)
	// @Cascade(value = { CascadeType.SAVE_UPDATE })
	public Set<Aanbieding> getAanbiedingen() {
		return aanbiedingen;
	}

	@SuppressWarnings("unused")
	private void setAanbiedingen(Set<Aanbieding> aanbiedingen) {
		this.aanbiedingen = aanbiedingen;
	}

	/**
	 * Voegt de gegeven aanbieding toe aan de lijst van deze kandidaat. De
	 * aanbieding wordt alleen toegevoegd als deze niet null is, nog niet
	 * voorkomt in de lijst en aan zowel een organisatie met bedrijfsrol als aan
	 * de juiste persoon refereert.
	 * 
	 * 
	 * De aanbieding wordt tevens toegevoegd aan het bedrijf van de organisatie
	 * van deze aanbieding.
	 * 
	 * @param aanbieding
	 *            De toe te voegen aanbieding. Deze aanbieding moet een
	 *            referentie hebben naar de persoon die eigenaar is van de
	 *            kandidaat waarop deze methode wordt aangeroepen. De aanbieding
	 *            moet ook een referentie hebben naar een organisatie met een
	 *            bedrijfsrol.
	 * @return true als na afloop de aanbieding voorkomt in de aanbiedingen van
	 *         zowel de kandidaat als van het bedrijf. Anders false.
	 */
	public synchronized boolean addAanbieding(Aanbieding aanbieding) {
		if (aanbieding == null || aanbieding.getPersoon() == null
				|| aanbieding.getOrganisatie() == null
				|| heeftAanbieding(aanbieding)) {
			return false;
		}
		Kandidaat kandidaat = aanbieding.getPersoon().getKandidaat();
		Bedrijf bedrijf = aanbieding.getOrganisatie().getBedrijf();
		if (kandidaat != null && bedrijf != null && kandidaat == this) {
			aanbiedingen.add(aanbieding);
			bedrijf.addAanbieding(aanbieding);
			return aanbiedingen.contains(aanbieding)
					&& bedrijf.getAanbiedingen().contains(aanbieding);
		}
		return false;
	}

	/**
	 * TODO bij het verwijderen van een aanbieding die ook in een
	 * arbeidsovereenkomst zit, dient deze ook verwijderd te worden! Verwijderd
	 * de gegeven aanbieding uit de lijst van deze kandidaat indien aanwezig. De
	 * aanbieding wordt tevens verwijderd bij het bedrijf van de organisatie van
	 * deze aanbieding.
	 * 
	 * @param aanbieding
	 *            De te verwijderen aanbieding. Deze aanbieding moet een
	 *            referentie hebben naar de persoon die eigenaar is van de
	 *            kandidaat waarop deze methode wordt aangeroepen. De aanbieding
	 *            moet ook een referentie hebben naar een organisatie met een
	 *            bedrijfsrol.
	 * @return true als na afloop de aanbieding niet voorkomt in de aanbiedingen
	 *         van zowel de kandidaat als van het bedrijf. Anders false.
	 */
	public synchronized boolean removeAanbieding(Aanbieding aanbieding) {
		if (aanbieding == null || aanbieding.getPersoon() == null
				|| aanbieding.getOrganisatie() == null
				|| !aanbiedingen.contains(aanbieding)) {
			return false;
		}
		Kandidaat kandidaat = aanbieding.getPersoon().getKandidaat();
		Bedrijf bedrijf = aanbieding.getOrganisatie().getBedrijf();
		if (kandidaat != null && bedrijf != null && kandidaat == this) {
			getAanbiedingen().remove(aanbieding);
			bedrijf.removeAanbieding(aanbieding);
			return !heeftAanbieding(aanbieding)
					&& !bedrijf.getAanbiedingen().contains(aanbieding);
		}
		return false;
	}

	public boolean heeftAanbieding(Aanbieding aanbieding) {
		return getAanbiedingen().contains(aanbieding);
	}

	@Embedded
	public CvGenerator getCvGenerator() {
		return cvGenerator;
	}

	@SuppressWarnings("unused")
	// de methode wordt gebruikt door Hibernate
	private void setCvGenerator(CvGenerator cvGenerator) {
		this.cvGenerator = cvGenerator;
	}

	/*
	 * Utils
	 */

	public int hashCode() {
		final int prime = 67;
		int hash = 1;
		hash = prime * hash + this.getId();
		return hash;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || !super.equals(obj)
				|| !(obj instanceof Kandidaat)) {
			return false;
		} else {
			Kandidaat other = (Kandidaat) obj;
			if (this.getId() > 0 && other.getId() > 0
					&& this.getId() != other.getId()) {
				return false;
			}
		}
		return true;
	}

	public String toString() {
		return "Kandidatenrol, subklasse van: " + super.toString();
	}

}