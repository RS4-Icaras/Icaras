package nl.rsvier.icaras.dao.intake;

import java.util.List;

import nl.rsvier.icaras.core.intake.Bemiddelingsovereenkomst;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;



@Repository("IBemiddelingsovereenkomstDao")
public class BemiddelingsovereenkomstDAO implements IBemiddelingsovereenkomstDAO {
	
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

	public Bemiddelingsovereenkomst findBemiddelingsovereenkomst(long id) {
		Bemiddelingsovereenkomst a = hibernateTemplate.load(Bemiddelingsovereenkomst.class, id);
		return a;
	}

	public void persistBemiddelingsovereenkomst(Bemiddelingsovereenkomst a) {
		hibernateTemplate.saveOrUpdate(a);	
	}

	public void updateBemiddelingsovereenkomst(Bemiddelingsovereenkomst a) {
		hibernateTemplate.saveOrUpdate(a);
	}

	public void deleteBemiddelingsovereenkomst(Bemiddelingsovereenkomst a) {
		hibernateTemplate.delete(a);
	}
	
	public List<Bemiddelingsovereenkomst> getAllBemiddelingsovereenkomst(){
		List<Bemiddelingsovereenkomst> alijst = (List<Bemiddelingsovereenkomst>) hibernateTemplate.loadAll(Bemiddelingsovereenkomst.class);
		return alijst;
	}

}
