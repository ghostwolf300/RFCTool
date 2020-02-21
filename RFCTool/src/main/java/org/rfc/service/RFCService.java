package org.rfc.service;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.rfc.dao.DAOFactory;
import org.rfc.dao.MaterialDAO;
import org.rfc.dao.excel.ExcelDAOFactory;
import org.rfc.dto.Material;
import org.rfc.dto.Material3;
import org.rfc.dto.UserFunction;
import org.rfc.dto.Worker;
import org.rfc.dto.Worker.StatusCode;
import org.rfc.function.AddPlantData;
import org.rfc.function.ChangePlantData;
import org.rfc.model.MaterialDataModel;
import org.rfc.model.PreviewDataModel;
import org.rfc.model.UserFunctionModel;
import org.rfc.model.WorkerModel;
import org.rfc.sap.SapSystem;
import org.rfc.sap.SapSystemFactory;

import com.sap.conn.jco.JCoException;

public class RFCService {
	
	private UserFunctionModel functionModel=null;
	private PreviewDataModel previewDataModel=null;
	private MaterialDataModel materialDataModel=null;
	private MaterialDAO<Material> materialDao=null;
	private WorkerModel workerModel=null;
	
	private StatusCode statusCode=null;
	
	public RFCService() {
		super();
		functionModel=new UserFunctionModel();
		previewDataModel=new PreviewDataModel();
		materialDataModel=new MaterialDataModel();
		workerModel=new WorkerModel();
		
	}

	public UserFunctionModel getFunctionModel() {
		return functionModel;
	}

	public void setFunctionModel(UserFunctionModel functionModel) {
		this.functionModel = functionModel;
	}

	public PreviewDataModel getPreviewDataModel() {
		return previewDataModel;
	}

	public void setPreviewDataModel(PreviewDataModel previewDataModel) {
		this.previewDataModel = previewDataModel;
	}

	public MaterialDataModel getMaterialDataModel() {
		return materialDataModel;
	}

	public void setMaterialDataModel(MaterialDataModel materialDataModel) {
		this.materialDataModel = materialDataModel;
	}
	
	public WorkerModel getWorkerModel() {
		return workerModel;
	}

	public void setWorkerModel(WorkerModel workerModel) {
		this.workerModel = workerModel;
	}
	
	public void loadFunctions() {
		List<String> functionNames=new ArrayList<String>();
		functionNames.add("AddPlantData");
		functionNames.add("ChangePlantData");
		List<UserFunction> functions=new ArrayList<UserFunction>();
		int counter=1;
		for(String fname : functionNames) {
			functions.add(new UserFunction(counter,fname));
			counter++;
		}
		functionModel.setFunctions(functions);
		
	}
	
	public void selectUserFunction(UserFunction userFunction) {
		functionModel.setSelectedFunction(userFunction);
	}
	
	public void loadPreviewDataFile(File file) {
		DAOFactory factory=new ExcelDAOFactory(file);
		materialDao=factory.getMaterialDAO();
		List<Row> previewDataList=materialDao.getPreviewDataList(50);
		System.out.println("Preview: "+previewDataList.size());
		previewDataModel.setPreviewDataList(previewDataList);
	}
	
	public void loadInputDataFile(File file) {
		List<Material> materials=null;
		UserFunction uf=functionModel.getSelectedFunction();
		DAOFactory factory=new ExcelDAOFactory(file);
		materialDao=factory.getMaterialDAO();
		try {
			Method method=materialDao.getClass().getMethod("get"+uf.getName()+"List", null);
			materials=(List<Material>) method.invoke(materialDao, null);
		} 
		catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		materialDataModel.setMaterials(materials);
	}
	
	public void createWorkers(int maxMaterials,boolean testRun) {
		List<Material> materials=materialDataModel.getMaterials();
		List<List<Material>> workerLists=this.slice(materials, maxMaterials);
		List<Worker> workers=new ArrayList<Worker>();
		
		SapSystemFactory factory=new SapSystemFactory();
		SapSystem sap=null;
		try {
			sap = factory.getSapSystem("TETCLNT280");
		} 
		catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int counter=1;
		for(List<Material> workerList : workerLists) {
			//Worker worker=new ChangePlantData(counter,workerList,sap.getDestination(),testRun);
			Worker worker=new ChangePlantData(counter,workerList,sap.getDestination(),testRun);
			workers.add(worker);
			counter++;
		}
		System.out.println("Workers: "+workers.size());
		workerModel.setWorkers(workers);
		statusCode=StatusCode.CREATED;
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
	
	public StatusCode getStatusCode() {
		return statusCode;
	}
	
	public void startOrPauseAll() {
		List<Worker> workers=workerModel.getWorkers();
		for(Worker worker : workers) {
			if(statusCode==StatusCode.CREATED) {
				System.out.println("starting worker: "+worker.getId());
				worker.startWorking();
			}
			else if(statusCode==StatusCode.RUNNING) {
				System.out.println("pausing worker: "+worker.getId());
				worker.pauseWorking();
			}
			else if(statusCode==StatusCode.PAUSED) {
				System.out.println("continue working: "+worker.getId());
				worker.continueWorking();
			}
		}
	}
	
	public void stopAll() {
		List<Worker> workers=workerModel.getWorkers();
		for(Worker worker : workers) {
			System.out.println("stop working: "+worker.getId());
			worker.stopWorking();
		}
		statusCode=StatusCode.STOPPED;
	}
	
	
}
