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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Pieter
 * 
 * @date 1 okt. 2014
 */
@Entity
@Table(name="trajecteenheid")
public class TrajectEenheid {
    
	private long trajectId;
    private String trajectEenheidNaam;
    private String trajectEenheidOmschrijving;
    private List<Materiaal> trajectMateriaalLijst;
    private Toets trajectEenheidToets;
    private List<Traject> trajecten;
    
    @Id
    @Column(name="traject_eenheid_id")
    @GeneratedValue
    public long getTrajectId() {
		return trajectId;
	}
    
	public void setTrajectId(long trajectId) {
		this.trajectId = trajectId;
	}
	
	@ManyToMany(mappedBy="trajectEenheden")
	public List<Traject> getTrajecten() {
		return trajecten;
	}
	public void setTrajecten(List<Traject> trajecten) {
		this.trajecten = trajecten;
	}
	/**
     * @return the trajectEenheidNaam
     */
    public String getTrajectEenheidNaam() {
        return this.trajectEenheidNaam;
    }
    /**
     * @param trajectEenheidNaam the trajectEenheidNaam to set
     */
    public void setTrajectEenheidNaam(String trajectEenheidNaam) {
        this.trajectEenheidNaam = trajectEenheidNaam;
    }
    /**
     * @return the trajectEenheidOmschrijving
     */
    public String getTrajectEenheidOmschrijving() {
        return this.trajectEenheidOmschrijving;
    }
    /**
     * @param trajectEenheidOmschrijving the trajectEenheidOmschrijving to set
     */
    public void setTrajectEenheidOmschrijving(String trajectEenheidOmschrijving) {
        this.trajectEenheidOmschrijving = trajectEenheidOmschrijving;
    }
    /**
     * @return the trajectMateriaalLijst
     */
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="trajecteenheid_materiaal",
                joinColumns={@JoinColumn(name="traject_eenheid_id")},
                inverseJoinColumns={@JoinColumn(name="materiaal_id")})
    public List<Materiaal> getTrajectMateriaalLijst() {
        return this.trajectMateriaalLijst;
    }
    /**
     * @param trajectMateriaalLijst the trajectMateriaalLijst to set
     */
    public void setTrajectMateriaalLijst(List<Materiaal> trajectMateriaalLijst) {
        this.trajectMateriaalLijst = trajectMateriaalLijst;
    }
    /**
     * @return the trajectEenheidToets
     */
    
    @OneToOne (cascade = CascadeType.ALL) 
    public Toets getTrajectEenheidToets() {
        return this.trajectEenheidToets;
    }
    
    /**
     * @param trajectEenheidToets the trajectEenheidToets to set
     */
    
    public void setTrajectEenheidToets(Toets trajectEenheidToets) {
        this.trajectEenheidToets = trajectEenheidToets;
    }
    
    public boolean equals(Object obj) {
    	boolean isEqual = false;
    	if (obj instanceof TrajectEenheid
    			&& this.trajectEenheidNaam.equals(((TrajectEenheid) obj).getTrajectEenheidNaam())
    			&& this.trajectEenheidOmschrijving.equals(((TrajectEenheid) obj).getTrajectEenheidOmschrijving())
    			// materiaal lijst
    			// traject lijst
    			&& this.trajectEenheidToets == ((TrajectEenheid) obj).getTrajectEenheidToets()) {
    		isEqual = true;
    	}
    	return isEqual;
    }
    

}
