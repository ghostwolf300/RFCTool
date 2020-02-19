package org.rfc.ui;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.rfc.dto.Worker.StatusCode;

public class ControlCellRenderer implements TableCellRenderer {

	/**
	 * 
	 */
	
	private JButton button=null;
	private static final ImageIcon[] ICONS= {
			new ImageIcon(RunMonitorPanel.class.getResource("/toolbarButtonGraphics/media/Play16.gif")),
			new ImageIcon(RunMonitorPanel.class.getResource("/toolbarButtonGraphics/media/Pause16.gif"))
	};
	
	public ControlCellRenderer() {
		super();
		button=new JButton();
		button.setIcon(ICONS[0]);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		StatusCode statusCode=(StatusCode) value;
		if(statusCode==StatusCode.CREATED) {
			button.setIcon(ICONS[0]);
		}
		else if(statusCode==StatusCode.RUNNING) {
			button.setIcon(ICONS[1]);
		}
		else if(statusCode==StatusCode.PAUSED) {
			button.setIcon(ICONS[0]);
		}
		else if(statusCode==StatusCode.STOPPED) {
			button.setIcon(ICONS[0]);
		}
		else if(statusCode==StatusCode.FINISHED) {
			button.setIcon(ICONS[0]);
		}
		return button;
	}

}
