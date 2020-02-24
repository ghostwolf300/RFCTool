package org.rfc.controller;

import java.io.File;
import java.util.List;

import org.rfc.dto.ReturnMessage;
import org.rfc.dto.UserFunction;
import org.rfc.dto.Worker;
import org.rfc.service.RFCService;

public class DefaultController extends AbstractController {
	
	private RFCService service=null;
	
	public DefaultController() {
		super();
		service=new RFCService();
		this.addModel(service.getFunctionModel());
		this.addModel(service.getPreviewDataModel());
		this.addModel(service.getMaterialDataModel());
		this.addModel(service.getWorkerModel());
	}
	
	public void loadFunctions() {
		service.loadFunctions();
	}
	
	public void selectUserFunction(UserFunction<?> userFunction) {
		service.selectUserFunction(userFunction);
	}
	
	public void loadPreviewDataFile(File file) {
		service.loadPreviewDataFile(file);
	}
	
	public void loadInputDataFile(File file) {
		service.loadInputDataFile(file);
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
	
	public void showLog(Worker worker) {
		System.out.println("Show log for worker "+worker.getId());
		service.setSelectedWorker(worker);
	}
	
}
