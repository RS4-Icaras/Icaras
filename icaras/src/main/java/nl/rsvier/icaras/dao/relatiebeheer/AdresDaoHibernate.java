package nl.rsvier.icaras.dao.relatiebeheer;

import java.util.List;

import nl.rsvier.icaras.core.relatiebeheer.Adres;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("IAdresDao")//Deze annotatie zorgt ook voor (aanmelding voor) translatie van exceptions naar Spring
public class AdresDaoHibernate implements IAdresDao {

	private SessionFactory sessionFactory;
	private HibernateTemplate hibernateTemplate;//zorgt voor de boilerplate code voor de try catch blokken
	//voor de connectie met de database (inclusief exception handling)

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void saveAdres(Adres adres) {
		hibernateTemplate.saveOrUpdate(adres);		
	}

	public void updateAdres(Adres adres) {
		hibernateTemplate.saveOrUpdate(adres);		
	}

	public void deleteAdres(Adres adres) {
		hibernateTemplate.delete(adres);		
	}
	
	public Adres getAdresById(int adresId) {
		Adres adres = hibernateTemplate.load(Adres.class, adresId);
		return adres;
		
	}

	public List<Adres> getAllAdressen() {
		List<Adres> adressenlijst = (List<Adres>) hibernateTemplate.loadAll(Adres.class);
		return adressenlijst;
	}

}
