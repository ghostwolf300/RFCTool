package org.rfc.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
	protected JCoFunctionTemplate template=null;
	protected JCoFunction function=null;
	protected JCoRepository repository=null;
	protected boolean testRun=true;
	protected StatusCode statusCode=null;
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
	
	public RunnableFunction(int id,JCoDestination destination) {
		this.id=id;
		this.destination=destination;
		allMessages=new ArrayList<ReturnMessage>();
		statusCode=StatusCode.CREATED;
	}
	
	public RunnableFunction(int id,JCoDestination destination,boolean testRun) {
		this.id=id;
		this.destination=destination;
		this.testRun=testRun;
		allMessages=new ArrayList<ReturnMessage>();
		statusCode=StatusCode.CREATED;
	}
	
	protected void initialize(String functionName) throws JCoException {
		repository=destination.getRepository();
		template=repository.getFunctionTemplate(functionName);
		function=template.getFunction();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	protected abstract void doWork() throws JCoException;
	
	public void run() {
		startTimeMs=System.currentTimeMillis();
		statusCode=StatusCode.RUNNING;
		this.statusChanged();
		try {
			doWork();
		}
		catch(JCoException e) {
			e.printStackTrace();
			//statusCode=StatusCode.STOPPED;
		}
		finally {
			try {
				JCoContext.end(destination);
			} 
			catch (JCoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			statusCode=StatusCode.FINISHED;
			endTimeMs=System.currentTimeMillis();
			runTimeMs=endTimeMs-startTimeMs;
			this.statusChanged();
			//this.progressUpdated();
		}
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

	public StatusCode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(StatusCode statusCode) {
		this.statusCode = statusCode;
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
	
	@Override
	public synchronized void startWorking() {
		if(this.statusCode!=StatusCode.RUNNING) {
			this.statusCode=StatusCode.RUNNING;
			thread=new Thread(this);
			thread.start();
			this.statusChanged();
		}
	}

	@Override
	public synchronized void pauseWorking() {
		if(this.statusCode==StatusCode.RUNNING) {
			this.statusCode=StatusCode.PAUSED;
			this.statusChanged();
			try {
				thread.wait();
			} 
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public synchronized void continueWorking() {
		if(this.statusCode==StatusCode.PAUSED) {
			thread.notify();
			this.statusCode=StatusCode.RUNNING;
			this.statusChanged();
		}
	}

	@Override
	public synchronized void stopWorking() {
		if(this.statusCode!=StatusCode.STOPPED) {
			this.statusCode=StatusCode.STOPPED;
			this.statusChanged();
		}
	}
	
	public synchronized void changeStatus(StatusCode newStatusCode) {
		this.statusCode=newStatusCode;
		this.statusChanged();
	}
	
	public void addWorkerListener(WorkerListener listener) {
		listeners.add(listener);
	}
	
	protected void statusChanged() {
		if(statusCode==StatusCode.FINISHED || statusCode==StatusCode.STOPPED) {
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

	
