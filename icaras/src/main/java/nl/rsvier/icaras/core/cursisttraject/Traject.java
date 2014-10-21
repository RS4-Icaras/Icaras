package nl.rsvier.icaras.core.cursisttraject;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Pieter
 * 
 * @date 1 okt. 2014
 */
@Entity
@Table (name = "TRAJECT")
public class Traject {
	

	private long trajectId;
    private String trajectNaam;
    private List<TrajectEenheid> trajectEenheden;   
    private List<Cursist> cursisten;
    
    //Functie getExpertise { for (Traject : TrajectEenheidLijst) traject.getNaam () };
    
	@Id
	@GeneratedValue
	@Column(name="traject_id")
	public long getTrajectId() {
		return trajectId;
	}
	
    public void setTrajectId(long trajectId) {
		this.trajectId = trajectId;
	}
    
    /**
     * @return the trajectNaam
     */
	@Column(name="TRAJECT_NAAM")
    public String getTrajectNaam() {
        return this.trajectNaam;
    }

	/**
     * @param trajectNaam the trajectNaam to set
     */
    public void setTrajectNaam(String trajectNaam) {
        this.trajectNaam = trajectNaam;
    }
    /**
     * @return the trajectEenheden
     */
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="traject_trajecteenheid",
                joinColumns={
    				@JoinColumn(name="traject_id")},
                inverseJoinColumns={
    				@JoinColumn(name="traject_eenheid_id")
    			}
    )
    public List<TrajectEenheid> getTrajectEenheden() {
        return this.trajectEenheden;
    }

    public void setTrajectEenheden(List<TrajectEenheid> trajectEenheden) {
        this.trajectEenheden = trajectEenheden;
    }
    
    
    @OneToMany(mappedBy="traject")
    public List<Cursist> getCursisten() {
		return cursisten;
	}

	public void setCursisten(List<Cursist> cursisten) {
		this.cursisten = cursisten;
	}
    
    public boolean equals(Object obj) {
    	boolean isEqual = false;
    	if (obj instanceof Traject
    			&& this.trajectNaam.equals(((Traject) obj).getTrajectNaam())
    			&& this.trajectId == ((Traject) obj).getTrajectId())
    			// list trajecteenheden
    			// list cursisten
    		{
    		isEqual = true;
    	}
    	return isEqual;
    }

}
