package com.yabushan.activiti.domain;

import org.activiti.engine.form.FormProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author yabushan
 * @Date 2021/6/19 15:35
 * @Version 1.0
 */
public class TaskFormDataImp  {



    private String formKey;
    private String deploymentId;
    private List<FormProperty> formPropertyImpl;
    private String assignee;
    private String name;
    private String parentTaskId;
    private Date createTime;
    private String executionId;
    private Map variables;
    private String processInstanceId;
    private String processDefinitionId;
    private String taskDefinitionKey;
    private String isDeleted;
    private String id;




    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(String parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }


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

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormKey() {
        return formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public List<FormProperty> getFormPropertyImpl() {
        return formPropertyImpl;
    }

    public void setFormPropertyImpl(List<FormProperty> formPropertyImpl) {
        this.formPropertyImpl = formPropertyImpl;
    }

    public Map getVariables() {
        return variables;
    }

    public void setVariables(Map variables) {
        this.variables = variables;
    }
}
