package org.rfc.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.rfc.dao.DAOFactory;
import org.rfc.dao.MaterialDAO;
import org.rfc.dao.excel.ExcelDAOFactory;
import org.rfc.dto.Material;
import org.rfc.dto.Worker;
import org.rfc.function.SaveMaterialReplica;
import org.rfc.model.MaterialDataModel;
import org.rfc.model.WorkerModel;
import org.rfc.sap.SapSystem;
import org.rfc.sap.SapSystemFactory;

import com.sap.conn.jco.JCoException;

public class RFCService {
	
	private MaterialDataModel materialDataModel=null;
	private MaterialDAO<Material> materialDao=null;
	private WorkerModel workerModel=null;
	
	public RFCService() {
		super();
		materialDataModel=new MaterialDataModel();
		workerModel=new WorkerModel();
		
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

	public void loadPlantDataFile(File file) {
		DAOFactory factory=new ExcelDAOFactory(file);
		materialDao=factory.getMaterialDAO();
		List<Material> materials=materialDao.getPlantDataList();
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
			Worker worker=new SaveMaterialReplica(counter,workerList,sap.getDestination(),testRun);
			workers.add(worker);
			counter++;
		}
		System.out.println("Workers: "+workers.size());
		workerModel.setWorkers(workers);
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
	
	public void startAll() {
		List<Worker> workers=workerModel.getWorkers();
		for(Worker worker : workers) {
			worker.startWorking();
		}
	}
	
	public void pauseAll() {
		List<Worker> workers=workerModel.getWorkers();
		for(Worker worker : workers) {
			worker.pauseWorking();
		}
	}
	
	public void stopAll() {
		List<Worker> workers=workerModel.getWorkers();
		for(Worker worker : workers) {
			worker.stopWorking();
		}
	}
	
}