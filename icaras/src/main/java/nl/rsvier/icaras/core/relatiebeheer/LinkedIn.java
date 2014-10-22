package nl.rsvier.icaras.core.relatiebeheer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("LinkedIn")
public class LinkedIn extends SocialMediaAccount {

	private static final long serialVersionUID = 1L;

	public LinkedIn() {
		super(NfaSoort.LINKEDIN);
	}

	@Override
	public void post(String message) {
		// TODO Auto-generated method stub
		
	}

}
