package nl.rsvier.icaras.core.relatiebeheer;

import javax.persistence.Entity;

@Entity
public class Leverancier extends OrganisatieRol {

	private static final long serialVersionUID = 1L;

	private String functie = "";

	public String getFunctie() {
		return this.functie;
	}

	public void setFunctie(String functie) {
		this.functie = functie;
	}
	
	/*
	 * Utils
	 */

	@Override
	public int hashCode() {
		final int prime = 227;
		int hash = 1;
		hash = prime * hash + this.getFunctie().hashCode(); // TODO: Geen goede businesskey
		// MAG IK ALSTUBLIEFT EEN REFERENTIE NAAR ORGANISATIE?!
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !super.equals(obj) || !(obj instanceof Leverancier)) {
			return false;
		} else {
			Leverancier other = (Leverancier) obj;
			if(this.getFunctie() != other.getFunctie()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "Leveranciersrol, subklasse van: " + super.toString();
	}
	
}
