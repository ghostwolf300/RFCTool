package org.rfc.ui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class HeaderListCellRenderer implements ListCellRenderer<String> { 
	
	private JLabel lblMappedField=null;
	private JLabel lblSourceFieldNumber=null;
	private JLabel lblText=null;
	
	public HeaderListCellRenderer() {
		super();
		initialize();
	}
	
	private void initialize() {
		lblMappedField=new JLabel("Field");
		lblSourceFieldNumber=new JLabel("Source");
		lblText=new JLabel("Text");
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
			boolean isSelected, boolean cellHasFocus) {
		switch(index) {
		case 0 :
			lblText.setText("Mapped Field");
			break;
		case 1 :
			lblText.setText(value);
			break;
		}
		return lblText;
	}

}
