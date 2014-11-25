package nl.rsvier.icaras.form.relatiebeheer;

import org.hibernate.validator.constraints.NotBlank;

import nl.rsvier.icaras.core.relatiebeheer.Organisatie;

public class OrganisatieForm {

	private int id;
	
	@NotBlank
	private String naam;

	public OrganisatieForm() {

	}

	public OrganisatieForm(Organisatie organisatie) {
		this.setId(organisatie.getId());
		this.setNaam(organisatie.getNaam());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaam() {
		return this.naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

}
