package org.rfc.sap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rfc.dao.DAOFactory;
import org.rfc.dao.MaterialDAO;
import org.rfc.dao.ReturnMessageDAO;
import org.rfc.dao.excel.ExcelDAOFactory;
import org.rfc.dto.FieldValue;
import org.rfc.dto.InputField;
import org.rfc.dto.Material3;
import org.rfc.dto.PlantData;
import org.rfc.dto.Material;
import org.rfc.dto.PlantData3;
import org.rfc.dto.UserFunction;
import org.rfc.dto.ReturnMessage;
import org.rfc.dto.Worker;
import org.rfc.dto.Worker.StatusCode;
import org.rfc.function.AddPlantData;
import org.rfc.function.ChangePlantData;

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
		//main.daoTest();
		main.reflectionTest();
		//main.fieldsTest();

	}
	
	public void reflectionTest() {
		UserFunction<ChangePlantData> f=new UserFunction<ChangePlantData>(ChangePlantData.class,1);
		Map<String,InputField<?>> fieldMap=f.getFieldMap();
		InputField<Double> mappedField=(InputField<Double>) fieldMap.get("PLND_DELRY");
		System.out.println("PLND_DELRY mapped field: "+mappedField.getMappedColumn());
		List<Material> materials=new ArrayList<Material>();
		materials.add(new Material());
		materials.add(new Material());
		Worker w=f.createWorker(1, materials, null, true);
		
	}
	
	public void fieldsTest() {
		InputField<String> fMaterialId=new InputField<String>("MATERIAL",true);
		InputField<String> fType=new InputField<String>("MATL_TYPE",false);
		InputField<Double> fGroup=new InputField<Double>("GROUP_ID",false);
		
		Material m = new Material();
		FieldValue<String> fval=new FieldValue<String>(fMaterialId,false);
		fval.setValue("TESTI00001");
	
		m.setMaterialId(fval);
		
		
		System.out.println(m.getMaterialId().getValue());
		
	}
	
	public void daoTest() {
		long startTime=System.currentTimeMillis();
		boolean testRun=true;
		//String dbPath="C:/Users/ville.susi/Documents/Digital Development/BAPIDB.accdb";
		String dbPath="C:/Users/ville.susi/Documents/Digital Development/Test Data/AddPlantData.xlsx";
		//DAOFactory daoFactory=new AccessDAOFactory(dbPath);
		DAOFactory daoFactory=new ExcelDAOFactory(new File(dbPath));
		MaterialDAO<Material> daoMaterial=daoFactory.getMaterialDAO();
		ReturnMessageDAO<ReturnMessage> daoReturnMessage=daoFactory.getReturnMessageDAO();
		//List<Material3> materials=daoMaterial.getPlantDataList();
		List<Material> material3s=daoMaterial.getAddPlantDataList();
		
		for(Material m : material3s) { 
			System.out.println(m.getMaterialId());
			Set<String> plants=m.getPlantDataMap().keySet(); 
			for(String plant : plants) {
				PlantData pd=m.getPlantDataMap().get(plant);
				System.out.println(pd.getPlant()+"\t"+pd.getPlannedDeliveryTime()); 
			} 
		}
		 
		
		List<List<Material>> runs=this.slice(material3s, 10000);
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
		
		List<Worker> workers=new ArrayList<Worker>();
		List<Thread> threads=new ArrayList<Thread>();
		AddPlantData func=null;
		Thread t=null;
		
		for(List<Material> run : runs) {
			System.out.println("Run count: "+run.size());
			//func=new AddPlantData(-1,run,sap.getDestination());
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
			for(Worker w : workers) {
				if(w.getStatus()==StatusCode.RUNNING) {
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
		for(Worker w1 : workers) {
			System.out.println(w1.getProgress()+"\tstatus: "+w1.getStatus()+"\tsuccess: "+w1.getSuccessCount()+"\terror: "+w1.getErrorCount());
		}
		
		long endTime=System.currentTimeMillis();
		long runTime=endTime-startTime;
		double hours=(runTime/1000)/3600;
		System.out.println("Run time (ms): "+runTime+"\tRun time (h): "+hours);
		
		for(ReturnMessage msg : AddPlantData.getReturnMessages()) {
			System.out.println(msg.toString());
		}
		
	}
	
	private List<List<Material>> slice(List<Material> material3s, int maxRows){
		List<List<Material>> slicedList=new ArrayList<List<Material>>();
		List<Material> slice=new ArrayList<Material>();
		int counter=0;
		for(Material m : material3s) {
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
		
		ChangePlantData f1=null;//new ChangePlantData(1,createTestMaterials1(),sap.getDestination());
		ChangePlantData f2=null;//new ChangePlantData(2,createTestMaterials2(),sap.getDestination());
		
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
		
		for(ReturnMessage msg : ChangePlantData.getReturnMessages()) {
			System.out.println(msg);
		}
		
		
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
			tHEADDATA.setValue("F_MATERIAL_SAVE","UPD");
			tHEADDATA.setValue("MATERIAL",material);
			
			tPLANTDATA.appendRow();
			tPLANTDATA.setValue("F_MATERIAL_SAVE", "UPD");
			tPLANTDATA.setValue("MATERIAL", material);
			tPLANTDATA.setValue("PLANT", plant);
			tPLANTDATA.setValue("PLND_DELRY", plannedDeliveryTime);
			
			tPLANTDATAX.appendRow();
			tPLANTDATAX.setValue("F_MATERIAL_SAVE", "UPD");
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
