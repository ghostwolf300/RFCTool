package org.rfc.dto;

public class PlantData extends DataObject {
	
	private Material material=null;
	private FieldValue<String> materialId=null;
	private FieldValue<String> plant=null;
	//General Plant
	private FieldValue<String> profitCenter=null;
	private FieldValue<String> loadingGroup=null;
	//Purchasing & MRP
	private FieldValue<String> purchasingGroup=null;
	private FieldValue<Integer> grProcessingTime=null;
	private FieldValue<String> mrpType=null;
	private FieldValue<Integer> reorderPoint=null;
	private FieldValue<String> mrpController=null;
	private FieldValue<String> lotSizingProcedure=null;
	private FieldValue<Integer> minLotSize=null;
	private FieldValue<String> procurementType=null;
	private FieldValue<String> specialProcurement=null;
	private FieldValue<String> issueStorageLocation=null;
	private FieldValue<String> storageLocationForEP=null;
	private FieldValue<Integer> plannedDeliveryTime=null;
	private FieldValue<String> periodIndicator=null;
	private FieldValue<String> availabilityCheck=null;
	private FieldValue<String> individualAndCollectiveReq=null;
	//Accounting & Costing
	private FieldValue<String> priceControl=null;
	private FieldValue<String> valuationClass=null;
	private FieldValue<Double> movingAveragePrice=null;
	private FieldValue<Double> standardPrice=null;
	private FieldValue<Integer> priceUnit=null;
	private FieldValue<Boolean> doNotCost=null;
	private FieldValue<Boolean> costWithQtyStructure=null;
	private FieldValue<Boolean> materialRelatedOrigin=null;
	//Storage Location
	private FieldValue<String> storageLocation=null;
	
	public PlantData() {
		super();
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
		this.materialId =material.getMaterialId();
	}

	public FieldValue<String> getMaterialId() {
		return materialId;
	}

	public void setMaterialId(FieldValue<String> materialId) {
		this.materialId = materialId;
	}

	public FieldValue<String> getPlant() {
		return plant;
	}

	public void setPlant(FieldValue<String> plant) {
		this.plant = plant;
	}

	public FieldValue<String> getProfitCenter() {
		return profitCenter;
	}

	public void setProfitCenter(FieldValue<String> profitCenter) {
		this.profitCenter = profitCenter;
	}

	public FieldValue<String> getLoadingGroup() {
		return loadingGroup;
	}

	public void setLoadingGroup(FieldValue<String> loadingGroup) {
		this.loadingGroup = loadingGroup;
	}

	public FieldValue<String> getPurchasingGroup() {
		return purchasingGroup;
	}

	public void setPurchasingGroup(FieldValue<String> purchasingGroup) {
		this.purchasingGroup = purchasingGroup;
	}

	public FieldValue<Integer> getGrProcessingTime() {
		return grProcessingTime;
	}

	public void setGrProcessingTime(FieldValue<Integer> grProcessingTime) {
		this.grProcessingTime = grProcessingTime;
	}

	public FieldValue<String> getMrpType() {
		return mrpType;
	}

	public void setMrpType(FieldValue<String> mrpType) {
		this.mrpType = mrpType;
	}

	public FieldValue<Integer> getReorderPoint() {
		return reorderPoint;
	}

	public void setReorderPoint(FieldValue<Integer> reorderPoint) {
		this.reorderPoint = reorderPoint;
	}

	public FieldValue<String> getMrpController() {
		return mrpController;
	}

	public void setMrpController(FieldValue<String> mrpController) {
		this.mrpController = mrpController;
	}

	public FieldValue<String> getLotSizingProcedure() {
		return lotSizingProcedure;
	}

	public void setLotSizingProcedure(FieldValue<String> lotSizingProcedure) {
		this.lotSizingProcedure = lotSizingProcedure;
	}

	public FieldValue<Integer> getMinLotSize() {
		return minLotSize;
	}

