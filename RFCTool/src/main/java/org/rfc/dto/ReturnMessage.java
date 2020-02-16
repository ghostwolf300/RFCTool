package org.rfc.dto;

public class ReturnMessage {
	String material=null;
	String number=null;
	String type;
	String message;
	String row;
	String messageVariable1=null;
	String messageVariable2=null;
	String messageVariable3=null;
	String messageVariable4=null;
	
	public ReturnMessage() {
		super();
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getMessageVariable1() {
		return messageVariable1;
	}

	public void setMessageVariable1(String messageVariable1) {
		this.messageVariable1 = messageVariable1;
	}

	public String getMessageVariable2() {
		return messageVariable2;
	}

	public void setMessageVariable2(String messageVariable2) {
		this.messageVariable2 = messageVariable2;
	}

	public String getMessageVariable3() {
		return messageVariable3;
	}

	public void setMessageVariable3(String messageVariable3) {
		this.messageVariable3 = messageVariable3;
	}

	public String getMessageVariable4() {
		return messageVariable4;
	}

	public void setMessageVariable4(String messageVariable4) {
		this.messageVariable4 = messageVariable4;
	}

	@Override
	public String toString() {
		return material + "; " + number + "; " + type + "; " + message + "; " + row + "; " + messageVariable1 + "; "
				+ messageVariable2 + "; " + messageVariable3 + "; " + messageVariable4 + "]";
	}
	
	
}
