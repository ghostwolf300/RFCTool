package org.rfc.dto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class DataObject {
	
	public FieldValue<?> getFieldValue(String propertyName){
		FieldValue<?> fieldValue=null;
		String methodName="get"+propertyName;
		Class<?> c=(Class<?>)this.getClass();
		try {
			Method method=c.getDeclaredMethod(methodName, null);
			fieldValue=(FieldValue<?>) method.invoke(this, null);
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
		return fieldValue;
	}
	
	public void setFieldValue(String propertyName,FieldValue<?> value) {
		String methodName="set"+propertyName;
		Class<?> c=(Class<?>)this.getClass();
		try {
			Method method=c.getDeclaredMethod(methodName, null);
			method.invoke(this, value);
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
	}
	
}
