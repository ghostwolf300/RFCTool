package org.rfc.model;

import java.util.List;

import org.rfc.dto.Worker;
import org.rfc.dto.Worker.StatusCode;

public class WorkerModel extends AbstractModel {
	public static final String P_WORKERS="p_workers";
	
	public List<Worker> workers=null;
	
	public WorkerModel() {
		super();
	}

	public List<Worker> getWorkers() {
		return workers;
	}

	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
		this.firePropertyChange(P_WORKERS, null, workers);
	}
	
	public void changeWorkerStatus(Worker worker,StatusCode statusCode) {
		
	}
	
	
}
