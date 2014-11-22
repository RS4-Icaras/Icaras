package nl.rsvier.icaras.form.relatiebeheer;

import javax.persistence.Entity;

@Entity
public class organisatieForm extends RelatieForm {

	private String naam;

	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

}
