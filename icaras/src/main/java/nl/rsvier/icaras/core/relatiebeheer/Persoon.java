package nl.rsvier.icaras.core.relatiebeheer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import nl.rsvier.icaras.core.IEntity;

// TODO: Auto-generated Javadoc

/**
 * Instanties van deze klasse representeren personen in de database. Heeft een
 * lijst van persoonsrollen met daarin de verschillende rollen die deze persoon
 * bekleedt (bijvoorbeeld kandidaat, cursist etc.) Erft ook een deel van de
 * persoonsgegevens van zijn superklasse relatie.
 * 
 * @Author: Leroy van den Hoogen
 * @Author: Thomas Slippens
 * @Author: Gordon Brouwer
 * 
 * @version: 1.0
 *
 */

@Entity
@PrimaryKeyJoinColumn(name = "relatie_Id")
public class Persoon extends Relatie implements IEntity {

	private static final long serialVersionUID = 1L;
	private String voornaam;
	private String achternaam;
	private String tussenvoegsels = "";
	private Calendar geboortedatum;
	private List<PersoonsRol> rollen;

	/*
	 * Constructoren
	 */

	public Persoon(String v, String t, String a) {
		this(v, a);
		this.setTussenvoegsels(t);
	}

	public Persoon(String v, String a) {
		this();
		this.setVoornaam(v);
		this.setAchternaam(a);
	}

	public Persoon() {
		this.rollen = new ArrayList<PersoonsRol>();
	}

	@Column(nullable = false)
	public String getVoornaam() {
		return voornaam;
	}

	public void setVoornaam(String voornaam) {
		this.voornaam = voornaam;
	}

	@Column(nullable = false)
	public String getAchternaam() {
		return achternaam;
	}

	public void setAchternaam(String achternaam) {
		this.achternaam = achternaam;
	}

	@Column(nullable = false)
	public String getTussenvoegsels() {
		return tussenvoegsels;
	}

	public void setTussenvoegsels(String tussenvoegsels) {
		this.tussenvoegsels = tussenvoegsels;
	}

	@Temporal(TemporalType.DATE)
	public Calendar getGeboortedatum() {
		return geboortedatum;
	}

	public void setGeboortedatum(Calendar geboortedatum) {
		this.geboortedatum = geboortedatum;
	}

	/*
	 * org.hibernate.annotations.CascadeType.SAVE_UPDATE Hibernate navigates the
	 * association when the Session is flushed and when an object is passed to
	 * save() or update(), and saves newly instantiated transient instances and
	 * persist changes to detached instances.
	 * 
	 * org.hibernate.annotations.CascadeType.DELETE Hibernate navigates the
	 * association and deletes associated persistent instances when an object is
	 * passed to delete() or remove().
	 */

	@OneToMany(cascade = CascadeType.ALL)
	// TODO moet nog @NotNull worden
	// @org.hibernate.annotations.Cascade({
	// org.hibernate.annotations.CascadeType.ALL,
	//
	// })
	private List<PersoonsRol> getRollen() {
		return rollen;
	}

	/**
	 * In het geval de lijst null is wordt niks toegekend.
	 *
	 * @param List
	 *            <PersoonsRol> rollenlijst
	 */
	@SuppressWarnings("unused")
	// methode nodig voor setten van rollen door Hibernate
	private void setRollen(List<PersoonsRol> rollenlijst) {
		if (rollenlijst != null) {
			this.rollen = rollenlijst;
		}
	}

	/**
	 * Voegt een persoonsrol toe.
	 * 
	 * Kijkt eerst of er al rollen zijn. Zoniet dan wordt een meegegeven rol
	 * meteen toegekend. Als deze rol een kandidaat is dan wordt de persoon van
	 * CVgenerator toegevoegd aan deze kandidaat.
	 * 
	 * Als er wel rollen zijn dan wordt geckeckt of deze specifieke rol al in de
	 * lijst voorkomt. Als dat zo is gebeurt er niets.
	 * 
	 * @param PersoonsRol
	 *            rol
	 */
	// TODO: exception toevoegen om zichtbaar te maken wanneer addRol mislukt?
	public synchronized boolean addRol(PersoonsRol rol) {
		boolean toegevoegd = false;
		// controleer of deze persoon dit type rol al heeft
		if (getRolByType(rol.getClass()) == null) {
			toegevoegd = this.rollen.add(rol);
			System.out.println("Rol geadd");
			if (rol instanceof Kandidaat) {
				((Kandidaat) rol).getCvGenerator().setPersoonReference(this);
				System.out
						.println("Rol geadd instance of kandidaat, Persoon van CVGen van deze kandidaat: "
								+ ((Kandidaat) rol).getCvGenerator()
										.getPersoon());
			}
			if (rol instanceof Werknemer) {
				System.out.println("Rol geadd instance of werknemer");

				// TODO Is een arbeidsovereenkomst vereist voor toevoegen
				// werknemerrol?
				// Indien vereist moet toegevoegd = this.rollen.add(rol); in if
				// statement; arbeidsovereenkomst heeft per definitie aanbieding
				// met zowel een persoon als een organisatie

			}
		}
		return toegevoegd;
	}

