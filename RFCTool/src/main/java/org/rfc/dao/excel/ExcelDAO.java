package org.rfc.dao.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class ExcelDAO {
	
	protected String dbPath=null;
	protected Workbook workbook=null;
	
	public ExcelDAO() {
		super();
	}
	
	public ExcelDAO(String dbPath) {
		super();
		this.dbPath=dbPath;
	}

	public String getDbPath() {
		return dbPath;
	}

	public void setDbPath(String dbPath) {
		this.dbPath = dbPath;
	}
	
	public void openConnection() throws IOException {
		FileInputStream fis=new FileInputStream(new File(dbPath));
		workbook=new XSSFWorkbook(fis);
	}
	
	public void closeConnection() throws IOException {
		if(workbook!=null) {
			workbook.close();
		}
	}
}
