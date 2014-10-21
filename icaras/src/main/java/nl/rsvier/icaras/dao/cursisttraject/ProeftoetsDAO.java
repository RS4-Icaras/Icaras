package nl.rsvier.icaras.dao.cursisttraject;

import java.util.List;

import nl.rsvier.icaras.core.cursisttraject.Proeftoets;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("proeftoetsDAO")
public class ProeftoetsDAO implements IProeftoetsDAO {

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
       
    public Proeftoets findProeftoetsByID(long id) {
		Proeftoets p = hibernateTemplate.load(Proeftoets.class, id);
		return p;
    }
   
    public void updateProeftoets(Proeftoets proeftoets) {
    	hibernateTemplate.saveOrUpdate(proeftoets);
    }
    
    public void saveProeftoets(Proeftoets proeftoets) {
    	hibernateTemplate.saveOrUpdate(proeftoets);
    }
   
    public void deleteProeftoets(Proeftoets proeftoets) {
    	hibernateTemplate.delete(proeftoets);
    }
    
	public List<Proeftoets> getAllProeftoetsen() {
		List<Proeftoets> proeftoetsenlijst = (List<Proeftoets>) hibernateTemplate.loadAll(Proeftoets.class);
		return proeftoetsenlijst;
	}
}