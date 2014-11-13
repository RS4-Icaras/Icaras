package nl.rsvier.icaras.core;

public class InvalidBusinessKeyException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidBusinessKeyException() {
		super();
	}

	public InvalidBusinessKeyException(String msg) {
		super(msg);
	}

	/*
	 * TODO: Remove dead code public InvalidBusinessKey(String
	 * msg, IEntity entity) { super(msg); this.setEntity(entity); }
	 * 
	 * private IEntity entity;
	 * 
	 * public IEntity getEntity() { return entity; }
	 * 
	 * public void setEntity(IEntity entity) { this.entity = entity; }
	 */

}
