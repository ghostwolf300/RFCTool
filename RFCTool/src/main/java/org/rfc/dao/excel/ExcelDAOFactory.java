package org.rfc.dao.excel;

import org.rfc.dao.DAOFactory;
import org.rfc.dao.MaterialDAO;
import org.rfc.dao.ReturnMessageDAO;
import org.rfc.dto.Material;
import org.rfc.dto.ReturnMessage;

public class ExcelDAOFactory implements DAOFactory {
	
	private String dbPath=null;
	
	public ExcelDAOFactory() {
		super();
	}
	
	public ExcelDAOFactory(String dbPath) {
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
		return new ExcelMaterialDAO(dbPath);
	}

	public ReturnMessageDAO<ReturnMessage> getReturnMessageDAO() {
		return new ExcelReturnMessageDAO(dbPath);
	}

	

}
