package org.rfc.dto;

public class InputField<T> {
	
	private String rfcName=null;
	private String sapName=null;
	private String propertyName=null;
	private String description=null;
	private boolean mandatory=false;
	private boolean enabled=true;
	private boolean changeIndicatorRequired=false;
	private int mappedColumn=-1;
	private boolean defaulted=false;
	private Object defaultValue=null;
	private Class<T> valueClass=null;
	private Class<?> dataObjectClass=null;
	
	public InputField(String rfcName,Class<T> valueClass) {
		super();
		this.rfcName=rfcName;
		this.valueClass=valueClass;
	}
	
	public InputField(String rfcName, boolean mandatory,Class<T> valueClass) {
		super();
		this.rfcName=rfcName;
		this.mandatory=mandatory;
		this.valueClass=valueClass;
	}
	
	public InputField(String rfcName,String sapName,String propertyName,Class<T> valueClass) {
		super();
		this.rfcName=rfcName;
		this.sapName=sapName;
		this.propertyName=propertyName;
		this.valueClass=valueClass;
	}
	
	public InputField(String rfcName,String sapName,String propertyName,boolean mandatory,Class<T> valueClass) {
		super();
		this.rfcName=rfcName;
		this.sapName=sapName;
		this.propertyName=propertyName;
		this.mandatory=mandatory;
		this.valueClass=valueClass;
	}
	
	public InputField(String rfcName,String sapName,String propertyName,String description,Class<T> valueClass) {
		super();
		this.rfcName=rfcName;
		this.sapName=sapName;
		this.propertyName=propertyName;
		this.description=description;
		this.valueClass=valueClass;
	}
	
	public InputField(String rfcName,String sapName,String propertyName,String description,boolean mandatory,Class<T> valueClass) {
		super();
		this.rfcName=rfcName;
		this.sapName=sapName;
		this.propertyName=propertyName;
		this.description=description;
		this.mandatory=mandatory;
		this.valueClass=valueClass;
	}

	public Class<T> getValueClass() {
		return valueClass;
	}

	public void setValueClass(Class<T> valueClass) {
		this.valueClass = valueClass;
	}

	public String getRfcName() {
		return rfcName;
	}

	public void setRfcName(String rfcName) {
		this.rfcName = rfcName;
	}

	public String getSapName() {
		return sapName;
	}

	public void setSapName(String sapName) {
		this.sapName = sapName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isChangeIndicatorRequired() {
		return changeIndicatorRequired;
	}

	public void setChangeIndicatorRequired(boolean changeIndicatorRequired) {
		this.changeIndicatorRequired = changeIndicatorRequired;
	}

	public int getMappedColumn() {
		return mappedColumn;
	}

	public void setMappedColumn(int mappedColumn) {
		this.mappedColumn = mappedColumn;
	}
	
	
	public boolean isDefaulted() {
		return defaulted;
	}

	public void setDefaulted(boolean defaulted) {
		this.defaulted = defaulted;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Class<?> getDataObjectClass() {
		return dataObjectClass;
	}

	public void setDataObjectClass(Class<?> dataObjectClass) {
		this.dataObjectClass = dataObjectClass;
	}

	public FieldValue<T> createFieldValue(T value) {
		FieldValue<T> fv=new FieldValue<T>(this);
		fv.setValue(value);
		return fv;
	}
	
	public FieldValue<T> createFieldValue() {
		return new FieldValue<T>(this);
	}
	
	@Override
	public String toString() {
		return rfcName;
	}
	
}
