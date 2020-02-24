package org.rfc.ui;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

public class HeaderListCellRenderer implements ListCellRenderer<String> { 
	
	private JLabel lblText=null;
	
	public HeaderListCellRenderer() {
		super();
		initialize();
	}
	
	private void initialize() {
		lblText=new JLabel("Text");
		lblText.setHorizontalAlignment(SwingConstants.CENTER);
		lblText.setVerticalAlignment(SwingConstants.CENTER);
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
			boolean isSelected, boolean cellHasFocus) {
		switch(index) {
		case 0 :
			lblText.setText(value);
			break;
		case 1 :
			lblText.setText(value);
			break;
		}
		return lblText;
	}

}
