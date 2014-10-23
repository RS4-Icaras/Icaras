package nl.rsvier.icaras.dao.relatiebeheer;

import nl.rsvier.icaras.core.relatiebeheer.Adres;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

import org.springframework.stereotype.Repository;

@Repository("IAdresDao")//Deze annotatie zorgt ook voor (aanmelding voor) translatie van exceptions naar Spring
public class AdresDaoHibernate extends GenericDaoHibernate<Adres> implements IAdresDao {

	public AdresDaoHibernate() {
		super(Adres.class);
	}
}
