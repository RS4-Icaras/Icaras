package nl.rsvier.icaras.core.relatiebeheer;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import nl.rsvier.icaras.core.IEntity;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Relatie implements IEntity {

	private static final long serialVersionUID = 1L;
	
	
	private int relatieId;
	private boolean gearchiveerd = false;
	private String opmerking;
	private boolean priveRelatie = false;
	private Set<Nfa> nfaLijst = new HashSet<Nfa>();
	private Set<Adres> adressen = new HashSet<Adres>();
	/**
	 * @return the relatieId
	 */
	@Id
	@GeneratedValue
	public int getRelatieId() {
		return relatieId;
	}
	/**
	 * @param relatieId the relatieId to set
	 */
	public void setRelatieId(int relatieId) {
		this.relatieId = relatieId;
	}
	public boolean isGearchiveerd() {//moeten de rollen van de te archiveren persoon ook automatisch gearchiveerd worden?
		return gearchiveerd;
	}
	public void setGearchiveerd(boolean gearchiveerd) {
		this.gearchiveerd = gearchiveerd;
	}
	public String getOpmerking() {
		return opmerking;
	}
	public void setOpmerking(String opmerking) {
		this.opmerking = opmerking;
	}
	public boolean isPriveRelatie() {
		return priveRelatie;
	}
	public void setPriveRelatie(boolean priveRelatie) {
		this.priveRelatie = priveRelatie;
	}
	/**
	 * @return the nfaLijst
	 */
	@OneToMany(orphanRemoval=true)//(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public Set<Nfa> getNfaLijst() {
		return nfaLijst;
	}
	/**
	 * @param nfaLijst the nfaLijst to set
	 */
	public void setNfaLijst(Set<Nfa> nfaLijst) {
		this.nfaLijst = nfaLijst;
	}
	/**
	 * @return the adressen
	 */
	@OneToMany(orphanRemoval=true)//(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public Set<Adres> getAdressen() {
		return adressen;
	}
	/**
	 * @param adressen the adressen to set
	 */
	public void setAdressen(Set<Adres> adressen) {
		this.adressen = adressen;
	}
	
	
	
	

}
