package com.library.www.po;

public class UserType {

	private Integer typeId;
	
	private String typeName;
	
	private Integer canBorrowNum;
	
	private Integer deadline;

	
	
	public UserType() {
		
	}

	public UserType(Integer typeId, String typeName, Integer canBorrowNum, Integer deadline) {
		super();
		this.typeId = typeId;
		this.typeName = typeName;
		this.canBorrowNum = canBorrowNum;
		this.deadline = deadline;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getCanBorrowNum() {
		return canBorrowNum;
	}

	public void setCanBorrowNum(Integer canBorrowNum) {
		this.canBorrowNum = canBorrowNum;
	}

	
	public Integer getDeadline() {
		return deadline;
	}

	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}

	@Override
	public String toString() {
		return "UserType [typeId=" + typeId + ", typeName=" + typeName + ", canBorrowNum=" + canBorrowNum
				+ ", deadline=" + deadline + "]";
	}
	
}
