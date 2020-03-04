package org.rfc.function;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rfc.dto.FieldValue;
import org.rfc.dto.InputField;
import org.rfc.dto.InputFieldManager;
import org.rfc.dto.Material;
import org.rfc.dto.PlantData;
import org.rfc.dto.ValuationData;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;


public class AddPlantData extends SaveMaterialReplica {
	
	public static final String FUNCTION_NAME="AddPlantData";
	
	public AddPlantData() {
		super();
	}
	
	public AddPlantData(int id) {
		super(id);
	}
	
	public AddPlantData(int id,JCoDestination destination) {
		super(id,destination);
	}
	
	public AddPlantData(int id,List<Material> materials,JCoDestination destination) {
		super(id,materials,destination);
	}
	
	public AddPlantData(int id,List<Material> materials,JCoDestination destination,boolean testRun) {
		super(id,materials,destination,testRun);
	}
	
	protected void executeFunction(Material material) throws JCoException {
		
		Map<String,JCoStructure> structureMap=super.getMaterialPlantData(material.getMaterialId(), "0700");
		copyPlantData(structureMap,material);
		
		tHEADDATA.appendRow();
		tHEADDATA.setValue("FUNCTION","UPD");
		tHEADDATA.setValue("MATERIAL",material.getMaterialId());
		tHEADDATA.setValue("SALES_VIEW","X");
		tHEADDATA.setValue("PURCHASE_VIEW","X");
		tHEADDATA.setValue("MRP_VIEW","X");
		tHEADDATA.setValue("ACCOUNT_VIEW", "X");
		tHEADDATA.setValue("COST_VIEW", "X");
		
		Set<String> plants=material.getPlantDataMap().keySet();
		
		for(String plant : plants) {
			PlantData pd=material.getPlantDataMap().get(plant);
			
			
			tPLANTDATA.appendRow();
			tPLANTDATA.setValue("FUNCTION", "INS");
			tPLANTDATA.setValue("MATERIAL", material.getMaterialId());
			tPLANTDATA.setValue("PLANT", pd.getPlant());
			tPLANTDATA.setValue("PROFIT_CTR", pd.getProfitCenter());
			tPLANTDATA.setValue("LOADINGGRP", pd.getLoadingGroup());
			tPLANTDATA.setValue("COUNTRYORI",pd.getOriginCountry());
			tPLANTDATA.setValue("COMM_CODE", pd.getCommodityCode());
			
			//---Purchasting & MRP
			tPLANTDATA.setValue("PUR_GROUP",pd.getPurchasingGroup());
			tPLANTDATA.setValue("GR_PR_TIME",pd.getGrProcessingTime());
			tPLANTDATA.setValue("MRP_TYPE","PD");
			tPLANTDATA.setValue("REORDER_PT",0);
			tPLANTDATA.setValue("MRP_CTRLER", pd.getMrpController());
			tPLANTDATA.setValue("LOTSIZEKEY",pd.getLotSizingProcedure());
			tPLANTDATA.setValue("MINLOTSIZE", pd.getMinLotSize());
			tPLANTDATA.setValue("PROC_TYPE", pd.getProcurementType());
			tPLANTDATA.setValue("SPPROCTYPE", "40");
			tPLANTDATA.setValue("ISS_ST_LOC", "0700");
			tPLANTDATA.setValue("SLOC_EXPRC", "0700");
			tPLANTDATA.setValue("PLND_DELRY", 2);
			tPLANTDATA.setValue("PERIOD_IND", pd.getPeriodIndicator());
			tPLANTDATA.setValue("AVAILCHECK", pd.getAvailabilityCheck());
			tPLANTDATA.setValue("DEP_REQ_ID", pd.getIndividualAndCollectiveReq());
			tPLANTDATA.setValue("NO_COSTING", "X");
			tPLANTDATA.setValue("SALES_VIEW","X");
			tPLANTDATA.setValue("MRP_VIEW","X");
			tPLANTDATA.setValue("PURCH_VIEW","X");
			tPLANTDATA.setValue("ACCOUNT_VIEW","X");
			tPLANTDATA.setValue("COST_VIEW","X");
			
			tPLANTDATAX.appendRow();
			tPLANTDATAX.setValue("FUNCTION", "INS");
			tPLANTDATAX.setValue("MATERIAL", pd.getMaterialId());
			tPLANTDATAX.setValue("PLANT", pd.getPlant());
			tPLANTDATAX.setValue("PROFIT_CTR", "X");
			tPLANTDATAX.setValue("LOADINGGRP", "X");
			tPLANTDATAX.setValue("COUNTRYORI","X");
			tPLANTDATAX.setValue("COMM_CODE", "X");
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
			tPLANTDATAX.setValue("NO_COSTING", "X");
			
			//---Storage Location
			tSTORAGELOCATIONDATA.appendRow();
			tSTORAGELOCATIONDATA.setValue("FUNCTION","INS");
			tSTORAGELOCATIONDATA.setValue("MATERIAL", material.getMaterialId());
			tSTORAGELOCATIONDATA.setValue("PLANT",pd.getPlant());
			tSTORAGELOCATIONDATA.setValue("STGE_LOC", "0700");
			tSTORAGELOCATIONDATA.setValue("MRP_VIEW","X");
			tSTORAGELOCATIONDATA.setValue("STORAGE_VIEW","X");
			
			tSTORAGELOCATIONDATAX.appendRow();
			tSTORAGELOCATIONDATAX.setValue("FUNCTION","INS");
			tSTORAGELOCATIONDATAX.setValue("MATERIAL", material.getMaterialId());
			tSTORAGELOCATIONDATAX.setValue("PLANT",pd.getPlant());
			tSTORAGELOCATIONDATAX.setValue("STGE_LOC", "0700");
			
			
			
		}
		
		//---Accounting & Costing
		Set<String> valAreas=material.getValuationDataMap().keySet();
		for(String valArea : valAreas) {
			ValuationData vd=material.getValuationDataMap().get(valArea);
			tVALUATIONDATA.appendRow();
			tVALUATIONDATA.setValue("FUNCTION", "INS");
			tVALUATIONDATA.setValue("MATERIAL", material.getMaterialId());
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
			tVALUATIONDATAX.setValue("MATERIAL", material.getMaterialId());
			tVALUATIONDATAX.setValue("VAL_AREA", vd.getValuationArea());
			tVALUATIONDATAX.setValue("PRICE_CTRL", "X");
			tVALUATIONDATAX.setValue("VAL_CLASS", "X");
			tVALUATIONDATAX.setValue("MOVING_PR","X");
			tVALUATIONDATAX.setValue("STD_PRICE", "X");
			tVALUATIONDATAX.setValue("PRICE_UNIT","X");
			tVALUATIONDATAX.setValue("QTY_STRUCT", "X");
			tVALUATIONDATAX.setValue("ORIG_MAT", "X");
		}
		
		execute(material);
		
		tHEADDATA.clear();
		tPLANTDATA.clear();
		tPLANTDATAX.clear();
		tSTORAGELOCATIONDATA.clear();
		tSTORAGELOCATIONDATAX.clear();
		tVALUATIONDATA.clear();
		tVALUATIONDATAX.clear();
		tRETURNMESSAGES.clear();
		
	}
	
	@Override
	public String getFunctionName() {
		return FUNCTION_NAME;
	}
	
}
