package com.yabushan.activiti.service;

import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FormServiceImpl implements FormService{
	@Autowired
	private EngineService engineService;
	@Override
	public TaskFormData queryFormData(String taskId) {
		TaskFormData taskFormData=engineService.getFormService().getTaskFormData(taskId);
		return taskFormData;

	}

	@Override
	public StartFormData getStartFormData(String processDefinitionId) {
		return engineService.getFormService().getStartFormData(processDefinitionId);
	}

	@Override
	public TaskFormData getTaskFormData(String taskId) {
		return  engineService.getFormService().getTaskFormData(taskId);
	}

	@Override
	public ProcessInstance submitStartFormData(String processDefinitionId, String businessKey, Map<String, String> properties) {
		return  engineService.getFormService().submitStartFormData(processDefinitionId,businessKey,properties);
	}

	@Override
	public void submitTaskFormData(String taskId, Map<String, String> properties) {

		 engineService.getFormService().submitTaskFormData(taskId,properties);
	}

	@Override
	public void saveFormData(String taskId, Map<String, String> properties) {
		engineService.getFormService().saveFormData(taskId,properties);
	}


}
