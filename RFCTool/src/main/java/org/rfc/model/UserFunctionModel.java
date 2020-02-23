package org.rfc.model;

import java.util.List;

import org.rfc.dto.UserFunction;
import org.rfc.function.RunnableFunction;

public class UserFunctionModel extends AbstractModel {
	
	public static enum Property{
		FUNCTIONS,
		SELECTED_FUNCTION
	}
	
	private List<UserFunction<? extends RunnableFunction>> functions=null;
	private UserFunction<? extends RunnableFunction> selectedFunction=null;
	
	public UserFunctionModel() {
		super();
	}

	public List<UserFunction<? extends RunnableFunction>> getFunctions() {
		return functions;
	}

	public void setFunctions(List<UserFunction<? extends RunnableFunction>> functions) {
		this.functions = functions;
		this.firePropertyChange(Property.FUNCTIONS.toString(), null, functions);
	}

	public UserFunction<? extends RunnableFunction> getSelectedFunction() {
		return selectedFunction;
	}

	public void setSelectedFunction(UserFunction<? extends RunnableFunction> selectedFunction) {
		this.selectedFunction = selectedFunction;
		this.firePropertyChange(Property.SELECTED_FUNCTION.toString(), null, this.selectedFunction);
	}
	
}
