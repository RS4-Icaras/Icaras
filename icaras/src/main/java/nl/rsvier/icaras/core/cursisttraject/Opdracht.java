package nl.rsvier.icaras.core.cursisttraject;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Pieter
 * 
 * @date 1 okt. 2014
 */
@Entity
@Table (name = "opdracht")
@AttributeOverrides({
    @AttributeOverride(name="tentamenBeschrijving", column=@Column(name="tentamen_beschrijving")),
    @AttributeOverride(name="isVolbracht", column=@Column(name="is_volbracht"))
})
public class Opdracht extends Toets {
    //private List<Persoon> collaberators;
    private Date startDatum;
    private Date eindDatum;
    /**
     * @return the startDatum
     */
    public Date getStartDatum() {
        return this.startDatum;
    }
    /**
     * @param startDatum the startDatum to set
     */
    public void setStartDatum(Date startDatum) {
        this.startDatum = startDatum;
    }
    /**
     * @return the eindDatum
     */
    public Date getEindDatum() {
        return this.eindDatum;
    }
    /**
     * @param eindDatum the eindDatum to set
     */
    public void setEindDatum(Date eindDatum) {
        this.eindDatum = eindDatum;
    }
    
    public boolean equals(Object obj) {
    	boolean isEqual = false;
    	if (obj instanceof Opdracht
    			&& this.startDatum ==((Opdracht) obj).getStartDatum()
    			&& this.eindDatum == ((Opdracht) obj).getEindDatum()
    			&& this.getCursist() == ((Opdracht) obj).getCursist()
    		    && this.getTentamenBeschrijving().equals(((Opdracht) obj).getTentamenBeschrijving())
    		    && this.getToetsId() == ((Opdracht) obj).getToetsId()) {
    		isEqual = true;
    	}
    	return isEqual;
    }
    
}

//
//) 