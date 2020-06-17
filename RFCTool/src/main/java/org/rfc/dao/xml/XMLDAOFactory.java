package org.rfc.dao.xml;

import java.io.File;

import org.rfc.dao.DAOFactory;
import org.rfc.dao.InvoiceDAO;
import org.rfc.dao.JDInvoiceDAO;
import org.rfc.dao.MaterialDAO;
import org.rfc.dao.PODAO;
import org.rfc.dao.ReturnMessageDAO;
import org.rfc.dto.Invoice;
import org.rfc.dto.Material;
import org.rfc.dto.PurchaseOrder;
import org.rfc.dto.ReturnMessage;

public class XMLDAOFactory implements DAOFactory {
	
	private File dbFile=null;
	
	public XMLDAOFactory() {
		super();
	}
	
	public XMLDAOFactory(File dbFile) {
		this.dbFile=dbFile;
	}
	
	public File getDbFile() {
		return dbFile;
	}

	public void setDbFile(File dbFile) {
		this.dbFile = dbFile;
	}

	@Override
	public MaterialDAO<Material> getMaterialDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PODAO<PurchaseOrder> getPODAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReturnMessageDAO<ReturnMessage> getReturnMessageDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvoiceDAO<Invoice> getInvoiceDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JDInvoiceDAO getJDInvoiceDAO() {
		return new XMLJDInvoiceDAO(dbFile);
	}

}
