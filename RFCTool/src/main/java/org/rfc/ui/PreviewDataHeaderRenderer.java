package org.rfc.ui;

import java.awt.Color;
import java.awt.Component;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.rfc.dto.InputField;

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
			list.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
			list.setBackground(Color.LIGHT_GRAY);

		}
		return list;
	}
	
	
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
			int col) {
		DefaultListModel<String> model=(DefaultListModel<String>) list.getModel();
		Map<String,InputField<?>> fieldMap=((PreviewDataTable)table).getFieldMap();
		InputField<?> mappedField=findField(fieldMap,col);
		if(mappedField!=null) {
			model.set(0, mappedField.getRfcName());
			list.setBackground(Color.YELLOW);
		}
		else {
			model.set(0, " ");
			list.setBackground(Color.LIGHT_GRAY);
		}
		model.set(1, String.valueOf(col));
		return list;
	}
	
	private InputField<?> findField(Map<String,InputField<?>> fieldMap,int col){
		Set<String> keys=fieldMap.keySet();
		for(String key : keys) {
			InputField<?> field=fieldMap.get(key);
			if(field.getMappedColumn()==col) {
				return field;
			}
		}
		return null;
	}

}
