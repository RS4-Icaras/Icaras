package nl.rsvier.icaras.core.arbeidsmarkt;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import nl.rsvier.icaras.core.IEntity;
import nl.rsvier.icaras.core.InvalidBusinessKeyException;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;

/**
 * Deze klasse representeert een aanbieding van een kandidaat aan een bedrijf.
 * Elke aanbieding wordt bidirectioneel vastgelegd; bij het instantiëren van een
 * aanbieding wordt deze toegevoegd aan zowel het bedrijf van de opgegeven
 * organisatie als aan de kandidaat van de opgegeven persoon.
 * 
 */

@Entity
public class Aanbieding implements IEntity {

	private static final long serialVersionUID = 1L;

	private int id;
	private Vacature vacature = null;
	private Persoon persoon;
	private Organisatie organisatie;

	/*
	 * Constructoren
	 */

	public Aanbieding(Persoon persoon, Organisatie organisatie,
			Vacature vacature) throws InvalidBusinessKeyException {
		this(persoon, organisatie);
		if (this.vacatureMagWordenGezet(vacature)) {
			this.setVacature(vacature);
			vacature.addAanbieding(this);
		} else {
			// TODO: Vacature is nog geen deel van de business key, passende
			// exception?
			throw new InvalidBusinessKeyException(
					"Aanbieding business key has not been properly initialized");
		}
	}

	public Aanbieding(Persoon persoon, Organisatie organisatie)
			throws InvalidBusinessKeyException {
		this();
		if (this.persoonMagWordenToegevoegd(persoon)
				&& this.organisatieMagWordenToegevoegd(organisatie)) {
			this.setPersoon(persoon);
			this.setOrganisatie(organisatie);
			/*
			 * Add this Aanbieding IF AND ONLY IF Persoon & Organisatie have
			 * been set! addAanbieding() will indirectly call this Aanbieding's
			 * hashCode() so you better be damn sure the business key fields
			 * have been properly initialized
			 */
			if (!this.getPersoon().getKandidaat().addAanbieding(this)) {
				throw new InvalidBusinessKeyException("Aanbieding bestaat al");
			}
		} else {
			throw new InvalidBusinessKeyException(
					"Aanbieding business key has not been properly initialized");
		}
	}

	private Aanbieding() {
		// Empty
	}

	/**
	 * Roep removeAllReferences() aan om alle referenties naar deze Aanbieding
	 * te verwijderen. Vervolgens kan Aanbieding worden gedelete zonder dat er
	 * data achterblijft in de database die verwijst naar deze Aanbieding die
	 * niet langer bestaat.
	 */
	public synchronized boolean removeAllReferences() {
		if (this.getVacature() != null) {
			this.getVacature().removeAanbieding(this);
		}
		if (this.getPersoon() != null) {
			this.getPersoon().getKandidaat().removeAanbieding(this);
		}
		if (this.getOrganisatie() != null) {
			this.getOrganisatie().getBedrijf().removeAanbieding(this);
		}

		return (this.getVacature() == null || !this.getVacature()
				.heeftAanbieding(this))
				&& !this.getPersoon().getKandidaat().heeftAanbieding(this)
				&& !this.getOrganisatie().getBedrijf().heeftAanbieding(this);
	}

	/*
	 * Identifier
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*
	 * Vacature
	 */

	@OneToOne()
	@JoinColumn(referencedColumnName = "id")
	public Vacature getVacature() {
		return this.vacature;
	}

	private void setVacature(Vacature vacature) {
		this.vacature = vacature;
	}

