package org.rfc.dto;

import java.util.ArrayList;
import java.util.List;

public class JDInvoice {
	
	private String invoiceNo;
	private List<JDInvoiceAmount> invoiceAmounts=null;
	private List<JDInvoiceItem> invoiceItems=null;
	
	public JDInvoice() {
		super();
	}
	
	public JDInvoice(String invoiceNo) {
		this.invoiceNo=invoiceNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public List<JDInvoiceAmount> getInvoiceAmounts() {
		return invoiceAmounts;
	}

	public void setInvoiceAmounts(List<JDInvoiceAmount> invoiceAmounts) {
		this.invoiceAmounts = invoiceAmounts;
	}
	
	public void addInvoiceAmount(JDInvoiceAmount amount) {
		if(invoiceAmounts==null) {
			invoiceAmounts=new ArrayList<JDInvoiceAmount>();
		}
		invoiceAmounts.add(amount);
		
	}

	public List<JDInvoiceItem> getInvoiceItems() {
		return invoiceItems;
	}

	public void setInvoiceItems(List<JDInvoiceItem> invoiceItems) {
		this.invoiceItems = invoiceItems;
	}
	
	public void addInvoiceItem(JDInvoiceItem item) {
		if(invoiceItems==null) {
			invoiceItems=new ArrayList<JDInvoiceItem>();
		}
		invoiceItems.add(item);
	}
	
}
