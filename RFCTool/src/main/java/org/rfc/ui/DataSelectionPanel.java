package org.rfc.ui;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

import org.apache.poi.ss.usermodel.Row;
import org.rfc.controller.DefaultController;
import org.rfc.dto.InputField;
import org.rfc.dto.Material3;
import org.rfc.dto.PlantData3;
import org.rfc.dto.PlantData;
import org.rfc.dto.UserFunction;
import org.rfc.model.MaterialDataModel;
import org.rfc.model.PreviewDataModel;
import org.rfc.model.UserFunctionModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class DataSelectionPanel extends JPanel implements IView,ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblNewLabel;
	private JTextField fldFilePath;
	private JButton btnChooseFile;
	private JButton btnOpenFile;
	private JScrollPane scrollPane;
	private PreviewDataTable tblPreviewData;
	private JButton btnNext;
	private DefaultController controller=null;
	private File dataFile=null;
	private ActionListener cardManager=null;
	private JTextField fldSelectedUserFunction;
	private JLabel lblSelectedUserFunction;

	/**
	 * Create the panel.
	 */
	public DataSelectionPanel(DefaultController controller,ActionListener cardManager) {
		this.controller=controller;
		this.controller.addView(this);
		this.cardManager=cardManager;
		initialize();
	}
	private void initialize() {
		setLayout(new MigLayout("", "[grow][grow]", "[][][grow][]"));
		add(getLblSelectedUserFunction(), "cell 0 0,alignx left");
		add(getFldSelectedUserFunction(), "cell 1 0,growx");
		add(getLblNewLabel(), "cell 0 1,alignx left");
		add(getFldFilePath(), "flowx,cell 1 1,growx");
		add(getBtnChooseFile(), "cell 1 1");
		add(getBtnOpenFile(), "cell 1 1");
		add(getScrollPane(), "cell 0 2 2 1,grow");
		add(getBtnNext(), "cell 0 3");
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("File path:");
		}
		return lblNewLabel;
	}
	private JTextField getFldFilePath() {
		if (fldFilePath == null) {
			fldFilePath = new JTextField();
			fldFilePath.setEditable(false);
			fldFilePath.setColumns(10);
		}
		return fldFilePath;
	}
	private JButton getBtnChooseFile() {
		if (btnChooseFile == null) {
			btnChooseFile = new JButton("Choose");
			btnChooseFile.addActionListener(this);
		}
		return btnChooseFile;
	}
	private JButton getBtnOpenFile() {
		if (btnOpenFile == null) {
			btnOpenFile = new JButton("Open");
			btnOpenFile.addActionListener(this);
		}
		return btnOpenFile;
	}
	private JScrollPane getScrollPane() {
		if (scrollPane == null) {
			scrollPane = new JScrollPane();
			scrollPane.setViewportView(getTblPreviewData());
		}
		return scrollPane;
	}
	private PreviewDataTable getTblPreviewData() {
		if (tblPreviewData == null) {
			tblPreviewData = new PreviewDataTable();
		}
		return tblPreviewData;
	}
	public JButton getBtnNext() {
		if (btnNext == null) {
			btnNext = new JButton("Next");
			btnNext.setEnabled(true);
			btnNext.addActionListener(this);
			btnNext.addActionListener(cardManager);
		}
		return btnNext;
	}
	private JTextField getFldSelectedUserFunction() {
		if (fldSelectedUserFunction == null) {
			fldSelectedUserFunction = new JTextField();
			fldSelectedUserFunction.setEditable(false);
			fldSelectedUserFunction.setColumns(10);
			
		}
		return fldSelectedUserFunction;
	}
	private JLabel getLblSelectedUserFunction() {
		if (lblSelectedUserFunction == null) {
			lblSelectedUserFunction = new JLabel("Selected function:");
		}
		return lblSelectedUserFunction;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.getBtnChooseFile())) {
			dataFile=chooseFile();
			if(dataFile!=null) {
				fldFilePath.setText(dataFile.getAbsolutePath());
			}
		}
		else if(e.getSource().equals(this.getBtnOpenFile())) {
			controller.loadPreviewDataFile(dataFile);
		}
		else if(e.getSource().equals(this.getBtnNext())) {
			controller.loadInputDataFile(dataFile);
		}
		
	}
	
	private File chooseFile() {
		File file=null;
		JFileChooser jfc=new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		int returnValue=jfc.showOpenDialog(null);
		if(returnValue==JFileChooser.APPROVE_OPTION) {
			file=jfc.getSelectedFile();
		}
		return file;
		
	}
	
	@SuppressWarnings("unchecked")
	public void modelPropertyChange(PropertyChangeEvent pce) {
		if(pce.getPropertyName().equals(UserFunctionModel.Property.SELECTED_FUNCTION.toString())) {
			System.out.println("Function selected!");
			UserFunction<?> uf=(UserFunction<?>) pce.getNewValue();
			fldSelectedUserFunction.setText(uf.getName());
		}
		else if(pce.getPropertyName().equals(PreviewDataModel.Property.PREVIEW_DATA.toString())) {
			List<Row> previewDataList=(List<Row>) pce.getNewValue();
			tblPreviewData.setPreviewDataList(previewDataList);
			btnNext.setEnabled(true);
		}
		else if(pce.getPropertyName().equals(PreviewDataModel.Property.FIELD_MAP.toString())) {
			Map<String,InputField<?>> fieldMap=(Map<String, InputField<?>>) pce.getNewValue();
			tblPreviewData.setFieldMap(fieldMap);
		}
		
	}
	
	
}
