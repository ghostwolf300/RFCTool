package org.rfc.controller;

import org.rfc.service.RFCService;

public class DefaultController extends AbstractController {
	
	private RFCService service=null;
	
	public DefaultController() {
		super();
		service=new RFCService();
		this.addModel(service.getMaterialDataModel());
		
	}
	
	
	
}
