package org.rfc.dto;

public class JDInvoiceAmount {
	
	private String invoiceNo;
	private String text;
	private double amount;
	
	public JDInvoiceAmount() {
		super();
	}
	
	public JDInvoiceAmount(String invoiceNo,String text,double amount) {
		super();
		this.invoiceNo=invoiceNo;
		this.text=text;
		this.amount=amount;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
