package nl.rsvier.icaras.dao.intake;

import java.util.List;
import nl.rsvier.icaras.core.intake.Werkervaringseenheid;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;


@Repository("IWerkervaringseenheidDao")
public class WerkervaringseenheidDAO implements IWerkervaringseenheidDAO {
	
	private SessionFactory sessionFactory;
	private HibernateTemplate hibernateTemplate;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public Werkervaringseenheid findWerkervaringseenheid(long id) {
		Werkervaringseenheid a = hibernateTemplate.load(Werkervaringseenheid.class, id);
		return a;
	}

	public void persistWerkervaringseenheid(Werkervaringseenheid a) {
		hibernateTemplate.saveOrUpdate(a);	
	}

	public void updateWerkervaringseenheid(Werkervaringseenheid a) {
		hibernateTemplate.saveOrUpdate(a);
	}

	public void deleteWerkervaringseenheid(Werkervaringseenheid a) {
		hibernateTemplate.delete(a);
	}
	
	public List<Werkervaringseenheid> getAllWerkervaringseenheid(){
		List<Werkervaringseenheid> alijst = (List<Werkervaringseenheid>) hibernateTemplate.loadAll(Werkervaringseenheid.class);
		return alijst;
	}

}
