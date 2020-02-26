package org.rfc.dao.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public abstract class TextFileDAO {
	
	protected File dbFile=null;
	protected String dbPath=null;
	protected char delimiter;
	protected String encoding=null;
	
	public TextFileDAO() {
		super();
	}
	
	public TextFileDAO(File dbFile) {
		super();
		this.dbFile=dbFile;
	}
	
	public TextFileDAO(File dbFile,char delimiter,String encoding) {
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
	
	protected BufferedReader getReader() throws UnsupportedEncodingException, FileNotFoundException {
		InputStreamReader isr=new InputStreamReader(new FileInputStream(dbFile),StandardCharsets.UTF_8);
		BufferedReader reader=new BufferedReader(isr);
		return reader;
	}
	
	protected String[] getFieldValues(String line) {
		String[] fieldValues=null;
		fieldValues=line.split(String.valueOf(delimiter));
		for(int i=0;i<fieldValues.length;i++) {
			fieldValues[i]=fieldValues[i].replace("\"", "");
		}
		return fieldValues;
	}
	
}
