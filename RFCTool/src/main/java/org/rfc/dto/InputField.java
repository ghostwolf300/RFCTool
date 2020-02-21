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
	
	public InputField(String rfcName) {
		super();
		this.rfcName=rfcName;
	}
	
	public InputField(String rfcName, boolean mandatory) {
		super();
		this.rfcName=rfcName;
		this.mandatory=mandatory;
	}
	
	public InputField(String rfcName,String sapName,String propertyName) {
		super();
		this.rfcName=rfcName;
		this.sapName=sapName;
		this.propertyName=propertyName;
	}
	
	public InputField(String rfcName,String sapName,String propertyName,boolean mandatory) {
		super();
		this.rfcName=rfcName;
		this.sapName=sapName;
		this.propertyName=propertyName;
		this.mandatory=mandatory;
	}
	
	public InputField(String rfcName,String sapName,String propertyName,String description) {
		super();
		this.rfcName=rfcName;
		this.sapName=sapName;
		this.propertyName=propertyName;
		this.description=description;
	}
	
	public InputField(String rfcName,String sapName,String propertyName,String description,boolean mandatory) {
		super();
		this.rfcName=rfcName;
		this.sapName=sapName;
		this.propertyName=propertyName;
		this.description=description;
		this.mandatory=mandatory;
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
	
	
	public FieldValue<T> createFieldValue() {
		return new FieldValue<T>(this);
	}
	
	@Override
	public String toString() {
		return rfcName;
	}
	
}
