package com.yabushan.activiti.domain;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Comment;

public class HistoryTaskInfo implements  Comparable<HistoryTaskInfo>,Cloneable{
	/**
	 * 历史流程任务
	 */
	private HistoricTaskInstance historicTaskInstance;
	/**
	 * 任务变量
	 */
	private String info;
	private String todoUserLoginId;
	private String endDateTime;
	private String nextStep;
	private String nextTodoUser;

	private  String startDateTime;

	public Integer getSORT() {
		return SORT;
	}

	public void setSORT(Integer SORT) {
		this.SORT = SORT;
	}

	private Integer SORT;


	private List<Comment> taskComment;
	private Map<String ,Object> varialbes;
	private Map<String ,Object> formVarialbes;
	public HistoricTaskInstance getHistoricTaskInstance() {
		return historicTaskInstance;
	}
	public void setHistoricTaskInstance(HistoricTaskInstance historicTaskInstance) {
		this.historicTaskInstance = historicTaskInstance;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public List<Comment> getTaskComment() {
		return taskComment;
	}
	public void setTaskComment(List<Comment> taskComment) {
		this.taskComment = taskComment;
	}


	public Map<String, Object> getVarialbes() {
		return varialbes;
	}

	public void setVarialbes(Map<String, Object> varialbes) {
		this.varialbes = varialbes;
	}

	public Map<String, Object> getFormVarialbes() {
		return formVarialbes;
	}

	public void setFormVarialbes(Map<String, Object> formVarialbes) {
		this.formVarialbes = formVarialbes;
	}

	public String getTodoUserLoginId() {
		return todoUserLoginId;
	}

	public void setTodoUserLoginId(String todoUserLoginId) {
		this.todoUserLoginId = todoUserLoginId;
	}

	public String getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}

	public String getNextStep() {
		return nextStep;
	}

	public void setNextStep(String nextStep) {
		this.nextStep = nextStep;
	}

	public String getNextTodoUser() {
		return nextTodoUser;
	}

	public void setNextTodoUser(String nextTodoUser) {
		this.nextTodoUser = nextTodoUser;
	}

	@Override
	public int compareTo(HistoryTaskInfo o) {
		if(this.getSORT()>o.getSORT()){
			return 1;
		}else{
			return 0;
		}

	}


	@Override
	public Object clone() throws CloneNotSupportedException {

		return (HistoryTaskInfo)super.clone();
	}

	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
}
