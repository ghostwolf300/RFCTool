package org.rfc.ui;

import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class PreviewDataHeaderRenderer implements TableCellRenderer {
	
	private JList<String> list=null;
	
	public PreviewDataHeaderRenderer() {
		super();
		initialize();
	}
	
	private void initialize() {
		list=getList();
	}
	
	private JList<String> getList() {
		if(list==null) {
			DefaultListModel<String> model=new DefaultListModel<String>();
			model.add(0, "Mapped Field");
			model.add(1, "Src index");
			list=new JList<String>(model);
			list.setCellRenderer(new HeaderListCellRenderer());

		}
		return list;
	}
	
	
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
			int col) {
		DefaultListModel<String> model=(DefaultListModel<String>) list.getModel();
		model.set(1, String.valueOf(col));
		return list;
	}

}
