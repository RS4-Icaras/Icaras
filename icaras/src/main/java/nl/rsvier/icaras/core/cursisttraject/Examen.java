package nl.rsvier.icaras.core.cursisttraject;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author Pieter
 * 
 * @date 1 okt. 2014
 */
@Entity
@Table (name = "examen")
public class Examen extends Toets {    
    
    private boolean isExtern;
    private List<Resultaat> toetsResultaten;
    private int toetsNorm;
    
    
    /**
     * @return the isExtern
     */
    public boolean isExtern() {
        return this.isExtern;
    }
    /**
     * @param isExtern the isExtern to set
     */
    public void setExtern(boolean isExtern) {
        this.isExtern = isExtern;
    }
    /**
     * @return the toetsResultaten
     */
    @OneToMany(mappedBy="examen")
    public List<Resultaat> getToetsResultaten() {
        return this.toetsResultaten;
    }
    
    /**
     * @param toetsResultaten the toetsResultaten to set
     */    
    public void setToetsResultaten(List<Resultaat> toetsResultaten) {
        this.toetsResultaten = toetsResultaten;
    }
    /**
     * @return the toetsNorm
     */
    public int getToetsNorm() {
        return this.toetsNorm;
    }
    /**
     * @param toetsNorm the toetsNorm to set
     */
    public void setToetsNorm(int toetsNorm) {
        this.toetsNorm = toetsNorm;
    }
    
    public boolean equals(Object obj) {
    	boolean isEqual = false;
    	if (obj instanceof Examen
    			&& this.isExtern == ((Examen) obj).isExtern()
    			&& this.toetsNorm == ((Examen) obj).getToetsNorm())
    	    //&& this.toetsResultaten.equals(((Examen) obj).toetsResultaten)) 
    			{
    		isEqual = true;
    	}
    	return isEqual;
    }

}
