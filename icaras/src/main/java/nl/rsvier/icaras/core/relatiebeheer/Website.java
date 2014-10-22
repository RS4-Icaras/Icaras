package nl.rsvier.icaras.core.relatiebeheer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Website")
public class Website extends Nfa {

	private static final long serialVersionUID = 1L;

	public Website() {
		super(NfaSoort.WEBSITE);
	}

}
