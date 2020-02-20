package org.rfc.ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import org.rfc.dto.UserFunction;

public class FunctionListModel extends AbstractListModel<UserFunction> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UserFunction> functions=null;
	
	public FunctionListModel() {
		super();
		functions=new ArrayList<UserFunction>();
	}
	
	public FunctionListModel(List<UserFunction> functions) {
		super();
		this.functions=functions;
	}
	
	public List<UserFunction> getFunctions() {
		return functions;
	}

	public void setFunctions(List<UserFunction> functions) {
		this.functions = functions;
		this.fireIntervalAdded(this, 0, this.functions.size()-1);
	}

	@Override
	public UserFunction getElementAt(int index) {
		return functions.get(index);
	}

	@Override
	public int getSize() {
		return functions.size();
	}

}
