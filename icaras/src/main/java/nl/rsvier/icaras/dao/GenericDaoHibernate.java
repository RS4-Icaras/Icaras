package nl.rsvier.icaras.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import nl.rsvier.icaras.core.IEntity;

public abstract class GenericDaoHibernate<T extends IEntity> implements IGenericDao<T> {
	
	private Class<T> type;
	private SessionFactory sessionFactory;
	private HibernateTemplate hibernateTemplate;//zorgt voor de boilerplate code voor de try catch blokken
	//voor de connectie met de database (inclusief exception handling)
	
	public GenericDaoHibernate(Class<T> type) {
		this.type = type;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	private Class<T> getType() {
		return this.type;
	}
	
	public void save(T entity) {
		hibernateTemplate.saveOrUpdate(entity);
	}
	
	public void update(T entity) {
		hibernateTemplate.saveOrUpdate(entity);
	}
	
	public void delete(T entity) {
		hibernateTemplate.delete(entity);
	}

	public T getById(int id) {
		return (T) hibernateTemplate.get(this.getType(), id);
	}
	
	/**
	 * Deze methode veronderstelt dat de naam van de klasse en de tabel waarin deze wordt
	 * opgeslagen identiek zijn. Override deze methode als dat niet het geval is.
	 */
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return (List<T>) hibernateTemplate.find("from " + getType().getName());
	}

}
