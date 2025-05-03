package com.yabushan.activiti.domain;

import com.yabushan.activiti.util.TaskInfoBean;

import java.util.List;

public class TaskInfo {

	/**
	 * 可以处理的环节
	 */
	private String outTransations;
	/**
	 * 任务信息
	 */
	private TaskInfoBean taskInfoBean;
	/**
	 * 下一个处理人和处理步骤
	 */
	private List<NextStepAndUser> nextStepAndUser;
	private String currentDealUserId;
	/**
	 * 发起人
	 */
	private String assignee;
	public String getOutTransations() {
		return outTransations;
	}
	public void setOutTransations(String outTransations) {
		this.outTransations = outTransations;
	}
	public TaskInfoBean getTaskInfoBean() {
		return taskInfoBean;
	}
	public void setTaskInfoBean(TaskInfoBean taskInfoBean) {
		this.taskInfoBean = taskInfoBean;
	}

	public List<NextStepAndUser> getNextStepAndUser() {
		return nextStepAndUser;
	}
	public void setNextStepAndUser(List<NextStepAndUser> nextStepAndUser) {
		this.nextStepAndUser = nextStepAndUser;
	}
	public String getCurrentDealUserId() {
		return currentDealUserId;
	}
	public void setCurrentDealUserId(String currentDealUserId) {
		this.currentDealUserId = currentDealUserId;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}



}
