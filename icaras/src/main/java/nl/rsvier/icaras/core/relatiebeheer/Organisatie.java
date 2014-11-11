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
		/*
		 * TODO: Make sure each instance of Organisatie has at least one type of
		 * OrganisatieRol assigned to it? [1..2] vs [0..2]?
		 */
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
	 * Controleer of de collectie daadwerkelijk een rol bevat van het type
	 * OrganisatieRol
	 * 
	 * @param: class literal van het gewenste type OrganisatieRol
	 * 
	 * @return: true wanneer de collectie een OrganisatieRol van het juiste type
	 *          bevat
	 */
	public <T extends OrganisatieRol> boolean hasRol(Class<T> clstype) {
		for (OrganisatieRol rol : this.getRollen()) {
			if (clstype.isInstance(rol)) {
				return true;
			}
		}
		return false;
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

	public static boolean naamConstraint(String str) {
		/*
		 * De voorwaarden waar een Naam aan moet voldoen
		 */
		return str != null && !str.equals("");
	}

	public boolean naamMagWordenToegevoegd(String str) {
		/*
		 * Om een Naam toe te voegen mag deze geen null zijn en moet deze aan de
		 * voorwaarde voldoen
		 */
		return !this.naamIsToegevoegd() && Organisatie.naamConstraint(str);
	}

	public boolean naamIsToegevoegd() {
		/*
		 * Naam is immutable. Als deze reeds is toegevoegd mag deze niet meer
		 * worder aangepast
		 */
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
		if (this.contactpersoonMagWordenToegevoegd(persoon)) {
			// Voeg Persoon toe aan de lijst van contactpersonen
			boolean a = this.getContactpersonen().add(persoon);
			// Vertel contactpersoon over deze organisatie
			boolean b = persoon.getContactpersoon().addOrganisatie(this,
					persoon);
			return a && b;
		}
		return false;
	}

	public boolean removeContactpersoon(Persoon persoon) {
		// als this.getContactpersonen() null is > nooit null, hoogstends leeg
		// als persoon null is > return false
		// als persoon geen contactpersoon rol heeft > is al afgevangen in de
		// add methode
		return this.getContactpersonen().remove(persoon);
	}

	public boolean contactpersoonIsToegevoegd(Persoon persoon) {
		return this.getContactpersonen().contains(persoon);
	}

	public boolean contactpersoonConstraint(Persoon persoon) {
		/*
		 * De voorwaarden waar een Contactpersoon aan moet voldoen om te worden
		 * toegevoegd
		 */
		return persoon != null && !this.contactpersoonIsToegevoegd(persoon)
				&& persoon.hasRol(Contactpersoon.class);
	}

	public boolean contactpersoonMagWordenToegevoegd(Persoon persoon) {
		return !this.contactpersoonIsToegevoegd(persoon)
				&& this.contactpersoonConstraint(persoon);
	}

	/*
	 * Util
	 */

	@Override
	public int hashCode() {
		final int prime = 67;
		int hash = 1;
		hash = prime * hash + this.getNaam().hashCode(); // TODO: We need a
															// better business
															// key than this
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
