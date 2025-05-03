package com.yabushan.activiti.service;

import java.util.List;

import com.yabushan.activiti.domain.HistoryTaskInfo;
import com.yabushan.activiti.domain.NextStepAndUser;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.task.Task;


public interface BaseFlowService {
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
	 * 获取下一步审批人信息
	 * @param task
	 * @return
	 */
	public List<NextStepAndUser> getNextStepAndUser(Task task);

	/**
	 * 发起时第一个用户处理环节的处理人
	 * @param processKey 流程keY
	 * @param userId 流程发起人ID
	 * @return
	 */
	public NextStepAndUser getFirstUser(String processKey, String userId, String empType, String processDefId, ProcessDefinitionEntity  pd);

	/**
	 * 发起时第一个用户处理环节的处理人
	 * @param processKey 流程keY
	 * @param userId 流程发起人ID
	 * @param empType 发起人类型（1：需要经过分管领导审批，2：不需要经过分管领导审批）
	 * @param businessKey 业务id
	 * @return
	 */
	public NextStepAndUser getFirstUser(String processKey, String userId, String empType, String businessKey);

	/**
	 * 获取流程发起人
	 *
	 */
	public String getRequestUserId(String taskId);
}
