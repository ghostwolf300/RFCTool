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
			execute();
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
	
	
	  public static List<ReturnMessage> getReturnMessages(){ 
		  return returnMessages;
	  }
	 

	
}
