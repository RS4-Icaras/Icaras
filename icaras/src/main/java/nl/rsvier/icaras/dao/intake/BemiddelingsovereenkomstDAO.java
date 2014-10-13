package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.Bemiddelingsovereenkomst;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import nl.rsvier.icaras.util.intake.HibernateUtil;

@Repository("bemiddelingsovereenkomstDao")
public class BemiddelingsovereenkomstDAO implements IBemiddelingsovereenkomstDAO {
	
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

	public Bemiddelingsovereenkomst findBemiddelingsovereenkomst(long id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Bemiddelingsovereenkomst a = (Bemiddelingsovereenkomst) session.get(Bemiddelingsovereenkomst.class, id);
		session.getTransaction().commit();
		return a;
		
	}

	public void persistBemiddelingsovereenkomst(Bemiddelingsovereenkomst a) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(a);
		session.getTransaction().commit();
		
	}

	public void updateBemiddelingsovereenkomst(Bemiddelingsovereenkomst a) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(a);
		session.getTransaction().commit();
		
	}
	
	public void deleteBemiddelingsovereenkomst(Bemiddelingsovereenkomst a) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(a);
		session.getTransaction().commit();
		
	}

}
