package nl.rsvier.icaras.form.relatiebeheer;

import javax.validation.constraints.Pattern;

public class PostbusForm {

	/*
	 * Attributen
	 */

	private int relatieId = 0;

	private int postbusId = 0;

	private boolean correspondentieAdres = false;

	@Pattern(regexp = "[\\d]{1,5}", message = "Een postbusnummer bestaat uit minimaal één en maximaal vijf cijfers")
	private String postbusnummer = "";

	@Pattern(regexp = "[0-9]{4}[a-zA-Z]{2}", message = " Vul 4 cijfers gevolgd door 2 letters in (1234AB)")
	private String postcode = "";

	@Pattern(regexp = "[a-zA-Z'-[\\s]]{2,100}", message = "Geef een geldige plaatsnaam")
	private String plaats = "";

	/*
	 * Constructoren
	 */

	public PostbusForm() {

	}

	public PostbusForm(int relatieId) {
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

	public int getPostbusId() {
		return postbusId;
	}

	public void setPostbusId(int postbusId) {
		this.postbusId = postbusId;
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

	public String getPostbusnummer() {
		return postbusnummer;
	}

	public void setPostbusnummer(String postbusnummer) {
		this.postbusnummer = postbusnummer;
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
