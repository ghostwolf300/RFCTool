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

public class ExpandToPlant extends SaveMaterialReplica {
	
	public ExpandToPlant(int id,JCoDestination destination) {
		super(id,destination);
	}
	
	public ExpandToPlant(int id,List<Material> materials,JCoDestination destination) {
		super(id,materials,destination);
	}
	
	public ExpandToPlant(int id,List<Material> materials,JCoDestination destination,boolean testRun) {
		super(id,materials,destination,testRun);
	}
	
	protected void doWork() throws JCoException {
		super.doWork();
		executePlantExpansion();
	}
	
	private void executePlantExpansion() throws JCoException {
		
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
			
			super.execute(material);
			
			tHEADDATA.clear();
			tPLANTDATA.clear();
			tPLANTDATAX.clear();
			tSTORAGELOCATIONDATA.clear();
			tSTORAGELOCATIONDATAX.clear();
			tVALUATIONDATA.clear();
			tVALUATIONDATAX.clear();
			tRETURNMESSAGES.clear();
			
			processedCount++;
			super.progressUpdated();
		}
		
	}
	
}
