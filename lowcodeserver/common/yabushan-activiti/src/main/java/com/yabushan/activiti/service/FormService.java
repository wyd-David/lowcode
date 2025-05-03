package com.yabushan.activiti.service;

import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.Map;

public interface FormService {

	/**
	 * 1.获取表单数据对象
	 * @param taskId
	 * @return
	 */
	 TaskFormData queryFormData(String taskId);

	 StartFormData getStartFormData(String processDefinitionId);

	 TaskFormData getTaskFormData(String taskId);

	ProcessInstance submitStartFormData(String processDefinitionId, String businessKey, Map<String, String> properties);
	void submitTaskFormData(String taskId, Map<String, String> properties);
	void saveFormData(String taskId, Map<String, String> properties);
}
