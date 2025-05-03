package com.yabushan.activiti.domain;

/**
 * 启动流程实例返回对象
 * @author yabushan
 *
 */
public class ProcessInstance {

	private String processInstanceId;//流程实例ID
	private String processDefinitionId;//流程定义ID
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}


}
