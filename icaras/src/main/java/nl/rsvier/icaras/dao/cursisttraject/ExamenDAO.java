package nl.rsvier.icaras.dao.cursisttraject;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.cursisttraject.Examen;

@Repository("examenDAO")
public class ExamenDAO implements IExamenDAO {

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

	public Examen findExamenByID(long id) {
		Examen e = hibernateTemplate.load(Examen.class, id);
		return e;
	}
		
	public void updateExamen(Examen examen) {
		hibernateTemplate.saveOrUpdate(examen);
	}
	
	public void saveExamen(Examen examen) {
		hibernateTemplate.saveOrUpdate(examen);
	}
	
	public void deleteExamen(Examen examen) {
		hibernateTemplate.delete(examen);
	}
	
	public List<Examen> getAllExamens() {
		List<Examen> examenlijst = (List<Examen>) hibernateTemplate.loadAll(Examen.class);
		return examenlijst;
	}
}