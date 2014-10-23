package nl.rsvier.icaras.dao.relatiebeheer;

import nl.rsvier.icaras.core.relatiebeheer.Nfa;
import nl.rsvier.icaras.dao.GenericDaoHibernate;

import org.springframework.stereotype.Repository;
@Repository("INfaDao")
public class NfaDaoHibernate extends GenericDaoHibernate<Nfa> implements INfaDao {

	public NfaDaoHibernate() {
		super(Nfa.class);
	}	
}
