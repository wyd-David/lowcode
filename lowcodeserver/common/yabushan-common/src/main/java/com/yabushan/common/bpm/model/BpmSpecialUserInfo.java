package com.yabushan.common.bpm.model;

import java.io.Serializable;

public class BpmSpecialUserInfo implements Serializable {
	private String userid;
	private String groupid; //code
	private String userName;
	private String type;
	private String roleId;
	private Integer typeCode;
	private String sType;
	private Integer nextNodeCode; //code

	public BpmSpecialUserInfo() {
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public Integer getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(Integer typeCode) {
		this.typeCode = typeCode;
	}
	public String getsType() {
		return sType;
	}
	public void setsType(String sType) {
		this.sType = sType;
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public Integer getNextNodeCode() {
		return nextNodeCode;
	}
	public void setNextNodeCode(Integer nextNodeCode) {
		this.nextNodeCode = nextNodeCode;
	}
	

}
