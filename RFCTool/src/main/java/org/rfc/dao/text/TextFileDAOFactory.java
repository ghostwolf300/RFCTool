package org.rfc.dao.text;

import java.io.File;

import org.rfc.dao.DAOFactory;
import org.rfc.dao.MaterialDAO;
import org.rfc.dao.ReturnMessageDAO;
import org.rfc.dto.Material;
import org.rfc.dto.ReturnMessage;

public class TextFileDAOFactory implements DAOFactory {
	
	private File dbFile=null;
	private String dbPath=null;
	private char delimiter;
	private String encoding=null;
	
	public TextFileDAOFactory() {
		super();
	}
	
	public TextFileDAOFactory(File dbFile) {
		super();
		this.dbFile=dbFile;
	}
	
	public TextFileDAOFactory(File dbFile,char delimiter,String encoding) {
		super();
		this.dbFile=dbFile;
		this.delimiter=delimiter;
		this.encoding=encoding;
	}
	
	public char getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public File getDbFile() {
		return dbFile;
	}

	public void setDbFile(File dbFile) {
		this.dbFile = dbFile;
	}

	public String getDbPath() {
		return dbPath;
	}

	public void setDbPath(String dbPath) {
		this.dbPath = dbPath;
	}

	@Override
	public MaterialDAO<Material> getMaterialDAO() {
		return new TextFileMaterialDAO(dbFile,';',"utf-8");
	}

	@Override
	public ReturnMessageDAO<ReturnMessage> getReturnMessageDAO() {
		// TODO Auto-generated method stub
		return null;
	}

}
