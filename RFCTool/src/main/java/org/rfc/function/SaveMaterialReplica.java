package org.rfc.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rfc.dto.FieldValue;
import org.rfc.dto.InputField;
import org.rfc.dto.Material;
import org.rfc.dto.PlantData;
import org.rfc.dto.ReturnMessage;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public abstract class SaveMaterialReplica extends RunnableFunction {
	
	
	public static final String F_MATERIAL_SAVE="BAPI_MATERIAL_SAVEREPLICA";
	public static final String F_MATERIAL_GET="BAPI_MATERIAL_GET_ALL";
	
	protected List<Material> materials=null;
	protected JCoFunction funcGetPlantData=null;
	
	protected JCoTable tHEADDATA=null;
	protected JCoTable tPLANTDATA=null;
	protected JCoTable tPLANTDATAX=null;
	protected JCoTable tSTORAGELOCATIONDATA=null;
	protected JCoTable tSTORAGELOCATIONDATAX=null;
	protected JCoTable tVALUATIONDATA=null;
	protected JCoTable tVALUATIONDATAX=null;
	protected JCoTable tRETURNMESSAGES=null;
	
	public static enum InputFields{
		MATERIAL,
		PLANT,
		PROFIT_CTR,
		SALES_VIEW,
		PUR_GROUP,
		GR_PR_TIME,
		MRP_TYPE,
		REORDER_PT,
		MRP_CTRLER,
		LOTSIZEKEY,
		MINLOTSIZE,
		PROC_TYPE,
		SPPROCTYPE,
		ISS_ST_LOC,
		SLOC_EXPRC,
		PLND_DELRY,
		PERIOD_IND,
		AVAILCHECK,
		DEP_REQ_ID,
		PURCH_VIEW,
		MRP_VIEW,
		NO_COSTING,
		ACCOUNT_VIEW,
		COST_VIEW,
		STGE_LOC,
		STORAGE_VIEW,
		VAL_AREA,
		PRICE_CTRL,
		VAL_CLASS,
		MOVING_PR,
		STD_PRICE,
		PRICE_UNIT,
		QTY_STRUCT,
		ORIG_MAT,
	}

	private static List<ReturnMessage> returnMessages=Collections.synchronizedList(new ArrayList<ReturnMessage>());
	
	public SaveMaterialReplica() {
		super();
	}
	
	public SaveMaterialReplica(int id) {
		super(id);
	}
	
	public SaveMaterialReplica(int id,JCoDestination destination) {
		super(id,destination);
		this.initialize();
	}
	
	public SaveMaterialReplica(int id,List<Material> materials,JCoDestination destination) {
		super(id,destination);
		this.materials=materials;
		this.workload=materials.size();
		this.initialize();
	}
	
	public SaveMaterialReplica(int id,List<Material> materials,JCoDestination destination,boolean testRun) {
		super(id,destination,testRun);
		this.materials=materials;
		this.workload=materials.size();
		this.initialize();
	}
	
	private void initialize() {
		
		JCoFunction function=null;
		
		super.initialize(new String[] {F_MATERIAL_SAVE,F_MATERIAL_GET});
		function=functionMap.get(F_MATERIAL_SAVE);
		function.getImportParameterList().setValue("NOAPPLLOG", "X");
		function.getImportParameterList().setValue("NOCHANGEDOC", "X");
		function.getImportParameterList().setValue("TESTRUN", (testRun==true ? "X" : " "));
		function.getImportParameterList().setValue("INPFLDCHECK", " ");
		
		tHEADDATA=function.getTableParameterList().getTable("HEADDATA");
		tPLANTDATA=function.getTableParameterList().getTable("PLANTDATA");
		tPLANTDATAX=function.getTableParameterList().getTable("PLANTDATAX");
		tSTORAGELOCATIONDATA=function.getTableParameterList().getTable("STORAGELOCATIONDATA");
		tSTORAGELOCATIONDATAX=function.getTableParameterList().getTable("STORAGELOCATIONDATAX");
		tVALUATIONDATA=function.getTableParameterList().getTable("VALUATIONDATA");
		tVALUATIONDATAX=function.getTableParameterList().getTable("VALUATIONDATAX");
		tRETURNMESSAGES=function.getTableParameterList().getTable("RETURNMESSAGES");
		
	}
	
	protected void execute(Material material) throws JCoException  {
		JCoContext.begin(destination);
		functionMap.get(F_MATERIAL_SAVE).execute(destination);
		
		List<ReturnMessage> functionMessages=createReturnMessages(tRETURNMESSAGES,material.getMaterialId().getValue());
		if(functionMessages!=null) {
			material.setMessages(functionMessages);
			synchronized(returnMessages) {
				returnMessages.addAll(functionMessages);
			}
		}
	}
	
	protected Map<String,JCoStructure> getMaterialPlantData(String material,String plant){
		Map<String,JCoStructure> exports=new HashMap<String,JCoStructure>();
		JCoFunction funcGetPlantData=functionMap.get(F_MATERIAL_GET);
		funcGetPlantData.getImportParameterList().setValue("MATERIAL", material);
		funcGetPlantData.getImportParameterList().setValue("COMP_CODE", "07");
		funcGetPlantData.getImportParameterList().setValue("VAL_AREA", plant);
		funcGetPlantData.getImportParameterList().setValue("PLANT", plant);
		
		try {
			funcGetPlantData.execute(destination);
		} 
		catch (JCoException e) {
			status=StatusCode.ERROR;
			statusChanged();
			e.printStackTrace();
		}
		
		exports.put("CLIENTDATA", funcGetPlantData.getExportParameterList().getStructure("CLIENTDATA"));
		exports.put("PLANTDATA", funcGetPlantData.getExportParameterList().getStructure("PLANTDATA"));
		exports.put("VALUATIONDATA",funcGetPlantData.getExportParameterList().getStructure("VALUATIONDATA"));
		
		return exports;
	}
	
	private List<ReturnMessage> createReturnMessages(JCoTable tRETURNMESSAGES,String material){
		List<ReturnMessage> messages=new ArrayList<ReturnMessage>();
		ReturnMessage message=null;
		
		do {
			message=new ReturnMessage();
			message.setWorkerId(this.getId());
			message.setMaterial(material);
			message.setNumber((String)tRETURNMESSAGES.getValue("NUMBER"));
			message.setType((String)tRETURNMESSAGES.getValue("TYPE"));
			message.setMessage((String) tRETURNMESSAGES.getValue("MESSAGE"));
			message.setRow(String.valueOf(tRETURNMESSAGES.getValue("ROW")));
			message.setMessageVariable1((String) tRETURNMESSAGES.getValue("MESSAGE_V1"));
			message.setMessageVariable2((String) tRETURNMESSAGES.getValue("MESSAGE_V2"));
			message.setMessageVariable3((String) tRETURNMESSAGES.getValue("MESSAGE_V3"));
			message.setMessageVariable4((String) tRETURNMESSAGES.getValue("MESSAGE_V4"));
			
			//only add errors or warnings to log; count all types
			if(message.getType().equals("S")) {
				successCount++;
				//messages.add(message);
			}
			else if(message.getType().equals("W")) {
				warningCount++;
				messages.add(message);
			}
			else if(message.getType().equals("E")){
				errorCount++;
				messages.add(message);
			}
			else {
				errorCount++;
				messages.add(message);
			}
		}
		while(tRETURNMESSAGES.nextRow());
		
		allMessages.addAll(messages);
		newMessages=messages;
		return messages;
	}

	public List<Material> getMaterials() {
		return materials;
	}

	public void setMaterials(List<Material> materials) {
		this.materials = materials;
		this.workload=materials.size();
	}
	
	protected void doWork() throws JCoException {
		this.initialize();
		for(Material material : materials) {
			if(status==StatusCode.PAUSED) {
				pauseThread();
			}
			executeFunction(material);
			processedCount++;
			progressUpdated();
		}
	}
	
	abstract void executeFunction(Material materials) throws JCoException;
	
	public static List<ReturnMessage> getReturnMessages(){ 
		return returnMessages;
	}

	@Override
	public abstract String getFunctionName();
	
	protected void copyPlantData(Map<String,JCoStructure> structureMap,Material material) {
		
		JCoStructure sCLIENTDATA=structureMap.get("CLIENTDATA");
		JCoStructure sPLANTDATA=structureMap.get("PLANTDATA");
		JCoStructure sVALUATIONDATA=structureMap.get("VALUATIONDATA");
		
		FieldValue<String> industrySector=new FieldValue<String>();
		industrySector.setValue(sCLIENTDATA.getString("IND_SECTOR"));
		material.setIndustrySector(industrySector);
		
		FieldValue<String> type=new FieldValue<String>();
		type.setValue(sCLIENTDATA.getString("MATL_TYPE"));
		material.setType(type);
		
		FieldValue<String> group=new FieldValue<String>();
		group.setValue(sCLIENTDATA.getString("MATL_GROUP"));
		material.setGroup(group);
		
		FieldValue<String> baseUom=new FieldValue<String>();
		baseUom.setValue(sCLIENTDATA.getString("BASE_UOM"));
		material.setBaseUom(baseUom);
		
		
		Set<String> plants=material.getPlantDataMap().keySet();
		for(String plant : plants) {
			PlantData pd=material.getPlantDataMap().get(plant);
			
			FieldValue<String> purchasingGroup=new FieldValue<String>();
			purchasingGroup.setValue(sPLANTDATA.getString("PUR_GROUP"));
			pd.setPurchasingGroup(purchasingGroup);
			
			FieldValue<Integer> grProcTime=new FieldValue<Integer>();
			grProcTime.setValue(sPLANTDATA.getInt("GR_PR_TIME"));
			pd.setGrProcessingTime(grProcTime);
			
			FieldValue<String> mrpController=new FieldValue<String>();
			mrpController.setValue(sPLANTDATA.getString("MRP_CTRLER"));
			pd.setMrpController(mrpController);
			
			FieldValue<String> lotSizeProc=new FieldValue<String>();
			lotSizeProc.setValue(sPLANTDATA.getString("LOTSIZEKEY"));
			pd.setLotSizingProcedure(lotSizeProc);
			
			FieldValue<Integer> minLotSize=new FieldValue<Integer>();
			minLotSize.setValue(sPLANTDATA.getInt("MINLOTSIZE"));
			pd.setMinLotSize(minLotSize);
			
			FieldValue<String> procType=new FieldValue<String>();
			procType.setValue(sPLANTDATA.getString("PROC_TYPE"));
			pd.setProcurementType(procType);
			
			FieldValue<String> periodIndicator=new FieldValue<String>();
			periodIndicator.setValue(sPLANTDATA.getString("PERIOD_IND"));
			pd.setPeriodIndicator(periodIndicator);
			
			FieldValue<String> availabilityCheck=new FieldValue<String>();
			availabilityCheck.setValue(sPLANTDATA.getString("AVAILCHECK"));
			pd.setAvailabilityCheck(availabilityCheck);
			
			FieldValue<String> depReqId=new FieldValue<String>();
			depReqId.setValue(sPLANTDATA.getString("DEP_REQ_ID"));
			pd.setIndividualAndCollectiveReq(depReqId);
			
			FieldValue<String> valuationClass=new FieldValue<String>();
			valuationClass.setValue(sVALUATIONDATA.getString("VAL_CLASS"));
			pd.setValuationClass(valuationClass);
			
			FieldValue<String> priceControl=new FieldValue<String>();
			priceControl.setValue(sVALUATIONDATA.getString("PRICE_CTRL"));
			pd.setPriceControl(priceControl);
			
			FieldValue<Double> movingPrice=new FieldValue<Double>();
			movingPrice.setValue(sVALUATIONDATA.getDouble("MOVING_PR"));
			pd.setMovingAveragePrice(movingPrice);
			
			FieldValue<Double> standardPrice=new FieldValue<Double>();
			standardPrice.setValue(sVALUATIONDATA.getDouble("STD_PRICE"));
			pd.setStandardPrice(standardPrice);
			
			FieldValue<Integer> priceUnit=new FieldValue<Integer>();
			priceUnit.setValue(sVALUATIONDATA.getInt("PRICE_UNIT"));
			pd.setPriceUnit(priceUnit);
			
			FieldValue<Boolean> qtyStructure=new FieldValue<Boolean>();
			qtyStructure.setValue((sVALUATIONDATA.getString("QTY_STRUCT").equals("X") ? true : false));
			pd.setCostWithQtyStructure(qtyStructure);
			
			FieldValue<Boolean> originMaterial=new FieldValue<Boolean>();
			originMaterial.setValue((sVALUATIONDATA.getString("ORIG_MAT").equals("X") ? true : false));
			pd.setMaterialRelatedOrigin(originMaterial);
			
			
		}
	}
	

}