	public void setMinLotSize(FieldValue<Integer> minLotSize) {
		this.minLotSize = minLotSize;
	}

	public FieldValue<String> getProcurementType() {
		return procurementType;
	}

	public void setProcurementType(FieldValue<String> procurementType) {
		this.procurementType = procurementType;
	}

	public FieldValue<String> getIssueStorageLocation() {
		return issueStorageLocation;
	}

	public void setIssueStorageLocation(FieldValue<String> issueStorageLocation) {
		this.issueStorageLocation = issueStorageLocation;
	}

	public FieldValue<String> getStorageLocationForEP() {
		return storageLocationForEP;
	}

	public void setStorageLocationForEP(FieldValue<String> storageLocationForEP) {
		this.storageLocationForEP = storageLocationForEP;
	}

	public FieldValue<String> getPeriodIndicator() {
		return periodIndicator;
	}

	public void setPeriodIndicator(FieldValue<String> periodIndicator) {
		this.periodIndicator = periodIndicator;
	}

	public FieldValue<String> getAvailabilityCheck() {
		return availabilityCheck;
	}

	public void setAvailabilityCheck(FieldValue<String> availabilityCheck) {
		this.availabilityCheck = availabilityCheck;
	}

	public FieldValue<String> getIndividualAndCollectiveReq() {
		return individualAndCollectiveReq;
	}

	public void setIndividualAndCollectiveReq(FieldValue<String> individualAndCollectiveReq) {
		this.individualAndCollectiveReq = individualAndCollectiveReq;
	}

	public FieldValue<Integer> getPlannedDeliveryTime() {
		return plannedDeliveryTime;
	}

	public void setPlannedDeliveryTime(FieldValue<Integer> plannedDeliveryTime) {
		this.plannedDeliveryTime = plannedDeliveryTime;
	}

	public FieldValue<String> getSpecialProcurement() {
		return specialProcurement;
	}

	public void setSpecialProcurement(FieldValue<String> specialProcurement) {
		this.specialProcurement = specialProcurement;
	}

	public FieldValue<String> getPriceControl() {
		return priceControl;
	}

	public void setPriceControl(FieldValue<String> priceControlIndicator) {
		this.priceControl = priceControlIndicator;
	}

	public FieldValue<String> getValuationClass() {
		return valuationClass;
	}

	public void setValuationClass(FieldValue<String> valuationClass) {
		this.valuationClass = valuationClass;
	}

	public FieldValue<Double> getMovingAveragePrice() {
		return movingAveragePrice;
	}

	public void setMovingAveragePrice(FieldValue<Double> movingAveragePrice) {
		this.movingAveragePrice = movingAveragePrice;
	}

	public FieldValue<Double> getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(FieldValue<Double> standardPrice) {
		this.standardPrice = standardPrice;
	}

	public FieldValue<Integer> getPriceUnit() {
		return priceUnit;
	}

	public void setPriceUnit(FieldValue<Integer> priceUnit) {
		this.priceUnit = priceUnit;
	}

	public FieldValue<Boolean> isDoNotCost() {
		return doNotCost;
	}

	public void setDoNotCost(FieldValue<Boolean> doNotCost) {
		this.doNotCost = doNotCost;
	}

	public FieldValue<Boolean> isCostWithQtyStructure() {
		return costWithQtyStructure;
	}

	public void setCostWithQtyStructure(FieldValue<Boolean> costWithQtyStructure) {
		this.costWithQtyStructure = costWithQtyStructure;
	}

	public FieldValue<Boolean> isMaterialRelatedOrigin() {
		return materialRelatedOrigin;
	}

	public void setMaterialRelatedOrigin(FieldValue<Boolean> materialRelatedOrigin) {
		this.materialRelatedOrigin = materialRelatedOrigin;
	}

	public FieldValue<String> getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(FieldValue<String> storageLocation) {
		this.storageLocation = storageLocation;
	}
	
}
