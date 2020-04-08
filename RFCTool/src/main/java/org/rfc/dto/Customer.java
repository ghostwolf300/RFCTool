package org.rfc.dto;

public class Customer extends AbstractDataObject {
	
	private String bpId;
	private String organisation;
	private String distributionChannel;
	private String division;
	private String priceGroup;
	private String statisticsGroup;
	private boolean orderCombination;
	private String shippingCondition;
	private String incoTerms;
	private String incoLocation;
	private String paymentTerm;
	private String accountAssignment;
	
	public Customer() {
		super();
	}
	
	public String getBpId() {
		return bpId;
	}
	public void setBpId(String bpId) {
		this.bpId = bpId;
	}
	public String getOrganisation() {
		return organisation;
	}
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}
	public String getDistributionChannel() {
		return distributionChannel;
	}
	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getPriceGroup() {
		return priceGroup;
	}
	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
	}
	public String getStatisticsGroup() {
		return statisticsGroup;
	}
	public void setStatisticsGroup(String statisticsGroup) {
		this.statisticsGroup = statisticsGroup;
	}
	public boolean isOrderCombination() {
		return orderCombination;
	}
	public void setOrderCombination(boolean orderCombination) {
		this.orderCombination = orderCombination;
	}
	public String getShippingCondition() {
		return shippingCondition;
	}
	public void setShippingCondition(String shippingCondition) {
		this.shippingCondition = shippingCondition;
	}
	public String getIncoTerms() {
		return incoTerms;
	}
	public void setIncoTerms(String incoTerms) {
		this.incoTerms = incoTerms;
	}
	public String getIncoLocation() {
		return incoLocation;
	}
	public void setIncoLocation(String incoLocation) {
		this.incoLocation = incoLocation;
	}
	public String getPaymentTerm() {
		return paymentTerm;
	}
	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}
	public String getAccountAssignment() {
		return accountAssignment;
	}
	public void setAccountAssignment(String accountAssignment) {
		this.accountAssignment = accountAssignment;
	}
	
}
