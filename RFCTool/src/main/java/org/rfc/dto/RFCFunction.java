package org.rfc.dto;

public class RFCFunction {
	
	private int id=-1;
	private String name;
	
	public RFCFunction() {
		super();
	}
	
	public RFCFunction(int id,String name) {
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

	@Override
	public String toString() {
		return name;
	}
	
}
