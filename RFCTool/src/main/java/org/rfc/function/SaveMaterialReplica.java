package org.rfc.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
	
	public static final String FUNCTION="BAPI_MATERIAL_SAVEREPLICA";
	
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

	private static List<ReturnMessage> returnMessages=Collections.synchronizedList(new ArrayList<ReturnMessage>());
	
	public SaveMaterialReplica(int id,JCoDestination destination) {
		super(id,destination);
	}
	
	public SaveMaterialReplica(int id,List<Material> materials,JCoDestination destination) {
		super(id,destination);
		this.materials=materials;
		this.workload=materials.size();
	}
	
	public SaveMaterialReplica(int id,List<Material> materials,JCoDestination destination,boolean testRun) {
		super(id,destination,testRun);
		this.materials=materials;
		this.workload=materials.size();
	}
	
	private void initialize() {
		
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
		
		try {
			JCoFunctionTemplate template=repository.getFunctionTemplate("BAPI_MATERIAL_GET_ALL");
			funcGetPlantData=template.getFunction();
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void execute(Material material) throws JCoException {
		JCoContext.begin(destination);
		function.execute(destination);
		List<ReturnMessage> functionMessages=createReturnMessages(tRETURNMESSAGES,material.getMaterialId());
		if(functionMessages!=null) {
			material.setMessages(functionMessages);
			synchronized(returnMessages) {
				returnMessages.addAll(functionMessages);
			}
		}
	}
	
	protected Map<String,JCoStructure> getPlantDataTables(String material,String plant) throws JCoException{
		Map<String,JCoStructure> exports=new HashMap<String,JCoStructure>();
		funcGetPlantData.getImportParameterList().setValue("MATERIAL", material);
		funcGetPlantData.getImportParameterList().setValue("COMP_CODE", "07");
		funcGetPlantData.getImportParameterList().setValue("VAL_AREA", plant);
		funcGetPlantData.getImportParameterList().setValue("PLANT", plant);
		
		funcGetPlantData.execute(destination);
		
		exports.put("VALUATIONDATA",funcGetPlantData.getExportParameterList().getStructure("VALUATIONDATA"));
		
		return exports;
	}
	
	private List<ReturnMessage> createReturnMessages(JCoTable tRETURNMESSAGES,String material){
		List<ReturnMessage> messages=new ArrayList<ReturnMessage>();
		ReturnMessage message=null;
		while(tRETURNMESSAGES.nextRow()) {
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
			if(message.getNumber().equals("103") || message.getNumber().equals("801") || message.getNumber().equals("048") || message.getNumber().equals("810") || message.getType().equals("E")) {
				if(message.getType().equals("S")) {
					successCount++;
				}
				else {
					errorCount++;
				}
			}
			messages.add(message);
		}
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
		super.initialize(FUNCTION);
		this.initialize();
	}
	
	public static List<ReturnMessage> getReturnMessages(){ 
		return returnMessages;
	}

	@Override
	public String getFunctionName() {
		return FUNCTION;
	}
	
	protected void setFieldValue(String sapField,Object value) {
		JCoTable tables[]=getTables(sapField);
		tables[0].setValue(getFunctionField(sapField), value);
		tables[1].setValue(getFunctionField(sapField), "X");
	}
	
	private JCoTable[] getTables(String sapField) {
		JCoTable[] tables=new JCoTable[2];
		JCoTable table=tPLANTDATA;
		JCoTable tablex=tPLANTDATAX;
		tables[0]=table;
		tables[1]=tablex;
		return tables;
	}
	
	private String getFunctionField(String sapField) {
		return "MRP_TYPE";
	}
	

}
