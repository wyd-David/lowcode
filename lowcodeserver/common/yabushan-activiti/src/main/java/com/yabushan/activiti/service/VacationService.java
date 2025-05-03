package com.yabushan.activiti.service;

import com.yabushan.activiti.domain.HistoryTaskInfo;
import com.yabushan.activiti.domain.NextStepAndUser;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;

import java.io.InputStream;
import java.util.List;


public interface VacationService {

	/**
	 * 1.获取历史任务
	 * @param processInstanceId 流程实例ID
	 * @param isFinish 是否只查询已完成的
	 * @param firstResult 分页开始值（可以为null）
	 * @param maxResults 分页结束值（可以为null）
	 * @return
	 */
	public List<HistoryTaskInfo> getAllHistoryStep(String processInstanceId, boolean isFinish, Integer firstResult, Integer maxResults);

	/**
	 * 发起时第一个用户处理环节的处理人
	 * @param processKey 流程keY
	 * @param userId 流程发起人ID
	 * @return
	 */
	public NextStepAndUser getFirstUser(String processKey, String userId, String empType, String processDefId, ProcessDefinitionEntity  pd);


	/**
	 * 获取流程发起人
	 *
	 */
	public String getRequestUserId(String taskId);

	/**
	 * 获取处理人
	 * @param processDefinitionKey
	 * @param userId
	 * @param empType
	 * @param
	 * @return
	 */
	public NextStepAndUser getFirstUser(String processKey, String userId, String empType);

	/**
	 * 封装历史任务
	 * @param list
	 * @return
	 */
	public List<HistoryTaskInfo> getHistoryInfo(List<HistoricTaskInstance> list);

	public InputStream queryProImg(String businessKey);
}
