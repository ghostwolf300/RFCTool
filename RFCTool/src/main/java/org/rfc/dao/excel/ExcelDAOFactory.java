package org.rfc.dao.excel;

import java.io.File;

import org.rfc.dao.DAOFactory;
import org.rfc.dao.MaterialDAO;
import org.rfc.dao.ReturnMessageDAO;
import org.rfc.dto.Material;
import org.rfc.dto.Material3;
import org.rfc.dto.ReturnMessage;

public class ExcelDAOFactory implements DAOFactory {
	
	private String dbPath=null;
	private File dbFile=null;
	
	public ExcelDAOFactory() {
		super();
	}
	
	public ExcelDAOFactory(String dbPath) {
		super();
		this.dbPath=dbPath;
	}
	
	public ExcelDAOFactory(File dbFile) {
		super();
		this.dbFile=dbFile;
	}
	
	public String getDbPath() {
		return dbPath;
	}

	public void setDbPath(String dbPath) {
		this.dbPath = dbPath;
	}
	
	public File getDbFile() {
		return dbFile;
	}

	public void setDbFile(File dbFile) {
		this.dbFile = dbFile;
	}

	public MaterialDAO<Material> getMaterialDAO() {
		return new ExcelMaterialDAO(dbFile);
	}

	public ReturnMessageDAO<ReturnMessage> getReturnMessageDAO() {
		return new ExcelReturnMessageDAO(dbFile);
	}

	

}
