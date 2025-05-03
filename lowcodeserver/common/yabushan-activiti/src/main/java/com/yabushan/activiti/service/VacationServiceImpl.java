package com.yabushan.activiti.service;

import com.yabushan.activiti.domain.HistoryTaskInfo;
import com.yabushan.activiti.domain.NextStepAndUser;
import com.yabushan.activiti.domain.WorkflowConstants;
import com.yabushan.activiti.util.Constant;
import com.yabushan.common.core.domain.entity.SysDept;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.system.domain.*;
import com.yabushan.system.domain.vo.SysUserVo;
import com.yabushan.system.mapper.TodoInfoMapper;
import com.yabushan.system.service.*;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class VacationServiceImpl implements  VacationService{

	@Autowired
	private HistoryQueryService historyQueryService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private EngineService engineService;
	@Autowired
	private workflowUtilService workflowUtilService;
	@Autowired
	private VariablesService variablesService;
	@Autowired
	private ProcessDefinitionService processDefinitionService;
	@Autowired
	private IServiceStepConfService serviceStepConfService;
	@Autowired
	private ISysUserService sysUserService;
	@Autowired
	private ISysPostService postService;

	@Autowired
	private ISysDeptService deptService;

	@Autowired
	private IRoleControlManagerService managerService;

	@Autowired
	private TodoInfoMapper todoInfoMapper;
	@Autowired
	private  IUumUserinfoService iUumUserinfoService;

	/**
	 * 实现类
	 */
	@Autowired
	private CustomProcessDiagramGeneratorService customProcessDiagramGeneratorService;

	@Override
	public List<HistoryTaskInfo> getAllHistoryStep(String processInstanceId, boolean isFinish, Integer firstResult, Integer maxResults) {
		List<HistoryTaskInfo> result =new ArrayList<HistoryTaskInfo>();
		//获取所有历史任务
		List<HistoricTaskInstance> list=historyQueryService.getHistoricTaskInstances(processInstanceId, isFinish, firstResult, maxResults);
		result = getHistoryInfo(list);
		return result;
	}

	public List<HistoryTaskInfo> getHistoryInfo(List<HistoricTaskInstance> list) {
		HistoryTaskInfo historyTaskInfo = null;
		List<HistoryTaskInfo> result = new ArrayList<HistoryTaskInfo>();


		if (list != null && list.size() > 0) {
			//遍历所有历史任务
			for (HistoricTaskInstance historicTaskInstance : list) {


				historyTaskInfo = new HistoryTaskInfo();
				historyTaskInfo.setHistoricTaskInstance(historicTaskInstance);
				//封装流程变量_未结束的流程
				Map<String, Object> taskObjectMap = historyQueryService.packageVariables(false,historicTaskInstance.getProcessInstanceId(), historicTaskInstance.getId());
				historyTaskInfo.setVarialbes(taskObjectMap);

				//封装批注信息
				List<Comment> comments = commentService.getCommentByTaskId(historicTaskInstance.getId());
				if (comments != null && comments.size() > 0) {
					historyTaskInfo.setTaskComment(comments);
				} else {
					historyTaskInfo.setTaskComment(null);
				}

				if("jump".equals(historicTaskInstance.getDeleteReason())){
					//已撤回
					TodoInfo todoInfo= new TodoInfo();
					todoInfo.setTaskId(historicTaskInstance.getId());
					List<TodoInfo> todoInfos = todoInfoMapper.selectTodoInfoList(todoInfo);
					if(todoInfos.size()>0){
						historicTaskInstance.setLocalizedName(historicTaskInstance.getName()+"-"+todoInfos.get(0).getRealDealUserName());
					}
				}

				//封装表单变量
				Map<String, Object> stringObjectMap = historyQueryService.packageVariables(true,historicTaskInstance.getProcessInstanceId(), historicTaskInstance.getId());
				historyTaskInfo.setFormVarialbes(stringObjectMap);
				result.add(historyTaskInfo);
			}
		}

		return result;
	}
	/**
	 * 流程是否已经结束
	 *
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	public boolean isFinished(String processInstanceId) {
		return engineService.getHistoryService().createHistoricProcessInstanceQuery().finished()
				.processInstanceId(processInstanceId).count() > 0;
	}

	@Override
	public InputStream queryProImg(String businessKey) {
		//查询历史实例
		List<HistoricProcessInstance> list = engineService.getHistoryService()
				.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey)
				.list();
		if (list.size() == 0) {
			return null;
		}
		//processInstanceId
		String processInstanceId = list.get(0).getId();
		String processDefinitionId = list.get(0).getProcessDefinitionId();
		//获取历史流程实例
		//获取流程图
		BpmnModel bpmnModel = engineService.getRepositoryService().getBpmnModel(processDefinitionId);
		ProcessEngineConfiguration processEngineConfiguration = engineService.getProcessEngineConfiguration();
		Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

		ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) engineService.getRepositoryService().getProcessDefinition(processDefinitionId);

		List<HistoricActivityInstance> highLightedActivitList = engineService.getHistoryService().createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
		//高亮环节id集合
		List<String> highLightedActivitis = new ArrayList<>();
		//高亮线路id集合
		List<String> highLightedFlows = this.getHighLightedFlows(definitionEntity, highLightedActivitList);

		for (HistoricActivityInstance tempActivity : highLightedActivitList) {
			String activityId = tempActivity.getActivityId();
			highLightedActivitis.add(activityId);
		}

		// 第一种方式，单独返回流程图，不高亮显示
		//InputStream imageStream = diagramGenerator.generatePngDiagram(bpmnModel);

		//第二重方式，ProcessDiagramGenerator 对象显示,中文显示的是口口口，设置字体就好了
        /*
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        InputStream inputStream = diagramGenerator.generateDiagram(
                bpmnModel,
                "png",
                highLightedActivitis,
                highLightedFlows,
                "宋体",
                "宋体",
                "宋体",
                processEngineConfiguration.getClassLoader(),
                1.0);*/

		// 第三种方式，需要重写ProcessDiagramGenerator的generateDiagram方法
		Set<String> currIds = engineService.getRuntimeService().createExecutionQuery().processInstanceId(processInstanceId).list()
				.stream().map(e -> e.getActivityId()).collect(Collectors.toSet());
		// 高亮图需要展示审批不通过高亮，需要将节点ID放入currIds集合中
		InputStream imageStream = customProcessDiagramGeneratorService.generateDiagram(bpmnModel, "png", highLightedActivitis,
				highLightedFlows, "宋体", "宋体", "宋体",
				null, 1.0,
				new Color[]{WorkflowConstants.COLOR_NORMAL, WorkflowConstants.COLOR_CURRENT}, currIds);
		return imageStream;
	}


	@Override
	public NextStepAndUser getFirstUser(String processKey, String userId, String empType, String processDefId, ProcessDefinitionEntity pd) {
		return null;
	}

	@Override
	public NextStepAndUser getFirstUser(String processKey, String userId, String empType ) {
		//根据流程key获取流程节点
		ServiceStepConf serviceStepConf=new ServiceStepConf();

		ProcessDefinition processDefinition = processDefinitionService.queryDefinitionsByDefinitionKey(processKey,true).get(0);
		serviceStepConf.setProcdefId(processDefinition.getKey());
		NextStepAndUser nextStepAndUser=new NextStepAndUser();
		List<ServiceStepConf> serviceStepConfs = serviceStepConfService.selectServiceStepConfList(serviceStepConf);
		//获取当前处理人所在组织   *暂时注释
		/*UumUserinfo uumUserinfo = new UumUserinfo();
		uumUserinfo.setLoginId(userId);*/
		//---------修改
		SysUserVo userVo = new SysUserVo();
		userVo.setUserName(userId);
		SysUser sysUser1 = sysUserService.selectUserByUserName(userId);
		userVo.setUserId(sysUser1.getUserId());
		//List<UumUserinfo> uumUserinfos1 = uumUserinfoService.selectUumUserinfoList(uumUserinfo);
		for (int i=0;i<serviceStepConfs.size();i++){
			ServiceStepConf step =serviceStepConfs.get(i);
			if(empType.equals(step.getStepName())){
				//判断该节点的审批类型
				if(("RY").equals(step.getDealType())){
					//具体到人
					nextStepAndUser.setNextUserId(step.getDealUserId());
					nextStepAndUser.setNextStep(step.getStepName());
					nextStepAndUser.setNextUserName(step.getDealUserName());

				}else if("SJ".equals(step.getDealType())){
					//直接上级
					nextStepAndUser.setNextUserId(step.getDealUserId());
					nextStepAndUser.setNextStep(step.getStepName());
					nextStepAndUser.setNextUserName(step.getDealUserName());


				}else if("SQR".equals(step.getDealType())){
					SysUser sysUser = sysUserService.selectUserByUserName(userId);
					nextStepAndUser.setNextUserId(sysUser.getUserName());
					nextStepAndUser.setNextStep(step.getStepName());
					nextStepAndUser.setNextUserName(sysUser.getNickName());
				}else if("JS".equals(step.getDealType())){
					//按角色
					List<SysUser> userByRoleId = sysUserService.getUserByRoleId(Long.parseLong(step.getDealRoleId()));
					StringBuffer accountBuf = new StringBuffer();
					StringBuffer deptInfoBuf = new StringBuffer();
					for (SysUser user : userByRoleId){
						accountBuf.append(user.getUserName()).append(",");
						UumUserinfo uumUserinfo = iUumUserinfoService.selectUumUserinfoById(user.getUserName());
						deptInfoBuf.append(user.getNickName());
						if(null!=uumUserinfo){
							deptInfoBuf.append("(").append(uumUserinfo.getDepartment()).append(")");
						}
						deptInfoBuf.append(",");
					}
					nextStepAndUser.setNextUserId(accountBuf.toString().substring(0,accountBuf.length()-1));
					nextStepAndUser.setNextStep(step.getStepName());
					nextStepAndUser.setNextUserName(deptInfoBuf.toString().substring(0,deptInfoBuf.length()-1));
				}else if("LCJS".equals(step.getDealType())){
					StringBuffer accountBuf = new StringBuffer();
					StringBuffer deptInfoBuf = new StringBuffer();
					//按流程角色获取审批人
					//TODO
						//1、获取申请人所在组织、及上级组织
						if(sysUser1!=null){
							Long deptId = sysUser1.getDeptId();
							SysDept sysDept = deptService.selectDeptById(deptId);
							String[] deptIds = sysDept.getAncestors().split("\\,");
							RoleControlManager  roleControlManager = new RoleControlManager();
							roleControlManager.setRoleControlId(serviceStepConf.getFlowRole());
							if(deptIds.length>0){
								//2、获取组织下的流程角色人员信息
								for(int j=deptIds.length-1;j>=0;j--){
									roleControlManager.setScope(deptIds[j]);
									List<RoleControlManager> roleControlManagers = managerService.selectRoleControlManagerList(roleControlManager);
									if(roleControlManagers.size()>0){
										for (RoleControlManager user:roleControlManagers ) {
											accountBuf.append(user.getUserId()).append(",");
											deptInfoBuf.append(user.getUserName());
											//获取部门
											UumUserinfo uumUserinfo = iUumUserinfoService.selectUumUserinfoById(user.getUserId());
											if(null!=uumUserinfo){
												deptInfoBuf.append("(")
														.append(uumUserinfo.getDepartment())
														.append(")");
											}
											deptInfoBuf.append(",");
										}
										//只找最近的一个组织下的人员
										break;
									}
								}
								//3、封装下一步处理人
								nextStepAndUser.setNextUserId(accountBuf.toString().substring(0,accountBuf.length()-1));
								nextStepAndUser.setNextStep(step.getStepName());
								nextStepAndUser.setNextUserName(deptInfoBuf.toString().substring(0,deptInfoBuf.length()-1));
							}
						}
				}else if("GWXX".equals(step.getDealType())){
					StringBuffer accountBuf = new StringBuffer();
					StringBuffer deptInfoBuf = new StringBuffer();
					//按岗位信息获取审批人
					//TODO
					//1、获取申请人所在组织、及上级组织

					SysPost sysPost = postService.selectPostById(Long.parseLong(step.getSystemRole()));
					if(sysPost != null){
						SysUser sysUser = null;
						//岗位注释，1代表获取申请人部门  2代表当前登录处理人部门
						if(StringUtils.isNotEmpty(sysPost.getRemark()) && sysPost.getRemark().contains("1")){
							sysUser = sysUserService.selectUserByUserName(sysUser1.getUserName());
						}else if(StringUtils.isNotEmpty(sysPost.getRemark()) && sysPost.getRemark().contains("2")){
							sysUser = sysUserService.selectUserByUserName(SecurityUtils.getUsername());
						}
						if(sysUser != null){
							//2、获取组织下的流程角色人员信息
							UumUserinfo userinfo = iUumUserinfoService.selectByUserId(sysUser.getUserName());
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
							nextStepAndUser.setNextStep(step.getStepName());
							nextStepAndUser.setNextUserName(deptInfoBuf.toString().substring(0,deptInfoBuf.length()-1));
						}
					}
				}
				break;
			}
		}
		return nextStepAndUser;
	}

	//根据用户ID获取部门信息



	@Override
	public String getRequestUserId(String taskId) {
		//获取流程发起人
		String requestUser =variablesService.getVariablesByTaskId(Constant.REQUEST_USER, taskId, false).toString();
		return requestUser;
	}


	/**
	 *
	 * @param list List<ActivityImpl>
	 * @param nodeId 用户节点ID
	 * @param isJumpStep 标识是否跳环节，1：是
	 * @return
	 */
	public NextStepAndUser getNextStepAndUserByNodeId(List<ActivityImpl> list,String nodeId,String isJumpStep){
		NextStepAndUser nextStepAndUser2 = new NextStepAndUser();
		for (ActivityImpl act1 : list) {
			if(nodeId.equals(act1.getId())){//公司总经理审批
				nextStepAndUser2.setNextStep(act1.getProperty("name").toString());
				nextStepAndUser2.setJumpStep(isJumpStep);//是否跳过步骤，1：是
				return nextStepAndUser2;
			}
		}
		return null;
	}


	/**
	 * 获取需要高亮的线
	 *
	 * @param processDefinitionEntity   节点
	 * @param historicActivityInstances 高亮点
	 */
	private List<String> getHighLightedFlows(
			ProcessDefinitionEntity processDefinitionEntity,
			List<HistoricActivityInstance> historicActivityInstances) {
		List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId
		for (int i = 0; i < historicActivityInstances.size() - 1; i++) {// 对历史流程节点进行遍历
			ActivityImpl activityImpl = processDefinitionEntity
					.findActivity(historicActivityInstances.get(i)
							.getActivityId());// 得到节点定义的详细信息
			List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需开始时间相同的节点
			ActivityImpl sameActivityImpl1 = processDefinitionEntity
					.findActivity(historicActivityInstances.get(i + 1)
							.getActivityId());
			// 将后面第一个节点放在时间相同节点的集合里
			sameStartTimeNodes.add(sameActivityImpl1);
			for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
				HistoricActivityInstance activityImpl1 = historicActivityInstances
						.get(j);// 后续第一个节点
				HistoricActivityInstance activityImpl2 = historicActivityInstances
						.get(j + 1);// 后续第二个节点
				if (activityImpl1.getStartTime().equals(
						activityImpl2.getStartTime())) {
					// 如果第一个节点和第二个节点开始时间相同保存
					ActivityImpl sameActivityImpl2 = processDefinitionEntity
							.findActivity(activityImpl2.getActivityId());
					sameStartTimeNodes.add(sameActivityImpl2);
				} else {
					// 有不相同跳出循环
					break;
				}
			}
			List<PvmTransition> pvmTransitions = activityImpl
					.getOutgoingTransitions();// 取出节点的所有出去的线
			for (PvmTransition pvmTransition : pvmTransitions) {
				// 对所有的线进行遍历
				ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition
						.getDestination();
				// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
				if (sameStartTimeNodes.contains(pvmActivityImpl)) {
					highFlows.add(pvmTransition.getId());
				}
			}
		}
		return highFlows;
	}


	/**
	 *
	 * @param userId 用户ID
	 * @return
	 */
	public NextStepAndUser getNextStepAndUser(String userId){

		return null;
	}


}
