package com.yabushan.activiti.service;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.yabushan.activiti.domain.DeployBean;
import com.yabushan.activiti.domain.ProcessInstance;
import com.yabushan.activiti.domain.RequestMessage;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;

public interface FlowService {
	/**部署流程*/
	public DeployBean saveInputStremDeploy(String processBpmn, String processPng, String rootPath);
	/**部署流程*/
	public DeployBean saveZipInputStreamDFeploy(String zipPath,String deployeName);


	public DeployBean saveNewDeploye(File file,String fileName);

	/**启动流程*/
	public ProcessInstance saveStartProcess(String userName, String businessId, String processDefinitionKey, Map<String, Object> variables);

	/**根据流程处理人查询正在执行的个人任务*/
	public List<Task> queryMyTasks(String userName);

	/**根据任务ID、指定下一个处理人、添加变量参数 完成任务*/
	public void saveFinishMyTask(RequestMessage requestMessage);

	/**查询所有流程定义*/
	public List<ProcessDefinition> queryDefinitions();

	/**查询最新版本的流程定义*/
	public List<ProcessDefinition> 	queryNewVersionDefinitions();

	/**删除流程*/
	public void deleteProcessDefinitonByDeploymentId(String deploymentId,boolean cascade);

	/**根据Key值删除该key对应的所有不同版本的流程定义*/
	public void deleteProcessDefinitionByKey(String processDefinitionKey);

	/**判断流程状态是否已经结束*/
	public boolean isProcessEnd(String processInstanceId);

	/**根据处理人查询历史任务*/
	public List<HistoricTaskInstance> getHistoricTaskInstancesByUserName(String userName);

	/**查询历史流程实例*/
	public HistoricProcessInstance getHistoricProcessInstanceById(String processInstanceId);
	public List<HistoricProcessInstance> getHistoricProcessInstanceByKey(String processDefinitionKey);
	public List<HistoricTaskInstance> getHistoricProcessInstanceByProcessDefId(String processInstanceId);


	/**设置流程变量*/
	public void setVariables(Map<String, String> mapParams,String taskId,boolean isBindTask);
	public <T> T setVariables(Class<T> classBean, String taskId,String title,boolean isBindTask);

	/**获取流程变量*/
	public String getVariables(String title,String taskId);
	public Object getVariable(String title, String taskId);

	/**查询历史流程变量（在流程结束后）*/
	public List<HistoricVariableInstance> getHistoricVariableInstancesByVariableName(String variableName);
	public List<HistoricVariableInstance> getHistoricVariableInstancesByProcessInstanceId(String processInstanceId);
	public List<HistoricVariableInstance> getHistoricVariableInstancesByTaskId(String taskId);
	public String getVariableLocal(String title, String taskId);
	/**查询历史活动*/
	public List<HistoricActivityInstance> getHistoricActivityInstances(String processInstanceId);

	/**查询历史任务*/
	public List<HistoricTaskInstance> getHistoricTaskInstances(String processInstanceId);

	/**
	 * 调换任务的处理人
	 * @param taskId
	 * @param dealUserId
	 */
	public void changeAssigneeTask(String taskId,String dealUserId);

	/**
	 * 查询当前人的组任务
	 * @param userName
	 * @return List<Task>
	 */
	public List<Task> getMyGroupTask(String userName);

	/**
	 * 查询任务的办理人列表
	 * @param taskId
	 * @return List<IdentityLink>
	 */
	public List<IdentityLink> getIdentityLinks(String taskId);

	/**
	 * 拾取任务，将组任务分配给某个人
	 * @param taskId 任务ID
	 * @param dealUserName 要分配给的处理人
	 */
	public void claim(String taskId,String dealUserName);


	/**
	 * 个人任务回退组任务
	 * @param taskId 任务ID
	 */
	public void backGroupTaskFromOwner(String taskId);

	/**
	 * 向组任务中添加成员
	 * @param taskId 任务ID
	 * @param DelaUserName 处理人
	 * @param flag ：add=添加，remove：删除
	 */
	public void addOrDeleteEmpToGroupTask(String taskId,List<String> DelaUserName,String flag);

	//使用部署对象ID和资源图片名称，获取图片的输入流
	public InputStream getImageInputStream(String deploymentId,String imageName);

	/**
	 * 查看流程附件
	 * @param deploymentId
	 * @throws Exception
	 */
	public void  downLoadImage(String deploymentId) throws Exception;

	/**
	 * 查看流程图
	 * @param deploymentId
	 * @param imageName
	 * @param out
	 * @throws Exception
	 */
	public void  viewImage(String deploymentId,String imageName,OutputStream out) throws Exception;

	/**
	 * 获取当前任务完成后的连线名称集合
	 * @param taskId
	 * @return
	 */
	public List<String> findOutComeListByTaskId(String taskId);

	//获取一个最新版本的实例
	public ProcessDefinition queryOneNewVersionDefinitions(String processDefinitionKey);
	//添加批注信息
	public void addCommentInfo(String taskId,String processInstanceId,String message);

	//根据任务id获取历史任务对应的批注
	public List<Comment> getCommentsByTaskId(String taskId);


	//根据taskId获取任务
	public Task getTaskInfo(String taskId);

	//获取下一步审批人信息
	public String getNextDealUser(Task task);

	//根据流程定义ID获取所有历史任务
	public List<Task> getAllHistoryTaskByProcessDefinitionId(String processDefId);

}
