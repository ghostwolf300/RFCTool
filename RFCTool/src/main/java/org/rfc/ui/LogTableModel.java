package org.rfc.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.rfc.dto.ReturnMessage;

public class LogTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int COL_WORKER_ID=0;
	public static final int COL_MATERIAL=1;
	public static final int COL_NUMBER=2;
	public static final int COL_TYPE=3;
	public static final int COL_MESSAGE=4;
	public static final int COL_ROW=5;
	public static final int COL_MSGV1=6;
	public static final int COL_MSGV2=7;
	public static final int COL_MSGV3=8;
	public static final int COL_MSGV4=9;
	
	
	private List<ReturnMessage> messages=null;
	
	@Override
	public int getColumnCount() {
		return 10;
	}

	@Override
	public int getRowCount() {
		if(messages==null) {
			return 0;
		}
		else {
			return messages.size();
		}
	}

	@Override
	public Object getValueAt(int row, int col) {
		if(messages==null) {
			return null;
		}
		ReturnMessage message=messages.get(row);
		switch(col) {
			case COL_WORKER_ID :
				return message.getWorkerId();
			case COL_MATERIAL :
				return message.getMaterial();
			case COL_NUMBER :
				return message.getNumber();
			case COL_TYPE :
				return message.getType();
			case COL_MESSAGE :
				return message.getMessage();
			case COL_ROW :
				return message.getRow();
			case COL_MSGV1 :
				return message.getMessageVariable1();
			case COL_MSGV2 :
				return message.getMessageVariable2();
			case COL_MSGV3 :
				return message.getMessageVariable3();
			case COL_MSGV4 :
				return message.getMessageVariable4();
			default :
				return null;
		}
	}

	public List<ReturnMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ReturnMessage> messages) {
		this.messages = messages;
		this.fireTableDataChanged();
	}
	
	public void addMessages(List<ReturnMessage> newMessages) {
		if(messages==null) {
			messages=new ArrayList<ReturnMessage>();
		}
		System.out.println("adding messages..."+newMessages.size());
		int firstRow=(messages.size()==0 ? 0 : messages.size()-1);
		messages.addAll(newMessages);
		this.fireTableRowsInserted(firstRow, (newMessages.size()==0 ? 0 : newMessages.size()-1));
	}

}
