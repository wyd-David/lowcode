package com.yabushan.activiti.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import com.yabushan.activiti.domain.DeployBean;
import com.yabushan.activiti.domain.ProcessInstance;
import com.yabushan.activiti.domain.RequestMessage;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class FlowServiceImpl implements FlowService{

	@Autowired
	private EngineService engineService;


	/**
	 * 流的形式部署流程定义
	 * @param processBpmn 流程文件
	 * @param processPng 流程图片
	 * @param rootPath resources下流程文件所在的文件夹路径
	 * @return DeployBean
	 */
	public DeployBean saveInputStremDeploy(String processBpmn, String processPng, String rootPath){
		DeployBean deployBean=new DeployBean();
		InputStream inInputStreamBpmn=null;
		InputStream inputStreamPng=null;
			 inInputStreamBpmn = this.getClass().getClassLoader().
					getResourceAsStream(rootPath+"/"+processBpmn);
         inputStreamPng = this.getClass().getClassLoader().
        	getResourceAsStream(rootPath+"/"+processPng);
         Deployment deployment=
 				engineService.getRepositoryService()
 								.createDeployment()
 								.name(this.getClass().getName())
 								.addInputStream(processBpmn, inInputStreamBpmn)
 								.addInputStream(processPng, inputStreamPng)
 								.deploy();
         //获取所有步骤信息

		deployBean.setDeployId(deployment.getId());
		deployBean.setDeployName(deployment.getName());
		return deployBean;
	}




	/**
	 * zip格式部署
	 * @param zipPath :zip文件路径
	 * @param deployeName 部署文件名
	 *
	 */
	@Override
	public DeployBean saveZipInputStreamDFeploy(String zipPath,String deployeName){
		DeployBean deployBean=new DeployBean();
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(zipPath);
		ZipInputStream zipInputStream= new ZipInputStream(in);
		Deployment deployment = engineService.getRepositoryService()
								.createDeployment()
								.name(deployeName)
								.addZipInputStream(zipInputStream)
								.deploy();
		deployBean.setDeployId(deployment.getId());
		deployBean.setDeployName(deployment.getName());
		return deployBean;
	}

	@Override
	public DeployBean saveNewDeploye(File file,String deployeName){
		DeployBean deployBean=new DeployBean();
		ZipInputStream zipInputStream;
		try {
			zipInputStream = new ZipInputStream(new FileInputStream(file));
			Deployment deployment=engineService.getRepositoryService().createDeployment().name(deployeName).addZipInputStream(zipInputStream).deploy();
			deployBean.setDeployId(deployment.getId());
			deployBean.setDeployName(deployment.getName());
			return deployBean;
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
			return null;
		}

	}

	/**
	 * 启动流程
	 * @param userName 流程处理人
	 * @param businessId 业务主键ID
	 * @param processDefinitionKey 流程主键Key值
	 * @param variables 流程变量
	 * @return
	 */
	@Override
	public ProcessInstance saveStartProcess(String userName, String businessId, String processDefinitionKey, Map<String, Object> variables){
		ProcessInstance processInstance=new ProcessInstance();
		//启动流程实例的同时，设置流程变量，使用流程变量来指定任务的办理人
		/*Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("userName", userName);*/
		//使用流程定义的Key启动流程实例
		engineService.getIdentityService().setAuthenticatedUserId(userName);
		org.activiti.engine.runtime.ProcessInstance pi = engineService.getRuntimeService()
															.startProcessInstanceByKey(processDefinitionKey,businessId,variables);
		processInstance.setProcessDefinitionId(pi.getProcessDefinitionId());//流程定义
		processInstance.setProcessInstanceId(pi.getId());//流程实例ID
		return processInstance;
	}


	/**
	 * 根据流程处理人查询个人任务
	 * @param userName 流程处理人
	 * @return java.util.List<Task>
	 */
	@Override
	public List<Task> queryMyTasks(String userName){
		List<Task> list = engineService.getTaskService()
				.createTaskQuery().taskAssignee(userName)
				.orderByTaskCreateTime().asc()
				.list();
		return list;
	}

	/**
	 *  根据任务ID、指定下一个处理人、添加变量参数 完成任务
	 * @param taskId 任务ID
	 * @param nextDealUserId 指定下一个处理人
	 * @param variables 添加变量参数
	 */
	@Override
	@Transactional
	public void saveFinishMyTask(RequestMessage requestMessage){
		Map<String, Object> variables=new HashMap<String, Object>();
		variables.put("nextDealUser", requestMessage.getNextDealUserIds());//流程中设置下一个审批人的占位符为：userName
		variables.put("message", requestMessage.getNextSeqFlow());//设置下一步步骤
		//variables.put("opinion", requestMessage.getApproveOpinion());//保存流程实体
		engineService.getTaskService().setVariableLocal(requestMessage.getTaskId(), "info", requestMessage.getApproveOpinion());
		//engineService.getTaskService().setVariable(requestMessage.getTaskId(), "message", requestMessage.getNextSeqFlow());
		claim(requestMessage.getTaskId(), requestMessage.getUserId());
		engineService.getTaskService().complete(requestMessage.getTaskId(), variables);
	}

	/**
	 * 查询所有流程定义
	 * @return java.util.List<ProcessDefinition>
	 */
	@Override
	public List<ProcessDefinition> queryDefinitions(){
		List<ProcessDefinition> list=engineService.getRepositoryService()
												.createProcessDefinitionQuery()
												.orderByProcessDefinitionVersion().asc()
												.list();
		return list;
	}

	/**
	 * 返回所有最新版本的流程定义
	 */
	public List<ProcessDefinition> 	queryNewVersionDefinitions(){
		List<ProcessDefinition> list = engineService.getRepositoryService()
										.createProcessDefinitionQuery()
										.orderByProcessDefinitionVersion().asc()//使用流程定义的版本升序排序
										.list();
		/**
		 * Map<String,ProcessDefinition>
		 * map 集合的key：流程定义的key
		 * map集合的value ： 流程定义的对象
		 * map集合的特点：当map集合key值相同的情况下，后一次的值将替换前一次的值
		 */
		Map<String,ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
		if(list!=null && list.size()>0){
			for (ProcessDefinition processDefinition : list) {
				map.put(processDefinition.getKey(), processDefinition);
			}
		}

		List<ProcessDefinition> pdList=new ArrayList<ProcessDefinition>(map.values());
		return pdList;

	}

	/**
	 * 返回一个所有最新版本的流程定义
	 * @param processDefinitionKey
	 * @return
	 */
	@Override
	public ProcessDefinition queryOneNewVersionDefinitions(String processDefinitionKey){
		List<ProcessDefinition> list = engineService.getRepositoryService()
										.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey)
										.orderByProcessDefinitionVersion().asc()//使用流程定义的版本升序排序
										.list();
		/**
		 * Map<String,ProcessDefinition>
		 * map 集合的key：流程定义的key
		 * map集合的value ： 流程定义的对象
		 * map集合的特点：当map集合key值相同的情况下，后一次的值将替换前一次的值
		 */
		Map<String,ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
		if(list!=null && list.size()>0){
			for (ProcessDefinition processDefinition : list) {
				map.put(processDefinition.getKey(), processDefinition);
			}
		}
		List<ProcessDefinition> pdList=new ArrayList<ProcessDefinition>(map.values());
		if(pdList.size()>0){
			return pdList.get(0);
		}
		return null;

	}

	/**
	 * 删除流程
	 * @param deploymentId 流程部署ID
	 * @param cascade 是否级联删除：
	 * 	cascade=true :是级联删除，不管流程是否启动，都能进行删除
	 *  cascade=false:不是级联删除，只能删除流程没有启动的流程，如果流程启动，就会抛出异常
	 *
	 */
	@Override
	public void deleteProcessDefinitonByDeploymentId(String deploymentId,boolean cascade){
		engineService.getRepositoryService().deleteDeployment(deploymentId, cascade);
	}

	/**
	 * 根据key删除新旧版本流程定义
	 */
	@Override
	public void deleteProcessDefinitionByKey(String processDefinitionKey) {
		//1.先使用流程定义的key查询流程定义，查询除所有的版本
		List<ProcessDefinition> list = engineService.getRepositoryService()
										.createProcessDefinitionQuery()
										.processDefinitionKey(processDefinitionKey)
										.list();
		//遍历，获取每个流程顶一顶 部署Id
		if(list!=null && list.size()>0){
			for (ProcessDefinition processDefinition : list) {
				//获取部署ID
				String deploymentId = processDefinition.getDeploymentId();
				engineService.getRepositoryService().deleteDeployment(deploymentId,true);
			}
		}
	}

	/**
	 * 根据流程实例ID判断流程是否已经结束
	 * @return boolean
	 */
	@Override
	public boolean isProcessEnd(String processInstanceId) {
		org.activiti.engine.runtime.ProcessInstance pi = engineService.getRuntimeService()
								.createProcessInstanceQuery()
								.processInstanceId(processInstanceId)
								.singleResult();
		if(pi==null){
			return true;
		}
		return false;
	}

	/**
	 * 查询历史任务
	 * @return List<HistoricTaskInstance>
	 */
	@Override
	public List<HistoricTaskInstance> getHistoricTaskInstancesByUserName(
			String userName) {
		List<HistoricTaskInstance> list = engineService.getHistoryService()
											.createHistoricTaskInstanceQuery()
											.taskAssignee(userName)
											.list();
		if(list!=null && list.size()>0){return list;}
		return null;
	}


	/**
	 * 查询历史流程实例
	 * @param processInstanceId
	 * @return HistoricProcessInstance
	 */
	@Override
	public HistoricProcessInstance getHistoricProcessInstanceById(
			String processInstanceId) {
		HistoricProcessInstance hpi = engineService.getHistoryService()
										.createHistoricProcessInstanceQuery()
										.processInstanceId(processInstanceId)
										.singleResult();
		return hpi;
	}
	/**
	 * 查询历史流程实例
	 * @param processDefinitionKey
	 * @return List<HistoricProcessInstance>
	 */
	public List<HistoricProcessInstance> getHistoricProcessInstanceByKey(String processDefinitionKey){
		List<HistoricProcessInstance> list = engineService.getHistoryService()
												.createHistoricProcessInstanceQuery()
												.processDefinitionKey(processDefinitionKey)
												.list();
		return list;
	}
	/**
	 * 查询历史流程实例
	 * @param processDefinitionKey
	 * @return List<HistoricProcessInstance>
	 */
	public List<HistoricTaskInstance> getHistoricProcessInstanceByProcessDefId(String processInstanceId){
		List<HistoricTaskInstance> list = engineService.getHistoryService()
				.createHistoricTaskInstanceQuery()
												.processInstanceId(processInstanceId).orderByTaskCreateTime().asc()
												.list();
		return list;
	}

	/**
	 *设置参数变量
	 * @param mapParams 参数key和value值
	 * @param taskId 任务ID
	 * @param isBindTask 是否绑定任务：绑定后将只能在当前环节查看
	 */
	@Override
	public void setVariables(Map<String, String> mapParams, String taskId,boolean isBindTask) {
    	TaskService taskService	=engineService.getTaskService();
    	boolean isBind=false;//默认不绑定任务,绑定任务后，之后在当前任务才能查看，其它环节将不能查看该变量
    	if(isBindTask){isBind=isBindTask;}
    	if(isBind){
    		for (Map.Entry<String, String> entry : mapParams.entrySet()) {
        		taskService.setVariableLocal(taskId, entry.getKey(), entry.getValue());
        	}
    	}else{
    		for (Map.Entry<String, String> entry : mapParams.entrySet()) {
        		taskService.setVariable(taskId, entry.getKey(), entry.getValue());
        	}
    	}
	}

	/**
	 *
	 * @param classBean 需要设置的对象类
	 * @param taskId 任务ID
	 * @param title 参数变量名
	 * @param isBindTask 是否绑定任务：绑定后将只能在当前环节查看
	 * @return
	 */
	@Override
	public <T> T setVariables(Class<T> classBean, String taskId,String title,boolean isBindTask) {
		TaskService taskService	=engineService.getTaskService();
    	boolean isBind=false;//默认不绑定任务,绑定任务后，之后在当前任务才能查看，其它环节将不能查看该变量
    	if(isBindTask){isBind=isBindTask;}
    	if(isBind){
        		taskService.setVariableLocal(taskId,title,classBean);
    	}else{
        		taskService.setVariable(taskId,title,classBean);
        	}
		return null;
	}




	/**
	 * 获取流程变量
	 * @param title 变量名
	 * @param taskId 任务名
	 * @return
	 */
	@Override
	public String getVariables(String title,String taskId) {
		TaskService taskService=engineService.getTaskService();
		return (String) taskService.getVariable(taskId,title);
	}


	/**
	 * 获取流程变量
	 * @param title 变量名
	 * @param taskId 任务名
	 * @return
	 */
	@Override
	public Object getVariable(String title, String taskId) {
		TaskService taskService=engineService.getTaskService();
		return taskService.getVariable(taskId, title);

	}





	/**
	 * 获取流程变量
	 * @param title 变量名
	 * @param taskId 任务名
	 * @return
	 */
	@Override
	public String getVariableLocal(String title, String taskId) {
		TaskService taskService=engineService.getTaskService();
		return (String) taskService.getVariableLocal(taskId, title);

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
	 * 查询历史活动
	 * @param processInstanceId
	 * @return List<HistoricActivityInstance>
	 */
	@Override
	public List<HistoricActivityInstance> getHistoricActivityInstances(String processInstanceId){
		List<HistoricActivityInstance> list = engineService.getHistoryService()
												.createHistoricActivityInstanceQuery()
												.processInstanceId(processInstanceId)
												.orderByHistoricActivityInstanceEndTime().asc()
												.list();
		return list;
	}

	@Override
	public List<HistoricTaskInstance> getHistoricTaskInstances(String processInstanceId){
		List<HistoricTaskInstance> list = engineService.getHistoryService()
											.createHistoricTaskInstanceQuery()
											.processInstanceId(processInstanceId)
											.orderByHistoricTaskInstanceStartTime().asc()
											.list();
		return list;
	}



	/**要将任务分配到的处理人
	 * @param taskId 任务ID
	 * @param
	 */
	@Override
	public void changeAssigneeTask(String taskId, String dealUserId) {
		engineService.getTaskService().setAssignee(taskId, dealUserId);

	}



	/**
	 * 查询我的组任务
	 * @param userName 处理人
	 */
	@Override
	public List<Task> getMyGroupTask(String userName) {
		List<Task> list = engineService.getTaskService()
							.createTaskQuery()
							.taskCandidateUser(userName)
							.orderByTaskCreateTime().desc()
							.list();
		return list;
	}



	/**
	 * 根据任务ID查询任务的办理人列表
	 */
	@Override
	public List<IdentityLink> getIdentityLinks(String taskId) {
		List<IdentityLink> list = engineService.getTaskService()
									.getIdentityLinksForTask(taskId);
		return list;

	}


	/**
	 * 将组任务分配给个人
	 * @param taskId 任务ID
	 * @param 要分配到的处理人
	 */
	@Override
	public void claim(String taskId, String dealUserName) {
		engineService.getTaskService().claim(taskId, dealUserName);
	}



	//回退组任务
	@Override
	public void backGroupTaskFromOwner(String taskId) {
		engineService.getTaskService().setAssignee(taskId, null);

	}



	/**
	 * 向组任务中添加或删除成员
	 * @param taskId
	 * @param List<String> DelaUserName
	 */
	@Override
	public void addOrDeleteEmpToGroupTask(String taskId,List<String> DelaUserName,String flag) {
		if(flag.equals("add")){
			//添加成员
			for (String userName : DelaUserName) {
				engineService.getTaskService().addCandidateGroup(taskId, userName);
			}
		}else{
			//删除成员
			for (String userName : DelaUserName) {
				engineService.getTaskService().deleteCandidateGroup(taskId, userName);
			}
		}
	}

	@Override
	public InputStream getImageInputStream(String deploymentId,String imageName){
		return  engineService.getRepositoryService()
				.getResourceAsStream(deploymentId, imageName);
	}
	/**
	 * 下载流程图
	 */
	@Override
	public void downLoadImage(String deploymentId) throws Exception {
		List<String> names = engineService.getRepositoryService()
								.getDeploymentResourceNames(deploymentId);
		String imageName = null;
		for (String name : names) {
			if(names.indexOf(".png")>=0){
				imageName=name;
			}
		}
		if(imageName!=null){
			File f =new File("C:/"+imageName);
			InputStream in = getImageInputStream(deploymentId, imageName);
			FileUtils.copyInputStreamToFile(in, f);
		}

	}


	@Override
	public void viewImage(String deploymentId,String imageName,OutputStream out) throws Exception {
		String image=null;
		image=imageName;
		if(imageName==null){
			List<String> names = engineService.getRepositoryService()
					.getDeploymentResourceNames(deploymentId);
			for (String name : names) {
				if(names.indexOf(".png")>=0){
					image=name;
				}
			}
		}
		InputStream in = getImageInputStream(deploymentId, image);
		for(int b=-1;(b=in.read())!=-1;){
			out.write(b);
		}
		out.close();
		in.close();

	}

	@Override
	public List<String> findOutComeListByTaskId(String taskId) {
		//返回存放连线的名称集合
		List<String> list = new ArrayList<String>();
		//1.使用任务ID，查询任务对象
		Task task = engineService.getTaskService().createTaskQuery()
								.taskId(taskId).singleResult();
		//2.获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		//3.查询processDefinitionEntity对象
		ProcessDefinitionEntity processDefinitionEntity=(ProcessDefinitionEntity) engineService.getRepositoryService()
														.getProcessDefinition(processDefinitionId);
		//使用任务对象task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		org.activiti.engine.runtime.ProcessInstance pi= engineService.getRuntimeService().createProcessInstanceQuery()
							.processInstanceId(processInstanceId).singleResult();
		//获取当前活动的id
		String activityId = pi.getActivityId();
		//获取当前的活动
		ActivityImpl activityImpl=processDefinitionEntity.findActivity(activityId);
		//获取当前活动完成后的连线的名称
		List<PvmTransition> pvmList =activityImpl.getOutgoingTransitions();
		if(pvmList!=null && pvmList.size()>0){
			for (PvmTransition pvmTransition : pvmList) {
				String name =(String) pvmTransition.getProperty("name");
				if(StringUtils.isNotBlank(name)){
					list.add(name);
				}else{
					list.add("默认提交");
				}
			}
		}
		return list;
	}

	//添加批注信息
	@Override
	public void addCommentInfo(String taskId, String processInstanceId,
			String message) {
		engineService.getTaskService().addComment(taskId, processInstanceId, message);

	}
	//根据任务id获取历史任务对应的批注
	@Override
	public List<Comment> getCommentsByTaskId(String taskId) {
		List<Comment> list = new ArrayList<Comment>();
		//使用当前的任务id，查询当前流程对应的历史任务ID
		//使用当前任务ID，获取当前任务对象
		Task task = engineService.getTaskService().createTaskQuery()
					.taskId(taskId).singleResult();
		//获取流程实例ID
		String processInstanceId =task.getProcessInstanceId();

		/**
		 * 方式1：
		 */
		/*//使用流程实例ID，查询历史任务，获取历史任务对应的每个任务ID
		List<HistoricTaskInstance> hiList = engineService.getHistoryService().createHistoricTaskInstanceQuery()
											.processInstanceId(processInstanceId).list();
		//遍历集合，获取每个任务ID
		if(hiList!=null && hiList.size()>0){
			for (HistoricTaskInstance historicTaskInstance : hiList) {
				//任务ID
				String htaskId =historicTaskInstance.getId();
				//获取批注信息
				List<Comment> taskList = engineService.getTaskService().getTaskComments(htaskId);
				list.addAll(taskList);
			}
		}*/

		/**
		 * 方式2：
		 */
		list=engineService.getTaskService().getProcessInstanceComments(processInstanceId);
		return list;
	}




	//获取单个任务信息
	@Override
	public Task getTaskInfo(String taskId) {
		Task task=engineService.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		return task;
	}


	@Override
	public String getNextDealUser(Task task) {
		String processDefinitonId=task.getProcessDefinitionId();
		//1.根据当前任务获取当前流程的流程定义，然后根据流程定义获得所有的节点：
		ProcessDefinitionEntity def = (ProcessDefinitionEntity)
				engineService.getRepositoryService().getProcessDefinition(processDefinitonId);
		List<ActivityImpl> activitiList = def.getActivities();  //rs是指RepositoryService的实例
		//2.根据任务获取当前流程执行ID，执行实例以及当前流程节点的ID：
		String excId = task.getExecutionId();
		ExecutionEntity execution = (ExecutionEntity) engineService.getRuntimeService().createExecutionQuery().executionId(excId).singleResult();
		String activitiId = execution.getActivityId();
		//3.循环activitiList并判断出当前流程所处节点然后得到当前节点实例，
		//根据节点实例获取所有从当前节点出发的路径，然后根据路径获得下一个节点实例：
		for (ActivityImpl activityImpl : activitiList) {
			if("userTask".equals(activityImpl.getProperty("type")) && !activitiId.equals(activityImpl.getId())){
				String user=null;//workflowUtilService.getNextDealUser(task.getProcessDefinitionId(),  activityImpl.getId());
				return user;
			}
			/*if(activitiId.equals(activityImpl.getId())){
				//获取该节点出去的所有线路
				List<PvmTransition> outTransitions =activityImpl.getOutgoingTransitions();
				for (PvmTransition pvmTransition : outTransitions) {
					//获取线路的终点节点
					PvmActivity activity=pvmTransition.getDestination();
				}

			}*/
		}
		return null;
	}




	@Override
	public List<Task> getAllHistoryTaskByProcessDefinitionId(String processDefId) {
		//获取所有任务
		List<HistoricTaskInstance> taskList = engineService.getHistoryService().createHistoricTaskInstanceQuery().processDefinitionId(processDefId).orderByTaskCreateTime().asc().list();
		if(taskList!=null && taskList.size()!=0){
			for (HistoricTaskInstance historicTaskInstance : taskList) {
				List<HistoricVariableInstance> variableInstances=	engineService.getHistoryService().createHistoricVariableInstanceQuery().taskId(historicTaskInstance.getId()).list();
				for (HistoricVariableInstance historicVariableInstance : variableInstances) {

				}
			}
		}

		return null;
	}


	//获取所有请假流程步骤
	//public List<RequestMessage>
}
