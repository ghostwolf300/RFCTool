package org.rfc.dao.access;

import org.rfc.dao.DAOFactory;
import org.rfc.dao.MaterialDAO;
import org.rfc.dao.ReturnMessageDAO;
import org.rfc.dto.Material;
import org.rfc.dto.ReturnMessage;

public class AccessDAOFactory implements DAOFactory {
	
	private String dbPath=null;
	
	public AccessDAOFactory() {
		super();
	}
	
	public AccessDAOFactory(String dbPath) {
		super();
		this.dbPath=dbPath;
	}
	
	public String getDbPath() {
		return dbPath;
	}

	public void setDbPath(String dbPath) {
		this.dbPath = dbPath;
	}

	public MaterialDAO<Material> getMaterialDAO() {
		return new AccessMaterialDAO(dbPath);
	}

	public ReturnMessageDAO<ReturnMessage> getReturnMessageDAO() {
		return new AccessReturnMessageDAO(dbPath);
	}

}
