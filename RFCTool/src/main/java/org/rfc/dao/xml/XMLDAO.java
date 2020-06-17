package org.rfc.dao.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public abstract class XMLDAO {
	
	protected File dbFile=null;
	protected Document doc=null;
	
	public XMLDAO() {
		super();
	}
	
	public XMLDAO(File dbFile) {
		this.dbFile=dbFile;
		this.createDocument();
	}
	
	public File getDbFile() {
		return dbFile;
	}

	public void setDbFile(File dbFile) {
		this.dbFile = dbFile;
		this.createDocument();
	}

	protected void createDocument() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		DocumentBuilder builder=null;
		try {
			builder=factory.newDocumentBuilder();
			doc=builder.parse(dbFile);
			doc.getDocumentElement().normalize();
		} 
		catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
