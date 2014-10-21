package nl.rsvier.icaras.dao.cursisttraject;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.cursisttraject.Resultaat;

@Repository("resultaatDAO")
public class ResultaatDAO implements IResultaatDAO {

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
	
	public Resultaat findResultaatByID(long id) {
		Resultaat r = hibernateTemplate.load(Resultaat.class, id);
		return r;
	}
	
	public void updateResultaat(Resultaat resultaat) {
		hibernateTemplate.saveOrUpdate(resultaat);
	}
	
	public void saveResultaat(Resultaat resultaat) {
		hibernateTemplate.saveOrUpdate(resultaat);
	}
	
	public void deleteResultaat(Resultaat resultaat) {
		hibernateTemplate.delete(resultaat);
	}
	
	public List<Resultaat> getAllResultaten() {
		List<Resultaat> resultatenlijst = (List<Resultaat>) hibernateTemplate.loadAll(Resultaat.class);
		return resultatenlijst;
	}
}