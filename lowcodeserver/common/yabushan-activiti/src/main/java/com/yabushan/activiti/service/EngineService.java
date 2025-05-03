package com.yabushan.activiti.service;

import javax.sql.DataSource;


//import jdk.nashorn.internal.ir.ReturnNode;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EngineService {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private ProcessEngineConfiguration processEngineConfiguration;
	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private  CommentService commentService;

	public void setProcessEngineConfiguration(){
		if(processEngineConfiguration==null){
			processEngineConfiguration=
					ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
			processEngineConfiguration.setDataSource(dataSource);
			processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
			processEngineConfiguration.setJobExecutorActivate(false);
			processEngineConfiguration.setActivityFontName("宋体");
			processEngineConfiguration.setAnnotationFontName("宋体");
			processEngineConfiguration.setLabelFontName("宋体");
		}
	}

	public ProcessEngineConfiguration getProcessEngineConfiguration(){
		return processEngineConfiguration;
	}

	public ProcessEngine setEngine(){
		setProcessEngineConfiguration();
		if(processEngine==null){
			processEngine = processEngineConfiguration.buildProcessEngine();
		}
		return processEngine;
	}

	/**
	 * 管理流程定义服务类
	 * @return
	 */
	public RepositoryService getRepositoryService(){
		if(processEngine==null){setEngine();}
		return processEngine.getRepositoryService();
	}

	/**
	 * 执行管理，包括启动、推进、删除流程实例等操作
	 * @return
	 */
	public RuntimeService getRuntimeService(){
		if(processEngine==null){setEngine();}
		return processEngine.getRuntimeService();
	}

	/**
	 * 任务管理
	 * @return
	 */
	public TaskService getTaskService(){
		if(processEngine==null){setEngine();}
		return processEngine.getTaskService();
	}
	/**
	 * 历史管理，执行完的数据的管理
	 * @return
	 */
	public HistoryService getHistoryService(){
		if(processEngine==null){setEngine();}
		return processEngine.getHistoryService();
	}

	/**
	 * 一个可选服务，任务表单管理
	 * @return
	 */
	public FormService getFormService(){
		if(processEngine==null){setEngine();}
		return processEngine.getFormService();
	}

	public IdentityService getIdentityService(){
		if(processEngine==null){setEngine();}
		return processEngine.getIdentityService();
	}

	public CommentService getCommentService(){
		return  commentService;
	}



}
