package org.rfc.dto;

import java.sql.Date;

public class POItem {
	private String poNumber;
	private long itemNumber;
	private String deleteIndicator;
	private Date changedOn;
	private String shortText;
	private String material;
	private String purchMaterial;
	private String infoRecord;
	private String vendorMaterial;
	private double quantity;
	private String unit;
	private String orderPriceUnit;
	private double netPrice;
	private String taxCode;
	private String customer;
	
	public POItem() {
		
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public long getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(long itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getDeleteIndicator() {
		return deleteIndicator;
	}

	public void setDeleteIndicator(String deleteIndicator) {
		this.deleteIndicator = deleteIndicator;
	}

	public Date getChangedOn() {
		return changedOn;
	}

	public void setChangedOn(Date changedOn) {
		this.changedOn = changedOn;
	}

	public String getShortText() {
		return shortText;
	}

	public void setShortText(String shortText) {
		this.shortText = shortText;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getPurchMaterial() {
		return purchMaterial;
	}

	public void setPurchMaterial(String purchMaterial) {
		this.purchMaterial = purchMaterial;
	}

	public String getInfoRecord() {
		return infoRecord;
	}

	public void setInfoRecord(String infoRecord) {
		this.infoRecord = infoRecord;
	}

	public String getVendorMaterial() {
		return vendorMaterial;
	}

	public void setVendorMaterial(String vendorMaterial) {
		this.vendorMaterial = vendorMaterial;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getOrderPriceUnit() {
		return orderPriceUnit;
	}

	public void setOrderPriceUnit(String orderPriceUnit) {
		this.orderPriceUnit = orderPriceUnit;
	}

	public double getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(double netPrice) {
		this.netPrice = netPrice;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
	
	
}
