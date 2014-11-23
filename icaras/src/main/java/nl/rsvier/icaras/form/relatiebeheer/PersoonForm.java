package nl.rsvier.icaras.form.relatiebeheer;

import java.util.Date;

public class PersoonForm {

	private int id;
	private String voornaam;
	private String tussenvoegsels;
	private String achternaam;
	private Date geboortedatum;

	public PersoonForm(int id, String voornaam, String tussenvoegsels,
			String achternaam, Date geboortedatum) {
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

	public Date getGeboortedatum() {
		return geboortedatum;
	}

	public void setGeboortedatum(Date geboortedatum) {
		this.geboortedatum = geboortedatum;
	}

}
