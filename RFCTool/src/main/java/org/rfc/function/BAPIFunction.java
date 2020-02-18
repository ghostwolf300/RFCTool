package org.rfc.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.rfc.dto.ReturnMessage;
import org.rfc.dto.Worker;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoFunctionTemplate;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoTable;

public abstract class BAPIFunction implements Runnable,Worker {
	
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
	
	private List<WorkerListener> listeners=Collections.synchronizedList(new ArrayList<WorkerListener>());
	private Thread thread=null;
	
	public BAPIFunction(JCoDestination destination) {
		this.destination=destination;
		this.statusCode=StatusCode.CREATED;
	}
	
	public BAPIFunction(JCoDestination destination,boolean testRun) {
		this.destination=destination;
		this.testRun=testRun;
		this.statusCode=StatusCode.CREATED;
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
		double percentage=processedCount/workload;
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

	protected void initialize(String functionName) throws JCoException {
		repository=destination.getRepository();
		template=repository.getFunctionTemplate(functionName);
		function=template.getFunction();
		//returnMessages=new ArrayList<ReturnMessage>();
	}
	
	protected List<ReturnMessage> createReturnMessages(JCoTable tRETURNMESSAGES,String material){
		List<ReturnMessage> messages=new ArrayList<ReturnMessage>();
		ReturnMessage message=null;
		//is this needed?
		//tRETURNMESSAGES.firstRow();
		while(tRETURNMESSAGES.nextRow()) {
			message=new ReturnMessage();
			message.setMaterial(material);
			message.setNumber((String)tRETURNMESSAGES.getValue("NUMBER"));
			message.setType((String)tRETURNMESSAGES.getValue("TYPE"));
			message.setMessage((String) tRETURNMESSAGES.getValue("MESSAGE"));
			message.setRow(String.valueOf(tRETURNMESSAGES.getValue("ROW")));
			message.setMessageVariable1((String) tRETURNMESSAGES.getValue("MESSAGE_V1"));
			message.setMessageVariable2((String) tRETURNMESSAGES.getValue("MESSAGE_V2"));
			message.setMessageVariable3((String) tRETURNMESSAGES.getValue("MESSAGE_V3"));
			message.setMessageVariable4((String) tRETURNMESSAGES.getValue("MESSAGE_V4"));
			if(message.getNumber().equals("103") || message.getNumber().equals("801") || message.getNumber().equals("048") || message.getNumber().equals("810") || message.getType().equals("E")) {
				if(message.getType().equals("S")) {
					successCount++;
				}
				else {
					errorCount++;
				}
				//Only add selected messages...
				//messages.add(message);
			}
			messages.add(message);
		}
		return messages;
	}
	
	@Override
	public synchronized void startWorking() {
		this.statusCode=StatusCode.RUNNING;
		thread=new Thread(this);
		thread.start();
		this.statusChanged();
	}

	@Override
	public synchronized void pauseWorking() {
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

	@Override
	public synchronized void continueWorking() {
		if(this.statusCode==StatusCode.PAUSED) {
			thread.notify();
		}
		this.statusCode=StatusCode.RUNNING;
		this.statusChanged();
		
	}

	@Override
	public synchronized void stopWorking() {
		this.statusCode=StatusCode.STOPPED;
	}
	
	public void addWorkerListener(WorkerListener listener) {
		listeners.add(listener);
	}
	
	protected void statusChanged() {
		synchronized(listeners) {
			for(WorkerListener listener : listeners) {
				listener.statusChanged(this);
			}
		}
	}
	
	protected void progressUpdated() {
		synchronized(listeners) {
			for(WorkerListener listener : listeners) {
				listener.progressUpdated(this);
			}
		}
	}
	
	
}

	

