package org.rfc.dao;

import java.util.List;

import org.rfc.dto.JDInvoice;
import org.rfc.dto.JDInvoiceAmount;
import org.rfc.dto.JDInvoiceItem;

public interface JDInvoiceDAO {
	public JDInvoice getInvoice();
	public void saveInvoiceHeader(List<JDInvoice> invoices);
	public void saveInvoiceItems(List<JDInvoiceItem> items);
	public void saveInvoiceAmounts(List<JDInvoiceAmount> amounts);
}
