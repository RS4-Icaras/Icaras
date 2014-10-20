package nl.rsvier.icaras.dao.intake;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.intake.Aanmelder;

@Repository("IAanmelderDao")
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
		Aanmelder a = hibernateTemplate.load(Aanmelder.class, id);
		return a;
		
	}

	public void persistAanmelder(Aanmelder aanmelder) {
		hibernateTemplate.saveOrUpdate(aanmelder);
		
	}

	public void updateAanmelder(Aanmelder aanmelder) {
		hibernateTemplate.saveOrUpdate(aanmelder);
		
	}

	public void deleteAanmelder(Aanmelder aanmelder) {
		hibernateTemplate.delete(aanmelder);
		
	}
	
	public List<Aanmelder> getAllAanmelder(){
		List<Aanmelder> aanmelderlijst = (List<Aanmelder>) hibernateTemplate.loadAll(Aanmelder.class);
		return aanmelderlijst;
	}
}
