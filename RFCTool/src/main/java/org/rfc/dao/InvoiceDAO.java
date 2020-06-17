package org.rfc.dao;

import java.util.List;

import org.rfc.dto.JDInvoice;

public interface InvoiceDAO<Invoice> {
	
	public List<Invoice> getInvoices();
	
}
