package nl.rsvier.icaras.dao.intake;

import java.util.List;

import nl.rsvier.icaras.core.intake.Aanmelder;
import nl.rsvier.icaras.core.intake.Arbeidsovereenkomst;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;
//import nl.rsvier.icaras.util.intake.HibernateUtil;

@Repository("IArbeidsovereenkomstDao")
public class ArbeidsovereenkomstDAO implements IArbeidsovereenkomstDAO {
	
	private SessionFactory sessionFactory;
	private HibernateTemplate hibernateTemplate;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	};

	public Arbeidsovereenkomst findArbeidsovereenkomst(long id) {
		Arbeidsovereenkomst a = hibernateTemplate.load(Arbeidsovereenkomst.class, id);
		return a;
		
	}

	public void persistArbeidsovereenkomst(Arbeidsovereenkomst a) {
		hibernateTemplate.saveOrUpdate(a);	
	}

	public void updateArbeidsovereenkomst(Arbeidsovereenkomst a) {
		hibernateTemplate.saveOrUpdate(a);
	}

	public void deleteArbeidsovereenkomst(Arbeidsovereenkomst a) {
		hibernateTemplate.delete(a);
	}
	
	public List<Arbeidsovereenkomst> getAllArbeidsovereenkomst(){
		List<Arbeidsovereenkomst> alijst = (List<Arbeidsovereenkomst>) hibernateTemplate.loadAll(Arbeidsovereenkomst.class);
		return alijst;
	}

}
