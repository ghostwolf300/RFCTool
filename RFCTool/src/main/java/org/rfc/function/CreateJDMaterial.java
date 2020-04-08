package org.rfc.function;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rfc.dto.Material;
import org.rfc.dto.PlantData;
import org.rfc.dto.SalesData;
import org.rfc.dto.ValuationData;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;


public class CreateJDMaterial extends SaveMaterialReplica {
	
	public static final String FUNCTION_NAME="CreateJDMaterial";
	private static final String PLANTS[][]= {
			{"0700","0000074210"},
			{"0702","0000074230"},
			{"0703","0000074250"},
			{"0704","0000074270"},
			{"0705","0000074290"},
			{"0706","0000074310"},
			{"0707","0000074320"},
			{"0708","0000074340"},
			{"0709","0000074210"},
			{"0710","0000074210"},
			{"0711","0000074380"},
			{"0712","0000074360"}
		};
	private static final Map<String,PlantData> PLANT_MAP=initPlantMap();
	private static final Map<String,ValuationData> VALUATION_DATA_MAP=initValuationDataMap();
	private static final List<SalesData> SALES_DATA_LIST=initSalesDataList();
	
	public CreateJDMaterial() {
		super();
	}
	
	public CreateJDMaterial(int id) {
		super(id);
	}
	
	private static Map<String,PlantData> initPlantMap(){
		Map<String,PlantData> plantMap=new HashMap<String,PlantData>();
		PlantData pd=null;
		String plant=null;
		for(int i=0;i<PLANTS.length;i++) {
			plant=PLANTS[i][0];
			pd=new PlantData();
			pd.setPlant(plant);
			pd.setProfitCenter(PLANTS[i][1]);
			pd.setPurchasingGroup("T09");
			pd.setMrpController("T09");
			pd.setGrProcessingTime(0);
			pd.setMrpType("PD");
			pd.setMinLotSize(0);
			pd.setReorderPoint(0);
			pd.setLotSizingProcedure("EX");
			pd.setProcurementType("F");
			if(plant.equals("0700")) {
				pd.setPlannedDeliveryTime(7);
			}
			else {
				pd.setPlannedDeliveryTime(2);
			}
			if(!plant.equals("0700")) {
				pd.setSpecialProcurement("40");
			}
			else {
				pd.setSpecialProcurement("");
			}
			if(plant.equals("0700")) {
				pd.setIssueStorageLocation("0701");
			}
			else {
				pd.setIssueStorageLocation("0700");
			}
			if(plant.equals("0700")) {
				pd.setStorageLocationForEP("0701");
			}
			else {
				pd.setStorageLocationForEP("0700");
			}
			pd.setPeriodIndicator("M");
			pd.setAvailabilityCheck("ZT");
			pd.setIndividualAndCollectiveReq("2");
			pd.setLoadingGroup("Z700");
			if(!plant.equals("0700")) {
				pd.setDoNotCost(true);
			}
			else {
				pd.setDoNotCost(false);
			}
			pd.setOriginCountry("DE");
			plantMap.put(plant, pd);
		}
		
		return plantMap;
	}
	
	private static Map<String,ValuationData> initValuationDataMap() {
		Map<String,ValuationData> valuationDataMap=new HashMap<String,ValuationData>();
		ValuationData vd=null;
		String valArea=null;
		for(int i=0;i<PLANTS.length;i++) {
			valArea=PLANTS[i][0];
			vd=new ValuationData();
			vd.setValuationArea(valArea);
			vd.setValuationClass("Z004");
			vd.setPriceControl("V");
			vd.setMovingAveragePrice(0.01);
			vd.setStandardPrice(0.01);
			vd.setPriceUnit(1);
			vd.setMaterialRelatedOrigin(true);
			vd.setCostWithQtyStructure(true);
			valuationDataMap.put(valArea, vd);
		}
		return valuationDataMap;
	}
	
