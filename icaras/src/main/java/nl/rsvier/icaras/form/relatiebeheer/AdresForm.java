package nl.rsvier.icaras.form.relatiebeheer;

import javax.validation.constraints.Pattern;

public class AdresForm {

	/*
	 * Attributen
	 */

	private int relatieId = 0;

	private int adresId = 0;

	private boolean postbus = false;

	private boolean correspondentieAdres = false;

	@Pattern(regexp = "[a-zA-Z'-[\\s]]{2,100}", message = "Geef de straatnaam op")
	private String straat;

	@Pattern(regexp = "[0-9]{1}.{0,24}", message = "Geef het huisnummer, gevolgd door eventuele toevoeging")
	private String huisnummer;

	@Pattern(regexp = "[0-9]{4}[a-zA-Z]{2}", message = " Vul 4 cijfers gevolgd door 2 letters in (1234AB)")
	private String postcode;

	@Pattern(regexp = "[a-zA-Z'-[\\s]]{2,100}", message = "Geef een geldige plaatsnaam")
	private String plaats;

	/*
	 * Constructoren
	 */

	public AdresForm() {

	}

	public AdresForm(int relatieId) {
		this.setRelatieId(relatieId);
	}

	/*
	 * Getters & Setters
	 */

	public int getRelatieId() {
		return relatieId;
	}

	public void setRelatieId(int relatieId) {
		this.relatieId = relatieId;
	}

	public int getAdresId() {
		return adresId;
	}

	public void setAdresId(int adresId) {
		this.adresId = adresId;
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

	/*
	 * Huisnummer is een String zodat er ook toevoegingen in kunnen worden
	 * meegenomen. Dit betekend dat het lastig is te valideren of het een getal
	 * is, en zelfs als je het uit elkaar haalt en een int voor het getal maakt
	 * in combinate met een String voor toevoegingen word het nog steeds als een
	 * string weggeschreven naar de database. Dat betekend dat ik met een
	 * reguliere expressie de string weer zou moeten ontleden om er een getal en
	 * string van te maken.
	 */
	public String getHuisnummer() {
		return huisnummer;
	}

	public void setHuisnummer(String huisnummer) {
		this.huisnummer = huisnummer;
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
