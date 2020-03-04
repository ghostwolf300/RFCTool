package org.rfc.dto;

public class PlantData extends AbstractDataObject {
	
	private Material material=null;
	private String materialId=null;
	private String plant=null;
	//General Plant
	private String profitCenter=null;
	private String loadingGroup=null;
	private String originCountry=null;
	private String commodityCode=null;
	//Purchasing & MRP
	private String purchasingGroup=null;
	private int grProcessingTime;
	private String mrpType=null;
	private int reorderPoint;
	private String mrpController=null;
	private String lotSizingProcedure=null;
	private int minLotSize;
	private String procurementType=null;
	private String specialProcurement=null;
	private String issueStorageLocation=null;
	private String storageLocationForEP=null;
	private int plannedDeliveryTime;
	private String periodIndicator=null;
	private String availabilityCheck=null;
	private String individualAndCollectiveReq=null;
	//finance related
	private boolean doNotCost=false;
	//storage location
	private String storageLocation=null;
	
	public PlantData() {
		super();
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
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

	public String getProfitCenter() {
		return profitCenter;
	}

	public void setProfitCenter(String profitCenter) {
		this.profitCenter = profitCenter;
	}

	public String getLoadingGroup() {
		return loadingGroup;
	}

	public void setLoadingGroup(String loadingGroup) {
		this.loadingGroup = loadingGroup;
	}

	public String getOriginCountry() {
		return originCountry;
	}

	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public String getPurchasingGroup() {
		return purchasingGroup;
	}

	public void setPurchasingGroup(String purchasingGroup) {
		this.purchasingGroup = purchasingGroup;
	}

	public int getGrProcessingTime() {
		return grProcessingTime;
	}

	public void setGrProcessingTime(int grProcessingTime) {
		this.grProcessingTime = grProcessingTime;
	}

	public String getMrpType() {
		return mrpType;
	}

	public void setMrpType(String mrpType) {
		this.mrpType = mrpType;
	}

	public int getReorderPoint() {
		return reorderPoint;
	}

	public void setReorderPoint(int reorderPoint) {
		this.reorderPoint = reorderPoint;
	}

	public String getMrpController() {
		return mrpController;
	}

	public void setMrpController(String mrpController) {
		this.mrpController = mrpController;
	}

	public String getLotSizingProcedure() {
		return lotSizingProcedure;
	}

	public void setLotSizingProcedure(String lotSizingProcedure) {
		this.lotSizingProcedure = lotSizingProcedure;
	}

	public int getMinLotSize() {
		return minLotSize;
	}

	public void setMinLotSize(int minLotSize) {
		this.minLotSize = minLotSize;
	}

	public String getProcurementType() {
		return procurementType;
	}

	public void setProcurementType(String procurementType) {
		this.procurementType = procurementType;
	}

	public String getSpecialProcurement() {
		return specialProcurement;
	}

	public void setSpecialProcurement(String specialProcurement) {
		this.specialProcurement = specialProcurement;
	}

	public String getIssueStorageLocation() {
		return issueStorageLocation;
	}

	public void setIssueStorageLocation(String issueStorageLocation) {
		this.issueStorageLocation = issueStorageLocation;
	}

	public String getStorageLocationForEP() {
		return storageLocationForEP;
	}

	public void setStorageLocationForEP(String storageLocationForEP) {
		this.storageLocationForEP = storageLocationForEP;
	}

	public int getPlannedDeliveryTime() {
		return plannedDeliveryTime;
	}

	public void setPlannedDeliveryTime(int plannedDeliveryTime) {
		this.plannedDeliveryTime = plannedDeliveryTime;
	}

	public String getPeriodIndicator() {
		return periodIndicator;
	}

	public void setPeriodIndicator(String periodIndicator) {
		this.periodIndicator = periodIndicator;
	}

	public String getAvailabilityCheck() {
		return availabilityCheck;
	}

	public void setAvailabilityCheck(String availabilityCheck) {
		this.availabilityCheck = availabilityCheck;
	}

	public String getIndividualAndCollectiveReq() {
		return individualAndCollectiveReq;
	}

	public void setIndividualAndCollectiveReq(String individualAndCollectiveReq) {
		this.individualAndCollectiveReq = individualAndCollectiveReq;
	}

	public boolean isDoNotCost() {
		return doNotCost;
	}

	public void setDoNotCost(boolean doNotCost) {
		this.doNotCost = doNotCost;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	
	
}
