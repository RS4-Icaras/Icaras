package nl.rsvier.icaras.form.relatiebeheer;

import org.hibernate.validator.constraints.NotBlank;

public class PostbusForm {

	/*
	 * Attributen
	 */
	
	private int relatieId = 0;
	
	private int postbusId = 0;

	private boolean correspondentieAdres = true;

	@NotBlank()
	// Een postbusnummer bestaat uit minimaal één en maximaal vijf cijfers
	private String postbusnummer = "";
	
	@NotBlank
	private String postcode = "";
	
	@NotBlank
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
