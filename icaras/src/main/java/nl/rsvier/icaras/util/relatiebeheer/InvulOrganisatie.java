package nl.rsvier.icaras.util.relatiebeheer;

import javax.persistence.Entity;

@Entity
public class InvulOrganisatie {

	private String naam;

	public InvulOrganisatie() {

	}

	/*
	 * Naam: Maak een naam uniek en onaanpasbaar zodat deze te gebruiken is als
	 * business logic key
	 */
	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

}
