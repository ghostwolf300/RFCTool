package org.rfc.dto;

public class PlantData {
	
	private String materialId=null;
	private String plant=null;
	private int plannedDeliveryTime;
	private String specialProcurement=null;
	
	public PlantData() {
		super();
	}

	public String getMaterialId() {
		return materialId;
	}

	public void setMaterialId(String materialId) {
		this.materialId = materialId;
	}

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public int getPlannedDeliveryTime() {
		return plannedDeliveryTime;
	}

	public void setPlannedDeliveryTime(int plannedDeliveryTime) {
		this.plannedDeliveryTime = plannedDeliveryTime;
	}

	public String getSpecialProcurement() {
		return specialProcurement;
	}

	public void setSpecialProcurement(String specialProcurement) {
		this.specialProcurement = specialProcurement;
	}
	
	
	
}
