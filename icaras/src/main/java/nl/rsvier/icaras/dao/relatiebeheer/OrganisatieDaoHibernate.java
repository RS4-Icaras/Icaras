package nl.rsvier.icaras.dao.relatiebeheer;

import nl.rsvier.icaras.core.relatiebeheer.Organisatie;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

import org.springframework.stereotype.Repository;

/*
 * Annotatie @Repository zorgt ook voor (aanmelding voor) translatie van
 * exceptions naar Spring
 */
@Repository("IOrganisatieDao")
public class OrganisatieDaoHibernate extends GenericDaoHibernate<Organisatie>
		implements IOrganisatieDao {

	public OrganisatieDaoHibernate() {
		super(Organisatie.class);
	}

}
