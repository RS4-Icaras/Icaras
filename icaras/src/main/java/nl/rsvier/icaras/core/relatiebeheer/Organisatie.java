package nl.rsvier.icaras.core.relatiebeheer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

import nl.rsvier.icaras.core.InvalidBusinessKeyException;

/**
 * Core klasse: Organisatie. Entiteit die een zakelijke relatie vertegenwoordigt
 * 
 * @author Mark van Meerten
 */

/*
 * TODO: Organisatie toevoegen frontend heeft een lege constructor nodig.
 * 
 * Mogelijke oplossing: o Invulobject o copyconstructor, booleanwaarde die op
 * true moet staan voordat er geschreven mag worden o public maken o Fuck
 * business-keys, stap over naar UUID
 * 
 * Lijkt erop dat het tijd word voor een UUID.
 */

@Entity
@PrimaryKeyJoinColumn(name = "relatie_id")
public class Organisatie extends Relatie {

	/*
	 * Attributen
	 */

	private static final long serialVersionUID = 1L;
	private Set<OrganisatieRol> rollen;
	private String naam;
	private Set<Persoon> contactpersonen;

	/*
	 * Constructoren
	 */

	public Organisatie(String str) throws InvalidBusinessKeyException {
		this();
		if (this.naamMagWordenToegevoegd(str)) {
			this.setNaam(str);
		} else {
			throw new InvalidBusinessKeyException(
					"Organisatie business key has not been properly initialized");
		}
	}

	private Organisatie() {
		this.rollen = new HashSet<OrganisatieRol>();
		this.contactpersonen = new HashSet<Persoon>();
	}

	/*
	 * OrganisatieRollen beheer
	 */

	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
	public Set<OrganisatieRol> getRollen() {
		return this.rollen;
	}

	@SuppressWarnings("unused")
	private void setRollen(Set<OrganisatieRol> rollenlijst) {
		this.rollen = rollenlijst;
	}

	/**
	 * Voeg een OrganisatieRol toe aan de collectie. Er kan slechts 1 rol van
	 * elke subklasse van OrganisatieRol worden toegevoegd
	 */
	public boolean addRol(OrganisatieRol rol) {
		if (rol != null && !this.heeftRol(rol.getClass())) {
			return this.getRollen().add(rol);
		}
		return false;
	}

	/**
	 * Retrieves the first element of List<OrganisatieRol> that matches the type
	 * we're requesting. Considering we're using a Collection type that doesn't
	 * allow duplicates and we check uniqueness before adding a new Rol that
	 * should be enough.
	 */
	public <T extends OrganisatieRol> OrganisatieRol getRolByType(
			Class<T> clstype) {
		for (OrganisatieRol rol : this.getRollen()) {
			if (clstype.isInstance(rol)) {
				return rol;
			}
		}
		return null;
	}

	/**
	 * Controleer of de collectie daadwerkelijk een rol bevat van het type
	 * OrganisatieRol
	 * 
	 * @param: class literal van het gewenste type OrganisatieRol
	 * 
	 * @return: true wanneer de collectie een OrganisatieRol van het juiste type
	 *          bevat
	 */
	public <T extends OrganisatieRol> boolean heeftRol(Class<T> clstype) {
		return this.getRolByType(clstype) != null;
	}

	@Transient
	public Bedrijf getBedrijf() {
		return (Bedrijf) this.getRolByType(Bedrijf.class);
	}

	@Transient
	public Leverancier getLeverancier() {
		return (Leverancier) this.getRolByType(Leverancier.class);
	}

	/*
	 * Naam: Maak een naam uniek en onaanpasbaar zodat deze te gebruiken is als
	 * business logic key
	 */

	@Column(unique = true, updatable = false)
	//@NotNull
	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public boolean naamConstraint(String str) {
		return str != null && !str.equals("");
	}

	public boolean naamMagWordenToegevoegd(String str) {
		return !this.heeftNaam() && this.naamConstraint(str);
	}

	public boolean heeftNaam() {
		return this.getNaam() != null;
	}

	/*
	 * Collectie: Contactpersonen
	 */

	@ManyToMany(cascade = javax.persistence.CascadeType.ALL)
	public Set<Persoon> getContactpersonen() {
		return contactpersonen;
	}

	@SuppressWarnings("unused")
	private void setContactpersonen(Set<Persoon> contactpersonen) {
		this.contactpersonen = contactpersonen;
	}

	/**
	 * Creëer bi-directionele relatie tussen Persoon > Contactpersoon >
	 * Organisatie
	 * 
	 * @param persoon
	 *            Houder van een contactpersoonsrol om toe te voegen aan de
	 *            collectie contactpersonen
	 * 
	 */
	public synchronized boolean addContactpersoon(Persoon persoon) {
		if (persoon == null || persoon.getContactpersoon() == null) {
			// Voorkom NullpointerExceptions
			return false;
		}
		if (this.contactpersoonMagWordenToegevoegd(persoon)) {
			this.getContactpersonen().add(persoon);
		}
		if (persoon.getContactpersoon().organisatieMagWordenToegevoegd(this)) {
			persoon.getContactpersoon().addOrganisatie(this, persoon);
		}
		return this.heeftContactpersoon(persoon)
				&& persoon.getContactpersoon().heeftOrganisatie(this);
	}

	/**
	 * Verbreek bi-directionele relatie tussen Persoon > Contactpersoon >
	 * Organisatie
	 * 
	 * @param persoon
	 *            Houder van een contactpersoonsrol om toe te verwijderen uit de
	 *            collectie contactpersonen
	 */
	public synchronized boolean removeContactpersoon(Persoon persoon) {
		if (persoon == null || persoon.getContactpersoon() == null) {
			// Voorkom NullpointerExceptions
			return false;
		}
		if (this.heeftContactpersoon(persoon)) {
			this.getContactpersonen().remove(persoon);
		}
		if (persoon.getContactpersoon().heeftOrganisatie(this)) {
			persoon.getContactpersoon().removeOrganisatie(this, persoon);
		}
		return !this.heeftContactpersoon(persoon)
				&& !persoon.getContactpersoon().heeftOrganisatie(this);
	}

	public boolean heeftContactpersoon(Persoon persoon) {
		return this.getContactpersonen().contains(persoon);
	}

	public boolean contactpersoonConstraint(Persoon persoon) {
		return persoon != null && persoon.heeftRol(Contactpersoon.class);
	}

	public boolean contactpersoonMagWordenToegevoegd(Persoon persoon) {
		return !this.heeftContactpersoon(persoon)
				&& this.contactpersoonConstraint(persoon);
	}

	/*
	 * Util
	 */

	@Override
	public int hashCode() {
		final int prime = 67;
		int hash = 1;
		hash = prime * hash + this.getNaam().hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || !(obj instanceof Organisatie)) {
			return false;
		} else {
			Organisatie other = (Organisatie) obj;
			if (!this.getNaam().equals(other.getNaam())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return this.getNaam();
	}

}
