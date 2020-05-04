package org.rfc.dto;

import java.sql.Date;
import java.util.List;

public class PurchaseOrder {
	
	private String poNumber;
	private String documentType;
	private Date createdOn;
	private String createdBy;
	private long itemInterval;
	private long lastItem;
	private String vendor;
	private String paymentTerm;
	private String purchOrg;
	private String purchGroup;
	private String currency;
	private Date documentDate;
	private List<POItem> items;
	
	public PurchaseOrder() {
		
	}
	
	public PurchaseOrder(String poNumber) {
		this.poNumber=poNumber;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public long getItemInterval() {
		return itemInterval;
	}

	public void setItemInterval(long itemInterval) {
		this.itemInterval = itemInterval;
	}

	public long getLastItem() {
		return lastItem;
	}

	public void setLastItem(long lastItem) {
		this.lastItem = lastItem;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public String getPurchOrg() {
		return purchOrg;
	}

	public void setPurchOrg(String purchOrg) {
		this.purchOrg = purchOrg;
	}

	public String getPurchGroup() {
		return purchGroup;
	}

	public void setPurchGroup(String purchGroup) {
		this.purchGroup = purchGroup;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public List<POItem> getItems() {
		return items;
	}

	public void setItems(List<POItem> items) {
		this.items = items;
	}
	
}
