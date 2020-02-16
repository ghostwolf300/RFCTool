package org.rfc.dao;

public interface ReturnMessageDAO<ReturnMessage> {
	
	public void removeAll();
	public void save(ReturnMessage message);
	
}
