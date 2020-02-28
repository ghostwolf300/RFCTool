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

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;


public class AddPurchMRPData extends SaveMaterialReplica {
	
	public static final String FUNCTION_NAME="AddPurchMRPData";
	
	public static final Map<String,InputField<?>> FIELD_MAP=initInputFieldMap();
	
	private static Map<String,InputField<?>> initInputFieldMap(){
		Map<String,InputField<?>> map=new HashMap<String,InputField<?>>();
		
		InputField<String> materialId=new InputField<String>("MATERIAL",null,"MaterialId",true,String.class);
		InputField<String> plant=new InputField<String>("PLANT",null,"Plant",true,String.class);
		InputField<String> purchGroup=new InputField<String>("PURCH_GROUP",null,"PurchasingGroup",false,String.class);
		InputField<Integer> grProcTime=new InputField<Integer>("GR_PR_TIME",null,"GrProcessingTime",false,Integer.class);
		InputField<String> mrpType=new InputField<String>("MRP_TYPE",null,"MrpType",false,String.class);
		InputField<Integer> reorderPoint=new InputField<Integer>("REORDER_PT",null,"ReorderPoint",false,Integer.class);
		InputField<String> mrpController=new InputField<String>("MRP_CTRLER",null,"MrpController",false,String.class);
		InputField<String> lotSizeKey=new InputField<String>("LOTSIZEKEY",null,"LotSizeKey",false,String.class);
		InputField<Integer> minLotSize=new InputField<Integer>("MINLOTSIZE",null,"MinLotSize",false,Integer.class);
		InputField<String> lotSizeProc=new InputField<String>("LOTSIZEKEY",null,"LotSizingProcedure",false,String.class);
		InputField<String> procType=new InputField<String>("PROC_TYPE",null,"ProcurementType",false,String.class);
		InputField<String> specialProc=new InputField<String>("SPPROCTYPE",null,"SpecialProcurement",false,String.class);
		InputField<String> issueStorageLoc=new InputField<String>("ISS_ST_LOC",null,"IssueStorageLocation",false,String.class);
		InputField<String> storageLocEP=new InputField<String>("SLOC_EXPRC",null,"StorageLocationForEP",false,String.class);
		InputField<Integer> planDelTime=new InputField<Integer>("PLND_DELRY",null,"PlannedDeliveryTime",false,Integer.class);
		InputField<String> periodIndicator=new InputField<String>("PERIOD_IND",null,"PeriodIndicator",false,String.class);
		InputField<String> availabilityCheck=new InputField<String>("AVAILCHECK",null,"AvailabilityCheck",false,String.class);
		InputField<String> depReqId=new InputField<String>("DEP_REQ_ID",null,"IndividualAndCollectiveReq",false,String.class);
		
		map.put(materialId.getRfcName(),materialId);
		map.put(plant.getRfcName(),plant);
		map.put(purchGroup.getRfcName(), purchGroup);
		map.put(grProcTime.getRfcName(), grProcTime);
		map.put(mrpType.getRfcName(), mrpType);
		map.put(reorderPoint.getRfcName(), reorderPoint);
		map.put(mrpController.getRfcName(), mrpController);
		map.put(lotSizeKey.getRfcName(), lotSizeKey);
		map.put(minLotSize.getRfcName(), minLotSize);
		map.put(lotSizeProc.getRfcName(), lotSizeProc);
		map.put(procType.getRfcName(), procType);
		map.put(specialProc.getRfcName(), specialProc);
		map.put(issueStorageLoc.getRfcName(), issueStorageLoc);
		map.put(storageLocEP.getRfcName(), storageLocEP);
		map.put(planDelTime.getRfcName(), planDelTime);
		map.put(periodIndicator.getRfcName(), periodIndicator);
		map.put(availabilityCheck.getRfcName(), availabilityCheck);
		map.put(depReqId.getRfcName(), depReqId);
		
		return Collections.unmodifiableMap(map);
	}
	
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
		
