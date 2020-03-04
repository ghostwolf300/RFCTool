package org.rfc.dto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.rfc.fields.RFCField;

public abstract class AbstractDataObject implements ExportData {
	
	public String getStringValue(RFCField fld) {
		return getValue(fld,String.class);
	}
	
	public Integer getIntegerValue(RFCField fld) {
		return getValue(fld,Integer.class);
	}

	public Double getDoubleValue(RFCField fld) {
		return getValue(fld,Double.class);
	}

	public Boolean getBooleanValue(RFCField fld) {
		return getValue(fld,Boolean.class);
	}
	
	
	private <T> T getValue(RFCField fld,Class<T> type) {
		String methodName=null;
		if(type==Boolean.class) {
			methodName="is"+fld.getMethodName();
		}
		else {
			methodName="get"+fld.getMethodName();
		}
		Class<? extends AbstractDataObject> c=(Class<? extends AbstractDataObject>)this.getClass();
		T value=null;
		try {
			Method method=c.getDeclaredMethod(methodName, null);
			value=type.cast(method.invoke(this, null));
		} 
		catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

}
