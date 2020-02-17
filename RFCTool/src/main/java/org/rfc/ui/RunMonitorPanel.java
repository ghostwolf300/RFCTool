package org.rfc.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.rfc.controller.DefaultController;
import org.rfc.dto.Worker;
import org.rfc.model.WorkerModel;

import javax.swing.JButton;

public class RunMonitorPanel extends JPanel implements IView,ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPaneRuns;
	private JTable tblRuns;
	private JButton btnStartAll;
	private JButton btnStopAll;
	private JScrollPane scrollPaneLog;
	private JTable tblLog;
	private DefaultController controller=null;
	private ActionListener cardManager=null;

	/**
	 * Create the panel.
	 */
	public RunMonitorPanel(DefaultController controller,ActionListener cardManager) {
		this.controller=controller;
		this.controller.addView(this);
		this.cardManager=cardManager;
		initialize();
	}
	private void initialize() {
		setLayout(new MigLayout("", "[grow]", "[][grow][grow][]"));
		add(getBtnStartAll(), "flowx,cell 0 0");
		add(getScrollPaneRuns(), "cell 0 1,grow");
		add(getBtnStopAll(), "cell 0 0");
		add(getScrollPaneLog(), "cell 0 2,grow");
	}

	private JScrollPane getScrollPaneRuns() {
		if (scrollPaneRuns == null) {
			scrollPaneRuns = new JScrollPane();
			scrollPaneRuns.setViewportView(getTblRuns());
		}
		return scrollPaneRuns;
	}
	private JTable getTblRuns() {
		if (tblRuns == null) {
			tblRuns = new JTable();
		}
		return tblRuns;
	}
	private JButton getBtnStartAll() {
		if (btnStartAll == null) {
			btnStartAll = new JButton("Start all");
			btnStartAll.addActionListener(this);
		}
		return btnStartAll;
	}
	private JButton getBtnStopAll() {
		if (btnStopAll == null) {
			btnStopAll = new JButton("Stop all");
			btnStopAll.addActionListener(this);
		}
		return btnStopAll;
	}
	private JScrollPane getScrollPaneLog() {
		if (scrollPaneLog == null) {
			scrollPaneLog = new JScrollPane();
			scrollPaneLog.setViewportView(getTblLog());
		}
		return scrollPaneLog;
	}
	private JTable getTblLog() {
		if (tblLog == null) {
			tblLog = new JTable();
		}
		return tblLog;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void modelPropertyChange(PropertyChangeEvent pce) {
		if(pce.getPropertyName().equals(WorkerModel.P_WORKERS)) {
			List<Worker> workers=(List<Worker>) pce.getNewValue();
			for(Worker w : workers) {
				System.out.println("ID: "+w.getId()+"\tFUNCTION: "+w.getFunctionName()+"\tWORKLOAD: "+w.getWorkload()+"\tSTATUS: "+w.getStatusCode());
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnStartAll)) {
			controller.startAll();
		}
		else if(e.getSource().equals(btnStopAll)){
			controller.stopAll();
		}
		
	}
	
}
