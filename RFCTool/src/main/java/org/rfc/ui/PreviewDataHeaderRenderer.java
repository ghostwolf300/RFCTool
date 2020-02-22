package org.rfc.ui;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class PreviewDataHeaderRenderer implements TableCellRenderer {
	
	private JList<JComponent> list=null;
	private JLabel lblHeaderText=null;
	private JLabel lblSourceField=null;
	
	public PreviewDataHeaderRenderer() {
		super();
		initialize();
	}
	
	private void initialize() {
		list=getList();
	}
	
	private JList<JComponent> getList() {
		if(list==null) {
			list=new JList<JComponent>(new JLabel[] {
					getLblHeaderText(),
					getLblSourceField()
			});
		}
		return list;
	}
	
	private JLabel getLblSourceField() {
		if(lblSourceField==null) {
			lblSourceField=new JLabel("Field");
			lblSourceField.setOpaque(true);
		}
		return lblSourceField;
	}
	
	private JLabel getLblHeaderText() {
		if(lblHeaderText==null) {
			lblHeaderText=new JLabel("Mapped To:");
			lblHeaderText.setOpaque(true);
		}
		return lblHeaderText;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
			int col) {
		lblSourceField.setText(String.valueOf(col));
		return list;
	}

}
