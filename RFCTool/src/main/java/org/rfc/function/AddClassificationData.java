package org.rfc.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.rfc.dto.Material;
import org.rfc.dto.ReturnMessage;
import org.rfc.dto.Worker.StatusCode;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

public class AddClassificationData extends RunnableFunction {
	
	public static final String FUNCTION_NAME="AddClassificationData";
	public static final String F_CREATE_CLASSIFICATION="BAPI_OBJCL_CREATE";
	public static final String F_TRANSACTION_COMMIT="BAPI_TRANSACTION_COMMIT";
	
	protected List<Material> materials=null;
	
	private static List<ReturnMessage> returnMessages=Collections.synchronizedList(new ArrayList<ReturnMessage>());
	
	protected JCoTable tRETURN=null;
	
	public AddClassificationData() {
		super();
		initialize();
	}
	
	public AddClassificationData(int id,List<Material> materials,JCoDestination destination,boolean testRun) {
		super(id,destination,testRun);
		this.materials=materials;
		initialize();
	}
	
	private void initialize() {
		super.initialize(new String[] {F_CREATE_CLASSIFICATION,F_TRANSACTION_COMMIT});
	}
	
	@Override
	public String getFunctionName() {
		return FUNCTION_NAME;
	}

	@Override
	protected void doWork() throws JCoException {
		//this.initialize();
		for(Material material : materials) {
			if(status==StatusCode.PAUSED) {
				pauseThread();
			}
			executeFunction(material);
			processedCount++;
			progressUpdated();
		}
		
	}
	
	private void executeFunction(Material m) throws JCoException {
		JCoContext.begin(destination);
		
		JCoFunction classFunc=functionMap.get(F_CREATE_CLASSIFICATION);
		JCoFunction commitFunc=functionMap.get(F_TRANSACTION_COMMIT);
		
		classFunc.getImportParameterList().setValue("OBJECTKEYNEW", m.getMaterialId());
		classFunc.getImportParameterList().setValue("OBJECTTABLENEW","MARA");
		classFunc.getImportParameterList().setValue("CLASSNUMNEW", "ECOMMERCE");
		classFunc.getImportParameterList().setValue("CLASSTYPENEW", "001");
		tRETURN=classFunc.getTableParameterList().getTable("RETURN");
		
		classFunc.execute(destination);
		commitFunc.execute(destination);
		
		List<ReturnMessage> functionMessages=createReturnMessages(tRETURN,m.getMaterialId());
		if(functionMessages!=null) {
			m.setMessages(functionMessages);
			synchronized(returnMessages) {
				returnMessages.addAll(functionMessages);
			}
		}
		
		clearTables();
	}
	
	private List<ReturnMessage> createReturnMessages(JCoTable tRETURN, String materialId) {
		List<ReturnMessage> messages=new ArrayList<ReturnMessage>();
		ReturnMessage message=null;
		boolean success=false;
		boolean warning=false;
		boolean error=false;
		
		do {
			message=new ReturnMessage();
			message.setWorkerId(this.getId());
			message.setMaterial(materialId);
			message.setNumber((String)tRETURN.getValue("NUMBER"));
			message.setType((String)tRETURN.getValue("TYPE"));
			message.setMessage((String) tRETURN.getValue("MESSAGE"));
			message.setRow(String.valueOf(tRETURN.getValue("ROW")));
			message.setMessageVariable1((String) tRETURN.getValue("MESSAGE_V1"));
			message.setMessageVariable2((String) tRETURN.getValue("MESSAGE_V2"));
			message.setMessageVariable3((String) tRETURN.getValue("MESSAGE_V3"));
			message.setMessageVariable4((String) tRETURN.getValue("MESSAGE_V4"));
			
			//only add errors or warnings to log; count all types
			if(message.getType().equals("S")) {
				success=true;
				//messages.add(message);
			}
			else if(message.getType().equals("W")) {
				warning=true;
				messages.add(message);
			}
			else if(message.getType().equals("E")){
				error=true;
				messages.add(message);
			}
			else {
				error=true;
				messages.add(message);
			}
		}
		while(tRETURN.nextRow());
		
		if(success) {
			successCount++;
		}
		else if(warning){
			warningCount++;
		}
		else if(error) {
			errorCount++;
		}
		
		allMessages.addAll(messages);
		newMessages=messages;
		return messages;
	}

	private void clearTables() {
		tRETURN.clear();
	}

}
