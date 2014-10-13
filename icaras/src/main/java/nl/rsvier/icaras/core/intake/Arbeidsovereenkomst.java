package nl.rsvier.icaras.core.intake;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


/**
 * 
 * @author Koen, Jelle
 *
 */
@Entity
@Table (name= "arbeidsovereenkomst")
@PrimaryKeyJoinColumn (name= "ID")
public class Arbeidsovereenkomst extends Overeenkomst implements Serializable{
	private static final long serialVersionUID = 1L;

	private boolean isGetekend = false;
	private boolean isStandaardOvereenkomst = false;
	private TMPBedrijf werkgever;
	private TMPPersoon werknemer;
	public Date startDatum;
	public Date eindDatum;
	public TMPPersoon ondergetekende;
	

	/**
	 * @return the isGetekend
	 */
	@Column(name="getekend")
	public boolean isGetekend() {
		return isGetekend;
	}
	/**
	 * @param isGetekend the isGetekend to set
	 */
	public void setGetekend(boolean isGetekend) {
		this.isGetekend = isGetekend;
	}
	/**
	 * @return the isStandaardOvereenkomst
	 */
	@Column(name="standaardovereenkomst")
	public boolean isStandaardOvereenkomst() {
		return isStandaardOvereenkomst;
	}
	/**
	 * @param isStandaardOvereenkomst the isStandaardOvereenkomst to set
	 */
	public void setStandaardOvereenkomst(boolean isStandaardOvereenkomst) {
		this.isStandaardOvereenkomst = isStandaardOvereenkomst;
	}
	/**
	 * @return the werkgever
	 */
	//TODO implementeren voor hibernate als er een bedrijfsklasse is.
	//@OneToOne
	@Column(name="werkgever")
	public TMPBedrijf getWerkgever() {
		return werkgever;
	}
	/**
	 * @param werkgever the werkgever to set
	 */
	public void setWerkgever(TMPBedrijf werkgever) {
		this.werkgever = werkgever;
	}
	/**
	 * @return the werknemer
	 */
	//TODO implementeren voor hibernate als er een persoonsklasse is.
	//@OneToOne
	@Column(name="werknemer")
	public TMPPersoon getWerknemer() {
		return werknemer;
	}
	/**
	 * @param werknemer the werknemer to set
	 */
	public void setWerknemer(TMPPersoon werknemer) {
		this.werknemer = werknemer;
	}
	/**
	 * @return the startDatum
	 */
	@Column(name="startdatum")
	public Date getStartDatum() {
		return startDatum;
	}
	/**
	 * @param startDatum the startDatum to set
	 */
	public void setStartDatum(Date startDatum) {
		this.startDatum = startDatum;
	}
	/**
	 * @return the eindDatum
	 */
	@Column(name="einddatum")
	public Date getEindDatum() {
		return eindDatum;
	}
	/**
	 * @param eindDatum the eindDatum to set
	 */
	public void setEindDatum(Date eindDatum) {
		this.eindDatum = eindDatum;
	}
	/**
	 * @return the ondergetekende
	 */
	//TODO implementeren voor hibernate als er een correcte persoonsklasse is.
	//@OneToOne
	@Column(name="ondergetekende")
	public TMPPersoon getOndergetekende() {
		return ondergetekende;
	}
	/**
	 * @param ondergetekende the ondergetekende to set
	 */
	public void setOndergetekende(TMPPersoon ondergetekende) {
		this.ondergetekende = ondergetekende;
	}
	
	
	
	
}
