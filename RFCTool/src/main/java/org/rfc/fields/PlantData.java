package org.rfc.fields;

public enum PlantData implements RFCField {
	
	FUNCTION(new FieldName("FUNCTION")),
	MATERIAL(new FieldName("MATERIAL","MaterialId","MARC-MATNR")),
	PLANT(new FieldName("PLANT","Plant","MARC-WERKS")),
	PROFIT_CTR(new FieldName("PROFIT_CTR","ProfitCenter","MARC-PRCTR")),
	PUR_GROUP(new FieldName("PUR_GROUP","PurchasingGroup","MARC-EKGRP")),
	GR_PR_TIME(new FieldName("GR_PR_TIME","GrProcessingTime","MARC-WEBAZ")),
	MRP_TYPE(new FieldName("MRP_TYPE","MrpType","MARC-DISMM")),
	REORDER_PT(new FieldName("REORDER_PT","ReorderPoint","MINBE")),
	MRP_CTRLER(new FieldName("MRP_CTRLER","MrpController","MARC-DISPO")),
	LOTSIZEKEY(new FieldName("LOTSIZEKEY","LotSizingProcedure","MARC-DISLS")),
	MINLOTSIZE(new FieldName("MINLOTSIZE","MinLotSize","MARC-BSTMI")),
	PROC_TYPE(new FieldName("PROC_TYPE","ProcurementType","MARC-BESKZ")),
	SPPROCTYPE(new FieldName("SPPROCTYPE","SpecialProcurement","MARC-SOBSL")),
	ISS_ST_LOC(new FieldName("ISS_ST_LOC","IssueStorageLocation","MARC-LGPRO")),
	SLOC_EXPRC(new FieldName("SLOC_EXPRC","StorageLocationForEP","MARC-LGFSB")),
	PLND_DELRY(new FieldName("PLND_DELRY","PlannedDeliveryTime","MARC-PLIFZ")),
	PERIOD_IND(new FieldName("PERIOD_IND","PeriodIndicator","MARC-PERKZ")),
	AVAILCHECK(new FieldName("AVAILCHECK","AvailabilityCheck","MARC-MTVFP")),
	DEP_REQ_ID(new FieldName("DEP_REQ_ID","IndividualAndCollectiveReq","MARC-SBDKZ")),
	NO_COSTING(new FieldName("NO_COSTING","DoNotCost","MARC-NCOST")),
	SALES_VIEW(new FieldName("SALES_VIEW")),
	PURCH_VIEW(new FieldName("PURCH_VIEW")),
	MRP_VIEW(new FieldName("MRP_VIEW")),
	ACCOUNT_VIEW(new FieldName("ACCOUNT_VIEW")),
	COST_VIEW(new FieldName("COST_VIEW")),
	STORAGE_VIEW(new FieldName("STORAGE_VIEW"));
	
	private final FieldName field;
	
	PlantData(FieldName field) {
		this.field=field;
	}

	@Override
	public String getRFCName() {
		return field.getRfcName();
	}

	@Override
	public String getMethodName() {
		return field.getMethodName();
	}

	@Override
	public String getSAPName() {
		return field.getSapName();
	}
	
}
