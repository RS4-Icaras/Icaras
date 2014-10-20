package nl.rsvier.icaras.dao.intake;

import java.util.List;
import nl.rsvier.icaras.core.intake.Scholingsovereenkomst;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("IScholingsovereenkomstDao")
public class ScholingsovereenkomstDAO implements IScholingsovereenkomstDAO {
	
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

	public Scholingsovereenkomst findScholingsovereenkomst(long id) {
		Scholingsovereenkomst a = hibernateTemplate.load(Scholingsovereenkomst.class, id);
		return a;
	}

	public void persistScholingsovereenkomst(Scholingsovereenkomst a) {
		hibernateTemplate.saveOrUpdate(a);	
	}

	public void updateScholingsovereenkomst(Scholingsovereenkomst a) {
		hibernateTemplate.saveOrUpdate(a);
	}

	public void deleteScholingsovereenkomst(Scholingsovereenkomst a) {
		hibernateTemplate.delete(a);
	}
	
	public List<Scholingsovereenkomst> getAllScholingsovereenkomst(){
		List<Scholingsovereenkomst> alijst = (List<Scholingsovereenkomst>) hibernateTemplate.loadAll(Scholingsovereenkomst.class);
		return alijst;
	}

}
