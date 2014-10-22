package nl.rsvier.icaras.core.relatiebeheer;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Twitter")
public class Twitter extends SocialMediaAccount {

	private static final long serialVersionUID = 1L;

	public Twitter() {
		super(NfaSoort.TWITTER);
	}

	@Override
	public void post(String message) {
		// TODO Auto-generated method stub
		
	}

}
