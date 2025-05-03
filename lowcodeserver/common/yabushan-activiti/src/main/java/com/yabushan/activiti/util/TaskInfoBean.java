package com.yabushan.activiti.util;

import java.util.Date;
import java.util.Map;

/**
 * 任务实体类
 * @author yabushan
 *
 */
public class TaskInfoBean implements  Comparable<TaskInfoBean> {

	private String taskId;//任务ID
	private String taskName;//任务名称
	private Date createTime;//任务创建时间
	private String assignee;//任务的办理人
	private String processInstanceId;//流程实例ID
	private String executionId;//流程对象ID
	private String processDefinitionId;//流程定义ID
	private String businessId;
	private Map<String,Object> excutionVariables;

	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}


	public Map<String, Object> getExcutionVariables() {
		return excutionVariables;
	}

	public void setExcutionVariables(Map<String, Object> excutionVariables) {
		this.excutionVariables = excutionVariables;
	}

	@Override
	public int compareTo(TaskInfoBean o) {
		if(this.getCreateTime().before(o.getCreateTime())){
			return 1;
		}else{
			return -1;
		}
	}


}
