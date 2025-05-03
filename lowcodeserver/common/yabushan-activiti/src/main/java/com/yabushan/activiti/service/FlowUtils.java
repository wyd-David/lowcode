package com.yabushan.activiti.service;

import com.yabushan.activiti.util.Constant;
import com.yabushan.activiti.util.TaskInfoBean;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FlowUtils {
	@Autowired
	private VariablesService variablesService;

	@Autowired
	private EngineService engineService;

	/**
	 * 组装任务信息
	 * @param task
	 * @return
	 */
	public TaskInfoBean returnTaskInfo(Task task){
		String businessId =variablesService.getVariablesByTaskId(Constant.BUSINESS_ID, task.getId(), false)+"";
		TaskInfoBean taskInfoBean=new TaskInfoBean();
		taskInfoBean.setAssignee(task.getAssignee());
		taskInfoBean.setCreateTime(task.getCreateTime());
		taskInfoBean.setExecutionId(task.getExecutionId());
		taskInfoBean.setProcessDefinitionId(task.getProcessDefinitionId());
		taskInfoBean.setProcessInstanceId(task.getProcessInstanceId());
		taskInfoBean.setTaskId(task.getId());
		taskInfoBean.setTaskName(task.getName());
		taskInfoBean.setBusinessId(businessId);

		//封装流程变量_未结束的流程
		Map<String, Object> variablesExcution = engineService.getRuntimeService().getVariables(task.getExecutionId());
		taskInfoBean.setExcutionVariables(variablesExcution);
		return taskInfoBean;
	}

}
