package nl.rsvier.icaras.dao.arbeidsmarkt;

import nl.rsvier.icaras.core.arbeidsmarkt.Vacature;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

import org.springframework.stereotype.Repository;

/*
 * Annotatie @Repository zorgt ook voor (aanmelding voor) translatie van
 * exceptions naar Spring
 */
@Repository("IVacatureDao")
public class VacatureDaoHibernate extends GenericDaoHibernate<Vacature>
		implements IVacatureDao {

	public VacatureDaoHibernate() {
		super(Vacature.class);
	}

	@Override
	public void delete(Vacature entity) {
		entity.removeAllReferences(); // TODO: SERVICELAAG
		super.delete(entity);
	}

}
