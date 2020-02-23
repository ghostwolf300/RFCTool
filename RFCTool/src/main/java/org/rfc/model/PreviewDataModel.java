package org.rfc.model;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.rfc.dto.InputField;

public class PreviewDataModel extends AbstractModel {
	
	public static enum Property{
		PREVIEW_DATA,
		FIELD_MAP
	};
	
	private Map<String,InputField<?>> fieldMap=null;
	private List<Row> previewDataList=null;
	
	public PreviewDataModel() {
		super();
	}

	public List<Row> getPreviewDataList() {
		return previewDataList;
	}

	public void setPreviewDataList(List<Row> previewDataList) {
		this.previewDataList = previewDataList;
		this.firePropertyChange(Property.PREVIEW_DATA.toString(), null, this.previewDataList);
	}

	public Map<String, InputField<?>> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(Map<String, InputField<?>> fieldMap) {
		this.fieldMap = fieldMap;
		this.firePropertyChange(Property.FIELD_MAP.toString(), null,this.fieldMap);
	}
	
}
