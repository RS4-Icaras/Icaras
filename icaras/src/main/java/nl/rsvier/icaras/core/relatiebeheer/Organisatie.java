package nl.rsvier.icaras.core.relatiebeheer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

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
	private List<Persoon> contactpersonen; // Kijk, een lijst!

	/*
	 * Constructoren
	 */

	public Organisatie(String str) throws InvalidBusinessKeyException {
		this();
		if (this.naamMagWordenToegevoegd(str)) {
			this.setNaam(str);
		}
	}

	private Organisatie() {
		this.rollen = new HashSet<OrganisatieRol>();
		this.contactpersonen = new ArrayList<Persoon>();
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
		if (rol != null && !this.hasRol(rol.getClass())) {
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
	public <T extends OrganisatieRol> OrganisatieRol getRol(Class<T> clstype) {
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
	public <T extends OrganisatieRol> boolean hasRol(Class<T> clstype) {
		return this.getRol(clstype) != null;
	}

	@Transient
	public Bedrijf getBedrijf() {
		return (Bedrijf) this.getRol(Bedrijf.class);
	}

	@Transient
	public Leverancier getLeverancier() {
		return (Leverancier) this.getRol(Leverancier.class);
	}

	/*
	 * Naam: Maak een naam uniek en onaanpasbaar zodat deze te gebruiken is als
	 * business logic key
	 */

	@Column(unique = true, updatable = false)
	@NotNull
	public String getNaam() {
		return this.naam;
	}

	private void setNaam(String naam) {
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

	@OneToMany(orphanRemoval = true, cascade = javax.persistence.CascadeType.ALL)
	public List<Persoon> getContactpersonen() {
		return contactpersonen;
	}

	@SuppressWarnings("unused")
	private void setContactpersonen(List<Persoon> contactpersonen) {
		this.contactpersonen = contactpersonen;
	}

	public boolean addContactpersoon(Persoon persoon) {
		if (persoon == null) { // Voorkom NullpointerExceptions
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

	/*
	 * Wat als: o this.getContactpersonen() null is > nooit null, hoogstens leeg
	 * o persoon geen contactpersoon rol heeft > is al afgevangen in de add
	 * methode en komt die dus ook niet voor in de collectie
	 */
	public boolean removeContactpersoon(Persoon persoon) {
		if (persoon == null) { // Voorkom NullpointerExceptions
			return false;
		}
		this.getContactpersonen().remove(persoon);
		persoon.getContactpersoon().removeOrganisatie(this, persoon);
		return !this.heeftContactpersoon(persoon)
				&& !persoon.getContactpersoon().heeftOrganisatie(this);
	}

	public boolean heeftContactpersoon(Persoon persoon) {
		return this.getContactpersonen().contains(persoon);
	}

	public boolean contactpersoonConstraint(Persoon persoon) {
		return persoon != null && persoon.hasRol(Contactpersoon.class);
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
		return "Organisatie(id=" + this.getId() + ", hash=" + this.hashCode()
				+ ") " + this.getNaam();
	}

}
