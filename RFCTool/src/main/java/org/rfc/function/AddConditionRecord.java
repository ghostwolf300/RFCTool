package org.rfc.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.rfc.dto.ConditionRecord;
import org.rfc.dto.Material;
import org.rfc.dto.ReturnMessage;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoTable;

public class AddConditionRecord extends RunnableFunction {
	
	public static final String FUNCTION_NAME="AddConditionRecord";
	public static final String F_CREATE_CONDITION="BAPI_PRICES_CONDITIONS";
	
	private static List<ReturnMessage> returnMessages=Collections.synchronizedList(new ArrayList<ReturnMessage>());
	
	protected JCoTable tBAPICONDIT=null;
	protected JCoTable tBAPIRET2=null;
	
	private List<ConditionRecord> conditionRecords=null;
	
	public AddConditionRecord() {
		super();
		initialize();
	}
	
	public AddConditionRecord(int id,List<ConditionRecord> conditionRecords,JCoDestination destination,boolean testRun) {
		super(id,destination,testRun);
		this.conditionRecords=conditionRecords;
		initialize();
	}
	
	private void initialize() {
		
	}
	
	@Override
	public String getFunctionName() {
		return FUNCTION_NAME;
	}

	@Override
	protected void doWork() throws JCoException {
		// TODO Auto-generated method stub

	}

}
