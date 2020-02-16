package org.rfc.ui;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.beans.PropertyChangeEvent;

import javax.swing.JButton;

public class DataSelectionPanel extends JPanel implements IView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblNewLabel;
	private JTextField textField;
	private JButton btnChooseFile;
	private JButton btnOpenFile;

	/**
	 * Create the panel.
	 */
	public DataSelectionPanel() {

		initialize();
	}
	private void initialize() {
		setLayout(new MigLayout("", "[][grow]", "[]"));
		add(getLblNewLabel(), "cell 0 0,alignx trailing");
		add(getTextField(), "flowx,cell 1 0,growx");
		add(getBtnChooseFile(), "cell 1 0");
		add(getBtnOpenFile(), "cell 1 0");
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("File path:");
		}
		return lblNewLabel;
	}
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setColumns(10);
		}
		return textField;
	}
	private JButton getBtnChooseFile() {
		if (btnChooseFile == null) {
			btnChooseFile = new JButton("Choose");
		}
		return btnChooseFile;
	}
	private JButton getBtnOpenFile() {
		if (btnOpenFile == null) {
			btnOpenFile = new JButton("Open");
		}
		return btnOpenFile;
	}
	public void modelPropertyChange(PropertyChangeEvent pce) {
		// TODO Auto-generated method stub
		
	}
}