	private static List<SalesData> initSalesDataList(){
		List<SalesData> salesDataList=new ArrayList<SalesData>();
		
		SalesData sd3=new SalesData();
		sd3.setSalesOrg("0700");
		sd3.setSalesChannel("70");
		sd3.setSalesDiv("00");
		sd3.setMaterialStatisticGroup("1");
		sd3.setItemCategoryGroup("NORM");
		sd3.setAccountAssignment("80");
		sd3.setDeliveringPlant("0700");
		salesDataList.add(sd3);
		
		SalesData sd1=new SalesData();
		sd1.setSalesOrg("0790");
		sd1.setSalesChannel("75");
		sd1.setSalesDiv("00");
		sd1.setMaterialStatisticGroup("1");
		sd1.setItemCategoryGroup("NORM");
		sd1.setAccountAssignment("81");
		sd1.setDeliveringPlant("0700");
		salesDataList.add(sd1);
		
		SalesData sd2=new SalesData();
		sd2.setSalesOrg("0799");
		sd2.setSalesChannel("99");
		sd2.setSalesDiv("00");
		sd2.setMaterialStatisticGroup("1");
		sd2.setItemCategoryGroup("NORM");
		sd2.setAccountAssignment("80");
		sd2.setDeliveringPlant("0700");
		salesDataList.add(sd2);
		
		return salesDataList;
	}
	
	public CreateJDMaterial(int id,JCoDestination destination) {
		super(id,destination);
	}
	
	public CreateJDMaterial(int id,List<Material> materials,JCoDestination destination) {
		super(id,materials,destination);
	}
	
	public CreateJDMaterial(int id,List<Material> materials,JCoDestination destination,boolean testRun) {
		super(id,materials,destination,testRun);
	}
	
	protected void executeFunction(Material material) throws JCoException {
		
		setHEADDATA(material);
		setCLIENTDATA(material);
		setMATERIALDESCRIPTION(material);
		setUNITSOFMEASURE(material);
		setSALESDATA(material);
		setTAXCLASSIFICATIONS(material);
		setFORECASTPARAMETERS(material,"0700");
		
		Set<String> plants=PLANT_MAP.keySet();
		
		//System.out.println(material.getMaterialId()+"\t"+material.getCnCode());
		
		for(String plant : plants) {
			PlantData pd=PLANT_MAP.get(plant);
			ValuationData vd=VALUATION_DATA_MAP.get(plant);
			setPLANTDATA(material,pd);
			setVALUATIONDATA(material,vd);
			setSTORAGELOCATIONDATA(material,plant);	
		}
		
		execute(material);
		
		clearTables();
		
	}
	
	private void setHEADDATA(Material m) {
		tHEADDATA.appendRow();
		tHEADDATA.setValue("FUNCTION","INS");
		tHEADDATA.setValue("MATERIAL",m.getMaterialId());
		tHEADDATA.setValue("IND_SECTOR", "M");
		tHEADDATA.setValue("MATL_TYPE","ZT02");
		tHEADDATA.setValue("BASIC_VIEW","X"); //K
		tHEADDATA.setValue("SALES_VIEW","X"); //V
		tHEADDATA.setValue("PURCHASE_VIEW","X"); //E
		tHEADDATA.setValue("MRP_VIEW","X");	//D
		tHEADDATA.setValue("STORAGE_VIEW", "X"); //L
		tHEADDATA.setValue("ACCOUNT_VIEW", "X"); //B
		tHEADDATA.setValue("FORECAST_VIEW", "X"); //P -missing
		tHEADDATA.setValue("QUALITY_VIEW", "X"); //Q
		tHEADDATA.setValue("COST_VIEW","X"); //G
	}
	
