package com.yabushan.activiti.domain;

import java.io.Serializable;

public class RequestMessage implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String approveOpinion;
	private String nextSeqFlow;
	private String taskId;
	private String nextDealUserIds;
	private String userId;
	public String getApproveOpinion() {
		return approveOpinion;
	}
	public void setApproveOpinion(String approveOpinion) {
		this.approveOpinion = approveOpinion;
	}
	public String getNextSeqFlow() {
		return nextSeqFlow;
	}
	public void setNextSeqFlow(String nextSeqFlow) {
		this.nextSeqFlow = nextSeqFlow;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getNextDealUserIds() {
		return nextDealUserIds;
	}
	public void setNextDealUserIds(String nextDealUserIds) {
		this.nextDealUserIds = nextDealUserIds;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}



}
