package nl.rsvier.icaras.core.relatiebeheer;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Contactpersoon extends PersoonsRol {

	private static final long serialVersionUID = 1L;

	private List<Organisatie> organisaties;
	private String functie = "";

	// TODO: UUID ipv business key

	/*
	 * Constructor
	 */

	public Contactpersoon() {
		this.organisaties = new ArrayList<Organisatie>();
	}

	/*
	 * Organisaties vertegenwoordigt door deze contactpersoon
	 */

	@OneToMany()
	@NotNull
	public List<Organisatie> getOrganisaties() {
		return organisaties;
	}

	@SuppressWarnings("unused")
	private void setOrganisaties(List<Organisatie> organisaties) {
		this.organisaties = organisaties;
	}

	/**
	 * Creëer bi-directionele relatie tussen Persoon > Contactpersoon >
	 * Organisatie
	 * 
	 * @param organisatie
	 *            Organisatie waar deze contactpersoon aan moet worden gekoppeld
	 * @param persoon
	 *            Houder van deze contactpersoonsrol
	 */
	public synchronized boolean addOrganisatie(Organisatie organisatie,
			Persoon persoon) {
		if (organisatie == null || persoon == null
				|| persoon.getContactpersoon() == null) {
			// Voorkom NullpointerExceptions
			return false;
		}
		if (!persoon.getContactpersoon().equals(this)) {
			// Voorkom dat er een willekeurige persoon word meegegeven aan
			// Organisatie die niet de houder is van deze contactpersoonsrol
			return false;
		}
		if (this.organisatieMagWordenToegevoegd(organisatie)) {
			this.getOrganisaties().add(organisatie);
		}
		if (organisatie.contactpersoonMagWordenToegevoegd(persoon)) {
			organisatie.addContactpersoon(persoon);
		}
		return this.heeftOrganisatie(organisatie)
				&& organisatie.heeftContactpersoon(persoon);
	}

	/**
	 * Verbreek bi-directionele relatie tussen Persoon > Contactpersoon >
	 * Organisatie
	 * 
	 * @param organisatie
	 *            Organisatie waar deze contactpersoon van moet worden
	 *            losgekoppeld
	 * @param persoon
	 *            Houder van deze contactpersoonsrol
	 */
	public synchronized boolean removeOrganisatie(Organisatie organisatie,
			Persoon persoon) {
		if (organisatie == null || persoon == null
				|| persoon.getContactpersoon() == null) {
			// Voorkom NullpointerExceptions
			return false;
		}
		if (!persoon.getContactpersoon().equals(this)) {
			// Voorkom dat er een willekeurige persoon word verwijderd van
			// Organisatie die niet de houder is van deze contactpersoonsrol
			return false;
		}
		if (this.heeftOrganisatie(organisatie)) {
			this.organisaties.remove(organisatie);
		}
		if (organisatie.heeftContactpersoon(persoon)) {
			organisatie.removeContactpersoon(persoon);
		}
		return !this.heeftOrganisatie(organisatie)
				&& !organisatie.heeftContactpersoon(persoon);
	}

	public boolean heeftOrganisatie(Organisatie organisatie) {
		return this.getOrganisaties().contains(organisatie);
	}

	public boolean organisatieConstraint(Organisatie organisatie) {
		return organisatie != null;
	}

	public boolean organisatieMagWordenToegevoegd(Organisatie organisatie) {
		return !this.heeftOrganisatie(organisatie)
				&& this.organisatieConstraint(organisatie);
	}

	/*
	 * Functie
	 */

	@Column(unique = true, updatable = false)
	@NotNull
	public String getFunctie() {
		return this.functie;
	}

	public void setFunctie(String str) {
		this.functie = str;
	}

	/*
	 * Utils
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || !(obj instanceof Contactpersoon)) {
			return false;
		} else {
			Contactpersoon other = (Contactpersoon) obj;
			if (!this.getFunctie().equals(other.getFunctie())) {
				return false;
			}
			if (this.getOrganisaties().size() > 0
					&& !this.getOrganisaties().equals(other.getOrganisaties())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("Contactpersoon(id=%s) functie: %s", this.getId(),
				this.getFunctie());
	}

}
