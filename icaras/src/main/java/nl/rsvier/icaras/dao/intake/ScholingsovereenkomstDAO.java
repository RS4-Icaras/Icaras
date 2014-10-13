package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.Scholingsovereenkomst;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("scholingsovereenkomstDao")
public class ScholingsovereenkomstDAO implements IScholingsovereenkomstDAO {
	
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

	public Scholingsovereenkomst findScholingsovereenkomst(long id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Scholingsovereenkomst a = (Scholingsovereenkomst) session.get(Scholingsovereenkomst.class, id);
		session.getTransaction().commit();
		return a;
		
	}

	public void persistScholingsovereenkomst(Scholingsovereenkomst a) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(a);
		session.getTransaction().commit();
		
	}

	public void updateScholingsovereenkomst(Scholingsovereenkomst a) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(a);
		session.getTransaction().commit();
		
	}
	
	public void deleteScholingsovereenkomst(Scholingsovereenkomst a) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(a);
		session.getTransaction().commit();
		
	}

}
