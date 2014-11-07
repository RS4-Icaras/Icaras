package nl.rsvier.icaras.dao.relatiebeheer;

import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.arbeidsmarkt.Arbeidsovereenkomst;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

@Repository("IArbeidsovereenkomstDao")
public class ArbeidsovereenkomstDaoHibernate extends
		GenericDaoHibernate<Arbeidsovereenkomst> implements
		IArbeidsovereenkomstDao {

	public ArbeidsovereenkomstDaoHibernate() {
		super(Arbeidsovereenkomst.class);
	}
}
