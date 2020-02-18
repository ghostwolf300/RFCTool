package org.rfc.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rfc.dto.Worker;
import org.rfc.dto.Worker.StatusCode;
import org.rfc.function.WorkerListener;

public class WorkerModel extends AbstractModel implements WorkerListener {
	
	public static final String P_WORKERS="p_workers";
	public static final String P_WORKER_STATUS="p_worker_status";
	public static final String P_WORKER_PROGRESS="p_worker_progress";
	public static final String P_TOTAL_RUN_TIME="p_total_run_time";
	public static final String P_WORKERS_EXECUTING="p_workers_executing";
	
	private List<Worker> workers=null;
	private long startTimeMs=-1;
	private long endTimeMs=-1;
	private long runTimeMs=-1;
	private int workerCount=0;
	private boolean executing=false;
	private Map<StatusCode,Integer> statusCountMap=null;
	
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
		this.firePropertyChange(P_WORKERS, null, workers);
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
			this.firePropertyChange(P_WORKERS_EXECUTING, null, executing);
		}
		else if(statusCountMap.get(StatusCode.FINISHED)+statusCountMap.get(StatusCode.STOPPED)==workerCount) {
			endTimeMs=System.currentTimeMillis();
			runTimeMs=endTimeMs-startTimeMs;
			executing=false;
			this.firePropertyChange(P_WORKERS_EXECUTING, null, executing);
			this.firePropertyChange(P_TOTAL_RUN_TIME, null, runTimeMs);
		}
		this.firePropertyChange(P_WORKER_STATUS, null, worker);
		
		
	}
	
	private void countStatuses(){
		statusCountMap.put(StatusCode.CREATED, 0);
		statusCountMap.put(StatusCode.READY, 0);
		statusCountMap.put(StatusCode.RUNNING, 0);
		statusCountMap.put(StatusCode.PAUSED, 0);
		statusCountMap.put(StatusCode.FINISHED, 0);
		statusCountMap.put(StatusCode.STOPPED, 0);
		
		for(Worker w : workers) {
			switch(w.getStatusCode()) {
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
		this.firePropertyChange(P_WORKER_PROGRESS, null, worker);
		long updTimeMs=System.currentTimeMillis()-startTimeMs;
		if(updTimeMs>=(runTimeMs+1000)) {
			runTimeMs=updTimeMs;
			this.firePropertyChange(P_TOTAL_RUN_TIME, null, runTimeMs);
		}
		else {
			runTimeMs=updTimeMs;
		}
	}
	
	
}
