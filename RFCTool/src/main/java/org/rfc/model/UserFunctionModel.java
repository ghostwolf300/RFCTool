package org.rfc.model;

import java.util.List;

import org.rfc.dto.UserFunction;

public class UserFunctionModel extends AbstractModel {
	
	public static final String P_FUNCTIONS="p_functions";
	public static final String P_SELECTED_FUNCTION="p_selected_function";
	
	private List<UserFunction> functions=null;
	private UserFunction selectedFunction=null;
	
	public UserFunctionModel() {
		super();
	}

	public List<UserFunction> getFunctions() {
		return functions;
	}

	public void setFunctions(List<UserFunction> functions) {
		this.functions = functions;
		this.firePropertyChange(P_FUNCTIONS, null, functions);
	}

	public UserFunction getSelectedFunction() {
		return selectedFunction;
	}

	public void setSelectedFunction(UserFunction selectedFunction) {
		this.selectedFunction = selectedFunction;
		this.firePropertyChange(P_SELECTED_FUNCTION, null, this.selectedFunction);
	}
	
}
