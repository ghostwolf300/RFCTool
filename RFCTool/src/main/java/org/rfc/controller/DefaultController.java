package org.rfc.controller;

import java.io.File;

import org.rfc.service.RFCService;

public class DefaultController extends AbstractController {
	
	private RFCService service=null;
	
	public DefaultController() {
		super();
		service=new RFCService();
		this.addModel(service.getMaterialDataModel());
		
	}
	
	public void loadPlantDataFile(File file) {
		service.loadPlantDataFile(file);
	}
	
	public void setRunSize(int runSize) {
		
	}
	
	
	
}
