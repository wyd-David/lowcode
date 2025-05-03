package com.yabushan.activiti.service;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	private EngineService engineService;
	@Override
	public void addCommentInfo(String taskId, String processInstanceId,
			String message) {
		engineService.getTaskService().addComment(taskId, processInstanceId, message);

	}

	@Override
	public List<Comment> getCommentsByTaskId(String taskId) {
		List<Comment> list = new ArrayList<Comment>();
		//使用当前的任务id，查询当前流程对应的历史任务ID
		//使用当前任务ID，获取当前任务对象
		Task task = engineService.getTaskService().createTaskQuery()
					.taskId(taskId).singleResult();
		//获取流程实例ID
		String processInstanceId =task.getProcessInstanceId();

		list=engineService.getTaskService().getProcessInstanceComments(processInstanceId);
		return list;
	}

	@Override
	public List<Comment> getCommentByTaskId(String taskId) {
		List<Comment> taskList = engineService.getTaskService().getTaskComments(taskId);
		return taskList;
	}

}
