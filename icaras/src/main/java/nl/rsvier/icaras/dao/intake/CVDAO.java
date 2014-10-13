package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.CV;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import nl.rsvier.icaras.util.intake.HibernateUtil;

@Repository("cvDao")
public class CVDAO implements ICVDAO {
	
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

	public CV findCV(long id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		CV a = (CV) session.get(CV.class, id);
		session.getTransaction().commit();
		return a;
		
	}

	public void persistCV(CV cv) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(cv);
		session.getTransaction().commit();
		
	}

	public void updateCV(CV cv) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(cv);
		session.getTransaction().commit();
		
	}

	public void deleteCV(CV cv) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(cv);
		session.getTransaction().commit();
		
	}

}
