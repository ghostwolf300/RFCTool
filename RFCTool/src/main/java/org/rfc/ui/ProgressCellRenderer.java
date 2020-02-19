package org.rfc.ui;

import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ProgressCellRenderer implements TableCellRenderer {

	/**
	 * 
	 */
		
	private JProgressBar bar=null;
	
	public ProgressCellRenderer() {
		bar=new JProgressBar(0,100);
		bar.setValue(0);
		bar.setStringPainted(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
			int col) {
		int progress=(int)(value);
		bar.setValue(progress);
		return bar;
	}

}
