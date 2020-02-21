package org.rfc.function;


import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rfc.dto.InputField;
import org.rfc.dto.Material3;
import org.rfc.dto.PlantData3;

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
	
	public AddPlantData(int id,List<Material3> material3s,JCoDestination destination) {
		super(id,material3s,destination);
	}
	
	public AddPlantData(int id,List<Material3> material3s,JCoDestination destination,boolean testRun) {
		super(id,material3s,destination,testRun);
	}
	
	protected void executeFunction(Material3 material3) throws JCoException {
		Map<String,JCoStructure> structureMap=super.getMaterialPlantData(material3.getMaterialId(), "0700");
		copyPlantData(structureMap,material3);
		
		tHEADDATA.appendRow();
		tHEADDATA.setValue("FUNCTION","INS");
		tHEADDATA.setValue("MATERIAL",material3.getMaterialId());
		
		Set<String> plants=material3.getPlantDataMap().keySet();
		
		for(String plant : plants) {
			PlantData3 plantData3=material3.getPlantDataMap().get(plant);
			tPLANTDATA.appendRow();
			tPLANTDATA.setValue("FUNCTION", "INS");
			tPLANTDATA.setValue("MATERIAL", material3.getMaterialId());
			tPLANTDATA.setValue("PLANT", plantData3.getPlant());
			tPLANTDATA.setValue("PROFIT_CTR",plantData3.getProfitCenter());
			tPLANTDATA.setValue("PUR_GROUP", plantData3.getPurchasingGroup());
			tPLANTDATA.setValue("GR_PR_TIME",plantData3.getGrProcessingTime());
			tPLANTDATA.setValue("MRP_TYPE",plantData3.getMrpType());
			tPLANTDATA.setValue("REORDER_PT",plantData3.getReorderPoint());
			tPLANTDATA.setValue("MRP_CTRLER", plantData3.getMrpController());
			tPLANTDATA.setValue("LOTSIZEKEY",plantData3.getLotSizingProcedure());
			tPLANTDATA.setValue("MINLOTSIZE", plantData3.getMinLotSize());
			tPLANTDATA.setValue("PROC_TYPE", plantData3.getProcurementType());
			tPLANTDATA.setValue("SPPROCTYPE", plantData3.getSpecialProcurement());
			tPLANTDATA.setValue("ISS_ST_LOC", plantData3.getIssueStorageLocation());
			tPLANTDATA.setValue("SLOC_EXPRC", plantData3.getStorageLocationForEP());
			tPLANTDATA.setValue("PLND_DELRY", plantData3.getPlannedDeliveryTime());
			tPLANTDATA.setValue("PERIOD_IND", plantData3.getPeriodIndicator());
			tPLANTDATA.setValue("AVAILCHECK", plantData3.getAvailabilityCheck());
			tPLANTDATA.setValue("DEP_REQ_ID", plantData3.getIndividualAndCollectiveReq());
			tPLANTDATA.setValue("NO_COSTING", (plantData3.isDoNotCost() ? "X" : " "));
			tPLANTDATA.setValue("SALES_VIEW", "X");
			tPLANTDATA.setValue("PURCH_VIEW","X");
			tPLANTDATA.setValue("MRP_VIEW", "X");
			tPLANTDATA.setValue("ACCOUNT_VIEW","X");
			tPLANTDATA.setValue("COST_VIEW","X");

			tPLANTDATAX.appendRow();
			tPLANTDATAX.setValue("FUNCTION", "INS");
			tPLANTDATAX.setValue("MATERIAL", material3.getMaterialId());
			tPLANTDATAX.setValue("PLANT", plantData3.getPlant());
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
			
			tSTORAGELOCATIONDATA.appendRow();
			tSTORAGELOCATIONDATA.setValue("FUNCTION","INS");
			tSTORAGELOCATIONDATA.setValue("MATERIAL", material3.getMaterialId());
			tSTORAGELOCATIONDATA.setValue("PLANT",plantData3.getPlant());
			tSTORAGELOCATIONDATA.setValue("STGE_LOC", plantData3.getStorageLocation());
			tSTORAGELOCATIONDATA.setValue("MRP_VIEW","X");
			tSTORAGELOCATIONDATA.setValue("STORAGE_VIEW","X");
			
			tSTORAGELOCATIONDATAX.appendRow();
			tSTORAGELOCATIONDATAX.setValue("FUNCTION","INS");
			tSTORAGELOCATIONDATAX.setValue("MATERIAL", material3.getMaterialId());
			tSTORAGELOCATIONDATAX.setValue("PLANT",plantData3.getPlant());
			tSTORAGELOCATIONDATAX.setValue("STGE_LOC", plantData3.getStorageLocation());
		
			tVALUATIONDATA.appendRow();
			tVALUATIONDATA.setValue("FUNCTION", "INS");
			tVALUATIONDATA.setValue("MATERIAL", material3.getMaterialId());
			tVALUATIONDATA.setValue("VAL_AREA", plantData3.getPlant());
			tVALUATIONDATA.setValue("PRICE_CTRL", plantData3.getPriceControl());
			tVALUATIONDATA.setValue("VAL_CLASS", plantData3.getValuationClass());
			tVALUATIONDATA.setValue("MOVING_PR", plantData3.getMovingAveragePrice());
			tVALUATIONDATA.setValue("STD_PRICE", plantData3.getStandardPrice());
			tVALUATIONDATA.setValue("PRICE_UNIT", plantData3.getPriceUnit());
			tVALUATIONDATA.setValue("QTY_STRUCT", (plantData3.isCostWithQtyStructure() ? "X" : " "));
			tVALUATIONDATA.setValue("ORIG_MAT", (plantData3.isMaterialRelatedOrigin() ? "X" : " "));
			tVALUATIONDATA.setValue("ACCOUNT_VIEW", "X");
			tVALUATIONDATA.setValue("COST_VIEW", "X");
			
			tVALUATIONDATAX.appendRow();
			tVALUATIONDATAX.setValue("FUNCTION", "INS");
			tVALUATIONDATAX.setValue("MATERIAL", material3.getMaterialId());
			tVALUATIONDATAX.setValue("VAL_AREA", plantData3.getPlant());
			tVALUATIONDATAX.setValue("PRICE_CTRL", "X");
			tVALUATIONDATAX.setValue("VAL_CLASS", "X");
			tVALUATIONDATAX.setValue("MOVING_PR","X");
			tVALUATIONDATAX.setValue("STD_PRICE", "X");
			tVALUATIONDATAX.setValue("PRICE_UNIT","X");
			tVALUATIONDATAX.setValue("QTY_STRUCT", "X");
			tVALUATIONDATAX.setValue("ORIG_MAT", "X");
			
		}
		
		execute(material3);
		
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
	
	private void copyPlantData(Map<String,JCoStructure> structureMap,Material3 material3) {
		JCoStructure sCLIENTDATA=structureMap.get("CLIENTDATA");
		JCoStructure sPLANTDATA=structureMap.get("PLANTDATA");
		JCoStructure sVALUATIONDATA=structureMap.get("VALUATIONDATA");
		
		material3.setIndustrySector(sCLIENTDATA.getString("IND_SECTOR"));
		material3.setType(sCLIENTDATA.getString("MATL_TYPE"));
		material3.setGroup(sCLIENTDATA.getString("MATL_GROUP"));
		material3.setBaseUom(sCLIENTDATA.getString("BASE_UOM"));
		
		
		Set<String> plants=material3.getPlantDataMap().keySet();
		for(String plant : plants) {
			PlantData3 pd=material3.getPlantDataMap().get(plant);
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

	@Override
	public InputField[] getInputFields() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
