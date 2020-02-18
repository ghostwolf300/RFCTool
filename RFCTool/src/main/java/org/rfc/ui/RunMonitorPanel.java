package org.rfc.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.rfc.controller.DefaultController;
import org.rfc.dto.Worker;
import org.rfc.model.WorkerModel;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class RunMonitorPanel extends JPanel implements IView,ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPaneRuns;
	private JTable tblWorkers;
	private JButton btnStartAll;
	private JButton btnPauseAll;
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
		add(getBtnPauseAll(), "cell 0 0");
		add(getBtnStopAll(), "cell 0 0");
		add(getScrollPaneLog(), "cell 0 2,grow");
	}

	private JScrollPane getScrollPaneRuns() {
		if (scrollPaneRuns == null) {
			scrollPaneRuns = new JScrollPane();
			scrollPaneRuns.setViewportView(getTblWorkers());
		}
		return scrollPaneRuns;
	}
	private JTable getTblWorkers() {
		if (tblWorkers == null) {
			tblWorkers = new WorkerTable();
		}
		return tblWorkers;
	}
	
	private JButton getBtnStartAll() {
		if (btnStartAll == null) {
			btnStartAll = new JButton();
			btnStartAll.setIcon(new ImageIcon(this.getClass().getResource("/toolbarButtonGraphics/media/Play16.gif")));
			btnStartAll.addActionListener(this);
			btnStartAll.setEnabled(true);
		}
		return btnStartAll;
	}
	private JButton getBtnPauseAll() {
		if (btnPauseAll == null) {
			btnPauseAll = new JButton("");
			btnPauseAll.setIcon(new ImageIcon(RunMonitorPanel.class.getResource("/toolbarButtonGraphics/media/Pause16.gif")));
			btnPauseAll.addActionListener(this);
			btnPauseAll.setEnabled(false);
		}
		return btnPauseAll;
	}
	private JButton getBtnStopAll() {
		if (btnStopAll == null) {
			btnStopAll = new JButton();
			btnStopAll.setIcon(new ImageIcon(this.getClass().getResource("/toolbarButtonGraphics/media/Stop16.gif")));
			btnStopAll.addActionListener(this);
			btnStopAll.setEnabled(false);
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
			WorkerTableModel model=(WorkerTableModel)tblWorkers.getModel();
			model.setWorkers(workers);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnStartAll)) {
			controller.startOrPauseAll();
			btnStartAll.setEnabled(false);
			btnPauseAll.setEnabled(true);
			btnStopAll.setEnabled(true);
		}
		else if(e.getSource().equals(btnPauseAll)) {
			controller.startOrPauseAll();
			btnStartAll.setEnabled(true);
			btnPauseAll.setEnabled(false);
			btnStopAll.setEnabled(true);
		}
		else if(e.getSource().equals(btnStopAll)){
			controller.stopAll();
			btnStartAll.setEnabled(true);
			btnPauseAll.setEnabled(false);
			btnStopAll.setEnabled(false);
		}
		
	}
	
	
}