	/*
	 * Wanneer er een Vacature is gekoppeld aan een Aanbieding, voeg deze
	 * Aanbieding dan ook toe aan de Vacature. Sta dit echter alleen toe wanneer
	 * zowel de Aanbieding als Vacature gebruik maken dezelfde Organisatie.
	 * 
	 * N.B. Ondanks dat Vacature null mag zijn accepteerd deze methode geen null
	 * waarde. Indien je geen Vacature wilt dan overschrijf je de default null
	 * waarde niet of gebruik je de methode removeVacature()
	 */
	public synchronized boolean setVacatureReferentie(Vacature vacature) {
		if (vacature == null) {
			// Voorkom NullpointerExceptions
			return false;
		}
		if (this.vacatureMagWordenGezet(vacature)
				&& vacature.aanbiedingMagWordenToegevoegd(this)) {
			this.setVacature(vacature);
			vacature.addAanbieding(this);
			return this.heeftVacature(vacature)
					&& vacature.heeftAanbieding(this);
		}
		return false;
	}

	public synchronized boolean removeVacature(Vacature vacature) {
		if (vacature == null) {
			// Voorkom NullpointerExceptions
			return false;
		}
		if (this.heeftVacature(vacature) && vacature.heeftAanbieding(this)) {
			/*
			 * Roep eerst removeAanbieding op vacature aan
			 */
			vacature.removeAanbieding(this);
			this.setVacature(null);
			return !this.heeftVacature(vacature)
					&& !vacature.heeftAanbieding(this);
		}
		return false;

	}

	public boolean vacatureConstraint(Vacature vacature) {
		return this.getOrganisatie().equals(vacature.getOrganisatie());
	}

	public boolean vacatureMagWordenGezet(Vacature vacature) {
		return this.vacatureConstraint(vacature);
	}

	public boolean heeftVacature(Vacature vacature) {
		return this.getVacature() != null
				&& this.getVacature().equals(vacature);
	}

	/*
	 * Persoon
	 */

	@OneToOne()
	@JoinColumn(referencedColumnName = "relatie_id")
	@NotNull
	public Persoon getPersoon() {
		return this.persoon;
	}

	private void setPersoon(Persoon persoon) {
		this.persoon = persoon;
	}

	public boolean persoonConstraint(Persoon persoon) {
		return persoon != null && persoon.getKandidaat() != null;
	}

	public boolean persoonMagWordenToegevoegd(Persoon persoon) {
		return !this.heeftPersoon() && this.persoonConstraint(persoon);
	}

	public boolean heeftPersoon() {
		return this.getPersoon() != null;
	}

	/*
	 * Organisatie
	 */

	@OneToOne()
	@JoinColumn(referencedColumnName = "relatie_id")
	@NotNull
	public Organisatie getOrganisatie() {
		return this.organisatie;
	}

	private void setOrganisatie(Organisatie organisatie) {
		this.organisatie = organisatie;
	}

	public boolean organisatieConstraint(Organisatie organisatie) {
		return organisatie != null && organisatie.getBedrijf() != null;
	}

	public boolean organisatieMagWordenToegevoegd(Organisatie organisatie) {
		return !this.heeftOrganisatie()
				&& this.organisatieConstraint(organisatie);
	}

	public boolean heeftOrganisatie() {
		return this.getOrganisatie() != null;
	}

	/*
	 * Utils
	 */

	@Override
	public int hashCode() {
		final int prime = 53;
		int hash = 1;
		hash = prime * hash + this.getPersoon().hashCode();
		hash = prime * hash + this.getOrganisatie().hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || !(obj instanceof Aanbieding)) {
			return false;
		} else {
			Aanbieding other = (Aanbieding) obj;
			if (!this.getPersoon().equals(other.getPersoon())) {
				return false;
			}
			if (!this.getOrganisatie().equals(other.getOrganisatie())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		String tmp = "";
		if (this.getVacature() != null) {
			tmp = "(vacature=" + this.getVacature().getOmschrijving() + ")";
		}
		return "Aanbieding(id=" + this.getId() + ", hash=" + this.hashCode()
				+ ") van Persoon: " + this.getPersoon() + " aan Organisatie: "
				+ this.getOrganisatie() + " " + tmp;

	}

}
