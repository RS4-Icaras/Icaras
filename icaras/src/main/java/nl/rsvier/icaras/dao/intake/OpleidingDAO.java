package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.Opleiding;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("opleidingDao")
public class OpleidingDAO implements IOpleidingDAO{
	
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

	public Opleiding findOpleiding(long id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Opleiding a = (Opleiding) session.get(Opleiding.class, id);
		session.getTransaction().commit();
		return a;
		
	}

	public void persistOpleiding(Opleiding a) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(a);
		session.getTransaction().commit();
		
	}

	public void updateOpleiding(Opleiding a) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(a);
		session.getTransaction().commit();
		
	}

	public void deleteOpleiding(Opleiding a) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(a);
		session.getTransaction().commit();
		
	}

}
