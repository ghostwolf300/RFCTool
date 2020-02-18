package org.rfc.ui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.rfc.dto.Worker;
import org.rfc.dto.Worker.StatusCode;
import org.rfc.function.WorkerListener;

public class WorkerTableModel extends AbstractTableModel implements WorkerListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int COL_ID=0;
	public static final int COL_FUNCTION_NAME=1;
	public static final int COL_TESTRUN=2;
	public static final int COL_WORKLOAD=3;
	public static final int COL_PROCESSED=4;
	public static final int COL_PROGRESS=5;
	public static final int COL_RUN_TIME=6;
	public static final int COL_SUCCESS=7;
	public static final int COL_WARNING=8;
	public static final int COL_ERROR=9;
	public static final int COL_STATUS=10;
	public static final int COL_LOG=11;
	public static final int COL_CONTROL=12;
	
	private List<Worker> workers=null;
	
	public WorkerTableModel() {
		super();
	}
	
	@Override
	public int getColumnCount() {
		return 13;
	}

	@Override
	public int getRowCount() {
		if(workers==null) {
			return 0;
		}
		else {
			return workers.size();
		}
	}

	@Override
	public Object getValueAt(int row, int col) {
		if(workers==null) {
			return null;
		}
		else {
			Worker worker=workers.get(row);
			switch(col) {
				case COL_ID : 
					return worker.getId();
				case COL_FUNCTION_NAME :
					return worker.getFunctionName();
				case COL_TESTRUN :
					return worker.isTestRun();
				case COL_WORKLOAD :
					return worker.getWorkload();
				case COL_PROCESSED :
					return worker.getProcessedCount();
				case COL_PROGRESS :
					return (int)(100*worker.getProgress());
				case COL_RUN_TIME :
					return (int)(worker.getRunTimeMs()/1000);
				case COL_SUCCESS :
					return worker.getSuccessCount();
				case COL_WARNING :
					return worker.getWarningCount();
				case COL_ERROR :
					return worker.getErrorCount();
				case COL_STATUS :
					return worker.getStatusCode();
				case COL_LOG :
					return null;
				case COL_CONTROL :
					return worker.getStatusCode();
				default :
					return null;
			}
		}
	}
	
	public void setValueAt(Object value,int row,int col) {
		Worker worker=workers.get(row);
		if(col==COL_CONTROL) {
			System.out.println("trying to update...");
			StatusCode statusCode=(StatusCode) value;
			if(statusCode==StatusCode.RUNNING && worker.getStatusCode()==StatusCode.CREATED) {
				worker.startWorking();
			}
			else if(statusCode==StatusCode.PAUSED && worker.getStatusCode()==StatusCode.RUNNING) {
				worker.pauseWorking();
			}
		}
	}

	public List<Worker> getWorkers() {
		return workers;
	}

	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
		for(Worker worker : this.workers) {
			worker.addWorkerListener(this);
		}
		this.fireTableDataChanged();
	}


	@Override
	public void statusChanged(Worker worker) {
		System.out.println("ID: "+worker.getId()+" statusChanged");
		this.fireTableDataChanged();
		
	}


	@Override
	public void progressUpdated(Worker worker) {
		System.out.println("ID: "+worker.getId()+" progressUpdated");
		this.fireTableDataChanged();	
	}
	
	

}
