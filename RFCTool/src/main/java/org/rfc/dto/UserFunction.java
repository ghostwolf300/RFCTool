package org.rfc.dto;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.rfc.function.RunnableFunction;

import com.sap.conn.jco.JCoDestination;

public class UserFunction<T extends RunnableFunction> {
	
	private int id=-1;
	private String name;
	private Class<T> functionClass=null;
	private Map<String,InputField<?>> fieldMap=null;
	
	public UserFunction(Class<T> functionClass,int id) {
		super();
		this.id=id;
		this.functionClass=functionClass;
		initialize();
	}
	
	public UserFunction(Class<T> functionClass,int id,String name) {
		super();
		this.functionClass=functionClass;
		this.id=id;
		this.name=name;
		initialize();
		
	}
	
	private void initialize() {
		try {
			Method m=null;
			//m=functionClass.getDeclaredMethod("getFieldMap", null);
			//fieldMap=(Map<String, InputField<?>>) m.invoke(null);
			Field f=functionClass.getDeclaredField("FUNCTION_NAME");
			name=(String)f.get(null);
		} 
		/*
		 * catch (NoSuchMethodException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
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
//		catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
		catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Class<T> getFunctionClass() {
		return functionClass;
	}

	public void setFunctionClass(Class<T> functionClass) {
		this.functionClass = functionClass;
	}

	public Map<String, InputField<?>> getFieldMap() {
		return fieldMap;
	}

	public void setFieldMap(Map<String, InputField<?>> fieldMap) {
		this.fieldMap = fieldMap;
	}

	public Worker createWorker(int id,List<Material> materials,JCoDestination destination,boolean testRun) {
		Worker runnable=null;
		
		Object[] param=new Object[] {
			id,materials,destination,testRun	
		};
		Class<?>[] paramClass=new Class<?>[] {
			int.class,List.class,JCoDestination.class,boolean.class
		};
		
//		Object[] param=new Object[] {
//			id	
//		};
//		Class<?>[] paramClass=new Class<?>[] {
//			int.class
//		};
		
		try {
			Constructor<T> constructor=functionClass.getConstructor(paramClass);
			runnable=(Worker) constructor.newInstance(param);
		} 
		catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (InstantiationException e) {
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
		
		
		return runnable;
	}

	@Override
	public String toString() {
		return name;
	}
	
}
