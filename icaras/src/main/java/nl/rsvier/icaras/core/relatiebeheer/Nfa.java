package nl.rsvier.icaras.core.relatiebeheer;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

import nl.rsvier.icaras.core.IEntity;

/**
 * 
 * Klasse voor alle niet fysieke adresvormen (waaronder telefoonnummers en
 * digitale communicatiekanalen)
 * 
 * @author Gerben
 * @author Gordon
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator", discriminatorType = DiscriminatorType.STRING)
public abstract class Nfa implements IEntity {

	private static final long serialVersionUID = 1L;

	public enum NfaSoort {
		EMAIL, TELEFOONNUMMER, WEBSITE, FACEBOOK, TWITTER, LINKEDIN, FAX
	}

	@Id
	@GeneratedValue
	private int id;
	@Enumerated(EnumType.STRING)
	// Constraint voor de column nfaSoort met alle waarden vd Enum toevoegen via
	// @Column nog toevoegen
	private final NfaSoort nfaSoort;
	private String nfaAdres;
	private String extraInfo;

	public Nfa(NfaSoort nfaSoort) {
		this.nfaSoort = nfaSoort;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public NfaSoort getNfaSoort() {
		return nfaSoort;
	}

	// public void setNfaSoort(NfaSoort nfaSoort) {
	// this.nfaSoort = nfaSoort;
	// }
	@NotNull
	public String getNfaAdres() {
		return nfaAdres;
	}

	public void setNfaAdres(String nfaAdres) {
		this.nfaAdres = nfaAdres;
	}

	//@NotNull
	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	public boolean equals(Object obj) {
		boolean isEqual = false;
		if (obj instanceof Nfa && this.nfaSoort == ((Nfa) obj).getNfaSoort()
				&& this.id == ((Nfa) obj).getId()
				&& this.nfaAdres.equals(((Nfa) obj).getNfaAdres())
				&& this.extraInfo.equals(((Nfa) obj).getExtraInfo())) {
			isEqual = true;
		}
		return isEqual;
	}

}
