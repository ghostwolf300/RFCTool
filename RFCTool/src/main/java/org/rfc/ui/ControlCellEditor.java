package org.rfc.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import org.rfc.dto.Worker.StatusCode;

public class ControlCellEditor extends AbstractCellEditor implements TableCellEditor,ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton button=null;
	private static final ImageIcon[] ICONS= {
			new ImageIcon(RunMonitorPanel.class.getResource("/toolbarButtonGraphics/media/Play16.gif")),
			new ImageIcon(RunMonitorPanel.class.getResource("/toolbarButtonGraphics/media/Pause16.gif"))
	};
	
	private StatusCode statusCode=null;
	
	public ControlCellEditor() {
		super();
		button=new JButton();
		button.setIcon(ICONS[0]);
		button.addActionListener(this);	
	}
	
	@Override
	public Object getCellEditorValue() {
		return statusCode;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		this.statusCode=(StatusCode) value;
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if(statusCode==StatusCode.CREATED) {
			statusCode=StatusCode.RUNNING;
		}
		else if(statusCode==StatusCode.RUNNING) {
			statusCode=StatusCode.PAUSED;
		}
		else if(statusCode==StatusCode.PAUSED) {
			statusCode=StatusCode.RUNNING;
		}
		else if(statusCode==StatusCode.STOPPED) {
			
		}
		else if(statusCode==StatusCode.FINISHED) {
			
		}
		this.fireEditingStopped();
	}

}
