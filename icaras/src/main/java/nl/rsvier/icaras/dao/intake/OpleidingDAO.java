package nl.rsvier.icaras.dao.intake;

import java.util.List;
import nl.rsvier.icaras.core.intake.Opleiding;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("IOpleidingDao")
public class OpleidingDAO implements IOpleidingDAO{
	
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

	public Opleiding findOpleiding(long id) {
		Opleiding a = hibernateTemplate.load(Opleiding.class, id);
		return a;
	}

	public void persistOpleiding(Opleiding a) {
		hibernateTemplate.saveOrUpdate(a);	
	}

	public void updateOpleiding(Opleiding a) {
		hibernateTemplate.saveOrUpdate(a);
	}

	public void deleteOpleiding(Opleiding a) {
		hibernateTemplate.delete(a);
	}
	
	public List<Opleiding> getAllOpleiding(){
		List<Opleiding> alijst = (List<Opleiding>) hibernateTemplate.loadAll(Opleiding.class);
		return alijst;
	}

}