	private void setCLIENTDATA(Material m) {
		tCLIENTDATA.appendRow();
		tCLIENTDATA.setValue("FUNCTION", "INS");
		tCLIENTDATA.setValue("MATERIAL", m.getMaterialId());
		tCLIENTDATA.setValue("MATL_GROUP", m.getGroup());
		tCLIENTDATA.setValue("OLD_MAT_NO", m.getOldMaterialNumber());
		tCLIENTDATA.setValue("BASE_UOM", "ST");
		tCLIENTDATA.setValue("ITEM_CAT", "NORM");
		tCLIENTDATA.setValue("PUR_STATUS", "01"); //01=design, 03=tuotanto
		tCLIENTDATA.setValue("NET_WEIGHT", m.getNetWeight());
		tCLIENTDATA.setValue("UNIT_OF_WT", "KG");
		tCLIENTDATA.setValue("STOR_CONDS", "71");
		tCLIENTDATA.setValue("PUR_VALKEY", "T001");
		tCLIENTDATA.setValue("TRANS_GRP", "Z001");
		
		tCLIENTDATAX.appendRow();
		tCLIENTDATAX.setValue("FUNCTION", "INS");
		tCLIENTDATAX.setValue("MATERIAL", m.getMaterialId());
		tCLIENTDATAX.setValue("MATL_GROUP", "X");
		tCLIENTDATAX.setValue("OLD_MAT_NO", "X");
		tCLIENTDATAX.setValue("BASE_UOM", "X");
		tCLIENTDATAX.setValue("ITEM_CAT", "X");
		tCLIENTDATAX.setValue("PUR_STATUS", "X");
		tCLIENTDATAX.setValue("NET_WEIGHT", "X");
		tCLIENTDATAX.setValue("UNIT_OF_WT", "X");
		tCLIENTDATAX.setValue("STOR_CONDS", "X");
		tCLIENTDATAX.setValue("PUR_VALKEY", "X");
		tCLIENTDATAX.setValue("TRANS_GRP", "X");
	}
	
	private void setMATERIALDESCRIPTION(Material m) {
		tMATERIALDESCRIPTION.appendRow();
		tMATERIALDESCRIPTION.setValue("FUNCTION", "INS");
		tMATERIALDESCRIPTION.setValue("MATERIAL", m.getMaterialId());
		tMATERIALDESCRIPTION.setValue("LANGU", "E");
		tMATERIALDESCRIPTION.setValue("MATL_DESC", m.getDescription());
		
		tMATERIALDESCRIPTION.appendRow();
		tMATERIALDESCRIPTION.setValue("FUNCTION", "INS");
		tMATERIALDESCRIPTION.setValue("MATERIAL", m.getMaterialId());
		tMATERIALDESCRIPTION.setValue("LANGU", "U");
		tMATERIALDESCRIPTION.setValue("MATL_DESC", m.getDescription());
		
		tMATERIALDESCRIPTION.appendRow();
		tMATERIALDESCRIPTION.setValue("FUNCTION", "INS");
		tMATERIALDESCRIPTION.setValue("MATERIAL", m.getMaterialId());
		tMATERIALDESCRIPTION.setValue("LANGU", "V");
		tMATERIALDESCRIPTION.setValue("MATL_DESC", m.getDescription());
	}
	
	private void setUNITSOFMEASURE(Material m) {
		tUNITSOFMEASURE.appendRow();
		tUNITSOFMEASURE.setValue("FUNCTION", "INS");
		tUNITSOFMEASURE.setValue("MATERIAL", m.getMaterialId());
		tUNITSOFMEASURE.setValue("ALT_UNIT", "ST");
		tUNITSOFMEASURE.setValue("LENGTH", m.getLength());
		tUNITSOFMEASURE.setValue("WIDTH", m.getWidth());
		tUNITSOFMEASURE.setValue("HEIGHT", m.getHeight());
		tUNITSOFMEASURE.setValue("UNIT_DIM","MM");
		tUNITSOFMEASURE.setValue("VOLUMEUNIT", "CDM");
		tUNITSOFMEASURE.setValue("GROSS_WT", m.getGrossWeight());
		tUNITSOFMEASURE.setValue("UNIT_OF_WT", "KG");
		
		tUNITSOFMEASUREX.appendRow();
		tUNITSOFMEASUREX.setValue("FUNCTION", "INS");
		tUNITSOFMEASUREX.setValue("MATERIAL", m.getMaterialId());
		tUNITSOFMEASUREX.setValue("ALT_UNIT", "ST");
		tUNITSOFMEASUREX.setValue("LENGTH", "X");
		tUNITSOFMEASUREX.setValue("WIDTH", "X");
		tUNITSOFMEASUREX.setValue("HEIGHT", "X");
		tUNITSOFMEASUREX.setValue("UNIT_DIM","X");
		tUNITSOFMEASUREX.setValue("VOLUMEUNIT", "X");
		tUNITSOFMEASUREX.setValue("GROSS_WT", "X");
		tUNITSOFMEASUREX.setValue("UNIT_OF_WT", "X");
	}
	
