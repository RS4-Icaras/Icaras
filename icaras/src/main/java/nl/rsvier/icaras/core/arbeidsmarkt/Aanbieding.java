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
 * Core klasse: Aanbieding
 * 
 * @author Mark van Meerten
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
		if (this.vacatureMagWordenToegevoegd(vacature)) {
			this.setVacature(vacature);
			vacature.addAanbieding(this);
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
			this.getPersoon().getKandidaat().addAanbieding(this);
			this.getOrganisatie().getBedrijf().addAanbieding(this);
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
	public boolean removeAllReferences() {
		boolean tmpA = false;
		boolean tmpB = false;
		boolean tmpC = false;
		if (this.getVacature() != null) {
			tmpA = this.getVacature().removeAanbieding(this);
		}
		if (this.getPersoon() != null) {
			tmpB = this.getPersoon().getKandidaat().removeAanbieding(this);
		}
		if (this.getOrganisatie() != null) {
			tmpC = this.getOrganisatie().getBedrijf().removeAanbieding(this);
		}
		return tmpA && tmpB && tmpC;
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
	 * zowel de Aanbieding als Vacature gebruik maken dezelfde Organisatie
	 */
	public boolean setVacatureReferentie(Vacature vacature) {
		if (this.vacatureMagWordenToegevoegd(vacature)) {
			this.setVacature(vacature);
			return vacature.addAanbieding(this);
		}
		return false;
	}

	public static boolean vacatureConstraint(Vacature vacature,
			Organisatie organisatie) {
		/*
		 * Voeg een vacature alleen toe wanneer de Organisatie van de Vacature
		 * overeenkomt met de Organisatie waaraan we een Aanbieding willen doen
		 */
		return vacature.getOrganisatie().equals(organisatie);
	}

	public boolean vacatureMagWordenToegevoegd(Vacature vacature) {

		return Aanbieding.vacatureConstraint(vacature,
						this.getOrganisatie());
	}

	/*
	 * Alleen verantwoordelijk voor het verwijderen van een Vacature uit de
	 * collectie Vacatures die we hier bewaren. @See Vacature.destructotron
	 * wanneer je alle referenties naar Vacature wilt verwijderen.
	 */
	public boolean removeVacature(Vacature vacature) {
		if (this.getVacature() != null && this.getVacature().equals(vacature)) {
			/*
			 * Verwijder de Aanbieding niet omdat deze niet langer aan een
			 * Vacature is gekoppeld. De Aanbieding kan nog steeds worden
			 * gebruikt voor de statistieken
			 */
			
			vacature.removeAanbieding(this);
			this.vacature = null;
			// TODO: andere kant ook
			return true;
		}
		return false;
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

	public static boolean persoonConstraint(Persoon persoon) {
		/*
		 * De voorwaarden waar een Organisatie aan moet voldoen. Organisatie mag
		 * nooit null zijn en moet een bedrijfsrol bevatten
		 */
		return persoon != null && persoon.getKandidaat() != null;
	}

	public boolean persoonMagWordenToegevoegd(Persoon persoon) {
		/*
		 * Om een Organisatie toe te voegen mag deze geen null zijn en moet deze
		 * aan de voorwaarde voldoen
		 */
		return !this.persoonIsToegevoegd()
				&& Aanbieding.persoonConstraint(persoon);
	}

	public boolean persoonIsToegevoegd() {
		/*
		 * Organisatie is immutable. Als deze reeds is toegevoegd mag deze niet
		 * meer worder aangepast
		 */
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

	public static boolean organisatieConstraint(Organisatie organisatie) {
		/*
		 * De voorwaarden waar een Organisatie aan moet voldoen. Organisatie mag
		 * nooit null zijn en moet een bedrijfsrol bevatten
		 */
		return organisatie != null && organisatie.getBedrijf() != null;
	}

	public boolean organisatieMagWordenToegevoegd(Organisatie organisatie) {
		/*
		 * Om een Organisatie toe te voegen mag deze geen null zijn en moet deze
		 * aan de voorwaarde voldoen
		 */
		return !this.organisatieIsToegevoegd()
				&& Aanbieding.organisatieConstraint(organisatie);
	}

	public boolean organisatieIsToegevoegd() {
		/*
		 * Organisatie is immutable. Als deze reeds is toegevoegd mag deze niet
		 * meer worder aangepast
		 */
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
			// TODO: One day we'll probably want to add Vacature to our business
			// key so we can connect Aanbieding to the latest issued Vacature
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
