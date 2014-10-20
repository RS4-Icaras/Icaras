package nl.rsvier.icaras.dao.intake;

import java.util.List;

import nl.rsvier.icaras.core.intake.CV;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("ICVDao")
public class CVDAO implements ICVDAO {
	
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

	public CV findCV(long id) {
		CV a = hibernateTemplate.load(CV.class, id);
		return a;
	}

	public void persistCV(CV a) {
		hibernateTemplate.saveOrUpdate(a);	
	}

	public void updateCV(CV a) {
		hibernateTemplate.saveOrUpdate(a);
	}

	public void deleteCV(CV a) {
		hibernateTemplate.delete(a);
	}
	
	public List<CV> getAllCV(){
		List<CV> alijst = (List<CV>) hibernateTemplate.loadAll(CV.class);
		return alijst;
	}

}
