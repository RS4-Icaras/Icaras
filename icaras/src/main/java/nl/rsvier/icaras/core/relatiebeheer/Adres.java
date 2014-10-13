package nl.rsvier.icaras.core.relatiebeheer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 
 * @author Gerben en Gordon 
 * Klasse voor fysieke adressen 
 * Dit kan een postbus of een adres met een straatnaam zijn.
 * Gebruik de hulpmethode maakPostbus voor een postbus en maakStraat om dit weer ongedaan te maken.
 *
 */
@Entity
public class Adres {

	private int adresId;
	private Boolean isPostbus;
	private String straat;
	private String huisOfPostbusNummer;
	private String postcode;
	private String plaats;
	@Transient
	private String straatVoorPostbus;

	/**
	 * @return the adresId
	 */
	@Id
	@GeneratedValue
	public int getAdresId() {
		return adresId;
	}

	/**
	 * @param adresId
	 *            the adresId to set
	 */
	public void setAdresId(int adresId) {
		this.adresId = adresId;
	}

	/**
	 * @return the isPostbus
	 */
	public Boolean getIsPostbus() {
		return isPostbus;
	}

	/**
	 * @param isPostbus
	 *            the isPostbus to set
	 */
	public void setIsPostbus(Boolean isPostbus) {
		this.isPostbus = isPostbus;
	}

	/**
	 * @return the straat
	 */
	public String getStraat() {
		return straat;
	}

	/**
	 * @param straat
	 *            the straat to set
	 */
	public void setStraat(String straat) {
		this.straat = straat;
	}

	/**
	 * @return the huisOfPostbusNummer
	 */
	public String getHuisOfPostbusNummer() {
		return huisOfPostbusNummer;
	}

	/**
	 * @param huisOfPostbusNummer
	 *            the huisOfPostbusNummer to set
	 */
	public void setHuisOfPostbusNummer(String huisOfPostbusNummer) {
		this.huisOfPostbusNummer = huisOfPostbusNummer;
	}

	/**
	 * @return the postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * @param postcode
	 *            the postcode to set
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * @return the plaats
	 */
	public String getPlaats() {
		return plaats;
	}

	/**
	 * @param plaats
	 *            the plaats to set
	 */
	public void setPlaats(String plaats) {
		this.plaats = plaats;
	}

	/**
	 * Hulpmethode om instantie tot Postbus te maken De straatnaam krijgt dan de
	 * waarde "nvt" De boolean isPostbus krijgt de waarde true
	 */
	public void maakPostbus() {
		isPostbus = true;
		straatVoorPostbus = straat;
		straat = "nvt";

	}

	/**
	 * Hulpmethode om instantie tot adres met een straatnaam te maken De
	 * straatnaam krijgt indien aanwezig de waarde van het transient attribuut
	 * straatVoorPostcode De boolean isPostbus krijgt de waarde false
	 */
	public void maakStraat() {

		if (isPostbus) {
			isPostbus = false;
			if (straatVoorPostbus == null) {
				straatVoorPostbus = "";
			}
			straat = straatVoorPostbus;
		}
	}
	
	public boolean equals(Object obj) {
		boolean isEqual = false;
		if (obj instanceof Adres
			&& this.adresId == ((Adres) obj).getAdresId()
			&& this.isPostbus == ((Adres) obj).getIsPostbus()
			&& this.straat.equals(((Adres) obj).getStraat())
			&& this.huisOfPostbusNummer.equals(((Adres) obj).getHuisOfPostbusNummer())
			&& this.postcode.equals(((Adres) obj).getPostcode())
			&& this.plaats.equals(((Adres) obj).getPlaats())) {
			isEqual = true;
		}
		return isEqual;	
	}

}

//if (!(obj instanceof Adres))
//		return false;
//	if (this.adresId != ((Adres) obj).getAdresId())
//		return false;
//	if (this.isPostbus != ((Adres) obj).getIsPostbus())
//			return false;
//	if (!this.straat.equals(((Adres) obj).getStraat()))
//		return false;	
//	if (!this.huisOfPostbusNummer.equals(((Adres) obj).getHuisOfPostbusNummer()))
//			return false;
//	if (!this.postcode.equals(((Adres) obj).getPostcode()))
//			return false;
//	if (!this.plaats.equals(((Adres) obj).getPlaats()))
//		return false;			
//
//return true;	