package org.rfc.dto;

import org.rfc.function.WorkerListener;

public interface Worker {
	
	public static enum StatusCode{
		CREATED,
		READY,
		RUNNING,
		PAUSED,
		FINISHED,
		STOPPED
	}
	
	public int getId();
	public String getFunctionName();
	public boolean isTestRun();
	public StatusCode getStatusCode();
	public int getWorkload();
	public int getProcessedCount();
	public double getProgress();
	public int getSuccessCount();
	public int getWarningCount();
	public int getErrorCount();
	public void startWorking();
	public void pauseWorking();
	public void continueWorking();
	public void stopWorking();
	public void addWorkerListener(WorkerListener listener);
	
	
}