	private void setSALESDATA(Material m) {
		for(SalesData sd : SALES_DATA_LIST) {
			tSALESDATA.appendRow();
			tSALESDATA.setValue("FUNCTION", "INS");
			tSALESDATA.setValue("MATERIAL", m.getMaterialId());
			tSALESDATA.setValue("SALES_ORG", sd.getSalesOrg());
			tSALESDATA.setValue("DISTR_CHAN", sd.getSalesChannel());
			tSALESDATA.setValue("MATL_STATS", sd.getMaterialStatisticGroup());
			tSALESDATA.setValue("ITEM_CAT", sd.getItemCategoryGroup());
			tSALESDATA.setValue("ACCT_ASSGT",sd.getAccountAssignment());
			tSALESDATA.setValue("DELYG_PLNT", sd.getDeliveringPlant());
			
			tSALESDATAX.appendRow();
			tSALESDATAX.setValue("FUNCTION", "INS");
			tSALESDATAX.setValue("MATERIAL", m.getMaterialId());
			tSALESDATAX.setValue("SALES_ORG", sd.getSalesOrg());
			tSALESDATAX.setValue("DISTR_CHAN", sd.getSalesChannel());
			tSALESDATAX.setValue("MATL_STATS", "X");
			tSALESDATAX.setValue("ITEM_CAT", "X");
			tSALESDATAX.setValue("ACCT_ASSGT","X");
			tSALESDATAX.setValue("DELYG_PLNT", "X");
		}
	}
	
	private void setTAXCLASSIFICATIONS(Material m) {
		tTAXCLASSIFICATIONS.appendRow();
		tTAXCLASSIFICATIONS.setValue("FUNCTION", "INS");
		tTAXCLASSIFICATIONS.setValue("MATERIAL", m.getMaterialId());
		tTAXCLASSIFICATIONS.setValue("DEPCOUNTRY", "FI");
		tTAXCLASSIFICATIONS.setValue("TAX_TYPE_1", "MWST");
		tTAXCLASSIFICATIONS.setValue("TAXCLASS_1","1");
	}
	
