package nl.rsvier.icaras.dao.relatiebeheer;

import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.relatiebeheer.Relatie;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

@Repository("IRelatieDao")
public class RelatieDaoHibernate extends GenericDaoHibernate<Relatie> implements IRelatieDao {

	public RelatieDaoHibernate() {
		super(Relatie.class);
	}




}