	/**
	 * Controleer of de collectie daadwerkelijk een rol bevat van het type
	 * PersoonsRol
	 * 
	 * @param: class literal van het gewenste type PersoonsRol
	 * 
	 * @return: true wanneer de collectie een PersoonsRol van het juiste type
	 *          bevat
	 */
	public <T extends PersoonsRol> boolean hasRol(Class<T> clstype) {
		for (PersoonsRol rol : this.getRollen()) {
			if (clstype.isInstance(rol)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Generieke hulpmethode voor de getMethoden per Rol
	 * 
	 * @param persoonsrol
	 *            Geef een subklasse van Persoonsrol waarop gezocht moet worden
	 * @return geeft de instantie van het opgegeven type terug Als dit type niet
	 *         in de lijst voorkomt, wordt null terug gegeven.
	 */
	private <T extends PersoonsRol> PersoonsRol getRolByType(
			Class<T> persoonsrolType) {
		PersoonsRol returnRol = null;
		if (rollen != null && !rollen.isEmpty()) {
			for (PersoonsRol r : rollen) {
				if (r.getClass().equals(persoonsrolType)) {
					returnRol = r;
				}
			}
		}
		return returnRol;
	}

	/**
	 * Vraag de aanmelderrol op van deze klasse
	 * 
	 * @return instantie van Aanmelder indien de persoon deze rol vervult,
	 *         anders null
	 */
	// @Transient
	// public Aanmelder getAanmelder() {
	// return (Aanmelder) getRolByType(Aanmelder.class);
	// }

	/**
	 * Vraag de contactpersoonrol op van deze klasse
	 * 
	 * @return instantie van Contactpersoon indien de persoon deze rol vervult,
	 *         anders null
	 */
	@Transient
	public Contactpersoon getContactpersoon() {
		return (Contactpersoon) getRolByType(Contactpersoon.class);
	}

	/**
	 * Vraag de cursistrol op van deze klasse
	 * 
	 * @return instantie van Cursist indien de persoon deze rol vervult, anders
	 *         null
	 */
	// @Transient
	// public Cursist getCursist() {
	// return (Cursist) getRolByType(Cursist.class);
	// }

	/**
	 * Vraag de kandidaat(rol) op van deze klasse
	 * 
	 * @return instantie van Kandidaat indien de persoon deze rol vervult,
	 *         anders null
	 */
	@Transient
	public Kandidaat getKandidaat() {
		return (Kandidaat) getRolByType(Kandidaat.class);
	}

	/**
	 * Vraag de kandidaatrol op van deze klasse
	 * 
	 * @return instantie van Kandidaat indien de persoon deze rol vervult,
	 *         anders null
	 */
	@Transient
	public Werknemer getWerknemer() {
		return (Werknemer) getRolByType(Werknemer.class);
	}

	/*
	 * Utils
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null || !(obj instanceof Persoon)) {
			return false;
		} else {
			Persoon other = (Persoon) obj;
			if (!this.getVoornaam().equals(other.getVoornaam())) {
				return false;
			}
			if (!this.getTussenvoegsels().equals(other.getTussenvoegsels())) {
				return false;
			}
			if (!this.getAchternaam().equals(other.getAchternaam())) {
				return false;
			}
			// Voorkom nullpointerexception, TODO: Mag Geboortedatum wel null
			// zijn?
			if (this.getGeboortedatum() != null
					&& !this.getGeboortedatum()
							.equals(other.getGeboortedatum())) {
				return false;
			}
			// TODO: Neem rollen mee in de vergelijking
		}
		return true;
	}

	@Transient
	public String getVolledigeNaam() {
		return this.getVoornaam() + " " + this.getTussenvoegsels()
				+ (this.getTussenvoegsels() != "" ? " " : "")
				+ this.getAchternaam();
	}

	@Override
	public String toString() {
		return "Persoon(id=" + this.getId() + ", hash=" + this.hashCode()
				+ "): " + this.getVolledigeNaam();
	}

	public String toVolledigeString() {
		return "Persoon(id=" + this.getId() + ", hash=" + this.hashCode()
				+ "): " + this.getVolledigeNaam() + ", bevat "
				+ this.getRollen().size();
	}

}