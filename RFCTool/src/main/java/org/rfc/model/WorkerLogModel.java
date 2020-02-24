package org.rfc.model;

import org.rfc.dto.Worker;
import org.rfc.function.WorkerListener;

public class WorkerLogModel extends AbstractModel implements WorkerListener {
	
	public static enum Property {
		LOG,
		STATUS
	};
	
	private Worker worker=null;
	
	public WorkerLogModel() {
		super();
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
		this.worker.addWorkerListener(this);
	}

	@Override
	public void statusChanged(Worker worker) {
		this.firePropertyChange(Property.STATUS.toString(), null, worker.getStatus());
	}

	@Override
	public void progressUpdated(Worker worker) {
		this.firePropertyChange(Property.LOG.toString(), null, worker.getNewMessages());
	}
	
}
