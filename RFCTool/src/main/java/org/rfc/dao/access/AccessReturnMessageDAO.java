package org.rfc.dao.access;

import org.rfc.dao.ReturnMessageDAO;
import org.rfc.dto.ReturnMessage;

public class AccessReturnMessageDAO extends AccessDAO implements ReturnMessageDAO<ReturnMessage> {
	
	public AccessReturnMessageDAO() {
		super();
	}
	
	public AccessReturnMessageDAO(String dbPath) {
		super(dbPath);
	}
	
	public void removeAll() {
		// TODO Auto-generated method stub
		
	}

	public void save(ReturnMessage message) {
		// TODO Auto-generated method stub
		
	}

}
