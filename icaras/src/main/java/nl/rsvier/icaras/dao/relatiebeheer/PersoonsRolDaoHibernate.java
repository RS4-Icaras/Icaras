package nl.rsvier.icaras.dao.relatiebeheer;

import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.relatiebeheer.PersoonsRol;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

@Repository("IPersoonsRolDao")
public class PersoonsRolDaoHibernate extends GenericDaoHibernate<PersoonsRol> implements IPersoonsRolDao {

	public PersoonsRolDaoHibernate() {
		super(PersoonsRol.class);
	}
	
}
