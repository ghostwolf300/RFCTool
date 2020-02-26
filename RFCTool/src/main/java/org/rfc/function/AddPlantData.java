package org.rfc.function;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rfc.dto.FieldValue;
import org.rfc.dto.InputField;
import org.rfc.dto.Material;
import org.rfc.dto.Material3;
import org.rfc.dto.PlantData;
import org.rfc.dto.PlantData3;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;


public class AddPlantData extends SaveMaterialReplica {
	
	public static final String FUNCTION_NAME="AddPlantData";
	
	public static final Map<String,InputField<?>> FIELD_MAP=initInputFieldMap();
	
	private static Map<String,InputField<?>> initInputFieldMap(){
		Map<String,InputField<?>> map=new HashMap<String,InputField<?>>();
		
		InputField<String> materialId=new InputField<String>("MATERIAL",null,"MaterialId",true);
		InputField<String> plant=new InputField<String>("PLANT",null,"Plant",true);
		InputField<String> profitCenter=new InputField<String>("",null,"ProfitCenter",true);
		
		map.put("MATERIAL",materialId);
		map.put("PLANT",plant);
		
		return Collections.unmodifiableMap(map);
	}
	
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
		Map<String,JCoStructure> structureMap=super.getMaterialPlantData(material.getMaterialId().getValue(), "0700");
		copyPlantData(structureMap,material);
		
		tHEADDATA.appendRow();
		tHEADDATA.setValue("FUNCTION","INS");
		tHEADDATA.setValue("MATERIAL",material.getMaterialId().getValue());
		
		Set<String> plants=material.getPlantDataMap().keySet();
		
		for(String plant : plants) {
			PlantData pd=material.getPlantDataMap().get(plant);
			tPLANTDATA.appendRow();
			tPLANTDATA.setValue("FUNCTION", "INS");
			tPLANTDATA.setValue("MATERIAL", material.getMaterialId().getValue());
			//---Plant
			tPLANTDATA.setValue("PLANT", pd.getPlant().getValue());
			tPLANTDATA.setValue("PROFIT_CTR",pd.getProfitCenter().getValue());
			tPLANTDATA.setValue("SALES_VIEW", "X");
			//---Purchasting & MRP
			tPLANTDATA.setValue("PUR_GROUP", pd.getPurchasingGroup().getValue());
			tPLANTDATA.setValue("GR_PR_TIME",pd.getGrProcessingTime().getValue());
			tPLANTDATA.setValue("MRP_TYPE",pd.getMrpType().getValue());
			tPLANTDATA.setValue("REORDER_PT",pd.getReorderPoint().getValue());
			tPLANTDATA.setValue("MRP_CTRLER", pd.getMrpController().getValue());
			tPLANTDATA.setValue("LOTSIZEKEY",pd.getLotSizingProcedure().getValue());
			tPLANTDATA.setValue("MINLOTSIZE", pd.getMinLotSize().getValue());
			tPLANTDATA.setValue("PROC_TYPE", pd.getProcurementType().getValue());
			tPLANTDATA.setValue("SPPROCTYPE", pd.getSpecialProcurement().getValue());
			tPLANTDATA.setValue("ISS_ST_LOC", pd.getIssueStorageLocation().getValue());
			tPLANTDATA.setValue("SLOC_EXPRC", pd.getStorageLocationForEP().getValue());
			tPLANTDATA.setValue("PLND_DELRY", pd.getPlannedDeliveryTime().getValue());
			tPLANTDATA.setValue("PERIOD_IND", pd.getPeriodIndicator().getValue());
			tPLANTDATA.setValue("AVAILCHECK", pd.getAvailabilityCheck().getValue());
			tPLANTDATA.setValue("DEP_REQ_ID", pd.getIndividualAndCollectiveReq().getValue());
			tPLANTDATA.setValue("PURCH_VIEW","X");
			tPLANTDATA.setValue("MRP_VIEW", "X");
			//---Accounting & Costing
			tPLANTDATA.setValue("NO_COSTING", (pd.isDoNotCost().getValue()==true ? "X" : " "));
			tPLANTDATA.setValue("ACCOUNT_VIEW","X");
			tPLANTDATA.setValue("COST_VIEW","X");

			tPLANTDATAX.appendRow();
			tPLANTDATAX.setValue("FUNCTION", "INS");
			tPLANTDATAX.setValue("MATERIAL", pd.getMaterialId().getValue());
			tPLANTDATAX.setValue("PLANT", pd.getPlant().getValue());
			tPLANTDATAX.setValue("PROFIT_CTR","X");
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
			tSTORAGELOCATIONDATA.setValue("MATERIAL", material.getMaterialId().getValue());
			tSTORAGELOCATIONDATA.setValue("PLANT",pd.getPlant().getValue());
			tSTORAGELOCATIONDATA.setValue("STGE_LOC", pd.getStorageLocation().getValue());
			tSTORAGELOCATIONDATA.setValue("MRP_VIEW","X");
			tSTORAGELOCATIONDATA.setValue("STORAGE_VIEW","X");
			
			tSTORAGELOCATIONDATAX.appendRow();
			tSTORAGELOCATIONDATAX.setValue("FUNCTION","INS");
			tSTORAGELOCATIONDATAX.setValue("MATERIAL", material.getMaterialId().getValue());
			tSTORAGELOCATIONDATAX.setValue("PLANT",pd.getPlant().getValue());
			tSTORAGELOCATIONDATAX.setValue("STGE_LOC", pd.getStorageLocation().getValue());
			
			//---Accounting & Costing
			tVALUATIONDATA.appendRow();
			tVALUATIONDATA.setValue("FUNCTION", "INS");
			tVALUATIONDATA.setValue("MATERIAL", material.getMaterialId().getValue());
			tVALUATIONDATA.setValue("VAL_AREA", pd.getPlant().getValue());
			tVALUATIONDATA.setValue("PRICE_CTRL", pd.getPriceControl().getValue());
			tVALUATIONDATA.setValue("VAL_CLASS", pd.getValuationClass().getValue());
			tVALUATIONDATA.setValue("MOVING_PR", pd.getMovingAveragePrice().getValue());
			tVALUATIONDATA.setValue("STD_PRICE", pd.getStandardPrice().getValue());
			tVALUATIONDATA.setValue("PRICE_UNIT", pd.getPriceUnit().getValue());
			tVALUATIONDATA.setValue("QTY_STRUCT", (pd.isCostWithQtyStructure().getValue()==true ? "X" : " "));
			tVALUATIONDATA.setValue("ORIG_MAT", (pd.isMaterialRelatedOrigin().getValue()==true ? "X" : " "));
			tVALUATIONDATA.setValue("ACCOUNT_VIEW", "X");
			tVALUATIONDATA.setValue("COST_VIEW", "X");
			
			tVALUATIONDATAX.appendRow();
			tVALUATIONDATAX.setValue("FUNCTION", "INS");
			tVALUATIONDATAX.setValue("MATERIAL", material.getMaterialId().getValue());
			tVALUATIONDATAX.setValue("VAL_AREA", pd.getPlant().getValue());
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
		
		processedCount++;
		progressUpdated();
	}
	
