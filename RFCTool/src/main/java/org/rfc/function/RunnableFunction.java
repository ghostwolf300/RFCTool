package org.rfc.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rfc.dto.InputField;
import org.rfc.dto.ReturnMessage;
import org.rfc.dto.Worker;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoTable;

public abstract class RunnableFunction implements Runnable,Worker {
	
	protected int id=-1;
	protected JCoDestination destination=null;
	protected Map<String,JCoFunction> functionMap=null;
	protected boolean testRun=true;
	protected StatusCode status=null;
	protected int workload=0;
	protected int processedCount=0;
	protected int successCount=0;
	protected int warningCount=0;
	protected int errorCount=0;
	protected long startTimeMs=0;
	protected long endTimeMs=0;
	protected long runTimeMs=0;
	protected List<ReturnMessage> allMessages=null;
	protected List<ReturnMessage> newMessages=null;
	
	private List<WorkerListener> listeners=Collections.synchronizedList(new ArrayList<WorkerListener>());
	private Thread thread=null;
	private static int threadCount=0;
	
	public RunnableFunction() {
		status=StatusCode.CREATED;
	}
	
	public RunnableFunction(int id) {
		this.id=id;
		status=StatusCode.CREATED;
	}
	
	public RunnableFunction(int id,JCoDestination destination) {
		this.id=id;
		this.destination=destination;
		allMessages=new ArrayList<ReturnMessage>();
		status=StatusCode.CREATED;
	}
	
	public RunnableFunction(int id,JCoDestination destination,boolean testRun) {
		this.id=id;
		this.destination=destination;
		this.testRun=testRun;
		allMessages=new ArrayList<ReturnMessage>();
		status=StatusCode.CREATED;
	}
	
	protected void initialize(String[] functionNames) {
		JCoFunctionTemplate template=null;
		JCoFunction function=null;
		JCoRepository repo=null;
		functionMap=new HashMap<String,JCoFunction>();
		try {
			repo=destination.getRepository();
			for(String functionName : functionNames) {
				template=repo.getFunctionTemplate(functionName);
				function=template.getFunction();
				functionMap.put(functionName, function);
			}
		} 
		catch (JCoException e) {
			status=StatusCode.ERROR;
			statusChanged();
			e.printStackTrace();
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public JCoDestination getDestination() {
		return destination;
	}

	public void setDestination(JCoDestination destination) {
		this.destination = destination;
	}
	
	public int getWorkload() {
		return workload;
	}

	public int getProcessedCount() {
		return processedCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public int getWarningCount() {
		return warningCount;
	}

	public int getErrorCount() {
		return errorCount;
	}
	
	public double getProgress() {
		double percentage=((double)processedCount)/((double)workload);
		return percentage;
	}

	public boolean isTestRun() {
		return testRun;
	}

	public void setTestRun(boolean testRun) {
		this.testRun = testRun;
	}

	public StatusCode getStatus() {
		return status;
	}

	public void setStatus(StatusCode statusCode) {
		this.status = statusCode;
	}
	
	public long getRunTimeMs() {
		return runTimeMs;
	}

	public List<ReturnMessage> getNewMessages() {
		return newMessages;
	}

	public List<ReturnMessage> getAllMessages() {
		return allMessages;
	}
	
	public static int getThreadCount() {
		return threadCount;
	}
	
	protected abstract void doWork() throws JCoException;
	
	public void run() {
		startTimeMs=System.currentTimeMillis();
		status=StatusCode.RUNNING;
		this.statusChanged();
		try {
			doWork();
		}
		catch(JCoException e) {
			e.printStackTrace();
			status=StatusCode.ERROR;
		}
		finally {
			try {
				JCoContext.end(destination);
			} 
			catch (JCoException e) {
				e.printStackTrace();
			}
			
			endTimeMs=System.currentTimeMillis();
			runTimeMs=endTimeMs-startTimeMs;
			
			status=StatusCode.FINISHED;
			this.statusChanged();
		}
	}
	
	protected void pauseThread() {
		try {
			thread.wait();
		} 
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized void startWorking() {
		if(status!=StatusCode.RUNNING) {
			status=StatusCode.RUNNING;
			thread=new Thread(this);
			thread.start();
			statusChanged();
		}
	}

	@Override
	public synchronized void pauseWorking() {
		if(status==StatusCode.RUNNING) {
			status=StatusCode.PAUSED;
		}
	}

	@Override
	public synchronized void continueWorking() {
		if(thread.getState()==Thread.State.WAITING && status==StatusCode.PAUSED) {
			thread.notify();
			status=StatusCode.RUNNING;
			statusChanged();
		}
	}

	@Override
	public synchronized void stopWorking() {
		if(status!=StatusCode.STOPPED) {
			status=StatusCode.STOPPED;
			statusChanged();
		}
	}
	
	public synchronized void changeStatus(StatusCode newStatusCode) {
		status=newStatusCode;
		statusChanged();
	}
	
	public void addWorkerListener(WorkerListener listener) {
		listeners.add(listener);
	}
	
	protected void statusChanged() {
		if(status==StatusCode.FINISHED || status==StatusCode.STOPPED) {
			endTimeMs=System.currentTimeMillis();
			runTimeMs=endTimeMs-startTimeMs;
		}
		synchronized(listeners) {
			for(WorkerListener listener : listeners) {
				listener.statusChanged(this);
			}
		}
	}
	
	protected void progressUpdated() {
		runTimeMs=System.currentTimeMillis()-startTimeMs;
		synchronized(listeners) {
			for(WorkerListener listener : listeners) {
				listener.progressUpdated(this);
			}
		}
	}
	
	
}

	

