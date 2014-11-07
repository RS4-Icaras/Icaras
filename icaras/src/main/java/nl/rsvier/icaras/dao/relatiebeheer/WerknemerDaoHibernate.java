package nl.rsvier.icaras.dao.relatiebeheer;

import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.relatiebeheer.Werknemer;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

@Repository("IWerknemerDao")
public class WerknemerDaoHibernate extends GenericDaoHibernate<Werknemer> implements IWerknemerDao {

	public WerknemerDaoHibernate() {
		super(Werknemer.class);
	}
}
