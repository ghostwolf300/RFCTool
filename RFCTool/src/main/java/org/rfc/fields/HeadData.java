package org.rfc.fields;

public enum HeadData implements RFCField {
	
	FUNCTION("FUNCTION"),
	MATERIAL("MATERIAL");
	
	private final String field;
	
	HeadData(String field) {
		this.field=field;
	}

	public String getField() {
		return field;
	}

	@Override
	public String getMethodName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRFCName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSAPName() {
		// TODO Auto-generated method stub
		return null;
	}
}
