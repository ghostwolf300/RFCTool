package org.rfc.dao.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class ExcelDAO {
	
	protected File dbFile=null;
	protected String dbPath=null;
	protected Workbook workbook=null;
	
	public ExcelDAO() {
		super();
	}
	
	public ExcelDAO(String dbPath) {
		super();
		dbFile=new File(dbPath);
	}
	
	public ExcelDAO(File dbFile) {
		super();
		this.dbFile=dbFile;
	}

	public String getDbPath() {
		return dbFile.getAbsolutePath();
	}

	public void setDbPath(String dbPath) {
		dbFile=new File(dbPath);
	}
	
	public void openConnection() throws IOException {
		FileInputStream fis=new FileInputStream(dbFile);
		workbook=new XSSFWorkbook(fis);
	}
	
	public void closeConnection() throws IOException {
		if(workbook!=null) {
			workbook.close();
		}
	}
}
