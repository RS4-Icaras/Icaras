package nl.rsvier.icaras.dao.relatiebeheer;

import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.arbeidsmarkt.Aanbieding;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

@Repository("IAanbiedingDao")
public class AanbiedingDaoHibernate extends GenericDaoHibernate<Aanbieding> implements IAanbiedingDao {

	public AanbiedingDaoHibernate() {
		super(Aanbieding.class);
	}
}
