package org.rfc.function;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.rfc.dto.ReturnMessage;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoTable;

public abstract class BAPIFunction {
	
	protected JCoDestination destination=null;
	protected JCoFunctionTemplate template=null;
	protected JCoFunction function=null;
	protected JCoRepository repository=null;
	protected int listSize=0;
	protected int processedCount=0;
	protected int successCount=0;
	protected int errorCount=0;
	//protected List<ReturnMessage> returnMessages=null;
	
	public BAPIFunction(JCoDestination destination) {
		this.destination=destination;
	}

	public JCoDestination getDestination() {
		return destination;
	}

	public void setDestination(JCoDestination destination) {
		this.destination = destination;
	}
	
	/*
	 * public List<ReturnMessage> getReturnMessages() { return returnMessages; }
	 */

	public int getListSize() {
		return listSize;
	}

	public int getProcessedCount() {
		return processedCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public int getErrorCount() {
		return errorCount;
	}
	
	public String getProgress() {
		double percentage=processedCount/listSize;
		NumberFormat format=NumberFormat.getPercentInstance();
		return "Progress: "+processedCount +" / "+listSize+" ("+format.format(percentage)+")\tsuccess: "+successCount+"\terror: "+errorCount;
	}

	protected void initialize(String functionName) throws JCoException {
		repository=destination.getRepository();
		template=repository.getFunctionTemplate(functionName);
		function=template.getFunction();
		//returnMessages=new ArrayList<ReturnMessage>();
	}
	
	protected List<ReturnMessage> createReturnMessages(JCoTable tRETURNMESSAGES,String material){
		List<ReturnMessage> messages=new ArrayList<ReturnMessage>();
		ReturnMessage message=null;
		//is this needed?
		//tRETURNMESSAGES.firstRow();
		while(tRETURNMESSAGES.nextRow()) {
			message=new ReturnMessage();
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
				//Only add selected messages...
				//messages.add(message);
			}
			messages.add(message);
		}
		return messages;
	}

	
}
