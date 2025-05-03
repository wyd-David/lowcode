package com.yabushan.activiti.util;

/**
 * 流程定义实体
 * @author yabushan
 *
 */
public class ProcessDefinitionBean {

	private String processDefinitionId;//流程定义ID
	private String processDefinitionName;//流程定义名称
	private	String processDefinitionKey;//流程定义的key
	private Integer processDefinitionVersion;//流程定义的版本
	private String processDefinitionResourceBpmn;//流程定义的资源文件名称
	private String processDefinitionResourcePng;//流程定义的资源文件png
	private String processDeploymentId;//部署对象ID
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getProcessDefinitionName() {
		return processDefinitionName;
	}
	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}
	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}
	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}
	public Integer getProcessDefinitionVersion() {
		return processDefinitionVersion;
	}
	public void setProcessDefinitionVersion(Integer processDefinitionVersion) {
		this.processDefinitionVersion = processDefinitionVersion;
	}
	public String getProcessDefinitionResourceBpmn() {
		return processDefinitionResourceBpmn;
	}
	public void setProcessDefinitionResourceBpmn(
			String processDefinitionResourceBpmn) {
		this.processDefinitionResourceBpmn = processDefinitionResourceBpmn;
	}
	public String getProcessDefinitionResourcePng() {
		return processDefinitionResourcePng;
	}
	public void setProcessDefinitionResourcePng(String processDefinitionResourcePng) {
		this.processDefinitionResourcePng = processDefinitionResourcePng;
	}
	public String getProcessDeploymentId() {
		return processDeploymentId;
	}
	public void setProcessDeploymentId(String processDeploymentId) {
		this.processDeploymentId = processDeploymentId;
	}



}