	private void setPLANTDATA(Material m,PlantData pd) {
		tPLANTDATA.appendRow();
		tPLANTDATA.setValue("FUNCTION", "INS");
		tPLANTDATA.setValue("MATERIAL", m.getMaterialId());
		tPLANTDATA.setValue("PLANT", pd.getPlant());
		tPLANTDATA.setValue("PROFIT_CTR", pd.getProfitCenter());
		tPLANTDATA.setValue("PUR_GROUP",pd.getPurchasingGroup());
		tPLANTDATA.setValue("GR_PR_TIME",pd.getGrProcessingTime());
		tPLANTDATA.setValue("MRP_TYPE",pd.getMrpType());
		tPLANTDATA.setValue("REORDER_PT",pd.getReorderPoint());
		tPLANTDATA.setValue("MRP_CTRLER", pd.getMrpController());
		tPLANTDATA.setValue("LOTSIZEKEY",pd.getLotSizingProcedure());
		tPLANTDATA.setValue("MINLOTSIZE", pd.getMinLotSize());
		tPLANTDATA.setValue("PROC_TYPE", pd.getProcurementType());
		tPLANTDATA.setValue("SPPROCTYPE", pd.getSpecialProcurement());
		tPLANTDATA.setValue("ISS_ST_LOC", pd.getIssueStorageLocation());
		tPLANTDATA.setValue("SLOC_EXPRC", pd.getStorageLocationForEP());
		tPLANTDATA.setValue("PLND_DELRY", pd.getPlannedDeliveryTime());
		tPLANTDATA.setValue("PERIOD_IND", pd.getPeriodIndicator());
		tPLANTDATA.setValue("AVAILCHECK", pd.getAvailabilityCheck());
		tPLANTDATA.setValue("DEP_REQ_ID", pd.getIndividualAndCollectiveReq());
		tPLANTDATA.setValue("LOADINGGRP", pd.getLoadingGroup());
		tPLANTDATA.setValue("NO_COSTING",(pd.isDoNotCost()==true ? "X" : " ") );
		tPLANTDATA.setValue("AUTO_RESET", "X");
		tPLANTDATA.setValue("COUNTRYORI", pd.getOriginCountry());
		tPLANTDATA.setValue("COMM_CODE", m.getCnCode());
		tPLANTDATA.setValue("SALES_VIEW","X"); //V
		tPLANTDATA.setValue("MRP_VIEW","X"); //D
		tPLANTDATA.setValue("PURCH_VIEW","X"); //E
		tPLANTDATA.setValue("STORAGE_VIEW","X"); //L
		tPLANTDATA.setValue("FORECAST_VIEW", "X"); //P
		tPLANTDATA.setValue("QUALITY_VIEW", "X"); //Q
		
		tPLANTDATAX.appendRow();
		tPLANTDATAX.setValue("FUNCTION", "INS");
		tPLANTDATAX.setValue("MATERIAL", m.getMaterialId());
		tPLANTDATAX.setValue("PLANT", pd.getPlant());
		tPLANTDATAX.setValue("PROFIT_CTR", "X");
		tPLANTDATAX.setValue("PUR_GROUP", "X");
		tPLANTDATAX.setValue("GR_PR_TIME","X");
		tPLANTDATAX.setValue("MRP_TYPE","X");
		tPLANTDATAX.setValue("REORDER_PT","X");
		tPLANTDATAX.setValue("MRP_CTRLER", "X");
		tPLANTDATAX.setValue("LOTSIZEKEY","X");
		tPLANTDATAX.setValue("MINLOTSIZE", "X");
		tPLANTDATAX.setValue("PROC_TYPE", "X");
		tPLANTDATAX.setValue("SPPROCTYPE", "X");
		tPLANTDATAX.setValue("ISS_ST_LOC", "X");
		tPLANTDATAX.setValue("SLOC_EXPRC", "X");
		tPLANTDATAX.setValue("PLND_DELRY", "X");
		tPLANTDATAX.setValue("PERIOD_IND", "X");
		tPLANTDATAX.setValue("AVAILCHECK", "X");
		tPLANTDATAX.setValue("DEP_REQ_ID", "X");
		tPLANTDATAX.setValue("LOADINGGRP", "X");
		tPLANTDATAX.setValue("NO_COSTING", "X");
		tPLANTDATAX.setValue("AUTO_RESET", "X");
		tPLANTDATAX.setValue("COUNTRYORI", "X");
		tPLANTDATAX.setValue("COMM_CODE", "X");
	}
	
	private void setVALUATIONDATA(Material m, ValuationData vd) {
		tVALUATIONDATA.appendRow();
		tVALUATIONDATA.setValue("FUNCTION", "INS");
		tVALUATIONDATA.setValue("MATERIAL", m.getMaterialId());
		tVALUATIONDATA.setValue("VAL_AREA", vd.getValuationArea());
		tVALUATIONDATA.setValue("PRICE_CTRL", vd.getPriceControl());
		tVALUATIONDATA.setValue("VAL_CLASS", vd.getValuationClass());
		tVALUATIONDATA.setValue("MOVING_PR", vd.getMovingAveragePrice());
		tVALUATIONDATA.setValue("STD_PRICE", vd.getStandardPrice());
		tVALUATIONDATA.setValue("PRICE_UNIT", vd.getPriceUnit());
		tVALUATIONDATA.setValue("QTY_STRUCT", (vd.isCostWithQtyStructure()==true ? "X" : " "));
		tVALUATIONDATA.setValue("ORIG_MAT", (vd.isMaterialRelatedOrigin()==true ? "X" : " "));
		tVALUATIONDATA.setValue("ACCOUNT_VIEW", "X");
		tVALUATIONDATA.setValue("COST_VIEW", "X");
		
		tVALUATIONDATAX.appendRow();
		tVALUATIONDATAX.setValue("FUNCTION", "INS");
		tVALUATIONDATAX.setValue("MATERIAL", m.getMaterialId());
		tVALUATIONDATAX.setValue("VAL_AREA", vd.getValuationArea());
		tVALUATIONDATAX.setValue("PRICE_CTRL", "X");
		tVALUATIONDATAX.setValue("VAL_CLASS", "X");
		tVALUATIONDATAX.setValue("MOVING_PR","X");
		tVALUATIONDATAX.setValue("STD_PRICE", "X");
		tVALUATIONDATAX.setValue("PRICE_UNIT","X");
		tVALUATIONDATAX.setValue("QTY_STRUCT", "X");
		tVALUATIONDATAX.setValue("ORIG_MAT", "X");
	}
	
