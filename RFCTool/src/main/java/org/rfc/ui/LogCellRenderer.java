package org.rfc.ui;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class LogCellRenderer implements TableCellRenderer {
	
	private static final ImageIcon LOG_ICON=new ImageIcon(LogCellRenderer.class.getResource("/toolbarButtonGraphics/general/Information16.gif"));
	private JButton button=null;
	
	public LogCellRenderer() {
		super();
		button=new JButton();
		button.setIcon(LOG_ICON);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
			int col) {
		return button;
	}

}
