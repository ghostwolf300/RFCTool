package org.rfc.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.rfc.dto.Worker.StatusCode;

public class StatusCellRenderer implements TableCellRenderer {
	
	private JLabel label=null;
	
	public StatusCellRenderer() {
		label=new JLabel();
		label.setOpaque(true);
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		StatusCode statusCode=(StatusCode) value;
		label.setText(statusCode.toString());
		label.setBackground(getBackground(statusCode));
		label.setForeground(getForeground(statusCode));
		return label;
	}
	
	private Color getBackground(StatusCode statusCode) {
		Color background=null;
		if(statusCode==StatusCode.CREATED) {
			background=Color.WHITE;
		}
		else if(statusCode==StatusCode.RUNNING) {
			background=Color.BLUE;
		}
		else if(statusCode==StatusCode.PAUSED) {
			background=Color.YELLOW;
		}
		else if(statusCode==StatusCode.FINISHED) {
			background=Color.GREEN;
		}
		else if(statusCode==StatusCode.STOPPED) {
			background=Color.RED;
		}
		else {
			background=Color.WHITE;
		}
		return background;
	}
	
	private Color getForeground(StatusCode statusCode) {
		Color foreground=null;
		if(statusCode==StatusCode.CREATED) {
			foreground=Color.BLACK;
		}
		else if(statusCode==StatusCode.RUNNING) {
			foreground=Color.WHITE;
		}
		else if(statusCode==StatusCode.PAUSED) {
			foreground=Color.BLACK;
		}
		else if(statusCode==StatusCode.FINISHED) {
			foreground=Color.BLACK;
		}
		else if(statusCode==StatusCode.STOPPED) {
			foreground=Color.WHITE;
		}
		else {
			foreground=Color.BLACK;
		}
		return foreground;
	}

}
