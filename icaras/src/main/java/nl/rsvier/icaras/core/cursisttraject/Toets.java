package nl.rsvier.icaras.core.cursisttraject;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Pieter
 * 
 * @date 1 okt. 2014
 */
@Entity
@Table (name = "toets")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Toets {
    
	private long toetsId;
    private String tentamenBeschrijving;
    private TrajectEenheid trajectEenheid;
    private boolean isVolbracht;
    private Cursist cursist;
    
    @ManyToOne
    @JoinColumn(name = "cursist_id")
    public Cursist getCursist() {
		return cursist;
	}

	public void setCursist(Cursist cursist) {
		this.cursist = cursist;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column (name = "toets_id")
    public long getToetsId() {
		return toetsId;
	}
    
	public void setToetsId(long toetsId) {
		this.toetsId = toetsId;
	}
	/**
     * @return the tentamenBeschrijving
     */
	@Column (name = "tentamen_beschrijving")
    public String getTentamenBeschrijving() {
        return this.tentamenBeschrijving;
    }
    /**
     * @param tentamenBeschrijving the tentamenBeschrijving to set
     */
    public void setTentamenBeschrijving(String tentamenBeschrijving) {
        this.tentamenBeschrijving = tentamenBeschrijving;
    }
    /**
     * @return the trajectEenheid
     */
    @OneToOne (cascade = CascadeType.ALL) 
    public TrajectEenheid getTrajectEenheid() {
        return this.trajectEenheid;
    }
    
    /**
     * @param trajectEenheid the trajectEenheid to set
     */
    
    public void setTrajectEenheid(TrajectEenheid trajectEenheid) {
        this.trajectEenheid = trajectEenheid;
    }
    
    /**
     * @return the isVolbracht
     */
    @Column (name = "volbracht")
    public boolean isVolbracht() {
        return this.isVolbracht;
    }
    /**
     * @param isVolbracht the isVolbracht to set
     */
    public void setVolbracht(boolean isVolbracht) {
        this.isVolbracht = isVolbracht;
    }
    

}
