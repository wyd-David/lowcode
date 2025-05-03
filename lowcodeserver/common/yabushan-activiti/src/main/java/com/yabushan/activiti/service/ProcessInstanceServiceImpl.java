package com.yabushan.activiti.service;

import com.alibaba.fastjson.JSON;
import com.yabushan.activiti.domain.NextStepAndUser;
import com.yabushan.activiti.util.Constant;
import com.yabushan.activiti.util.Utils;
import com.yabushan.common.core.domain.entity.SysDept;
import com.yabushan.common.core.domain.entity.SysRole;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.system.domain.*;
import com.yabushan.system.domain.vo.MyDeptVo;
import com.yabushan.system.domain.vo.SysUserVo;
import com.yabushan.system.service.*;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ProcessInstanceServiceImpl implements ProcessInstanceService{
	@Autowired
	private EngineService engineService;

	@Autowired
	private IServiceStepConfService serviceStepConfService;

	@Autowired
	private ISysUserService sysUserService;

	@Autowired
	private ISysRoleService roleService;

	@Autowired
	private VariablesService variablesService;

	@Autowired
	private ISysDeptService deptService;

	@Autowired
	private IRoleControlManagerService managerService;

	@Autowired
	private  IUumUserinfoService iUumUserinfoService;
	@Autowired
	private ISysPostService postService;


	@Override
	public ProcessInstance StartProcess(String userId, String businessId,
										String processDefinitionKey) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(Constant.REQUEST_USER, userId);//流程规则定义的发起人的ID
		variables.put(Constant.BUSINESS_ID, businessId);//业务主键ID
		variables.put(Constant.REQUEST_USER_NAME, SecurityUtils.getNickName());//发起人名称
		//使用流程定义的Key启动流程实例
		engineService.getIdentityService().setAuthenticatedUserId(userId);
		ProcessInstance pi = engineService.getRuntimeService()
				.startProcessInstanceByKey(processDefinitionKey,businessId,variables);
		return pi;
	}

	@Override
	public List<Task> queryMySingleTasks(String userId) {
		List<Task> list = engineService.getTaskService()
				.createTaskQuery().taskAssignee(userId)
				.orderByTaskCreateTime().asc()
				.list();
		return list;
	}

	@Override
	public List<Task> queryMyGroupTasks(String userId) {

		//	List<Task> list2 = engineService.getTaskService().createTaskQuery().taskCandidateUser("777").list();
		List<Task> list = engineService.getTaskService()
				.createTaskQuery()
				.taskCandidateUser(userId)
				.orderByTaskCreateTime().desc()
				.list();
		return list;
	}

	@Override
	public List<Task> getMyTasks(String userId) {
		List<Task> tasks = new ArrayList<Task>();
		tasks.addAll(queryMySingleTasks(userId));
		tasks.addAll(queryMyGroupTasks(userId));
		return tasks;

	}

	@Override
	public void claim(String taskId, String dealUser) {
		engineService.getTaskService().claim(taskId, dealUser);
	}

	@Override
	public void trunTask(String taskId, String dealUser) {
		engineService.getTaskService().setAssignee(taskId,dealUser);
	}

	@Override
	public ProcessInstance processInstanceStep(String processInstanceId) {
		ProcessInstance pi = engineService.getRuntimeService()
				.createProcessInstanceQuery()
				.processInstanceId(processInstanceId)
				.singleResult();
		return pi;
	}

	@Override
	public void completeTask(String taskId,String userId,String nextDealUser,String nextSeqFlow,String stepInfo) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("nextDealUser",nextDealUser);//流程中设置下一个审批人的占位符为：userName
		variables.put("message", nextSeqFlow);//设置下一步步骤
		//variables.put("opinion", requestMessage.getApproveOpinion());//保存流程实体
		engineService.getTaskService().setVariableLocal(taskId,Constant.STEP_INFO, stepInfo);
		//engineService.getTaskService().setVariable(requestMessage.getTaskId(), "message", requestMessage.getNextSeqFlow());
		claim(taskId,userId);
		engineService.getTaskService().complete(taskId, variables);

	}

	@Override
	public void changeAssigneeTask(String taskId, String dealUserId) {
		engineService.getTaskService().setAssignee(taskId, dealUserId);
	}

	@Override
	public List<IdentityLink> getIdentityLinks(String taskId) {
		List<IdentityLink> list = engineService.getTaskService()
				.getIdentityLinksForTask(taskId);
		return list;
	}
	@Override
	public List<NextStepAndUser> queryOutComeListByTaskId(String taskId) {
		NextStepAndUser nextStepAndUser =null;
		List<NextStepAndUser> list = new ArrayList<>();

		//1.使用任务ID，查询任务对象
		Task task = engineService.getTaskService().createTaskQuery()
				.taskId(taskId).singleResult();
		//2.获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		String requestUser = (String)variablesService.getVariablesByExcutionId("requestUser", task.getExecutionId(), false);
		//3.查询processDefinitionEntity对象
		ProcessDefinitionEntity processDefinitionEntity=(ProcessDefinitionEntity) engineService.getRepositoryService()
				.getProcessDefinition(processDefinitionId);
		//使用任务对象task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		//使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi= engineService.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		//获取当前活动的id
		String activityId = pi.getActivityId();
		//获取当前的活动
		ActivityImpl activityImpl=processDefinitionEntity.findActivity(activityId);
		if(activityImpl==null ){
			//如果为空，说明是多实例
			nextStepAndUser = new NextStepAndUser();
			nextStepAndUser.setOutLine("提交");
			list.add(nextStepAndUser);
			return list;
		}
		//获取当前活动完成后的连线的名称
		List<PvmTransition> pvmList =activityImpl.getOutgoingTransitions();
		List<PvmTransition> outTransitionsTemp = null;
		if(pvmList!=null && pvmList.size()>0){
			for (PvmTransition pvmTransition : pvmList) {
				if("exclusiveGateway".equals(pvmTransition.getDestination().getProperty("type"))){
					//如果流向线路为排他网关
					//获取网关的下一步流出
					outTransitionsTemp=pvmTransition.getDestination().getOutgoingTransitions();
					if(outTransitionsTemp.size()>0){
						//获取网关的输出节点
						/*for(int i=0;i<outTransitionsTemp.size();i++){
							nextStepAndUser = new NextStepAndUser();
							String name =(String) outTransitionsTemp.get(i).getProperty("name");//获取名称，如果是网关，那么输出线上的名称必须与网关输出的节点名称一致
							nextStepAndUser.setOutLine(name);
							nextStepAndUser.setNextStep(name);
							//获取环节办理人
							ServiceStepConf conf = new ServiceStepConf();
							conf.setProcdefId(task.getProcessDefinitionId());
							conf.setStepName(name);
							getUserInfo(conf,nextStepAndUser,requestUser);
							list.add(nextStepAndUser);

						}*/

						NextStepAndUser nextStepAndUser1 =new NextStepAndUser();
						String outLinkName =(String) pvmTransition.getProperty("name");
						//获取ID
						String outLinkId =pvmTransition.getId();
						nextStepAndUser1.setOutLineId(outLinkId);
						Object stepName = pvmTransition.getDestination().getProperty("name");//获取名称
						nextStepAndUser1.setOutLine(outLinkName);
						nextStepAndUser1.setNextStep(stepName+"");
						list.add(nextStepAndUser1);

					}
				}else{
					nextStepAndUser =new NextStepAndUser();
					String outLinkName =(String) pvmTransition.getProperty("name");
					Object stepName = pvmTransition.getDestination().getProperty("name");//获取名称

					String outLinkId =pvmTransition.getId();
					nextStepAndUser.setOutLineId(outLinkId);
					nextStepAndUser.setOutLine(outLinkName);
					nextStepAndUser.setNextStep(stepName.toString());
					//获取环节办理人
					ServiceStepConf conf = new ServiceStepConf();
					conf.setProcdefId(task.getProcessDefinitionId());
					conf.setStepName(stepName.toString());
					//List<ServiceStepConf> serviceStepConfs = serviceStepConfService.selectServiceStepConfList(conf);
					getUserInfo(conf,nextStepAndUser,requestUser);
					/*if(serviceStepConfs.size()>0){
						nextStepAndUser.setJumpStep(serviceStepConfs.get(0).getStepCode());
						nextStepAndUser.setNextUserId(serviceStepConfs.get(0).getDealUserId());
						nextStepAndUser.setNextUserName(serviceStepConfs.get(0).getDealUserName());
						nextStepAndUser.setNextUserRole(serviceStepConfs.get(0).getDealRoleName());
						nextStepAndUser.setNextUserRoleId(serviceStepConfs.get(0).getDealRoleId());
						nextStepAndUser.setNextDealType(serviceStepConfs.get(0).getDealType());
					}*/
					list.add(nextStepAndUser);

				}
			}
		}
		Collections.sort(list,NextStepAndUser::compareTo);
		return list;
	}

	@Override
	public Task queryTaskInfo(String taskId) {
		Task task=engineService.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		return task;
	}

	@Override
	public PvmActivity queryOutComeNameByTaskId(String taskId, String nextStepName) {
		PvmActivity destPa = null;
		// 1.使用任务ID，查询任务对象
		Task task = engineService.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		// 2.获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		// 3.查询processDefinitionEntity对象
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) engineService.getRepositoryService().getProcessDefinition(processDefinitionId);
		// 使用任务对象task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = engineService.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		// 获取当前活动的id
		String activityId = pi.getActivityId();
		// 获取当前的活动
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		// 获取当前活动完成后的连线的名称
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		if (pvmList != null && pvmList.size() > 0) {
			for (PvmTransition pvmTransition : pvmList) {
				String name = (String) pvmTransition.getProperty("name");
				if (StringUtils.isNotBlank(name) && name.equals(nextStepName)) {
					destPa = pvmTransition.getDestination();
					break;
				}
			}
		}
		return destPa;
	}

	@Override
	public void completeTask(String taskId,String userId,String nextDealUser,String nextNodeName,String nextSeqFlow,String stepInfo) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("nextDealUser",nextDealUser);//流程中设置下一个审批人的占位符为：userName
		variables.put("message", nextSeqFlow);//设置下一步步骤
		if(StringUtils.isNoneEmpty(nextNodeName)) {
			variables.put("nextNodeName", nextNodeName);//设置下一步任务名称
		}
		engineService.getCommentService().addCommentInfo(taskId,null,nextNodeName);
		//variables.put("opinion", requestMessage.getApproveOpinion());//保存流程实体
		engineService.getTaskService().setVariableLocal(taskId,Constant.STEP_INFO, stepInfo);
		//engineService.getTaskService().setVariable(requestMessage.getTaskId(), "message", requestMessage.getNextSeqFlow());
		claim(taskId,userId);
		engineService.getTaskService().complete(taskId, variables);

	}

	@Override
	public List<Task> queryMySingleTasks(String userId,String processInstanceId) {
		TaskQuery taskQuery = engineService.getTaskService().createTaskQuery();
		taskQuery.taskAssignee(userId);
		if(StringUtils.isNotEmpty(processInstanceId)) {
			taskQuery.processInstanceId(processInstanceId);
		}
		List<Task> list = taskQuery.orderByTaskCreateTime().asc().list();
		return list;
	}

	@Override
	public List<Task> queryMySingleTasksByProcessInstanceId(String processInstanceId) {
		TaskQuery taskQuery = engineService.getTaskService().createTaskQuery();
		//taskQuery.taskAssignee(userId);
		if(StringUtils.isNotEmpty(processInstanceId)) {
			taskQuery.processInstanceId(processInstanceId);
		}
		List<Task> list = taskQuery.orderByTaskCreateTime().asc().list();
		return list;
	}

	@Override
	public void completeTaskWithParams(String taskId, String userId, String nextDealUser, String nextSeqFlow, String stepInfo,String params) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("nextDealUser",nextDealUser);//流程中设置下一个审批人的占位符为：userName
		variables.put("message", nextSeqFlow);//设置下一步步骤
		engineService.getTaskService().setVariableLocal(taskId,Constant.STEP_INFO, stepInfo);
		//保存自定义参数
		if(Utils.IsNotEmpty(params)) {
			Map maps = (Map)JSON.parse(params);
			variables.putAll(maps);
		}
		claim(taskId,userId);
		engineService.getTaskService().complete(taskId, variables);
	}

	@Override
	public ProcessInstance starkFormTask(String processDefinitionId,String requestUserId, String businessKey, String variables) {
		ProcessInstance processInstance=null;
		Map request = JSON.parseObject(variables);
		request.put(Constant.REQUEST_USER,requestUserId);//流程规则定义的发起人的ID
		request.put(Constant.BUSINESS_ID, businessKey);//业务主键ID
		request.put(Constant.REQUEST_USER_NAME,requestUserId!="admin"?SecurityUtils.getNickName():"系统管理员");//发起人名称
		ProcessDefinition pd = engineService.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionId).latestVersion()
				.singleResult();

		engineService.getIdentityService().setAuthenticatedUserId(requestUserId);
		processInstance = engineService.getFormService().submitStartFormData(pd.getId(), businessKey, request);
		return  processInstance;
	}

	@Override
	public void completeFormTask(String taskId,String userId, Map formVariables, Map taskVariables,Map excutionVarialbes) {
		engineService.getFormService().saveFormData(taskId,formVariables);
		engineService.getTaskService().setVariablesLocal(taskId,taskVariables);
		Task task = engineService.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		engineService.getRuntimeService().setVariables(task.getExecutionId(),excutionVarialbes);
		claim(taskId,userId);
		//兼容子流程实例的情况
		if(taskVariables.containsKey("nextDealUserList")){
			List<String> candiateUserList=new ArrayList<String>();//创建多个子流程用的集合
			String strs[]=taskVariables.get("nextDealUserList").toString().split(",");//将1,2,3,4..拼接的科室code字符串解析到数组里
			for (String s : strs) {
				candiateUserList.add(s);//将科室code存入集合中
			}
			taskVariables.put("nextDealUserLists", candiateUserList);//多个子流程集合
		}
		engineService.getTaskService().complete(taskId, taskVariables);




	}

	@Override
	public void saveTask(String taskId, Map formVariables, Map taskVariables) {
		engineService.getFormService().saveFormData(taskId,formVariables);
		engineService.getTaskService().setVariablesLocal(taskId,taskVariables);

	}

	@Override
	public NextStepAndUser getNextStepAndUser(Task task) {
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl)engineService.getRepositoryService()).getDeployedProcessDefinition(task.getProcessDefinitionId());

		List<ActivityImpl> activitiList = def.getActivities();  //rs是指RepositoryService的实例
		String excId = task.getExecutionId();
		ExecutionEntity execution = (ExecutionEntity) engineService.getRuntimeService().createExecutionQuery().executionId(excId).singleResult();
		String activitiId = execution.getActivityId();

		for(ActivityImpl activityImpl:activitiList){
			String id = activityImpl.getId();
			if(activitiId.equals(id)){
				System.out.println("当前任务："+activityImpl.getProperty("name")); //输出某个节点的某种属性
				List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();//获取从某个节点出来的所有线路
				for(PvmTransition tr:outTransitions){
					PvmActivity ac = tr.getDestination(); //获取线路的终点节点
					System.out.println("下一步任务任务："+ac.getProperty("name"));
				}
				break;
			}
		}
		return null;
	}

	@Override
	public ProcessInstance StartProcessWithParams(String userId, String businessId, String processDefinitionKey,
												  String params) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(Constant.REQUEST_USER, userId);//流程规则定义的发起人的ID
		variables.put(Constant.BUSINESS_ID, businessId);//业务主键ID
		variables.put(Constant.REQUEST_USER_NAME, SecurityUtils.getNickName());//发起人名称
		//保存自定义参数
		if(Utils.IsNotEmpty(params)) {
			Map maps = (Map)JSON.parse(params);
			variables.putAll(maps);
		}
		//使用流程定义的Key启动流程实例
		engineService.getIdentityService().setAuthenticatedUserId(userId);
		ProcessInstance pi = engineService.getRuntimeService()
				.startProcessInstanceByKey(processDefinitionKey,businessId,variables);
		return pi;
	}

	private void getUserInfo(ServiceStepConf conf,NextStepAndUser nextStepAndUser,String assignee){
		List<ServiceStepConf> serviceStepConfs = serviceStepConfService.selectServiceStepConfList(conf);
		if(serviceStepConfs.size()>0){
			//如果有下一步，获取这部的返回人员信息
			if("JS".equals(serviceStepConfs.get(0).getDealType())){
				//按角色
				//获取角色人员信息
				//TODO
				SysRole sysRole = roleService.selectRoleById(Long.parseLong(serviceStepConfs.get(0).getDealRoleId()));
				List<SysUser> userRoles = sysUserService.getUserByRoleId(Long.parseLong(serviceStepConfs.get(0).getDealRoleId()));
				if(userRoles.size()>0){
					StringBuffer userIds = new StringBuffer();
					StringBuffer userNames = new StringBuffer();
					for (SysUser user:userRoles ) {
						userIds.append(user.getUserName()).append(",");
						//获取人员所在部门
						UumUserinfo uumUserinfo = iUumUserinfoService.selectUumUserinfoById(user.getUserName());
						userNames.append(user.getNickName());
						if(null!=uumUserinfo){
							userNames.append("(").append(uumUserinfo.getDepartment()).append(")");
						}
						userNames.append(",");
					}
					nextStepAndUser.setJumpStep(serviceStepConfs.get(0).getStepCode());
					nextStepAndUser.setNextUserId(userIds.substring(0, userIds.length()-1));
					nextStepAndUser.setNextUserName(userNames.substring(0, userNames.length()-1));
					nextStepAndUser.setNextUserRole(sysRole.getRoleName());
					nextStepAndUser.setNextUserRoleId(sysRole.getRoleId().toString());
					nextStepAndUser.setNextDealType(serviceStepConfs.get(0).getDealType());
				}

			}else if("RY".equals(serviceStepConfs.get(0).getDealType())){
				//按人员
				nextStepAndUser.setJumpStep(serviceStepConfs.get(0).getStepCode());
				nextStepAndUser.setNextUserId(serviceStepConfs.get(0).getDealUserId());
				nextStepAndUser.setNextUserName(serviceStepConfs.get(0).getDealUserName());
				nextStepAndUser.setNextUserRole(serviceStepConfs.get(0).getDealRoleName());
				nextStepAndUser.setNextUserRoleId(serviceStepConfs.get(0).getDealRoleId());
				nextStepAndUser.setNextDealType(serviceStepConfs.get(0).getDealType());
			}else if("SQR".equals(serviceStepConfs.get(0).getDealType())){
				//按申请人返回
				//TODO
					//1、根据申请人姓名获取申请人信息
				SysUser sysUser1 = sysUserService.selectUserByUserName(assignee);
				nextStepAndUser.setNextUserId(assignee);
				nextStepAndUser.setNextStep(serviceStepConfs.get(0).getStepName());

				//获取人员所在部门
				StringBuffer deptInfo = new StringBuffer();
				UumUserinfo uumUserinfo = iUumUserinfoService.selectUumUserinfoById(assignee);
				deptInfo.append(sysUser1.getNickName());
				if(null!=uumUserinfo){
					deptInfo.append("(").append(uumUserinfo.getDepartment()).append(")");
				}
				deptInfo.append(",");
				nextStepAndUser.setNextUserName(deptInfo.toString().substring(0,deptInfo.length()-1));

			}else if("LCJS".equals(serviceStepConfs.get(0).getDealType())){
				StringBuffer accountBuf = new StringBuffer();
				StringBuffer deptInfoBuf = new StringBuffer();
				//按流程角色获取审批人
				//TODO
				//1、获取申请人所在组织、及上级组织
				SysUser sysUser1 = sysUserService.selectUserByUserName(assignee);
				if(sysUser1!=null){
					Long deptId = sysUser1.getDeptId();
					SysDept sysDept = deptService.selectDeptById(deptId);
					String[] deptIds = sysDept.getAncestors().split("\\,");
					RoleControlManager roleControlManager = new RoleControlManager();
					roleControlManager.setRoleControlId(serviceStepConfs.get(0).getFlowRole());
					if(deptIds.length>0){
						//2、获取组织下的流程角色人员信息
						for(int j=deptIds.length-1;j>=0;j--){
							roleControlManager.setScope(deptIds[j]);
							List<RoleControlManager> roleControlManagers = managerService.selectRoleControlManagerList(roleControlManager);
							if(roleControlManagers.size()>0){
								for (RoleControlManager user:roleControlManagers ) {
									accountBuf.append(user.getUserId()).append(",");

									//获取人员所在部门
									UumUserinfo uumUserinfo = iUumUserinfoService.selectUumUserinfoById(user.getUserId());
									deptInfoBuf.append(user.getUserId());
									if(null!=uumUserinfo){
										deptInfoBuf.append("(").append(uumUserinfo.getDepartment()).append(")");
									}
									deptInfoBuf.append(",");

									//deptInfoBuf.append(user.getUserName()).append(",");
								}
								break;
							}
						}
					}
					//3、封装下一步处理人
					nextStepAndUser.setNextUserId(accountBuf.toString().substring(0,accountBuf.length()-1));
					nextStepAndUser.setNextStep(serviceStepConfs.get(0).getStepName());
					nextStepAndUser.setNextUserName(deptInfoBuf.toString().substring(0,deptInfoBuf.length()-1));
				}

			}else if("GWXX".equals(serviceStepConfs.get(0).getDealType())){
				StringBuffer accountBuf = new StringBuffer();
				StringBuffer deptInfoBuf = new StringBuffer();
				//按岗位信息获取审批人
				//TODO
				//1、获取申请人所在组织、及上级组织


				SysPost sysPost = postService.selectPostById(Long.parseLong(serviceStepConfs.get(0).getSystemRole()));
				if(sysPost != null){
					SysUser sysUser1 = null;
					//岗位注释，1代表获取申请人部门  2代表当前登录处理人部门
					if(StringUtils.isNotEmpty(sysPost.getRemark()) && sysPost.getRemark().contains("1")){
						sysUser1 = sysUserService.selectUserByUserName(assignee);
					}else if(StringUtils.isNotEmpty(sysPost.getRemark()) && sysPost.getRemark().contains("2")){
						sysUser1 = sysUserService.selectUserByUserName(SecurityUtils.getUsername());
					}
					if(sysUser1 != null){
						//2、获取组织下的流程角色人员信息
						UumUserinfo userinfo = iUumUserinfoService.selectByUserId(sysUser1.getUserName());
						UumUserinfo uumUserinfo = new UumUserinfo();
						uumUserinfo.setDepartmentid(userinfo.getDepartmentid());
						uumUserinfo.setEmployeeclass(sysPost.getPostCode());
						List<UumUserinfo> uumUserinfos = iUumUserinfoService.selectUumUserinfoList(uumUserinfo);
						for (UumUserinfo user:uumUserinfos ) {
							accountBuf.append(user.getUserid()).append(",");
							deptInfoBuf.append(user.getFullname());
							//获取部门
							deptInfoBuf.append("(")
									.append(user.getDepartment())
									.append(")");
							deptInfoBuf.append(",");
						}
						//3、封装下一步处理人
						nextStepAndUser.setNextUserId(accountBuf.toString().substring(0,accountBuf.length()-1));
						nextStepAndUser.setNextStep(serviceStepConfs.get(0).getStepName());
						nextStepAndUser.setNextUserName(deptInfoBuf.toString().substring(0,deptInfoBuf.length()-1));
					}

				}
			}
		}



	}


}