	private void setSTORAGELOCATIONDATA(Material m,String plant) {
		tSTORAGELOCATIONDATA.appendRow();
		tSTORAGELOCATIONDATA.setValue("FUNCTION","INS");
		tSTORAGELOCATIONDATA.setValue("MATERIAL", m.getMaterialId());
		tSTORAGELOCATIONDATA.setValue("PLANT",plant);
		if(!plant.equals("0700")) {
			tSTORAGELOCATIONDATA.setValue("STGE_LOC", "0700");
		}
		else {
			tSTORAGELOCATIONDATA.setValue("STGE_LOC", "0701");
		}
		
		tSTORAGELOCATIONDATA.setValue("MRP_VIEW","X");
		tSTORAGELOCATIONDATA.setValue("STORAGE_VIEW","X");
		
		tSTORAGELOCATIONDATAX.appendRow();
		tSTORAGELOCATIONDATAX.setValue("FUNCTION","INS");
		tSTORAGELOCATIONDATAX.setValue("MATERIAL", m.getMaterialId());
		tSTORAGELOCATIONDATAX.setValue("PLANT",plant);
		if(!plant.equals("0700")) {
			tSTORAGELOCATIONDATAX.setValue("STGE_LOC", "0700");
		}
		else {
			tSTORAGELOCATIONDATAX.setValue("STGE_LOC", "0701");
		}
	}
	
	private void setFORECASTPARAMETERS(Material m,String plant) {
		
		tFORECASTPARAMETERS.appendRow();
		tFORECASTPARAMETERS.setValue("FUNCTION","INS");
		tFORECASTPARAMETERS.setValue("MATERIAL", m.getMaterialId());
		tFORECASTPARAMETERS.setValue("PLANT", plant);
		tFORECASTPARAMETERS.setValue("FORE_MODEL", "D");
		tFORECASTPARAMETERS.setValue("HIST_VALS", 60);
		tFORECASTPARAMETERS.setValue("FORE_PDS", 12);
		tFORECASTPARAMETERS.setValue("INITIALIZE", "X");
		tFORECASTPARAMETERS.setValue("TRACKLIMIT", 4.0);
		tFORECASTPARAMETERS.setValue("MODEL_SP", "2");
		
		tFORECASTPARAMETERSX.appendRow();
		tFORECASTPARAMETERSX.setValue("FUNCTION", "INS");
		tFORECASTPARAMETERSX.setValue("MATERIAL", m.getMaterialId());
		tFORECASTPARAMETERSX.setValue("PLANT", plant);
		tFORECASTPARAMETERSX.setValue("FORE_MODEL", "X");
		tFORECASTPARAMETERSX.setValue("HIST_VALS", "X");
		tFORECASTPARAMETERSX.setValue("FORE_PDS", "X");
		tFORECASTPARAMETERSX.setValue("INITIALIZE", "X");
		tFORECASTPARAMETERSX.setValue("TRACKLIMIT", "X");
		tFORECASTPARAMETERSX.setValue("MODEL_SP", "X");
	}
	
	private void setEXTENSIONIN(Material m) {
		
	}
	
	private void clearTables() {
		tHEADDATA.clear();
		tMATERIALDESCRIPTION.clear();
		tUNITSOFMEASURE.clear();
		tUNITSOFMEASUREX.clear();
		tCLIENTDATA.clear();
		tCLIENTDATAX.clear();
		tSALESDATA.clear();
		tSALESDATAX.clear();
		tTAXCLASSIFICATIONS.clear();
		tPLANTDATA.clear();
		tPLANTDATAX.clear();
		tVALUATIONDATA.clear();
		tVALUATIONDATAX.clear();
		tSTORAGELOCATIONDATA.clear();
		tSTORAGELOCATIONDATAX.clear();
		tFORECASTPARAMETERS.clear();
		tFORECASTPARAMETERSX.clear();
		tRETURNMESSAGES.clear();
	}

	@Override
	public String getFunctionName() {
		return FUNCTION_NAME;
	}

}
