package org.rfc.ui;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.rfc.controller.DefaultController;
import org.rfc.model.MaterialDataModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

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
	private JButton btnStart;
	private DefaultController controller=null;
	private ActionListener cardManager=null;
	private JButton btnBack;
	
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
		setLayout(new MigLayout("", "[grow][grow]", "[][][][][grow][]"));
		add(getLblMaterials(), "cell 0 0,alignx left");
		add(getFldMaterialCount(), "cell 1 0");
		add(getLblTotalRows(), "cell 0 1,alignx left");
		add(getFldTotalRowCount(), "cell 1 1,alignx left");
		add(getLblMaxMaterialsworker(), "cell 0 2,alignx left");
		add(getFldMaxMaterials(), "cell 1 2,alignx left");
		add(getBtnCreateWorkers(), "cell 0 3");
		add(getScrollPane(), "cell 0 4 2 1,grow");
		add(getBtnBack(), "cell 0 5");
		add(getBtnStart(), "cell 1 5");
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
	public JButton getBtnStart() {
		if (btnStart == null) {
			btnStart = new JButton("Start");
			btnStart.addActionListener(cardManager);
		}
		return btnStart;
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
			controller.createWorkers(maxMaterials);
		}
		
	}
	
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
		
	}
}
