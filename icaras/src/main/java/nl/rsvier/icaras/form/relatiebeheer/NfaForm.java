package nl.rsvier.icaras.form.relatiebeheer;

import nl.rsvier.icaras.core.relatiebeheer.Nfa;
import nl.rsvier.icaras.core.relatiebeheer.Nfa.NfaSoort;

import org.hibernate.validator.constraints.NotBlank;

public class NfaForm {

	private int relatieId;
	private int nfaId;

	private NfaSoort[] nfaSoorten;
	
	private Nfa.NfaSoort nfaSoort;

	@NotBlank
	private String nfaAdres;

	private String extraInfo;

	public NfaForm() {
		this.setNfaSoorten(Nfa.NfaSoort.values());
	}

	public NfaForm(int relatieId) {
		this();
		this.setRelatieId(relatieId);
	}

	public int getRelatieId() {
		return relatieId;
	}

	public void setRelatieId(int relatieId) {
		this.relatieId = relatieId;
	}

	public int getNfaId() {
		return nfaId;
	}

	public void setNfaId(int nfaId) {
		this.nfaId = nfaId;
	}

	public NfaSoort[] getNfaSoorten() {
		return nfaSoorten;
	}

	public void setNfaSoorten(NfaSoort[] nfaSoorten) {
		this.nfaSoorten = nfaSoorten;
	}

	public Nfa.NfaSoort getNfaSoort() {
		return nfaSoort;
	}

	public void setNfaSoort(Nfa.NfaSoort nfaSoort) {
		this.nfaSoort = nfaSoort;
	}

	public String getNfaAdres() {
		return nfaAdres;
	}

	public void setNfaAdres(String nfaAdres) {
		this.nfaAdres = nfaAdres;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
	
}