		Map<String,JCoStructure> structureMap=getMaterialPlantData(material.getMaterialId().getValue(), "0700");
		copyPlantData(structureMap,material);
		
		tHEADDATA.appendRow();
		tHEADDATA.setValue("FUNCTION","UPD");
		tHEADDATA.setValue("MATERIAL",material.getMaterialId().getValue());
		tHEADDATA.setValue("SALES_VIEW","X");
		tHEADDATA.setValue("PURCHASE_VIEW","X");
		tHEADDATA.setValue("MRP_VIEW","X");
		
		Set<String> plants=material.getPlantDataMap().keySet();
		
		for(String plant : plants) {
			
			PlantData pd=material.getPlantDataMap().get(plant);
			
			tPLANTDATA.appendRow();
			tPLANTDATA.setValue("FUNCTION", "UPD");
			tPLANTDATA.setValue("MATERIAL", material.getMaterialId().getValue());
			tPLANTDATA.setValue("PLANT", pd.getPlant().getValue());
			
			tPLANTDATA.setValue("PUR_GROUP",pd.getPurchasingGroup().getValue());
			tPLANTDATA.setValue("GR_PR_TIME",pd.getGrProcessingTime().getValue());
			tPLANTDATA.setValue("MRP_TYPE","PD");
			tPLANTDATA.setValue("REORDER_PT",0);
			tPLANTDATA.setValue("MRP_CTRLER", pd.getMrpController().getValue());
			tPLANTDATA.setValue("LOTSIZEKEY",pd.getLotSizingProcedure().getValue());
			tPLANTDATA.setValue("MINLOTSIZE", pd.getMinLotSize().getValue());
			tPLANTDATA.setValue("PROC_TYPE", pd.getProcurementType().getValue());
			tPLANTDATA.setValue("SPPROCTYPE", "40");
			tPLANTDATA.setValue("ISS_ST_LOC", "0700");
			tPLANTDATA.setValue("SLOC_EXPRC", "0700");
			tPLANTDATA.setValue("PLND_DELRY", 2);
			tPLANTDATA.setValue("PERIOD_IND", pd.getPeriodIndicator().getValue());
			tPLANTDATA.setValue("AVAILCHECK", pd.getAvailabilityCheck().getValue());
			tPLANTDATA.setValue("DEP_REQ_ID", pd.getIndividualAndCollectiveReq().getValue());
			
			tPLANTDATA.setValue("SALES_VIEW","X");
			tPLANTDATA.setValue("MRP_VIEW","X");
			tPLANTDATA.setValue("PURCH_VIEW","X");
			

			tPLANTDATAX.appendRow();
			tPLANTDATAX.setValue("FUNCTION", "UPD");
			tPLANTDATAX.setValue("MATERIAL", pd.getMaterialId().getValue());
			tPLANTDATAX.setValue("PLANT", pd.getPlant().getValue());
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
			tSTORAGELOCATIONDATA.setValue("MATERIAL", material.getMaterialId().getValue());
			tSTORAGELOCATIONDATA.setValue("PLANT",pd.getPlant().getValue());
			tSTORAGELOCATIONDATA.setValue("STGE_LOC", "0700");
			
			tSTORAGELOCATIONDATA.setValue("MRP_VIEW","X");
			tSTORAGELOCATIONDATA.setValue("STORAGE_VIEW","X");
			
			tSTORAGELOCATIONDATAX.appendRow();
			tSTORAGELOCATIONDATAX.setValue("FUNCTION","INS");
			tSTORAGELOCATIONDATAX.setValue("MATERIAL", material.getMaterialId().getValue());
			tSTORAGELOCATIONDATAX.setValue("PLANT",pd.getPlant().getValue());
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
	
	public static Map<String, InputField<?>> getFieldMap() {
		return FIELD_MAP;
	}


}
