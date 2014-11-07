package nl.rsvier.icaras.dao.relatiebeheer;

import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.relatiebeheer.OrganisatieRol;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

@Repository("IOrganisatieRolDaoHibernate")
public class OrganisatieRolDaoHibernate extends GenericDaoHibernate<OrganisatieRol> implements IOrganisatieRolDaoHibernate {

	public OrganisatieRolDaoHibernate() {
		super(OrganisatieRol.class);
	}
}
