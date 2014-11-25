package nl.rsvier.icaras.form.relatiebeheer;

import java.util.Calendar;

import nl.rsvier.icaras.core.relatiebeheer.Persoon;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

public class PersoonForm {

	private int id;
	
	@NotBlank
	private String voornaam;
	
	private String tussenvoegsels = "";
	
	@NotBlank
	private String achternaam;

	@DateTimeFormat(pattern = "dd-mm-yyyy")
	private Calendar geboortedatum;

	public PersoonForm() {

	}

	public PersoonForm(Persoon persoon) {
		this.setId(persoon.getId());
		this.setVoornaam(persoon.getVoornaam());
		this.setTussenvoegsels(persoon.getTussenvoegsels());
		this.setAchternaam(persoon.getAchternaam());
		this.setGeboortedatum(persoon.getGeboortedatum());
	}

	public PersoonForm(int id, String voornaam, String tussenvoegsels,
			String achternaam, Calendar geboortedatum) {
		this.setId(id);
		this.setVoornaam(voornaam);
		this.setTussenvoegsels(tussenvoegsels);
		this.setAchternaam(achternaam);
		this.setGeboortedatum(geboortedatum);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	public String getTussenvoegsels() {
		return tussenvoegsels;
	}

	public void setTussenvoegsels(String tussenvoegsels) {
		this.tussenvoegsels = tussenvoegsels;
	}

	public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	public Calendar getGeboortedatum() {
		return geboortedatum;
	}

	public void setGeboortedatum(Calendar geboortedatum) {
		this.geboortedatum = geboortedatum;
	}

}
