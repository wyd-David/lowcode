package com.yabushan.activiti.domain;

import java.text.Collator;
import java.util.Locale;

public class NextStepAndUser implements Comparable<NextStepAndUser>{

	private String nextStep;
	private String nextUserId;
	private String nextUserName;
	private String nextUserRole = "";
	private String jumpStep;
	private String outLine;
	private String nextUserRoleId;
	private String nextDealType;
	private String outLineId;

	public String getOutLineId() {
		return outLineId;
	}

	public void setOutLineId(String outLineId) {
		this.outLineId = outLineId;
	}

	public String getNextStep() {
		return nextStep;
	}
	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}
	public String getNextUserId() {
		return nextUserId;
	}
	public void setNextUserId(String nextUserId) {
		this.nextUserId = nextUserId;
	}
	public String getNextUserName() {
		return nextUserName;
	}
	public void setNextUserName(String nextUserName) {
		this.nextUserName = nextUserName;
	}
	public String getNextUserRole() {
		return nextUserRole;
	}
	public void setNextUserRole(String nextUserRole) {
		this.nextUserRole = nextUserRole;
	}
	public String getJumpStep() {
		return jumpStep;
	}
	public void setJumpStep(String jumpStep) {
		this.jumpStep = jumpStep;
	}


	public String getOutLine() {
		return outLine;
	}

	public void setOutLine(String outLine) {
		this.outLine = outLine;
	}

	public String getNextUserRoleId() {
		return nextUserRoleId;
	}

	public void setNextUserRoleId(String nextUserRoleId) {
		this.nextUserRoleId = nextUserRoleId;
	}

	public String getNextDealType() {
		return nextDealType;
	}

	public void setNextDealType(String nextDealType) {
		this.nextDealType = nextDealType;
	}

	@Override
	public int compareTo(NextStepAndUser o) {
		return Collator.getInstance(Locale.CHINESE).compare(this.getOutLineId(),o.getOutLineId());
		//　return this.age - user.getAge();　　// 根据年龄升序排列，降序修改相减顺序即可
	}
}
