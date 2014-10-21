package nl.rsvier.icaras.dao.cursisttraject;

import java.util.List;

import nl.rsvier.icaras.core.cursisttraject.Boek;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("boekDAO")
public class BoekDAO implements IBoekDAO {

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
		
	public Boek findBoekByID(long id) {
		Boek b = hibernateTemplate.load(Boek.class, id);
		return b;
	}
	
	public void updateBoek(Boek boek) {
		hibernateTemplate.saveOrUpdate(boek);
	}
	
	public void saveBoek(Boek boek) {
		hibernateTemplate.saveOrUpdate(boek);
	}
	
	public void deleteBoek(Boek boek) {
		hibernateTemplate.delete(boek);
	}
	
	public List<Boek> getAllBoeken() {
		List<Boek> boekenlijst = (List<Boek>) hibernateTemplate.loadAll(Boek.class);
		return boekenlijst;
	}
}
