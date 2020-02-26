package org.rfc.dto;

public class FieldValue<T> {
	
	public static final String IGNORE_INDICATOR="\\";
	
	protected InputField<T> inputField=null;
	protected boolean ignored=false;
	protected T value;
	
	public FieldValue() {
		super();
	}
	
	public FieldValue(InputField<T> inputField) {
		super();
		this.inputField=inputField;
	}
	
	public FieldValue(InputField<T> inputField, boolean ignored) {
		super();
		this.inputField=inputField;
		this.ignored=ignored;
	}

	public boolean isIgnored() {
		return ignored;
	}

	public void setIgnored(boolean ignored) {
		this.ignored = ignored;
	}

	public InputField<T> getInputField() {
		return inputField;
	}

	public void setInputField(InputField<T> inputField) {
		this.inputField = inputField;
	}
	
	public static boolean isIgnoreIndicator(String value) {
		if(value.equals(IGNORE_INDICATOR)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setValue(T value) {
		this.value=value;
	}
	
	public void setValue2(String value) {
		this.value=(T)value;
	}
	
	public T getValue() {
		return this.value;
	}
	
	
	
}
