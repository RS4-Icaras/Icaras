package nl.rsvier.icaras.core.relatiebeheer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Leverancier extends OrganisatieRol {

	private static final long serialVersionUID = 1L;

	private String functie;

	// TODO: UUID ipv business key
	// TODO: @NotNull validator toevoegen?

	/*
	 * Constructor
	 */

	public Leverancier() {
		
	}

	/*
	 * Functie
	 */

	@Column(unique = true, updatable = false)
	//@NotNull
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
		if (obj == null || !super.equals(obj) || !(obj instanceof Leverancier)) {
			return false;
		} else {
			Leverancier other = (Leverancier) obj;
			if (!this.getFunctie().equals(other.getFunctie())) {
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
