package org.rfc.dto;

public class ValuationData extends AbstractDataObject {
	
	private String valuationArea;
	private String priceControl;
	private String valuationClass;
	private double movingAveragePrice;
	private double standardPrice;
	private int priceUnit;
	private boolean costWithQtyStructure=false;
	private boolean materialRelatedOrigin=false;
	
	public ValuationData() {
		super();
	}

	public String getValuationArea() {
		return valuationArea;
	}

	public void setValuationArea(String valuationArea) {
		this.valuationArea = valuationArea;
	}

	public String getPriceControl() {
		return priceControl;
	}

	public void setPriceControl(String priceControl) {
		this.priceControl = priceControl;
	}

	public String getValuationClass() {
		return valuationClass;
	}

	public void setValuationClass(String valuationClass) {
		this.valuationClass = valuationClass;
	}

	public double getMovingAveragePrice() {
		return movingAveragePrice;
	}

	public void setMovingAveragePrice(double movingAveragePrice) {
		this.movingAveragePrice = movingAveragePrice;
	}

	public double getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(double standardPrice) {
		this.standardPrice = standardPrice;
	}

	public int getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(int priceUnit) {
		this.priceUnit = priceUnit;
	}

	public boolean isCostWithQtyStructure() {
		return costWithQtyStructure;
	}

	public void setCostWithQtyStructure(boolean costWithQtyStructure) {
		this.costWithQtyStructure = costWithQtyStructure;
	}

	public boolean isMaterialRelatedOrigin() {
		return materialRelatedOrigin;
	}

	public void setMaterialRelatedOrigin(boolean materialRelatedOrigin) {
		this.materialRelatedOrigin = materialRelatedOrigin;
	}
	
	
}
