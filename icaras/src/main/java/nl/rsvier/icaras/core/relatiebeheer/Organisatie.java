package nl.rsvier.icaras.core.relatiebeheer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

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

	/*
	 * Constructoren
	 */

	public Organisatie(String s) {
		this();
		this.setNaam(s);
	}

	public Organisatie() {
		/*
		 * TODO: Make sure each instance of Organisatie has at least one type of
		 * OrganisatieRol assigned to it? [1..2] vs [0..2]?
		 */
		this.rollen = new HashSet<OrganisatieRol>();
	}

	/*
	 * OrganisatieRollen beheer
	 */

	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
	public Set<OrganisatieRol> getRollen() {
		return this.rollen;
	}

	public void setRollen(Set<OrganisatieRol> rollenlijst) {
		this.rollen = rollenlijst;
	}

	/*
	 * Controleer of de collectie daadwerkelijk een rol bevat van het type
	 * OrganisatieRol
	 * 
	 * @param: class literal van het gewenste type OrganisatieRol
	 * 
	 * @return: true wanneer de collectie een OrganisatieRol van het juiste type
	 * bevat
	 */
	public <T extends OrganisatieRol> boolean hasRol(Class<T> clstype) {
		for (OrganisatieRol rol : this.getRollen()) {
			if (clstype.isInstance(rol)) {
				return true;
			}
		}
		return false;
	}
	
	@Transient
	public Bedrijf getBedrijf() {
		return ((Bedrijf) this.getRol(Bedrijf.class));
	}
	
	@Transient
	public Leverancier getLeverancier() {
		return ((Leverancier) this.getRol(Leverancier.class));
	}
	

	/*
	 * Voeg een OrganisatieRol toe aan de collectie. Er kan slechts 1 rol van
	 * elke subklasse van OrganisatieRol worden toegevoegd
	 */
	public boolean addRol(OrganisatieRol rol) {
		if (!this.hasRol(rol.getClass())) {
			return this.getRollen().add(rol);
		}
		return false;
	}

	/*
	 * Retrieves the first element of List<OrganisatieRol> that matches the type
	 * we're requesting. Considering we're using a Collection type that doesn't
	 * allow duplicates that should be enough.
	 * 
	 * OR
	 * 
	 * voorlopig even door de hele collectie loopen om een extra check te doen.
	 * het zou niet moeten mogen voorkomen dat een organisatie meerdere rollen
	 * van hetzelfde type bevat
	 */

	public <T extends OrganisatieRol> OrganisatieRol getRol(Class<T> clstype) {
		int counter = 0;
		OrganisatieRol tmp = null;
		for (OrganisatieRol rol : this.getRollen()) {
			if (clstype.isInstance(rol)) {
				tmp = rol;
				if (counter++ > 1) {
					throw new RuntimeException(
							"Meerdere OrganisatieRollen van type: "
									+ rol.getClass().getName()
									+ " gevonden binnen de rollen collectie van Organisatie. Dit zou niet mogen voorkomen!");
				}
			}
		}
		return tmp;
	}

	/*
	 * Naam: Maak een naam uniek en onaanpasbaar zodat deze te gebruiken is als
	 * business logic key
	 */

	@Column(unique = true, updatable = false)
	@NotNull
	public String getNaam() {
		return this.naam; // TODO: robuust maken. ensure naam is unique
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	/*
	 * Util
	 */

	@Override
	public int hashCode() {
		final int prime = 67;
		int hash = 1;
		hash = prime * hash
				+ ((this.getNaam() == null) ? 0 : this.getNaam().hashCode());
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
			if (!this.getNaam().equals(other.getNaam())) {//TODO nullpointer als naam null is
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
