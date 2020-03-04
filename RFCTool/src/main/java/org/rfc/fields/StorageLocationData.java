package org.rfc.fields;

public enum StorageLocationData implements RFCField {
	
	FUNCTION(new FieldName("FUNCTION","Function")),
	MATERIAL(new FieldName("MATERIAL","MaterialId","MARD-MATNR")),
	PLANT(new FieldName("PLANT","Plant","MARD-WERKS")),
	STGE_LOC(new FieldName("STGE_LOC","StorageLocation","MARD-LGORT")),
	MRP_VIEW(new FieldName("MRP_VIEW","MrpView")),
	STORAGE_VIEW(new FieldName("STORAGE_VIEW","StorageView"));
	
	private final FieldName fieldName;
	
	StorageLocationData(FieldName fieldName) {
		this.fieldName=fieldName;
	}
	
	@Override
	public String getMethodName() {
		return fieldName.getMethodName();
	}

	@Override
	public String getRFCName() {
		return fieldName.getRfcName();
	}

	@Override
	public String getSAPName() {
		return fieldName.getSapName();
	}
}
