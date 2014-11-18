package nl.rsvier.icaras.dao.relatiebeheer;

import nl.rsvier.icaras.core.relatiebeheer.Contactpersoon;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

import org.springframework.stereotype.Repository;

@Repository("IContactpersoonDao")
public class ContactpersoonDaoHibernate extends
		GenericDaoHibernate<Contactpersoon> implements IContactpersoonDao {

	public ContactpersoonDaoHibernate() {
		super(Contactpersoon.class);
	}

}
