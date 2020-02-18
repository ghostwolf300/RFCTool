package org.rfc.ui;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.rfc.dto.Worker.StatusCode;

public class ControlCellRenderer extends JButton implements TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private StatusCode statusCode=null;
	private static final ImageIcon[] ICONS= {
			new ImageIcon(RunMonitorPanel.class.getResource("/toolbarButtonGraphics/media/Play16.gif")),
			new ImageIcon(RunMonitorPanel.class.getResource("/toolbarButtonGraphics/media/Pause16.gif"))
	};
	
	public ControlCellRenderer() {
		super();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		this.statusCode=(StatusCode) value;
		if(statusCode==StatusCode.CREATED) {
			this.setIcon(ICONS[0]);
		}
		else if(statusCode==StatusCode.RUNNING) {
			this.setIcon(ICONS[1]);
		}
		else if(statusCode==StatusCode.PAUSED) {
			this.setIcon(ICONS[0]);
		}
		else if(statusCode==StatusCode.STOPPED) {
			this.setIcon(ICONS[0]);
		}
		else if(statusCode==StatusCode.FINISHED) {
			this.setIcon(ICONS[0]);
			this.setEnabled(false);
		}
		return this;
	}

}
