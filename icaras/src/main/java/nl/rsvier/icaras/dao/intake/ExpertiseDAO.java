package nl.rsvier.icaras.dao.intake;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import nl.rsvier.icaras.core.intake.Expertise;

@Repository("IExpertiseDao")
public class ExpertiseDAO implements IExpertiseDAO {
	
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

	public Expertise findExpertise(long id) {
		Expertise a = hibernateTemplate.load(Expertise.class, id);
		return a;
		
	}

	public void persistExpertise(Expertise expertise) {
		hibernateTemplate.saveOrUpdate(expertise);
		
	}

	public void updateExpertise(Expertise expertise) {
		hibernateTemplate.saveOrUpdate(expertise);
		
	}

	public void deleteExpertise(Expertise expertise) {
		hibernateTemplate.delete(expertise);
		
	}
	
	public List<Expertise> getAllExpertise(){
		List<Expertise> expertiselijst = (List<Expertise>) hibernateTemplate.loadAll(Expertise.class);
		return expertiselijst;
	}

}
