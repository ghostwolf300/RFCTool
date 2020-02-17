package org.rfc.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.rfc.dto.Material;
import org.rfc.dto.PlantData;
import org.rfc.dto.ReturnMessage;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoTable;

public class SaveMaterialReplica extends BAPIFunction implements Runnable {
	
	public static final String FUNCTION="BAPI_MATERIAL_SAVEREPLICA";
	private List<Material> materials=null;
	private boolean testRun=true;
	private static List<ReturnMessage> returnMessages=Collections.synchronizedList(new ArrayList<ReturnMessage>());
	private boolean executing=false;
	
	public SaveMaterialReplica(JCoDestination destination) {
		super(destination);
	}
	
	public SaveMaterialReplica(List<Material> materials,JCoDestination destination) {
		super(destination);
		this.materials=materials;
		listSize=materials.size();
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
		listSize=materials.size();
	}

	public boolean isTestRun() {
		return testRun;
	}

	public void setTestRun(boolean testRun) {
		this.testRun = testRun;
	}

	public boolean isExecuting() {
		return executing;
	}

	public void run() {
		try {
			executing=true;
			initialize(FUNCTION);
			//execute();
			executeExpansion();
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				JCoContext.end(destination);
			} 
			catch (JCoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			executing=false;
		}
		
	}
	
	private void execute() throws JCoException {
		
		JCoTable tHEADDATA=null;
		JCoTable tPLANTDATA=null;
		JCoTable tPLANTDATAX=null;
		JCoTable tRETURNMESSAGES=null;
		List<ReturnMessage> functionMessages=null;
		
		function.getImportParameterList().setValue("NOAPPLLOG", "X");
		function.getImportParameterList().setValue("NOCHANGEDOC", "X");
		if(testRun) {
			function.getImportParameterList().setValue("TESTRUN", "X");
		}
		else {
			function.getImportParameterList().setValue("TESTRUN", " ");
		}
		function.getImportParameterList().setValue("INPFLDCHECK", " ");
		
		tHEADDATA=function.getTableParameterList().getTable("HEADDATA");
		tPLANTDATA=function.getTableParameterList().getTable("PLANTDATA");
		tPLANTDATAX=function.getTableParameterList().getTable("PLANTDATAX");
		tRETURNMESSAGES=function.getTableParameterList().getTable("RETURNMESSAGES");
		
		for(Material material : materials) {
			
			tHEADDATA.appendRow();
			tHEADDATA.setValue("FUNCTION","UPD");
			tHEADDATA.setValue("MATERIAL",material.getMaterialId());
			
			Set<String> plants=material.getPlantDataMap().keySet();
			
			for(String plant : plants) {
				PlantData plantData=material.getPlantDataMap().get(plant);
				tPLANTDATA.appendRow();
				tPLANTDATA.setValue("FUNCTION", "UPD");
				tPLANTDATA.setValue("MATERIAL", material.getMaterialId());
				tPLANTDATA.setValue("PLANT", plantData.getPlant());
				tPLANTDATA.setValue("PLND_DELRY", plantData.getPlannedDeliveryTime());

				tPLANTDATAX.appendRow();
				tPLANTDATAX.setValue("FUNCTION", "UPD");
				tPLANTDATAX.setValue("MATERIAL", material.getMaterialId());
				tPLANTDATAX.setValue("PLANT", plantData.getPlant());
				tPLANTDATAX.setValue("PLND_DELRY", "X");
			}
			
			JCoContext.begin(destination);
			function.execute(destination);
			
			functionMessages=this.createReturnMessages(tRETURNMESSAGES,material.getMaterialId());
			
			synchronized(returnMessages) {
				returnMessages.addAll(functionMessages);
			}
			
			tHEADDATA.clear();
			tPLANTDATA.clear();
			tPLANTDATAX.clear();
			tRETURNMESSAGES.clear();
			
			processedCount++;
			
		}
		
	}
	
