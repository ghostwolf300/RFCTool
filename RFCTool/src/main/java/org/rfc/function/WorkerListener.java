package org.rfc.function;

import org.rfc.dto.Worker;

public interface WorkerListener {
	
	public void statusChanged(Worker worker);
	public void progressUpdated(Worker worker);
	
}
