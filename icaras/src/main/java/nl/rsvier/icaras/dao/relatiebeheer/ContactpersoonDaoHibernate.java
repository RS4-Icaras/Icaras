package nl.rsvier.icaras.dao.relatiebeheer;

import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.relatiebeheer.Contactpersoon;
import nl.rsvier.icaras.core.relatiebeheer.Kandidaat;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

@Repository("IKandidaatDao")
public class ContactpersoonDaoHibernate extends GenericDaoHibernate<Contactpersoon> implements IContactpersoonDao {
	
	public ContactpersoonDaoHibernate() {
		super(Contactpersoon.class);
	}
}
