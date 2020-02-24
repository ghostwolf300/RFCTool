package org.rfc.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class LogCellEditor extends AbstractCellEditor implements TableCellEditor,ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final ImageIcon LOG_ICON=new ImageIcon(LogCellRenderer.class.getResource("/toolbarButtonGraphics/general/Information16.gif"));
	private JButton button=null;
	
	private boolean showLog=false;
	
	public LogCellEditor() {
		super();
		button=new JButton();
		button.setIcon(LOG_ICON);
		button.addActionListener(this);
	}
	
	@Override
	public Object getCellEditorValue() {
		return showLog;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
		// TODO Auto-generated method stub
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(showLog) {
			showLog=false;
		}
		else {
			showLog=true;
		}
		this.fireEditingStopped();
		
	}

}
