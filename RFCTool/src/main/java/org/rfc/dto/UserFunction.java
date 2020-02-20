package org.rfc.dto;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.rfc.function.ChangePlantData;
import org.rfc.function.RunnableFunction;

import com.sap.conn.jco.JCoDestination;

public class UserFunction {
	
	private int id=-1;
	private String name;
	
	public UserFunction() {
		super();
	}
	
	public UserFunction(int id,String name) {
		super();
		this.id=id;
		this.name=name;
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
	
	@SuppressWarnings("unchecked")
	public Worker createWorker(Object[] param,Class<?>[] paramClass) {
		Worker runnable=null;
//		Class<?>[] paramClass=new Class<?>[param.length];
//		for(int i=0;i<param.length;i++) {
//			paramClass[i]=param.getClass();
//		}
		
		try {
			Class<?> c=(Class<?>)Class.forName("org.rfc.function."+name);
			Constructor<?> constructor=c.getConstructor(paramClass);
			runnable=(Worker) constructor.newInstance(param);
			
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (SecurityException e) {
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
