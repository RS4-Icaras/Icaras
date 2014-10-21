package nl.rsvier.icaras.dao.cursisttraject;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.cursisttraject.Traject;

@Repository("trajectDAO")
public class TrajectDAO implements ITrajectDAO {

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
	
	public Traject findTrajectByID(long id) {
		Traject p = hibernateTemplate.load(Traject.class, id);
		return p;
	}
	
	public void updateTraject(Traject traject) {
		hibernateTemplate.saveOrUpdate(traject);
	}
	
	public void saveTraject(Traject traject) {
		hibernateTemplate.saveOrUpdate(traject);
	}
	
	public void deleteTraject(Traject traject) {
		hibernateTemplate.delete(traject);
	}
	
	public List<Traject> getAllTrajecten() {
		List<Traject> trajectenlijst = (List<Traject>) hibernateTemplate.loadAll(Traject.class);
		return trajectenlijst;
	}
}