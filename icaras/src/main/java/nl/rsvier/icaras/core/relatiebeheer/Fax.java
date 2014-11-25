package nl.rsvier.icaras.core.relatiebeheer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
@Entity
@DiscriminatorValue("Fax")
public class Fax extends Nfa {

	private static final long serialVersionUID = 1L;

	public Fax() {
		super(NfaSoort.FAX);
	}

}
