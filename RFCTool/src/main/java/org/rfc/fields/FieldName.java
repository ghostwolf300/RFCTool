package org.rfc.fields;

public class FieldName {
	private String rfcName;
	private String methodName;
	private String sapName;
	
	public FieldName(String rfcName) {
		super();
		this.rfcName=rfcName;
	}
	
	public FieldName(String rfcName,String methodName) {
		super();
		this.rfcName=rfcName;
		this.methodName=methodName;
	}
	
	public FieldName(String rfcName,String methodName,String sapName) {
		super();
		this.rfcName=rfcName;
		this.methodName=methodName;
		this.sapName=sapName;
	}

	public String getRfcName() {
		return rfcName;
	}

	public void setRfcName(String rfcName) {
		this.rfcName = rfcName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getSapName() {
		return sapName;
	}

	public void setSapName(String sapName) {
		this.sapName = sapName;
	}
}
