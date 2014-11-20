package nl.rsvier.icaras.dao.arbeidsmarkt;

import nl.rsvier.icaras.core.arbeidsmarkt.Aanbieding;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

import org.springframework.stereotype.Repository;

/*
 * Annotatie @Repository zorgt ook voor (aanmelding voor) translatie van
 * exceptions naar Spring
 */
@Repository("IAanbiedingDao")
public class AanbiedingDaoHibernate extends GenericDaoHibernate<Aanbieding>
		implements IAanbiedingDao {

	public AanbiedingDaoHibernate() {
		super(Aanbieding.class);
	}

	@Override
	public void delete(Aanbieding entity) {
		entity.removeAllReferences();
		super.delete(entity);
	}

}
