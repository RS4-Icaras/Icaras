package nl.rsvier.icaras.dao.relatiebeheer;

import nl.rsvier.icaras.core.relatiebeheer.Leverancier;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

import org.springframework.stereotype.Repository;

@Repository("ILeverancierDao")
public class LeverancierDaoHibernate extends GenericDaoHibernate<Leverancier>
		implements ILeverancierDao {

	public LeverancierDaoHibernate() {
		super(Leverancier.class);
	}
}
