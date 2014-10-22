package nl.rsvier.icaras.core.cursisttraject;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import nl.rsvier.icaras.core.intake.Expertise;


/**
 * @author Pieter
 * 
 * @date 1 okt. 2014
 */

@Entity
@Table (name="cursist")
public class Cursist implements Serializable {
	private static final long serialVersionUID = 5415078172055314155L;
	private long cursistId;
	//implements persoonRol  
	//private Persoon persoon;
    private boolean isKlaar;  
    //Via traject expertise ophalen
    private Traject traject; //ManyToOne
    private List<Toets> toetsenLijst;
    
    //Materiaal : Materiaal ophalen via traject -> trajecteenheid -> getMateriaal?
    private List<Materiaal> materiaalLijst; 
    private Set<Expertise> expertise = new HashSet<Expertise>();
    
    @Id @GeneratedValue
    @Column (name = "cursist_id")
    public long getCursistId() {
		return cursistId;
	}
    
	public void setCursistId(long cursistId) {
		this.cursistId = cursistId;
	}
	
	
	
	/**
	 * @return the expertise
	 */
	@ManyToMany(mappedBy="cursisten", cascade = CascadeType.ALL)
	public Set<Expertise> getExpertise() {
		return expertise;
	}

	/**
	 * @param expertise the expertise to set
	 */
	public void setExpertise(Set<Expertise> expertise) {
		this.expertise = expertise;
	}

	@OneToMany (mappedBy = "cursist")	
	public List<Toets> getToetsenLijst() {
		return toetsenLijst;
	}

	public void setToetsenLijst(List<Toets> toetsenLijst) {
		this.toetsenLijst = toetsenLijst;
	}

	/**
     * @return the isKlaar
     */
    @Column (name = "klaar", nullable = true)
    public boolean isKlaar() {
        return this.isKlaar;
    }
	/**
     * @param isKlaar the isKlaar to set
     */
    public void setKlaar(boolean isKlaar) {
        this.isKlaar = isKlaar;
    }
     
	@ManyToOne
	@JoinColumn(name="traject_id")
    public Traject getTraject() {
        return this.traject;
    }
  
    public void setTraject(Traject traject) {
        this.traject = traject;
    }


    @OneToMany(mappedBy="cursist")
    public List<Materiaal> getMateriaalLijst() {
        return this.materiaalLijst;
    }
  
    public void setMateriaalLijst(List<Materiaal> materiaalLijst) {
        this.materiaalLijst = materiaalLijst;
    }

    public boolean equals(Object obj) {
    	boolean isEqual = false;
    	if (obj instanceof Cursist
    			&& this.cursistId == ((Cursist) obj).getCursistId()
    			&& this.traject == ((Cursist) obj).getTraject()
    			&& this.cursistId == ((Cursist) obj).getCursistId()
    			// toetsenlijst
    		    // materiaallijst
    			&& this.isKlaar == ((Cursist) obj).isKlaar()) {
    		isEqual = true;
    	}
    	return isEqual;
    }

}
