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
	private double totalMerchandise;
	private double surcharge;
	private double grossAmount;
	private double unplannedDeliveryCost;
	private double vatAmount;
	private String taxCode;
	private String payTerms;
	private Date baselineDate;
	private String vendorInvoice;
	private boolean approved;
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

	public double getTotalMerchandise() {
		return totalMerchandise;
	}

	public void setTotalMerchandise(double totalMerchandise) {
		this.totalMerchandise = totalMerchandise;
	}

	public double getSurcharge() {
		return surcharge;
	}

	public void setSurcharge(double surcharge) {
		this.surcharge = surcharge;
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
	
	public String getVendorInvoice() {
		return vendorInvoice;
	}

	public void setVendorInvoice(String vendorInvoice) {
		this.vendorInvoice = vendorInvoice;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public void addItem(InvoiceItem item) {
		if(items==null) {
			items=new ArrayList<InvoiceItem>();
		}
		items.add(item);
	}
	
	public boolean itemsMatchTotalMerchandise() {
		boolean match=false;
		double valueSum=0.0;
		if(items!=null) {
			for(InvoiceItem item : items) {
				valueSum=+item.getInvItemAmount();
			}
			if(valueSum==totalMerchandise) {
				match=true;
			}
		}
		return match;
	}
	
	public double getItemAmountSum() {
		double valueSum=0.0;
		if(items!=null) {
			for(InvoiceItem item : items) {
				valueSum=valueSum+item.getInvItemAmount();
			}
		}
		return valueSum;
	}
	
	public boolean canApprove() {
		boolean approve=false;
		double itemSum=getItemAmountSum();
		double currentAmount=itemSum+unplannedDeliveryCost+vatAmount;
		System.out.println(currentAmount+"\t"+grossAmount);
		if(Math.abs(grossAmount-currentAmount)<0.01) {
			approve=true;
		}
		
		return approve;
	}
	
	
}
