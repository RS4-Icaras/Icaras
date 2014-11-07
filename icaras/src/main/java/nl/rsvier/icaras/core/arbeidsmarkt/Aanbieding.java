package nl.rsvier.icaras.core.arbeidsmarkt;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import nl.rsvier.icaras.core.IEntity;
import nl.rsvier.icaras.core.relatiebeheer.Bedrijf;
import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;

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
			Vacature vacature) {
		this(persoon, organisatie);
		this.setVacature(vacature);

		// Don't add this Aanbieding UNTIL Persoon & Organisatie have been set!
		vacature.addAanbieding(this);
	}

	public Aanbieding(Persoon persoon, Organisatie organisatie) {
		this();
		if (persoonMagWordenToegevoegd(persoon))
			if (organisatieMagWordenToegevoegd(organisatie))
				this.setPersoon(persoon);
		this.setOrganisatie(organisatie);

		// Don't add this Aanbieding UNTIL Persoon & Organisatie have been set!
		persoon.getKandidaat().addAanbieding(this);
		((Bedrijf) organisatie.getRol(Bedrijf.class)).addAanbieding(this);
	}

	private Aanbieding() {
		// Empty
	}

	/*
	 * Roep removeAllReferences() aan om alle referenties naar deze Aanbieding
	 * te verwijderen. Vervolgens kan Aanbieding worden gedelete zonder dat er
	 * data achterblijft in de database die verwijst naar deze Aanbieding die
	 * niet langer bestaat.
	 */
	public boolean removeAllReferences() {
		boolean tmp = false;
		if (this.vacature != null) {
			tmp = this.vacature.removeAanbieding(this);
		}
		if (this.persoon != null) {
			tmp = persoon.getKandidaat().removeAanbieding(this);
		}
		if (this.organisatie != null) {
			tmp = ((Bedrijf) this.organisatie.getRol(Bedrijf.class))
					.removeAanbieding(this);
		}
		return tmp;
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
		if (this.getOrganisatie() != null
				&& this.getOrganisatie().equals(vacature.getOrganisatie())) {
			this.setVacature(vacature);
			return vacature.addAanbieding(this);
		}
		return false;
	}

	/*
	 * Alleen verantwoordelijk voor het verwijderen van een Vacature uit de
	 * collectie Vacatures die we hier bewaren. @See Vacature.destructotron
	 * wanneer je alle referenties naar Vacature wilt verwijderen.
	 */
	public boolean removeVacature(Vacature vacature) {
		if (this.getVacature() != null && this.getVacature().equals(vacature)) {
			// Set this.vacature to null, because the Aanbieding should still be
			// stored in our database (with a null value for column: Vacature)
			this.vacature = null;
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

	/**
	 * Stelt in welke persoon bij deze aanbieding hoort. Accepteert alleen een
	 * persoon die zowel niet null is als een kandidaatrol heeft. Als deze
	 * aandbieding hetzelfde object is als de aanbieding van de kandidaatrol,
	 * dan wordt de persoon toegevoegd.
	 * 
	 * @param persoon
	 */
	public void setPersoonReferentie(Persoon persoon) {
		if (persoonMagWordenToegevoegd(persoon)) {
			this.setPersoon(persoon);
			persoon.getKandidaat().addAanbieding(this);
		}

	}

	private boolean persoonMagWordenToegevoegd(Persoon persoon) {
		return (persoon != null && persoon.getKandidaat() != null);
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

	public void setOrganisatieReferentie(Organisatie organisatie) {
		if (organisatieMagWordenToegevoegd(organisatie)) {
			this.setOrganisatie(organisatie);
			((Bedrijf) organisatie.getRol(Bedrijf.class)).addAanbieding(this);
		}
	}

	private boolean organisatieMagWordenToegevoegd(Organisatie organisatie) {
		return (organisatie != null && ((Bedrijf) organisatie
				.getRol(Bedrijf.class)) != null);
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
