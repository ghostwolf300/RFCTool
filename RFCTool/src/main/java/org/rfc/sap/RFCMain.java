package org.rfc.sap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.lang.reflect.Type;

import org.rfc.dao.DAOFactory;
import org.rfc.dao.MaterialDAO;
import org.rfc.dao.text.TextFileDAOFactory;
import org.rfc.dto.FieldValue;
import org.rfc.dto.InputField;
import org.rfc.dto.PlantData;
import org.rfc.dto.Material;
import org.rfc.dto.UserFunction;
import org.rfc.dto.ReturnMessage;
import org.rfc.dto.Worker;
import org.rfc.function.ChangePlantData;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoStructure;
import com.sap.conn.jco.JCoTable;

public class RFCMain {
	
	private SAPConnection sap;
	
	public RFCMain() {
		sap=new SAPConnection();
	}
	
	public static void main(String[] args) {
		RFCMain main=new RFCMain();
		main.ExecuteTest();
		//main.ExecuteThreadTest();
		//main.daoTest();
		//main.reflectionTest();
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
		InputField<String> fMaterialId=new InputField<String>("MATERIAL",true,String.class);
		InputField<String> fType=new InputField<String>("MATL_TYPE",false,String.class);
		InputField<Double> fGroup=new InputField<Double>("GROUP_ID",false,Double.class);
		
		List<InputField<?>> fields=new ArrayList<InputField<?>>();
		fields.add(fMaterialId);
		fields.add(fType);
		fields.add(fGroup);
		
		for(InputField<?> f : fields) {
			System.out.println(f.getValueClass());
		}
		
	}
	
	public void daoTest() {
		
		String dbPath="C:/Users/ville.susi/Documents/Digital Development/MD Side Tasks/Do Not Cost update/DoNotCostUpdate_All_Plants.txt";
		
		DAOFactory daoFactory=new TextFileDAOFactory(new File(dbPath));
		MaterialDAO<Material> daoMaterial=daoFactory.getMaterialDAO();
		List<Material> materials=daoMaterial.getChangePlantDataList();
		
		System.out.println("Materials size: "+materials.size());
		
		Material m=materials.get(0);
		System.out.println(m.getMaterialId());
		Map<String,PlantData> pdMap=m.getPlantDataMap();
		Set<String> keySet=pdMap.keySet();
		for(String plant : keySet) {
			PlantData pd=pdMap.get(plant);
			System.out.println(pd.getPlant()+"\t"+pd.isDoNotCost());
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
		JCoFunction commitFunction;
		JCoRepository repository=null;
		JCoTable tRETURN=null;
		
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
			template=repository.getFunctionTemplate("BAPI_OBJCL_CREATE");
			function=template.getFunction();
			template=repository.getFunctionTemplate("BAPI_TRANSACTION_COMMIT");
			commitFunction=template.getFunction();
			
			String material="JDW0050025204";
			
			function.getImportParameterList().setValue("OBJECTKEYNEW", material);
			function.getImportParameterList().setValue("OBJECTTABLENEW","MARA");
			function.getImportParameterList().setValue("CLASSNUMNEW", "ECOMMERCE");
			function.getImportParameterList().setValue("CLASSTYPENEW", "001");
			tRETURN=function.getTableParameterList().getTable("RETURN");
			
			function.execute(destination);
			commitFunction.execute(destination);
			
			do {
				System.out.println(tRETURN.getValue("TYPE")+" "+tRETURN.getValue("ID")+" "+tRETURN.getValue("NUMBER")+" "+tRETURN.getValue("MESSAGE"));
			}
			while(tRETURN.nextRow());
			
			
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