	private void executeExpansion() throws JCoException {
		
		JCoTable tHEADDATA=null;
		JCoTable tPLANTDATA=null;
		JCoTable tPLANTDATAX=null;
		JCoTable tSTORAGELOCATIONDATA=null;
		JCoTable tSTORAGELOCATIONDATAX=null;
		JCoTable tVALUATIONDATA=null;
		JCoTable tVALUATIONDATAX=null;
		JCoTable tRETURNMESSAGES=null;
		List<ReturnMessage> functionMessages=null;
		
		function.getImportParameterList().setValue("NOAPPLLOG", "X");
		function.getImportParameterList().setValue("NOCHANGEDOC", "X");
		if(testRun) {
			function.getImportParameterList().setValue("TESTRUN", "X");
		}
		else {
			function.getImportParameterList().setValue("TESTRUN", " ");
		}
		function.getImportParameterList().setValue("INPFLDCHECK", " ");
		
		tHEADDATA=function.getTableParameterList().getTable("HEADDATA");
		tPLANTDATA=function.getTableParameterList().getTable("PLANTDATA");
		tPLANTDATAX=function.getTableParameterList().getTable("PLANTDATAX");
		tSTORAGELOCATIONDATA=function.getTableParameterList().getTable("STORAGELOCATIONDATA");
		tSTORAGELOCATIONDATAX=function.getTableParameterList().getTable("STORAGELOCATIONDATAX");
		tVALUATIONDATA=function.getTableParameterList().getTable("VALUATIONDATA");
		tVALUATIONDATAX=function.getTableParameterList().getTable("VALUATIONDATAX");
		tRETURNMESSAGES=function.getTableParameterList().getTable("RETURNMESSAGES");
		
		for(Material material : materials) {
			
			tHEADDATA.appendRow();
			tHEADDATA.setValue("FUNCTION","INS");
			tHEADDATA.setValue("MATERIAL",material.getMaterialId());
			
			Set<String> plants=material.getPlantDataMap().keySet();
			
			for(String plant : plants) {
				PlantData plantData=material.getPlantDataMap().get(plant);
				tPLANTDATA.appendRow();
				tPLANTDATA.setValue("FUNCTION", "INS");
				tPLANTDATA.setValue("MATERIAL", material.getMaterialId());
				tPLANTDATA.setValue("PLANT", plantData.getPlant());
				tPLANTDATA.setValue("PROFIT_CTR", plantData.getProfitCenter());
				tPLANTDATA.setValue("LOADINGGRP", plantData.getLoadingGroup());
				tPLANTDATA.setValue("PUR_GROUP", plantData.getPurchasingGroup());
				tPLANTDATA.setValue("GR_PR_TIME",plantData.getGrProcessingTime());
				tPLANTDATA.setValue("MRP_TYPE",plantData.getMrpType());
				tPLANTDATA.setValue("REORDER_PT",plantData.getReorderPoint());
				tPLANTDATA.setValue("MRP_CTRLER", plantData.getMrpController());
				tPLANTDATA.setValue("LOTSIZEKEY",plantData.getLotSizingProcedure());
				tPLANTDATA.setValue("MINLOTSIZE", plantData.getMinLotSize());
				tPLANTDATA.setValue("PROC_TYPE", plantData.getProcurementType());
				tPLANTDATA.setValue("SPPROCTYPE", plantData.getSpecialProcurement());
				tPLANTDATA.setValue("ISS_ST_LOC", plantData.getIssueStorageLocation());
				tPLANTDATA.setValue("SLOC_EXPRC", plantData.getStorageLocationForEP());
				tPLANTDATA.setValue("PLND_DELRY", plantData.getPlannedDeliveryTime());
				tPLANTDATA.setValue("PERIOD_IND", plantData.getPeriodIndicator());
				tPLANTDATA.setValue("AVAILCHECK", plantData.getAvailabilityCheck());
				tPLANTDATA.setValue("DEP_REQ_ID", plantData.getIndividualAndCollectiveReq());
				tPLANTDATA.setValue("SALES_VIEW", "X");
				tPLANTDATA.setValue("PURCH_VIEW","X");
				tPLANTDATA.setValue("MRP_VIEW", "X");
				tPLANTDATA.setValue("ACCOUNT_VIEW","X");
				tPLANTDATA.setValue("COST_VIEW","X");

				tPLANTDATAX.appendRow();
				tPLANTDATAX.setValue("FUNCTION", "INS");
				tPLANTDATAX.setValue("MATERIAL", material.getMaterialId());
				tPLANTDATAX.setValue("PLANT", plantData.getPlant());
				tPLANTDATAX.setValue("PROFIT_CTR", "X");
				tPLANTDATAX.setValue("LOADINGGRP", "X");
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
				
				tSTORAGELOCATIONDATA.appendRow();
				tSTORAGELOCATIONDATA.setValue("FUNCTION","INS");
				tSTORAGELOCATIONDATA.setValue("MATERIAL", material.getMaterialId());
				tSTORAGELOCATIONDATA.setValue("PLANT",plantData.getPlant());
				tSTORAGELOCATIONDATA.setValue("STGE_LOC", plantData.getStorageLocation());
				
				tSTORAGELOCATIONDATAX.appendRow();
				tSTORAGELOCATIONDATAX.setValue("FUNCTION","INS");
				tSTORAGELOCATIONDATAX.setValue("MATERIAL", material.getMaterialId());
				tSTORAGELOCATIONDATAX.setValue("PLANT",plantData.getPlant());
				tSTORAGELOCATIONDATAX.setValue("STGE_LOC", plantData.getStorageLocation());
				
				tVALUATIONDATA.appendRow();
				tVALUATIONDATA.setValue("FUNCTION", "INS");
				tVALUATIONDATA.setValue("MATERIAL", material.getMaterialId());
				tVALUATIONDATA.setValue("VAL_AREA", plantData.getPlant());
				tVALUATIONDATA.setValue("PRICE_CTRL", plantData.getPriceControl());
				tVALUATIONDATA.setValue("VAL_CLASS", plantData.getValuationClass());
				tVALUATIONDATA.setValue("PRICE_UNIT", plantData.getPriceUnit());
				tVALUATIONDATA.setValue("QTY_STRUCT", (plantData.isCostWithQtyStructure() ? "X" : " "));
				tVALUATIONDATA.setValue("ORIG_MAT", (plantData.isMaterialRelatedOrigin() ? "X" : " "));
				tVALUATIONDATA.setValue("ACCOUNT_VIEW", "X");
				tVALUATIONDATA.setValue("COST_VIEW", "X");
				
				
				tVALUATIONDATAX.appendRow();
				tVALUATIONDATAX.setValue("FUNCTION", "INS");
				tVALUATIONDATAX.setValue("MATERIAL", material.getMaterialId());
				tVALUATIONDATAX.setValue("VAL_AREA", plantData.getPlant());
				tVALUATIONDATAX.setValue("PRICE_CTRL", "X");
				tVALUATIONDATAX.setValue("VAL_CLASS", "X");
				tVALUATIONDATAX.setValue("PRICE_UNIT","X");
				tVALUATIONDATAX.setValue("QTY_STRUCT", "X");
				tVALUATIONDATAX.setValue("ORIG_MAT", "X");
				
			}
			
			JCoContext.begin(destination);
			function.execute(destination);
		
			functionMessages=this.createReturnMessages(tRETURNMESSAGES,material.getMaterialId());
			
			synchronized(returnMessages) {
				returnMessages.addAll(functionMessages);
			}
			
			tHEADDATA.clear();
			tPLANTDATA.clear();
			tPLANTDATAX.clear();
			tSTORAGELOCATIONDATA.clear();
			tSTORAGELOCATIONDATAX.clear();
			tVALUATIONDATA.clear();
			tVALUATIONDATAX.clear();
			tRETURNMESSAGES.clear();
			
			processedCount++;
			
		}
		
	}
	
	
	public static List<ReturnMessage> getReturnMessages(){ 
		return returnMessages;
	}
	 

	
}
