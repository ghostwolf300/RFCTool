package org.rfc.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InputFieldManager<T> {
	
	private Class<T> dataObjectClass=null;
	private Map<String,InputField<?>> inputFields=null;
	
	public InputFieldManager(Class<T> dataObjectClass) {
		super();
		this.dataObjectClass=dataObjectClass;
		inputFields=new HashMap<String,InputField<?>>();
	}

	public Map<String, InputField<?>> getInputFields() {
		return inputFields;
	}

	public void setInputFields(Map<String, InputField<?>> inputFields) {
		this.inputFields = inputFields;
	}

	public Class<T> getDataObjectClass() {
		return dataObjectClass;
	}

	public void setDataObjectClass(Class<T> dataObjectClass) {
		this.dataObjectClass = dataObjectClass;
	}
	
	public Map<String,InputField<?>> getMappedFields(){
		Map<String,InputField<?>> mappedFields=null;
		Set<String> fieldNames=inputFields.keySet();
		for(String fieldName : fieldNames) {
			InputField<?> f=inputFields.get(fieldName);
			if(f.isDefaulted()==false) {
				if(mappedFields==null) {
					mappedFields=new HashMap<String,InputField<?>>();
				}
				mappedFields.put(fieldName, f);
			}
		}
		return mappedFields;
	}
	
	public Map<String,InputField<?>> getDefaultedFields(){
		Map<String,InputField<?>> defaultedFields=null;
		Set<String> fieldNames=inputFields.keySet();
		for(String fieldName : fieldNames) {
			InputField<?> f=inputFields.get(fieldName);
			if(f.isDefaulted()) {
				if(defaultedFields==null) {
					defaultedFields=new HashMap<String,InputField<?>>();
				}
				defaultedFields.put(fieldName, f);
			}
		}
		return defaultedFields;
	}
	
	public void addMappedField(String rfcName,String propertyName,Class<?> valueClass,int mappedColumn) {
		InputField<?> f=null;
		if(valueClass.equals(String.class)) {
			f=new InputField<String>(rfcName,null,propertyName,String.class);
		}
		else if(valueClass.equals(Integer.class)) {
			f=new InputField<Integer>(rfcName,null,propertyName,Integer.class);
		}
		else if(valueClass.equals(Double.class)) {
			f=new InputField<Double>(rfcName,null,propertyName,Double.class);
		}
		else if(valueClass.equals(Boolean.class)) {
			f=new InputField<Boolean>(rfcName,null,propertyName,Boolean.class);
		}
		f.setMappedColumn(mappedColumn);
		f.setDefaulted(false);
		inputFields.put(f.getRfcName(), f);
	}
	
	public void addDefaultedField(String rfcName,String propertyName,Class<?> valueClass,Object defaultValue) {
		InputField<?> f=null;
		if(valueClass.equals(String.class)) {
			f=new InputField<String>(rfcName,null,propertyName,String.class);
			
		}
		else if(valueClass.equals(Integer.class)) {
			f=new InputField<Integer>(rfcName,null,propertyName,Integer.class);
		}
		else if(valueClass.equals(Double.class)) {
			f=new InputField<Double>(rfcName,null,propertyName,Double.class);
		}
		else if(valueClass.equals(Boolean.class)) {
			f=new InputField<Boolean>(rfcName,null,propertyName,Boolean.class);
		}
		f.setDefaultValue(defaultValue);
		f.setDefaulted(true);
		inputFields.put(f.getRfcName(), f);
	}
	
	
	
}
