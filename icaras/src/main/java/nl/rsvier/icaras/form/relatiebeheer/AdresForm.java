package nl.rsvier.icaras.form.relatiebeheer;

public class AdresForm {

	private int id;
	
	private boolean postbus;
	private boolean correspondentieAdres;
	
	private String straat;
	private String huisOfPostbusNummer;
	private String postcode;
	private String plaats;
	
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
