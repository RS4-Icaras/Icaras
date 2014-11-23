package nl.rsvier.icaras.core.relatiebeheer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import nl.rsvier.icaras.core.IEntity;

/**
 * 
 * Klasse voor fysieke adressen. Dit kan een postbus of een adres met een
 * straatnaam zijn. Standaard is adres met straatnaam. Gebruik de hulpmethode
 * maakPostbus voor een postbus en maakStraat om dit weer ongedaan te maken. Een
 * adres kan een correspondentieadres zijn. Dit kan slechts voor 1 adres gelden
 * in de adressenlijst van een Relatie. De meest recente toewijzing als
 * correspondentieadres overschrijft eventuele eerdere toewijzingen.
 *
 * @author Gerben
 * @author Gordon
 */
@Entity
public class Adres implements IEntity {

	private static final long serialVersionUID = 1L;

	private int id;
	private boolean isCorrespondentieAdres;
	private boolean isPostbus;

	private String straat;
	private String huisOfPostbusNummer;
	private String postcode;
	private String plaats;
	@Transient
	private String straatVoorPostbus;

	public Adres() {
		setIsPostbus(false);
		setIsCorrespondentieAdres(false);
	}

	public Adres(boolean isCorrespondentieAdres, boolean isPostbus,
			String postcode, String huisOfPostbusNummer, String plaats,
			String straat) {

		if (isPostbus) {
			maakPostbus();
		} else {
			setIsPostbus(false);
			setStraat(straat);
		}
		setIsCorrespondentieAdres(isCorrespondentieAdres);
		setPostcode(postcode);
		setHuisOfPostbusNummer(huisOfPostbusNummer);
		setPlaats(plaats);
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	@SuppressWarnings("unused")
	private void setId(int id) {
		this.id = id;
	}

	public boolean getIsCorrespondentieAdres() {
		return isCorrespondentieAdres;
	}

	/*
	 * Reflection requires a getter, even though standard naming convention for
	 * boolean getters is: "isBoolean()", provide an "isCorrespondentieAdres()"
	 * method for convenience
	 */
	@Transient
	public boolean isCorrespondentieAdres() {
		return this.getIsCorrespondentieAdres();
	}

	private void setIsCorrespondentieAdres(boolean isCorrespondentieAdres) {
		this.isCorrespondentieAdres = isCorrespondentieAdres;
	}

	/**
	 * Maakt van dit adres een correspondentieadres mits de opgegeven relatie
	 * eigenaar is van deze adresinstantie. Aanroep kan dus pas na het toevoegen
	 * van een adres.
	 * 
	 * Zorgt er tevens voor dat dit adres het enige correspondentieadres in de
	 * lijst van de betreffende relatie is.
	 * 
	 * @param relatie
	 *            De relatie moet eigenaar zijn van de adresinstantie waarop
	 *            deze methode wordt aangeroepen, anders doet de methode niets.
	 * @return True als de juiste persoon is opgegeven, of het adres al
	 *         correspondentieadres was, anders false.
	 */
	public boolean maakCorrespondentieAdres(Relatie relatie) {
		if (isCorrespondentieAdres) {
			return true;
		}
		if (relatie.heeftAdres(this)) {
			setIsCorrespondentieAdres(true);
			// mogelijk nu 2 correspondentieadressen
			// verwijder de andere als dit het geval is
			for (Adres r : relatie.getAdressen()) {
				if (r.isCorrespondentieAdres && !r.equals(this)) {
					r.geenCorrespondentieAdres(relatie);
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * LET OP: Dit is slechts een hulpmethode die nooit zal slagen indien deze
	 * zelf wordt aangeroepen! Gebruik de Methode maakCorrespondentieAdres om
	 * indirect alle andere adressen van de betreffende relatie tot
	 * niet-correspondentieadres te maken.
	 * 
	 * LET OP: Werkt enkel als de methode addAdres tijdelijk een 2e
	 * correspondentieadres toevoegt alvorens deze methode aan te roepen en is
	 * dus enkel voor die methode bedoeld. Moet echter public zijn vanwege het
	 * zich in een andere klasse (Relatie) bevinden van die methode.
	 * 
	 * @param relatie
	 *            De relatie moet eigenaar zijn van de adresinstantie waarop
	 *            deze methode wordt aangeroepen, anders doet de methode niets.
	 * @return True als de juiste persoon is opgegeven, of het adres al geen
	 *         correspondentieadres was, anders false.
	 */
	public boolean geenCorrespondentieAdres(Relatie relatie) {
		if (!isCorrespondentieAdres) {
			return true;
		}
		if (relatie.heeftAdres(this)) {
			for (Adres r : relatie.getAdressen()) {
				if (r.isCorrespondentieAdres && !r.equals(this)) {
					setIsCorrespondentieAdres(false);
					return true;
				}
			}
		}
		return false;
	}

	public Boolean getIsPostbus() {
		return isPostbus;
	}

	/*
	 * Reflection requires a getter, even though standard naming convention for
	 * boolean getters is: "isBoolean()", provide an "isPostbus()" method for
	 * convenience
	 */
	@Transient
	public Boolean isPostbus() {
		return this.getIsPostbus();
	}

	private void setIsPostbus(Boolean isPostbus) {// private ter verplichting
													// van maakStraat() en
													// maakPostbus()
		this.isPostbus = isPostbus;
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

	/**
	 * Hulpmethode om instantie tot Postbus te maken De straatnaam krijgt dan de
	 * waarde "nvt" De boolean isPostbus krijgt de waarde true
	 */
	public void maakPostbus() {
		setIsPostbus(true);
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
			setIsPostbus(false);
			if (straatVoorPostbus == null) {
				straatVoorPostbus = "";
			}
			straat = straatVoorPostbus;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 67;
		int hash = 1;
		hash = hash + this.getHuisOfPostbusNummer().hashCode();
		hash = hash + this.getPlaats().hashCode();
		hash = hash + this.getPostcode().hashCode();
		hash = hash + this.getStraat().hashCode();
		hash = hash + this.getIsPostbus().hashCode();
		hash = prime * hash;// + this.getId();
		return hash;
	}

	public boolean equals(Object obj) {
		boolean isEqual = false;
		if (obj instanceof Adres
				&& this.id == ((Adres) obj).getId()
				&& this.isPostbus == ((Adres) obj).getIsPostbus()
				&& this.straat.equals(((Adres) obj).getStraat())
				&& this.huisOfPostbusNummer.equals(((Adres) obj)
						.getHuisOfPostbusNummer())
				&& this.postcode.equals(((Adres) obj).getPostcode())
				&& this.plaats.equals(((Adres) obj).getPlaats())) {
			isEqual = true;
		}
		return isEqual;
	}

	public String toString() {
		if (isPostbus()) {
			return String.format("Postbus %s, %s %s",
					this.getHuisOfPostbusNummer(), this.getPostcode(),
					this.getPlaats());
		} else {
			return String.format("%s %s, %s %s", this.getStraat(),
					this.getHuisOfPostbusNummer(), this.getPostcode(),
					this.getPlaats());
		}
	}
}