	private void copyPlantData(Map<String,JCoStructure> structureMap,Material material) {
		JCoStructure sCLIENTDATA=structureMap.get("CLIENTDATA");
		JCoStructure sPLANTDATA=structureMap.get("PLANTDATA");
		JCoStructure sVALUATIONDATA=structureMap.get("VALUATIONDATA");
		
		FieldValue<String> industrySector=null;
		material.setIndustrySector(sCLIENTDATA.getString("IND_SECTOR"));
		material.setType(sCLIENTDATA.getString("MATL_TYPE"));
		material.setGroup(sCLIENTDATA.getString("MATL_GROUP"));
		material.setBaseUom(sCLIENTDATA.getString("BASE_UOM"));
		
		
		Set<String> plants=material.getPlantDataMap().keySet();
		for(String plant : plants) {
			PlantData3 pd=material.getPlantDataMap().get(plant);
			pd.setPurchasingGroup(sPLANTDATA.getString("PUR_GROUP"));
			pd.setMrpController(sPLANTDATA.getString("MRP_CTRLER"));
			
			pd.setValuationClass(sVALUATIONDATA.getString("VAL_CLASS"));
			pd.setPriceControl(sVALUATIONDATA.getString("PRICE_CTRL"));
			pd.setMovingAveragePrice(sVALUATIONDATA.getDouble("MOVING_PR"));
			pd.setStandardPrice(sVALUATIONDATA.getDouble("STD_PRICE"));
			
			pd.setDoNotCost((pd.getMaterial().getType().equals("ZT07") ? true : false));
			
		}
		
	}

	@Override
	public String getFunctionName() {
		return FUNCTION_NAME;
	}


}
