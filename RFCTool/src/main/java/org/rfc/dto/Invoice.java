package org.rfc.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
	
	private String invoiceDoc;
	private Date documentDate;
	private Date postingDate;
	private String reference;
	private String invoicingParty;
	private double grossAmount;
	private double unplannedDeliveryCost;
	private double vatAmount;
	private String taxCode;
	private String payTerms;
	private Date baselineDate;
	private List<InvoiceItem> items=null;
	
	
	public Invoice() {
		
	}
	
	public Invoice(String invoiceDoc) {
		this.invoiceDoc=invoiceDoc;
	}
	
	public Invoice(String invoiceDoc,List<InvoiceItem> items) {
		this.invoiceDoc=invoiceDoc;
		this.items=items;
	}

	public String getInvoiceDoc() {
		return invoiceDoc;
	}

	public void setInvoiceDoc(String invoiceDoc) {
		this.invoiceDoc = invoiceDoc;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public Date getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getInvoicingParty() {
		return invoicingParty;
	}

	public void setInvoicingParty(String invoicingParty) {
		this.invoicingParty = invoicingParty;
	}

	public double getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(double grossAmount) {
		this.grossAmount = grossAmount;
	}

	public double getUnplannedDeliveryCost() {
		return unplannedDeliveryCost;
	}

	public void setUnplannedDeliveryCost(double unplannedDeliveryCost) {
		this.unplannedDeliveryCost = unplannedDeliveryCost;
	}

	public double getVatAmount() {
		return vatAmount;
	}

	public void setVatAmount(double vatAmount) {
		this.vatAmount = vatAmount;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getPayTerms() {
		return payTerms;
	}

	public void setPayTerms(String payTerms) {
		this.payTerms = payTerms;
	}

	public Date getBaselineDate() {
		return baselineDate;
	}

	public void setBaselineDate(Date baselineDate) {
		this.baselineDate = baselineDate;
	}

	public List<InvoiceItem> getItems() {
		return items;
	}

	public void setItems(List<InvoiceItem> items) {
		this.items = items;
	}
	
	public void addItem(InvoiceItem item) {
		if(items==null) {
			items=new ArrayList<InvoiceItem>();
		}
		items.add(item);
	}
	
}
