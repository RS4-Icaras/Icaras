package nl.rsvier.icaras.dao.cursisttraject;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.cursisttraject.Cursist;

@Repository("cursistDAO")
public class CursistDAO implements ICursistDAO {

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
	
	public void saveCursist(Cursist cursist) {
		hibernateTemplate.saveOrUpdate(cursist);
	}
	
	public void updateCursist(Cursist cursist) {
		hibernateTemplate.saveOrUpdate(cursist);
	}
	
	public void deleteCursist(Cursist cursist) {
		hibernateTemplate.delete(cursist);
	}
	
	public Cursist findCursistByID(long id) {
		Cursist c = hibernateTemplate.load(Cursist.class, id);
		return c;
	}

	public List<Cursist> getAllCursisten() {
		List<Cursist> cursistenlijst = (List<Cursist>) hibernateTemplate.loadAll(Cursist.class);
		return cursistenlijst;
	}
}
