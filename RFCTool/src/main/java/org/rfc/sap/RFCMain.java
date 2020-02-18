package org.rfc.sap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.rfc.dao.DAOFactory;
import org.rfc.dao.MaterialDAO;
import org.rfc.dao.ReturnMessageDAO;
import org.rfc.dao.access.AccessDAOFactory;
import org.rfc.dao.excel.ExcelDAOFactory;
import org.rfc.dto.Material;
import org.rfc.dto.PlantData;
import org.rfc.dto.ReturnMessage;
import org.rfc.dto.Worker.StatusCode;
import org.rfc.function.SaveMaterialReplica;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoTable;

public class RFCMain {
	
	private SAPConnection sap;
	
	public RFCMain() {
		sap=new SAPConnection();
	}
	
	public static void main(String[] args) {
		RFCMain main=new RFCMain();
		//main.ExecuteTest();
		//main.ExecuteThreadTest();
		main.daoTest();

	}
	
	public void daoTest() {
		long startTime=System.currentTimeMillis();
		boolean testRun=false;
		//String dbPath="C:/Users/ville.susi/Documents/Digital Development/BAPIDB.accdb";
		String dbPath="C:/Users/ville.susi/Documents/Digital Development/Test Data/Purch and MRP.xlsx";
		//DAOFactory daoFactory=new AccessDAOFactory(dbPath);
		DAOFactory daoFactory=new ExcelDAOFactory(new File(dbPath));
		MaterialDAO<Material> daoMaterial=daoFactory.getMaterialDAO();
		ReturnMessageDAO<ReturnMessage> daoReturnMessage=daoFactory.getReturnMessageDAO();
		//List<Material> materials=daoMaterial.getPlantDataList();
		List<Material> materials=daoMaterial.getOpenToPlantList();
		
		for(Material m : materials) { 
			System.out.println(m.getMaterialId());
			Set<String> plants=m.getPlantDataMap().keySet(); 
			for(String plant : plants) {
				PlantData pd=m.getPlantDataMap().get(plant);
				System.out.println(pd.getPlant()+"\t"+pd.getPlannedDeliveryTime()); 
			} 
		}
		 
		
		List<List<Material>> runs=this.slice(materials, 10000);
		System.out.println(runs.size());
		
		SapSystemFactory factory=new SapSystemFactory();
		SapSystem sap=null;
		try {
			sap = factory.getSapSystem("TETCLNT280");
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<SaveMaterialReplica> workers=new ArrayList<SaveMaterialReplica>();
		List<Thread> threads=new ArrayList<Thread>();
		SaveMaterialReplica func=null;
		Thread t=null;
		
		for(List<Material> run : runs) {
			System.out.println("Run count: "+run.size());
			func=new SaveMaterialReplica(-1,run,sap.getDestination());
			func.setTestRun(testRun);
			workers.add(func);
			t=new Thread(func);
			t.start();
			threads.add(t);
			try {
				Thread.sleep(2000);
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		boolean finished=false;
		while(finished==false){
			finished=true;
			for(SaveMaterialReplica w : workers) {
				if(w.getStatusCode()==StatusCode.RUNNING) {
					System.out.println(w.getProgress());
					finished=false;
				}
			}
			try {
				System.out.println("Sleeping 5s");
				Thread.sleep(5000);
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("End results");
		for(SaveMaterialReplica w1 : workers) {
			System.out.println(w1.getProgress()+"\tstatus: "+w1.getStatusCode()+"\tsuccess: "+w1.getSuccessCount()+"\terror: "+w1.getErrorCount());
		}
		
		long endTime=System.currentTimeMillis();
		long runTime=endTime-startTime;
		double hours=(runTime/1000)/3600;
		System.out.println("Run time (ms): "+runTime+"\tRun time (h): "+hours);
		
		for(ReturnMessage msg : SaveMaterialReplica.getReturnMessages()) {
			System.out.println(msg.toString());
		}
		
	}
	
	private List<List<Material>> slice(List<Material> materials, int maxRows){
		List<List<Material>> slicedList=new ArrayList<List<Material>>();
		List<Material> slice=new ArrayList<Material>();
		int counter=0;
		for(Material m : materials) {
			if(counter % maxRows==0 && counter>0) {
				slicedList.add(slice);
				slice=new ArrayList<Material>();
			}
			slice.add(m);
			counter++;
		}
		if(slice.size()>0) {
			slicedList.add(slice);
		}
		return slicedList;
	}
	
	public void ExecuteThreadTest() {
		
		SapSystemFactory factory=new SapSystemFactory();
		SapSystem sap=null;
		try {
			sap = factory.getSapSystem("TETCLNT280");
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SaveMaterialReplica f1=new SaveMaterialReplica(1,createTestMaterials1(),sap.getDestination());
		SaveMaterialReplica f2=new SaveMaterialReplica(2,createTestMaterials2(),sap.getDestination());
		
		f1.setTestRun(false);
		f2.setTestRun(false);
		
		Thread t1=new Thread(f1);
		Thread t2=new Thread(f2);
		t1.start();
		t2.start();
		
		while(t1.isAlive() || t2.isAlive()) {
			System.out.println("F1: "+f1.getProgress());
			System.out.println("F2: "+f2.getProgress());
			System.out.println("Thread(s) running... Sleeping 5s");
			try {
				Thread.sleep(5000);
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Ready!");
		System.out.println("F1: "+f1.getProgress());
		System.out.println("F2: "+f2.getProgress());
		
		for(ReturnMessage msg : SaveMaterialReplica.getReturnMessages()) {
			System.out.println(msg);
		}
		
		
	}
	
	private List<Material> createTestMaterials1(){
		List<Material> materials=new ArrayList<Material>();
		
		Material material=null;
		PlantData pd=null;
		
		material=new Material();
		material.setMaterialId("LIN1154322835");
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0702");
		pd.setPlannedDeliveryTime(2);
		material.addPlantData(pd.getPlant(), pd);
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0703");
		pd.setPlannedDeliveryTime(2);
		material.addPlantData(pd.getPlant(), pd);
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0704");
		pd.setPlannedDeliveryTime(2);
		material.addPlantData(pd.getPlant(), pd);
		
		materials.add(material);
		
		material=new Material();
		material.setMaterialId("LIN1834461200");
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0702");
		pd.setPlannedDeliveryTime(2);
		material.addPlantData(pd.getPlant(), pd);
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0703");
		pd.setPlannedDeliveryTime(2);
		material.addPlantData(pd.getPlant(), pd);
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0704");
		pd.setPlannedDeliveryTime(2);
		material.addPlantData(pd.getPlant(), pd);
		
		materials.add(material);
		
		material=new Material();
		material.setMaterialId("LIN0009839731");
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0702");
		pd.setPlannedDeliveryTime(2);
		material.addPlantData(pd.getPlant(), pd);
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0703");
		pd.setPlannedDeliveryTime(2);
		material.addPlantData(pd.getPlant(), pd);
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0704");
		pd.setPlannedDeliveryTime(2);
		material.addPlantData(pd.getPlant(), pd);
		
		materials.add(material);
		
		return materials;
	}
	
	private List<Material> createTestMaterials2(){
		List<Material> materials=new ArrayList<Material>();
		
		Material material=null;
		PlantData pd=null;
		
		material=new Material();
		material.setMaterialId("JDWBLV10029");
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0702");
		pd.setPlannedDeliveryTime(3);
		material.addPlantData(pd.getPlant(), pd);
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0703");
		pd.setPlannedDeliveryTime(4);
		material.addPlantData(pd.getPlant(), pd);
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0704");
		pd.setPlannedDeliveryTime(5);
		material.addPlantData(pd.getPlant(), pd);
		
		materials.add(material);
		
		material=new Material();
		material.setMaterialId("JDWBM24282");
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0702");
		pd.setPlannedDeliveryTime(3);
		material.addPlantData(pd.getPlant(), pd);
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0703");
		pd.setPlannedDeliveryTime(4);
		material.addPlantData(pd.getPlant(), pd);
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0704");
		pd.setPlannedDeliveryTime(5);
		material.addPlantData(pd.getPlant(), pd);
		
		materials.add(material);
		
		material=new Material();
		material.setMaterialId("JDWAL206482");
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0702");
		pd.setPlannedDeliveryTime(3);
		material.addPlantData(pd.getPlant(), pd);
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0703");
		pd.setPlannedDeliveryTime(4);
		material.addPlantData(pd.getPlant(), pd);
		
		pd=new PlantData();
		pd.setMaterialId(material.getMaterialId());
		pd.setPlant("0704");
		pd.setPlannedDeliveryTime(5);
		material.addPlantData(pd.getPlant(), pd);
		
		materials.add(material);
		
		return materials;
	}
	
	
	public void ExecuteTest() {
		
		SapSystemFactory factory=new SapSystemFactory();
		SapSystem sap=null;
		JCoDestination destination=null;
		JCoFunctionTemplate template=null;
		JCoFunction function=null;
		JCoRepository repository=null;
		
		JCoTable tHEADDATA=null;
		JCoTable tPLANTDATA=null;
		JCoTable tPLANTDATAX=null;
		JCoTable tRETURNMESSAGES=null;
		
		String material="LIN1154322835";
		String plant="0703";
		int plannedDeliveryTime=2;
		
		try {
			sap=factory.getSapSystem("TETCLNT280");
			
			System.out.println(sap.getName());
			System.out.println(sap.getMsHost());
			System.out.println(sap.getMsServ());
			System.out.println(sap.getGroup());
			System.out.println(sap.getRouter());
			System.out.println(sap.getClient());
			System.out.println("Ping succesful: "+sap.ping());
			
			destination=sap.getDestination();
			
			JCoContext.begin(destination);
			
			repository=destination.getRepository();
			template=repository.getFunctionTemplate("BAPI_MATERIAL_SAVEREPLICA");
			function=template.getFunction();
			
			function.getImportParameterList().setValue("NOAPPLLOG", "X");
			function.getImportParameterList().setValue("NOCHANGEDOC", "X");
			function.getImportParameterList().setValue("TESTRUN", " ");
			function.getImportParameterList().setValue("INPFLDCHECK", " ");
			
			
			tHEADDATA=function.getTableParameterList().getTable("HEADDATA");
			tPLANTDATA=function.getTableParameterList().getTable("PLANTDATA");
			tPLANTDATAX=function.getTableParameterList().getTable("PLANTDATAX");
			tRETURNMESSAGES=function.getTableParameterList().getTable("RETURNMESSAGES");
			
			tHEADDATA.appendRow();
			tHEADDATA.setValue("FUNCTION","UPD");
			tHEADDATA.setValue("MATERIAL",material);
			
			tPLANTDATA.appendRow();
			tPLANTDATA.setValue("FUNCTION", "UPD");
			tPLANTDATA.setValue("MATERIAL", material);
			tPLANTDATA.setValue("PLANT", plant);
			tPLANTDATA.setValue("PLND_DELRY", plannedDeliveryTime);
			
			tPLANTDATAX.appendRow();
			tPLANTDATAX.setValue("FUNCTION", "UPD");
			tPLANTDATAX.setValue("MATERIAL", material);
			tPLANTDATAX.setValue("PLANT", plant);
			tPLANTDATAX.setValue("PLND_DELRY", "X");
			
			function.execute(destination);
			
			while(tRETURNMESSAGES.nextRow()) {
				System.out.println(tRETURNMESSAGES.getValue("NUMBER"));
				System.out.println(tRETURNMESSAGES.getValue("TYPE"));
				System.out.println(tRETURNMESSAGES.getValue("MESSAGE"));
				System.out.println(tRETURNMESSAGES.getValue("ROW"));
				System.out.println(tRETURNMESSAGES.getValue("MESSAGE_V1"));
				System.out.println(tRETURNMESSAGES.getValue("MESSAGE_V2"));
				System.out.println(tRETURNMESSAGES.getValue("MESSAGE_V3"));
				System.out.println(tRETURNMESSAGES.getValue("MESSAGE_V4"));
			}
			
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
		}
		
		
		
	}

}
