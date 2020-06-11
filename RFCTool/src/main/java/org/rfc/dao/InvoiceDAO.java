package org.rfc.dao;

import java.util.List;

public interface InvoiceDAO<Invoice> {
	
	public List<Invoice> getInvoices();
	
}
