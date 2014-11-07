package nl.rsvier.icaras.dao.relatiebeheer;

import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.relatiebeheer.Persoon;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

@Repository("IPersoonDao")
public class PersoonDaoHibernate extends GenericDaoHibernate<Persoon> implements IPersoonDao {
	
	public PersoonDaoHibernate() {
		super(Persoon.class);
	}
}
