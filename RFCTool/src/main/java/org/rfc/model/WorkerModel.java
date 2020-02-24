package org.rfc.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rfc.dto.Worker;
import org.rfc.dto.Worker.StatusCode;
import org.rfc.function.WorkerListener;

public class WorkerModel extends AbstractModel implements WorkerListener {
	
	public static enum Property{
		WORKERS,
		SELECTED_WORKER,
		WORKER_STATUS,
		WORKER_PROGRESS,
		TOTAL_RUN_TIME,
		WORKERS_EXECUTING
	};
	
	private List<Worker> workers=null;
	private long startTimeMs=-1;
	private long endTimeMs=-1;
	private long runTimeMs=-1;
	private int workerCount=0;
	private boolean executing=false;
	private Map<StatusCode,Integer> statusCountMap=null;
	private Worker selectedWorker=null;
	
	public WorkerModel() {
		super();
		initStatusCountMap();
	}
	
	private void initStatusCountMap() {
		statusCountMap=new HashMap<StatusCode,Integer>();
		statusCountMap.put(StatusCode.CREATED, 0);
		statusCountMap.put(StatusCode.READY, 0);
		statusCountMap.put(StatusCode.RUNNING, 0);
		statusCountMap.put(StatusCode.PAUSED, 0);
		statusCountMap.put(StatusCode.FINISHED, 0);
		statusCountMap.put(StatusCode.STOPPED, 0);
	}

	public List<Worker> getWorkers() {
		return workers;
	}

	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
		workerCount=this.workers.size();
		statusCountMap.put(StatusCode.CREATED, workerCount);
		for(Worker w : this.workers) {
			w.addWorkerListener(this);
		}
		this.firePropertyChange(Property.WORKERS.toString(), null, workers);
	}

	public Worker getSelectedWorker() {
		return selectedWorker;
	}

	public void setSelectedWorker(Worker selectedWorker) {
		this.selectedWorker = selectedWorker;
		this.firePropertyChange(Property.SELECTED_WORKER.toString(), null, this.selectedWorker);
	}

	public Map<StatusCode, Integer> getStatusCountMap() {
		return statusCountMap;
	}

	public void setStatusCountMap(Map<StatusCode, Integer> statusCountMap) {
		this.statusCountMap = statusCountMap;
	}

	public long getStartTimeMs() {
		return startTimeMs;
	}

	public long getEndTimeMs() {
		return endTimeMs;
	}

	public long getRunTimeMs() {
		return runTimeMs;
	}

	public boolean isExecuting() {
		return executing;
	}

	@Override
	public void statusChanged(Worker worker) {
		countStatuses();
		if(statusCountMap.get(StatusCode.CREATED)==workerCount
				&& statusCountMap.get(StatusCode.RUNNING)>0) {
			startTimeMs=System.currentTimeMillis();
			executing=true;
			this.firePropertyChange(Property.WORKERS_EXECUTING.toString(), null, executing);
		}
		else if(statusCountMap.get(StatusCode.FINISHED)+statusCountMap.get(StatusCode.STOPPED)==workerCount) {
			endTimeMs=System.currentTimeMillis();
			runTimeMs=endTimeMs-startTimeMs;
			executing=false;
			this.firePropertyChange(Property.WORKERS_EXECUTING.toString(), null, executing);
			this.firePropertyChange(Property.TOTAL_RUN_TIME.toString(), null, runTimeMs);
		}
		this.firePropertyChange(Property.WORKER_STATUS.toString(), null, worker);
		
		
	}
	
	private void countStatuses(){
		statusCountMap.put(StatusCode.CREATED, 0);
		statusCountMap.put(StatusCode.READY, 0);
		statusCountMap.put(StatusCode.RUNNING, 0);
		statusCountMap.put(StatusCode.PAUSED, 0);
		statusCountMap.put(StatusCode.FINISHED, 0);
		statusCountMap.put(StatusCode.STOPPED, 0);
		
		for(Worker w : workers) {
			switch(w.getStatus()) {
				case CREATED :
					statusCountMap.put(StatusCode.CREATED, statusCountMap.get(StatusCode.CREATED)+1);
					break;
				case READY :
					statusCountMap.put(StatusCode.READY, statusCountMap.get(StatusCode.READY)+1);
					break;
				case RUNNING :
					statusCountMap.put(StatusCode.RUNNING, statusCountMap.get(StatusCode.RUNNING)+1);
					break;
				case PAUSED :
					statusCountMap.put(StatusCode.PAUSED, statusCountMap.get(StatusCode.PAUSED)+1);
					break;
				case FINISHED :
					statusCountMap.put(StatusCode.FINISHED, statusCountMap.get(StatusCode.FINISHED)+1);
					break;
				case STOPPED :
					statusCountMap.put(StatusCode.STOPPED, statusCountMap.get(StatusCode.STOPPED)+1);
					break;
				default:
					break;
			}
		}
	}

	@Override
	public void progressUpdated(Worker worker) {
		this.firePropertyChange(Property.WORKER_PROGRESS.toString(), null, worker);
		long updTimeMs=System.currentTimeMillis()-startTimeMs;
		if(updTimeMs>=(runTimeMs+1000)) {
			runTimeMs=updTimeMs;
			this.firePropertyChange(Property.TOTAL_RUN_TIME.toString(), null, runTimeMs);
		}
		else {
			runTimeMs=updTimeMs;
		}
	}
	
	
}
