package nl.rsvier.icaras.core.intake;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author Koen, Jelle
 *
 */
@Entity
@Table(name = "overeenkomst")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Overeenkomst implements Serializable {
	
private static final long serialVersionUID = 1L;

public long id;	
public boolean isGetekend = false;
public boolean isStandaardOvereenkomst = false;
public Date startDatum;
public Date eindDatum;
public TMPPersoon ondergetekende;

@Id
@GeneratedValue
@Column(nullable = false)
public long getId() {
	return id;
}
/**
 * @param id the id to set
 */
public void setId(long id) {
	this.id = id;
}

/**
 * @return the isStandaardOvereenkomst
 */
@Transient
public boolean isStandaardOvereenkomst() {
	return isStandaardOvereenkomst;
}
/**
 * @param isStandaardOvereenkomst the isStandaardOvereenkomst to set
 */
public void setStandaardOvereenkomst(boolean isStandaardOvereenkomst) {
	this.isStandaardOvereenkomst = isStandaardOvereenkomst;
}
/**
 * @return the startDatum
 */
@Transient
public Date getStartDatum() {
	return startDatum;
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
@Transient
public Date getEindDatum() {
	return eindDatum;
}
/**
 * @param eindDatum the eindDatum to set
 */
public void setEindDatum(Date eindDatum) {
	this.eindDatum = eindDatum;
}


/**
 * @return the isGetekend 
 */
@Transient
public boolean isGetekend() {
	return isGetekend;
}

/**
 * @param setGetekend the setGetekend to set
 */
public void setGetekend(boolean isGetekend) {
	this.isGetekend = isGetekend;
}
/**
 * @return the ondergetekende
 */
@Transient
public TMPPersoon getOndergetekende() {
	return ondergetekende;
}
/**
 * @param ondergetekende the ondergetekende to set
 */
public void setOndergetekende(TMPPersoon ondergetekende) {
	this.ondergetekende = ondergetekende;
}


	
}
