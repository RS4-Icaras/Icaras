package nl.rsvier.icaras.dao.cursisttraject;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.cursisttraject.TrajectEenheid;

@Repository("trajectEenheidDAO")
public class TrajectEenheidDAO implements ITrajectEenheidDAO {
	
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
		
	public TrajectEenheid findTrajectEenheidByID(long id) {
		TrajectEenheid t = hibernateTemplate.load(TrajectEenheid.class, id);
		return t;
	}
	
	public void updateTrajectEenheid(TrajectEenheid trajectEenheid) {
		hibernateTemplate.saveOrUpdate(trajectEenheid);
	}
	
	public void saveTrajectEenheid(TrajectEenheid trajectEenheid) {
		hibernateTemplate.saveOrUpdate(trajectEenheid);
	}
	
	public void deleteTrajectEenheid(TrajectEenheid trajectEenheid) {
		hibernateTemplate.delete(trajectEenheid);
	}
	
	public List<TrajectEenheid> getAllTrajectEenheden() {
		List<TrajectEenheid> trajectEenhedenlijst = (List<TrajectEenheid>) hibernateTemplate.loadAll(TrajectEenheid.class);
		return trajectEenhedenlijst;
	}
}