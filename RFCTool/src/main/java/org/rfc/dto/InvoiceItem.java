package org.rfc.dto;

public class InvoiceItem {
	
	private long invItemNumber;
	private String poNumber;
	private long poItemNumber;
	private String debitCreditIndicator;
	private String taxCode;
	private double invItemAmount;
	private double invItemQty;
	private String poUnit;
	private double poPriceQty;
	private String poPriceUom;
	private String itemText;
	private String goodsReceipt;
	private long refItem;
	private boolean grBased=false;
	
	public InvoiceItem() {
		
	}
	
	public InvoiceItem(long InvItemNumber,double invItemAmount,double invItemQty,String poNumber,long poItemNumber,String poUnit,String goodsReceipt, long refItem) {
		this.invItemNumber=InvItemNumber;
		this.invItemAmount=invItemAmount;
		this.invItemQty=invItemQty;
		this.poNumber=poNumber;
		this.poItemNumber=poItemNumber;
		this.poUnit=poUnit;
		this.goodsReceipt=goodsReceipt;
		this.refItem=refItem;
		grBased=true;
	}
	
	public InvoiceItem(long InvItemNumber,double invItemAmount,double invItemQty,String poNumber,long poItemNumber,String poUnit) {
		this.invItemNumber=InvItemNumber;
		this.invItemAmount=invItemAmount;
		this.invItemQty=invItemQty;
		this.poNumber=poNumber;
		this.poItemNumber=poItemNumber;
		this.poUnit=poUnit;
		grBased=false;
	}
	
	public long getInvItemNumber() {
		return invItemNumber;
	}

	public void setInvItemNumber(long invItemNumber) {
		this.invItemNumber = invItemNumber;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public long getPoItemNumber() {
		return poItemNumber;
	}

	public void setPoItemNumber(long poItemNumber) {
		this.poItemNumber = poItemNumber;
	}

	public String getDebitCreditIndicator() {
		return debitCreditIndicator;
	}

	public void setDebitCreditIndicator(String debitCreditIndicator) {
		this.debitCreditIndicator = debitCreditIndicator;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public double getInvItemAmount() {
		return invItemAmount;
	}

	public void setInvItemAmount(double invItemAmount) {
		this.invItemAmount = invItemAmount;
	}

	public double getInvItemQty() {
		return invItemQty;
	}

	public void setInvItemQty(double invItemQty) {
		this.invItemQty = invItemQty;
	}

	public String getPoUnit() {
		return poUnit;
	}

	public void setPoUnit(String poUnit) {
		this.poUnit = poUnit;
	}

	public double getPoPriceQty() {
		return poPriceQty;
	}

	public void setPoPriceQty(double poPriceQty) {
		this.poPriceQty = poPriceQty;
	}

	public String getPoPriceUom() {
		return poPriceUom;
	}

	public void setPoPriceUom(String poPriceUom) {
		this.poPriceUom = poPriceUom;
	}

	public String getItemText() {
		return itemText;
	}

	public void setItemText(String itemText) {
		this.itemText = itemText;
	}

	public String getGoodsReceipt() {
		return goodsReceipt;
	}

	public void setGoodsReceipt(String goodsReceipt) {
		this.goodsReceipt = goodsReceipt;
	}

	public long getRefItem() {
		return refItem;
	}

	public void setRefItem(long refItem) {
		this.refItem = refItem;
	}

	public boolean isGrBased() {
		return grBased;
	}

	public void setGrBased(boolean grBased) {
		this.grBased = grBased;
	}
	
	
	
	
}
