package org.rfc.model;

import java.util.List;

import org.rfc.dto.RFCFunction;

public class RFCFunctionModel extends AbstractModel {
	
	public static final String P_FUNCTIONS="p_functions";
	
	private List<RFCFunction> functions=null;
	
	public RFCFunctionModel() {
		super();
	}

	public List<RFCFunction> getFunctions() {
		return functions;
	}

	public void setFunctions(List<RFCFunction> functions) {
		this.functions = functions;
		this.firePropertyChange(P_FUNCTIONS, null, functions);
	}
	
}
