package nl.rsvier.icaras.dao.intake;

import nl.rsvier.icaras.core.intake.Arbeidsovereenkomst;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
//import nl.rsvier.icaras.util.intake.HibernateUtil;

@Repository("arbeidsovereenkomstDao")
public class ArbeidsovereenkomstDAO implements IArbeidsovereenkomstDAO {
	
	private SessionFactory sessionFactory;
	private HibernateTemplate hibernateTemplate;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	};

	public Arbeidsovereenkomst findArbeidsovereenkomst(long id) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Arbeidsovereenkomst a = (Arbeidsovereenkomst) session.get(Arbeidsovereenkomst.class, id);
		session.getTransaction().commit();
		return a;
		
	}

	public void persistArbeidsovereenkomst(Arbeidsovereenkomst a) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.save(a);
		session.getTransaction().commit();
		
	}

	public void updateArbeidsovereenkomst(Arbeidsovereenkomst a) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.saveOrUpdate(a);
		session.getTransaction().commit();
		
	}

	public void deleteArbeidsovereenkomst(Arbeidsovereenkomst a) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.delete(a);
		session.getTransaction().commit();
		
	}

}
