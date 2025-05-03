package com.yabushan.activiti.service;

import com.yabushan.activiti.util.Constant;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.*;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class HistoryQueryServiceImpl implements HistoryQueryService{
	@Autowired
	private EngineService engineService;

	@Override
	public List<HistoricProcessInstance> getHistoricProcessInstanceByKey(
			String ID, String flag, boolean isFinish,Integer firstResult ,Integer maxResults) {
		List<HistoricProcessInstance> list=null;
		if(firstResult==null){
			firstResult= Constant.firstResult;
		}
		if(maxResults==null){
			maxResults=Constant.maxResults;
		}
		if("key".equals(flag)){//按流程KEY查询
			if(isFinish){
				list = engineService.getHistoryService()
						.createHistoricProcessInstanceQuery().finished()
						.processDefinitionKey(ID).orderByProcessInstanceStartTime().asc()
						.listPage(firstResult, maxResults);
			}else{
				list = engineService.getHistoryService()
						.createHistoricProcessInstanceQuery()
						.processDefinitionKey(ID).orderByProcessInstanceStartTime().asc()
						.listPage(firstResult, maxResults);
			}

		}else if("pid".equals(flag)){
			list = engineService.getHistoryService()
					.createHistoricProcessInstanceQuery()
					.processInstanceId(ID).orderByProcessInstanceStartTime().asc()
					.list();
		}else if("bid".equals(flag)){
			list = engineService.getHistoryService()
					.createHistoricProcessInstanceQuery().processInstanceBusinessKey(ID)
					.orderByProcessInstanceStartTime().asc()
					.list();
		}
		return list;

	}

	@Override
	public List<HistoricActivityInstance> getHistoricActivityInstances(
			String processInstanceId, boolean isFinish,Integer firstResult ,Integer maxResults) {
		if(firstResult==null){
			firstResult=Constant.firstResult;
		}
		if(maxResults==null){
			maxResults=Constant.maxResults;
		}
		List<HistoricActivityInstance> list = engineService.getHistoryService()
				.createHistoricActivityInstanceQuery()
				.processInstanceId(processInstanceId)
				.orderByHistoricActivityInstanceEndTime().asc()
				.listPage(firstResult, maxResults);
		return list;
	}

	@Override
	public List<HistoricTaskInstance> getHistoricTaskInstances(
			String processInstanceId,boolean isFinish,Integer firstResult ,Integer maxResults) {
		List<HistoricTaskInstance> list=null;
		if(firstResult==null){
			firstResult=Constant.firstResult;
		}
		if(maxResults==null){
			maxResults=Constant.maxResults;
		}
		if(isFinish){
			list = engineService.getHistoryService()
					.createHistoricTaskInstanceQuery()
					.processInstanceId(processInstanceId).finished()
//					.orderByTaskId().asc()
					.orderByTaskCreateTime().desc()
					.listPage(firstResult, maxResults);
		}else{
			list = engineService.getHistoryService()
					.createHistoricTaskInstanceQuery()
					.processInstanceId(processInstanceId)
					//.orderByTaskId().desc()
					.orderByTaskCreateTime().desc()
					.orderByDeleteReason().asc()
					.orderByTaskDueDate().desc()
					.listPage(firstResult, maxResults);
		}

		return list;
	}

	/**
	 * 查询历史流程变量（在流程结束后）
	 * @param variableName 变量名
	 * @return List<HistoricVariableInstance>
	 */
	@Override
	public List<HistoricVariableInstance> getHistoricVariableInstancesByVariableName(String variableName){
		List<HistoricVariableInstance> list = engineService.getHistoryService()
				.createHistoricVariableInstanceQuery()
				.variableName(variableName)
				.list();
		return list;
	}
	/**
	 * 查询历史流程变量（在流程结束后）
	 * @param variableName 变量名
	 * @return List<HistoricVariableInstance>
	 */
	@Override
	public List<HistoricVariableInstance> getHistoricVariableInstancesByTaskId(String taskId){
		List<HistoricVariableInstance> list = engineService.getHistoryService()
				.createHistoricVariableInstanceQuery()
				.taskId(taskId)
				.list();
		return list;
	}

	/**
	 * 查询历史流程变量
	 */
	@Override
	public List<HistoricVariableInstance> getHistoricVariableInstancesByProcessInstanceId(String processInstanceId){
		List<HistoricVariableInstance> list = engineService.getHistoryService()
				.createHistoricVariableInstanceQuery()
				.processInstanceId(processInstanceId)
				.list();
		return list;

	}
	/**
	 * 获取流程变量
	 * @param title 变量名
	 * @param taskId 任务名
	 * @return
	 */
	@Override
	public Object getVariableLocal(String title, String taskId) {
		TaskService taskService=engineService.getTaskService();
		return  taskService.getVariableLocal(taskId, title);
	}

	@Override
	public Object getVariable(String title, String taskId) {
		TaskService taskService=engineService.getTaskService();
		return  taskService.getVariable(taskId, title);
	}

	@Override
	public Map<String, Object> packageVariables(Boolean isFormVariables,String processInstanceId,String taskId) {
		Map<String,Object> historyVariables = new HashMap<>();
		List<HistoricDetail> list = engineService.getHistoryService().createHistoricDetailQuery().processInstanceId(processInstanceId).taskId(taskId).list();
		for(HistoricDetail historicDetail : list){
			if(historicDetail instanceof HistoricFormProperty && isFormVariables){
				//表单中的字段
				HistoricFormProperty field = (HistoricFormProperty) historicDetail;
				historyVariables.put(field.getPropertyId(), field.getPropertyValue());
			}else if(historicDetail instanceof  HistoricVariableUpdate && !isFormVariables){
				//普通变量
				HistoricDetailVariableInstanceUpdateEntity variable
						= (HistoricDetailVariableInstanceUpdateEntity) historicDetail;
				historyVariables.put(variable.getName(),variable.getValue());
			}
		}
		return historyVariables;
	}

	@Override
	public List<HistoricTaskInstance> getMyFinishTask(String userId, Integer firstResult, Integer maxResults) {
		if(firstResult==null){firstResult=0;}
		if(maxResults==null){maxResults=100;}
		List<HistoricTaskInstance> list = engineService.getHistoryService()
				.createHistoricTaskInstanceQuery().taskAssignee(userId)
				.finished()
				.orderByTaskCreateTime().desc()
				.listPage(firstResult, maxResults);
		return list;
	}

}
