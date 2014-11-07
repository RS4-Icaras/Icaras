package nl.rsvier.icaras.core.arbeidsmarkt;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

import nl.rsvier.icaras.core.relatiebeheer.Kandidaat;
import nl.rsvier.icaras.core.relatiebeheer.Persoon;


/**
 * Is verantwoordelijk voor het automatisch genereren van een (kandidaats-)CV.
 */
@Embeddable
public class CvGenerator implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private Persoon persoon;

	@OneToOne
	//@JoinColumn(name = "persoon_relatieId")
	public Persoon getPersoon() {
		return persoon;
	}
		
	@SuppressWarnings("unused")//Methode is enkel voor Hibernate
	private void setPersoon(Persoon persoon) {
		this.persoon = persoon;
	}
	/**
	 * Stelt in welke persoon bij deze CVGenerator hoort. 
	 * Accepteert alleen een persoon die zowel niet null is als een kandidaatrol heeft.
	 * Als deze CVGenerator hetzelfde object is als de 
	 * CVGenerator van de kandidaatrol, 
	 * dan wordt de persoon toegevoegd.
	 * 
	 * @param persoon
	 */
	public void setPersoonReference(Persoon persoon) {
		  if (persoon != null){
			  Kandidaat k = null;//hulp variabele
		   //kijk of de meegegeven persoonsinstantie een kandidaatrol heeft
						  k = persoon.getKandidaat();
			   //als er een kandidaatrol was en het huidige object is gelijk aan diens CvGenerator
				  if (k != null && k.getCvGenerator()==this){//controle op this werkt niet samen met Hibernate
			   //ken dan deze persoon toe
			        	 this.persoon = persoon;
				  }
		  }     //als aan bovenstaande niet voldaan is, doe niets
	}	
}
