package nl.rsvier.icaras.form.relatiebeheer;

import java.util.Calendar;

import javax.validation.constraints.Pattern;

import nl.rsvier.icaras.core.relatiebeheer.Persoon;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

public class PersoonForm {

	private int id;

	@Length(min = 2, max = 100, message = "Vul uw voornaam in")
	private String voornaam;

	@Pattern(regexp = "[a-zA-Z'-[\\s]]{0,40}", message = "Alleen letters, spaties, ''' of - zijn toegestaan")
	private String tussenvoegsels = "";

	@Length(min = 2, max = 100, message = "Vul uw achternaam in")
	private String achternaam;

	// @Pattern(regexp = "\\d{2}[-/]{0,1}\\d{2}[-/]{0,1}\\d{4}"), message =
	// "Geef een datum op (dd-mm-jjjj)")
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
