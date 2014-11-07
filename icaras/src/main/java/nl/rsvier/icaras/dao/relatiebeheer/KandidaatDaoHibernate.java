package nl.rsvier.icaras.dao.relatiebeheer;

import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.relatiebeheer.Kandidaat;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

@Repository("IKandidaatDao")
public class KandidaatDaoHibernate extends GenericDaoHibernate<Kandidaat> implements IKandidaatDao {
	
	public KandidaatDaoHibernate() {
		super(Kandidaat.class);
	}
}
