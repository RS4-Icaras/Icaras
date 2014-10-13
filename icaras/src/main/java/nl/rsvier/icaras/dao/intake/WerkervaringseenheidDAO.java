package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.Werkervaringseenheid;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import nl.rsvier.icaras.util.intake.HibernateUtil;

@Repository("werkervaringseenheidDao")
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
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Werkervaringseenheid a = (Werkervaringseenheid) session.get(Werkervaringseenheid.class, id);
		session.getTransaction().commit();
		return a;
		
	}

	public void persistWerkervaringseenheid(Werkervaringseenheid a) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(a);
		session.getTransaction().commit();
		
	}

	public void updateWerkervaringseenheid(Werkervaringseenheid a) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(a);
		session.getTransaction().commit();
		
	}

	public void deleteWerkervaringseenheid(Werkervaringseenheid a) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(a);
		session.getTransaction().commit();
		
	}

}
