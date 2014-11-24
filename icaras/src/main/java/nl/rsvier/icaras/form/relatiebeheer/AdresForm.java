package nl.rsvier.icaras.form.relatiebeheer;

import org.hibernate.validator.constraints.NotBlank;

public class AdresForm {

	private int relatie_id = 0;
	private int id = 0;

	private boolean postbus = false;
	private boolean correspondentieAdres = false;

	// Maak een custom annotation die @NotEmpty is op voorwaarde dat postbus
	// true is, zoals: @StraatNotBlank?
	private String straat = "";
	@NotBlank(message="hoi")
	private String huisOfPostbusNummer = "";
	@NotBlank
	private String postcode = "";
	@NotBlank
	private String plaats = "";

	// Dan kan deze methode weg, en kunnen we Bindingresult gaan gebruiken in
	// onze controller
	public boolean isValid() {
		if (!this.isAllesIngevuld()) {
			return false;
		}
		return true;
	}

	// Dan kan deze methode weg, en kunnen we Bindingresult gaan gebruiken in
	// onze controller
	public boolean isAllesIngevuld() {
		if (this.getHuisOfPostbusNummer().equals("")
				|| this.getPostcode().equals("") || this.getPlaats().equals("")
				|| isStraatZonderStraat()) {
			return false;
		}
		return true;
	}

	public boolean isStraatZonderStraat() {
		if (!this.isPostbus()
				&& (this.getStraat().equals("nvt") || this.getStraat().equals(
						""))) {
			return true;
		}
		return false;
	}

	public int getRelatie_id() {
		return relatie_id;
	}

	public void setRelatie_id(int relatie_id) {
		this.relatie_id = relatie_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getPostbus() {
		return postbus;
	}

	public boolean isPostbus() {
		return getPostbus();
	}

	public void setPostbus(boolean postbus) {
		this.postbus = postbus;
	}

	public boolean getCorrespondentieAdres() {
		return correspondentieAdres;
	}

	public boolean isCorrespondentieAdres() {
		return getCorrespondentieAdres();
	}

	public void setCorrespondentieAdres(boolean isCorrespondentieAdres) {
		this.correspondentieAdres = isCorrespondentieAdres;
	}

	public String getStraat() {
		return straat;
	}

	public void setStraat(String straat) {
		this.straat = straat;
	}

	public String getHuisOfPostbusNummer() {
		return huisOfPostbusNummer;
	}

	public void setHuisOfPostbusNummer(String huisOfPostbusNummer) {
		this.huisOfPostbusNummer = huisOfPostbusNummer;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPlaats() {
		return plaats;
	}

	public void setPlaats(String plaats) {
		this.plaats = plaats;
	}

}
