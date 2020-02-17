package org.rfc.ui;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.rfc.controller.DefaultController;
import org.rfc.dto.Worker;
import org.rfc.model.MaterialDataModel;
import org.rfc.model.WorkerModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JCheckBox;

public class RunSettingsPanel extends JPanel implements IView,ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblMaterials;
	private JTextField fldMaterialCount;
	private JLabel lblTotalRows;
	private JTextField fldTotalRowCount;
	private JLabel lblMaxMaterialsworker;
	private JTextField fldMaxMaterials;
	private JButton btnCreateWorkers;
	private JScrollPane scrollPane;
	private JTable tblWorkers;
	private JButton btnNext;
	private DefaultController controller=null;
	private ActionListener cardManager=null;
	private JButton btnBack;
	private JCheckBox chkbxTestRun;
	
	/**
	 * Create the panel.
	 */
	public RunSettingsPanel(DefaultController controller,ActionListener cardManager) {
		this.controller=controller;
		this.controller.addView(this);
		this.cardManager=cardManager;
		initialize();
	}
	private void initialize() {
		setLayout(new MigLayout("", "[grow][grow]", "[][][][][][grow][]"));
		add(getLblMaterials(), "cell 0 0,alignx left");
		add(getFldMaterialCount(), "cell 1 0");
		add(getLblTotalRows(), "cell 0 1,alignx left");
		add(getFldTotalRowCount(), "cell 1 1,alignx left");
		add(getLblMaxMaterialsworker(), "cell 0 2,alignx left");
		add(getFldMaxMaterials(), "cell 1 2,alignx left");
		add(getChkbxTestRun(), "cell 1 3");
		add(getBtnCreateWorkers(), "cell 0 4");
		add(getScrollPane(), "cell 0 5 2 1,grow");
		add(getBtnBack(), "cell 0 6");
		add(getBtnNext(), "cell 1 6");
	}

	private JLabel getLblMaterials() {
		if (lblMaterials == null) {
			lblMaterials = new JLabel("Materials:");
		}
		return lblMaterials;
	}
	private JTextField getFldMaterialCount() {
		if (fldMaterialCount == null) {
			fldMaterialCount = new JTextField();
			fldMaterialCount.setEditable(false);
			fldMaterialCount.setColumns(10);
		}
		return fldMaterialCount;
	}
	private JLabel getLblTotalRows() {
		if (lblTotalRows == null) {
			lblTotalRows = new JLabel("Total rows:");
		}
		return lblTotalRows;
	}
	private JTextField getFldTotalRowCount() {
		if (fldTotalRowCount == null) {
			fldTotalRowCount = new JTextField();
			fldTotalRowCount.setEditable(false);
			fldTotalRowCount.setColumns(10);
		}
		return fldTotalRowCount;
	}
	private JLabel getLblMaxMaterialsworker() {
		if (lblMaxMaterialsworker == null) {
			lblMaxMaterialsworker = new JLabel("Max materials/worker:");
		}
		return lblMaxMaterialsworker;
	}
	private JTextField getFldMaxMaterials() {
		if (fldMaxMaterials == null) {
			fldMaxMaterials = new JTextField();
			fldMaxMaterials.setText("");
			fldMaxMaterials.setColumns(10);
		}
		return fldMaxMaterials;
	}
	private JButton getBtnCreateWorkers() {
		if (btnCreateWorkers == null) {
			btnCreateWorkers = new JButton("Create workers");
			btnCreateWorkers.addActionListener(this);
		}
		return btnCreateWorkers;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTblWorkers());
		}
		return scrollPane;
	}
	private JTable getTblWorkers() {
		if (tblWorkers == null) {
			tblWorkers = new JTable();
		}
		return tblWorkers;
	}
	public JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton("Next");
			btnNext.addActionListener(cardManager);
		}
		return btnNext;
	}
	
	public JButton getBtnBack() {
		if (btnBack == null) {
			btnBack = new JButton("Back");
			btnBack.addActionListener(cardManager);
		}
		return btnBack;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(btnCreateWorkers)) {
			int maxMaterials=Integer.valueOf(this.fldMaxMaterials.getText());
			boolean testRun=chkbxTestRun.isSelected();
			controller.createWorkers(maxMaterials,testRun);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void modelPropertyChange(PropertyChangeEvent pce) {
		if(pce.getPropertyName().equals(MaterialDataModel.P_MATERIAL_COUNT)) {
			int materialCount=(int)pce.getNewValue();
			this.fldMaterialCount.setText(String.valueOf(materialCount));
		}
		else if(pce.getPropertyName().equals(MaterialDataModel.P_PLANT_DATA_COUNT)) {
			int plantDataCount=(int)pce.getNewValue();
			this.fldTotalRowCount.setText(String.valueOf(plantDataCount));
		}
		else if(pce.getPropertyName().equals(WorkerModel.P_WORKERS)) {
			List<Worker> workers=(List<Worker>) pce.getNewValue();
			//for(Worker w : workers) {
			//	System.out.println("ID: "+w.getId()+"\tFUNCTION: "+w.getFunctionName()+"\tWORKLOAD: "+w.getWorkload()+"\tSTATUS: "+w.getStatusCode());
			//}
		}
		
	}
	private JCheckBox getChkbxTestRun() {
		if (chkbxTestRun == null) {
			chkbxTestRun = new JCheckBox("Test run");
			chkbxTestRun.setSelected(true);
		}
		return chkbxTestRun;
	}
}
