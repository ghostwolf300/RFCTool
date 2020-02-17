package org.rfc.dao.excel;

import java.io.File;

import org.rfc.dao.ReturnMessageDAO;
import org.rfc.dto.ReturnMessage;

public class ExcelReturnMessageDAO extends ExcelDAO implements ReturnMessageDAO<ReturnMessage> {
	
	public ExcelReturnMessageDAO() {
		super();
	}
	
	public ExcelReturnMessageDAO(String dbPath) {
		super(dbPath);
	}
	
	public ExcelReturnMessageDAO(File dbFile) {
		super(dbFile);
	}
	
	public void removeAll() {
		// TODO Auto-generated method stub
		
	}

	public void save(ReturnMessage message) {
		// TODO Auto-generated method stub
		
	}

}
