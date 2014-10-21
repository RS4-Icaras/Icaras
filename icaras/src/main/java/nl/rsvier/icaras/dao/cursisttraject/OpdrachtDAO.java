package nl.rsvier.icaras.dao.cursisttraject;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.cursisttraject.Opdracht;

@Repository("opdrachtDAO")
public class OpdrachtDAO implements IOpdrachtDAO {

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
	
	public Opdracht findOpdrachtByID(long id) {
		Opdracht p = hibernateTemplate.load(Opdracht.class, id);
		return p;
	}
		
	public void updateOpdracht(Opdracht opdracht) {
		hibernateTemplate.saveOrUpdate(opdracht);
	}
	
	public void saveOpdracht(Opdracht opdracht) {
		hibernateTemplate.saveOrUpdate(opdracht);
	}
	
	public void deleteOpdracht(Opdracht opdracht) {
		hibernateTemplate.delete(opdracht);
	}
	
	public List<Opdracht> getAllOpdrachten() {
		List<Opdracht> opdrachtenlijst = (List<Opdracht>) hibernateTemplate.loadAll(Opdracht.class);
		return opdrachtenlijst;
	}
}