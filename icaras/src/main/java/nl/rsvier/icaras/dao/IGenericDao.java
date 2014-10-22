package nl.rsvier.icaras.dao;

import java.util.List;

import nl.rsvier.icaras.core.IEntity;

public interface IGenericDao<T extends IEntity> {
	
	public void save(T entity);
	
	public void update(T entity);
	
	public void delete(T entity);
	
	public T getById(int id);
	
	public List<T> getAll();

}
