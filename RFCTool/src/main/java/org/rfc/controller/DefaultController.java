package org.rfc.controller;

import java.io.File;

import org.rfc.service.RFCService;

public class DefaultController extends AbstractController {
	
	private RFCService service=null;
	
	public DefaultController() {
		super();
		service=new RFCService();
		this.addModel(service.getMaterialDataModel());
		this.addModel(service.getWorkerModel());
		
	}
	
	public void loadPlantDataFile(File file) {
		service.loadPlantDataFile(file);
	}
	
	public void createWorkers(int maxMaterials,boolean testRun) {
		service.createWorkers(maxMaterials,testRun);
	}
	
	public void startOrPauseAll() {
		service.startOrPauseAll();
	}
	
	public void stopAll() {
		service.stopAll();
	}
	
	
	
}
