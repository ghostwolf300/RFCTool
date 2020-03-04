package org.rfc.dto;

import org.rfc.fields.RFCField;

public interface ExportData {
	
	public String getStringValue(RFCField fld);
	public Integer getIntegerValue(RFCField fld);
	public Double getDoubleValue(RFCField fld);
	public Boolean getBooleanValue(RFCField fld);
	
}
