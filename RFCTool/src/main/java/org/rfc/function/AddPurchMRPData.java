package org.rfc.function;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rfc.dto.FieldValue;
import org.rfc.dto.InputField;
import org.rfc.dto.Material;
import org.rfc.dto.PlantData;
import org.rfc.dto.ValuationData;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;


public class AddPurchMRPData extends SaveMaterialReplica {
	
	public static final String FUNCTION_NAME="AddPurchMRPData";
	
	public AddPurchMRPData() {
		super();
	}
	
	public AddPurchMRPData(int id) {
		super(id);
	}
	
	public AddPurchMRPData(int id,JCoDestination destination) {
		super(id,destination);
	}
	
	public AddPurchMRPData(int id,List<Material> materials,JCoDestination destination) {
		super(id,materials,destination);
	}
	
	public AddPurchMRPData(int id,List<Material> materials,JCoDestination destination,boolean testRun) {
		super(id,materials,destination,testRun);
	}
	
	protected void executeFunction(Material material) throws JCoException {
		
		Map<String,JCoStructure> structureMap=getMaterialPlantData(material.getMaterialId(), "0700");
		copyPlantData(structureMap,material);
		
		tHEADDATA.appendRow();
		tHEADDATA.setValue("FUNCTION","UPD");
		tHEADDATA.setValue("MATERIAL",material.getMaterialId());
		tHEADDATA.setValue("SALES_VIEW","X");
		tHEADDATA.setValue("PURCHASE_VIEW","X");
		tHEADDATA.setValue("MRP_VIEW","X");
		
		Set<String> plants=material.getPlantDataMap().keySet();
		
		for(String plant : plants) {
			
			PlantData pd=material.getPlantDataMap().get(plant);
			
			tPLANTDATA.appendRow();
			tPLANTDATA.setValue("FUNCTION", "UPD");
			tPLANTDATA.setValue("MATERIAL", material.getMaterialId());
			tPLANTDATA.setValue("PLANT", pd.getPlant());
			
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
			
			tPLANTDATA.setValue("SALES_VIEW","X");
			tPLANTDATA.setValue("MRP_VIEW","X");
			tPLANTDATA.setValue("PURCH_VIEW","X");
			

			tPLANTDATAX.appendRow();
			tPLANTDATAX.setValue("FUNCTION", "UPD");
			tPLANTDATAX.setValue("MATERIAL", pd.getMaterialId());
			tPLANTDATAX.setValue("PLANT", pd.getPlant());
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
		
		execute(material);
		
		tHEADDATA.clear();
		tPLANTDATA.clear();
		tPLANTDATAX.clear();
		tRETURNMESSAGES.clear();
		
	}

	@Override
	public String getFunctionName() {
		return FUNCTION_NAME;
	}

}
