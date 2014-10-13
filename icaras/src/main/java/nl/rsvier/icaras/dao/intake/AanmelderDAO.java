package nl.rsvier.icaras.dao.intake;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
import nl.rsvier.icaras.core.intake.Aanmelder;

@Repository("aanmelderDao")
public class AanmelderDAO implements IAanmelderDAO {
	
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

	public Aanmelder findAanmelder(long id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Aanmelder a = (Aanmelder) session.get(Aanmelder.class, id);
		session.getTransaction().commit();
		return a;
		
	}

	public void persistAanmelder(Aanmelder aanmelder) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(aanmelder);
		session.getTransaction().commit();
		
	}

	public void updateAanmelder(Aanmelder aanmelder) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(aanmelder);
		session.getTransaction().commit();
		
	}

	public void deleteAanmelder(Aanmelder aanmelder) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(aanmelder);
		session.getTransaction().commit();
		
	}


}
