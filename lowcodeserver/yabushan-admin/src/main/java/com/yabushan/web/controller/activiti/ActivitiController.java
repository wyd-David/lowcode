package com.yabushan.web.controller.activiti;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.yabushan.activiti.domain.*;
import com.yabushan.activiti.service.ActivitiService;
import com.yabushan.activiti.util.*;
import com.yabushan.common.annotation.Log;
import com.yabushan.common.core.controller.BaseController;
import com.yabushan.common.core.domain.AjaxResult;
import com.yabushan.common.enums.BusinessType;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.system.domain.vo.ActHiProcinstVo;
import com.yabushan.system.service.IActHiTaskinstService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/activiti")
@Slf4j
@Api(tags = "服务")
public class ActivitiController extends BaseController {

	//Logger log = Logger.getLogger(VacationController.class);

	/*
	 * @Inject(name="flowEngineService") private FlowService flowEngineService;
	 */
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private IActHiTaskinstService iActHiTaskinstService;






	/**
	 * 查询流程定义
	 * @param definitionKey
	 * @param isNew
	 * @return
	 */
	@PostMapping("/queryDefVersion")
	@ApiOperation(value = "2.查询流程定义")
	@Log(title = "查询流程定义",businessType = BusinessType.CLEAN)
	public  RestRspVO<List<ProcessDefinitionBean>> queryDefinition(String definitionKey, String isNew) {
		RestRspVO<List<ProcessDefinitionBean>> result = new RestRspVO<List<ProcessDefinitionBean>>();
		try{
			List<ProcessDefinitionBean> processDefinitionBeans = activitiService.queryDefinition(definitionKey, isNew);
			result.setCode(ErrMsgType.SUCCESS.getErrcode());
			result.setMessage(ErrMsgType.SUCCESS.getErrmsg());
			result.setResult(processDefinitionBeans);
		} catch (Exception e) {
			result.setCode(ErrMsgType.Fail.getErrcode());
			result.setMessage(ErrMsgType.Fail.getErrmsg());
			log.error(e.getMessage(), e);
		}
		return result;
	}
	/**
	 * 根据业务ID、用户账号，状态，去获取任务taskId信息
	 *
	 */
	@PostMapping("/selectByKeyUserList")
	@ApiOperation(value = "13.根据业务ID、用户账号，状态，去获取任务taskId信息")
	public AjaxResult selectByKeyUserList(ActHiProcinstVo actHiProcinstVo) {
		try {
			List<ActHiProcinstVo> actHiProcinstVoslist = iActHiTaskinstService.selectByKeyUserList(actHiProcinstVo);
			return AjaxResult.success(actHiProcinstVoslist);
		} catch (Exception e) {
			log.error(e.toString());
			return  AjaxResult.error(e.toString());
		}

	}

	/**
	 * 获取所有节点
	 *
	 * @param processDefId
	 */
	@PostMapping("/getAllFlowNode")
	@ApiOperation(value = "3.获取所有节点")
	public AjaxResult getAllFlowNode(String processDefId) {
		try {
			List<StepCodeName> list = activitiService.getAllFlowNode(processDefId);
			return AjaxResult.success(list);
		} catch (Exception e) {
			log.error(e.toString());
			return  AjaxResult.error(e.toString());
		}

	}


	@PostMapping("/startFormTask")
	@ApiOperation(value = "4.表单流程——启动任务" )
	@ApiOperationSupport(order = 1)
	public AjaxResult starkFormTask(String processDefinitionKey, String requestUserId, String businessKey, String variables, String isAutoFinishFirstStep){
		try {
			String s = activitiService.starkFormTask(processDefinitionKey, requestUserId, businessKey, variables, isAutoFinishFirstStep);
			return AjaxResult.success("成功",s);
		}catch (Exception e){
			return AjaxResult.success("失败",e.getMessage().toString());
		}
	}

	/**
	 * 根据流程环节名称获取初始化办理人信息
	 */
	@RequestMapping("/getDealUserByProcessInstanceId")
	@Log(title = "查询节点办理人信息", businessType = BusinessType.SELECT)
	@ApiOperation(value = "5.根据流程节点名称获取办理人信息")
	public AjaxResult getFirstDealUserByKey(String processKey, String userId, String stepName) {
		if(StringUtils.isEmpty(stepName)){
			return AjaxResult.error("请输入节点名称！");
		}
		try {
			NextStepAndUser firstDealUserByKey = activitiService.getFirstDealUserByKey(processKey, userId, stepName);
			return AjaxResult.success("成功！",firstDealUserByKey);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return AjaxResult.success("流程定义不存在");
		}

	}
	/**
	 * 获取我的待办任务
	 *
	 * @return
	 */
	@PostMapping("/getAllTask")
	@ApiOperation(value = "6.获取我的待办任务",position = 1)
	public AjaxResult queryAllTask(String userId) {
		if (Utils.IsEmpty(userId)) {
			return AjaxResult.error(ErrMsgType.START_USER_NULL.getErrmsg());
		}
		try {
			List<TaskInfoBean> taskInfoBeans = activitiService.queryAllTask(userId);
			Collections.sort(taskInfoBeans);//排序
			return AjaxResult.success("成功！",taskInfoBeans);
		} catch (Exception e) {
			log.error(e.toString());
			return AjaxResult.error("失败！",e.getMessage().toString());
		}

	}

	/**
	 * 根据TaskId获取当前任务以及下一步处理人
	 *
	 * @param taskId
	 * @return
	 */
	@PostMapping("/getOneTaskInfo")
	@ApiOperation(value = "7.根据TaskId获取当前任务以及下一步处理人")
	public AjaxResult getOneTaskInfo(String taskId) {
		RestRspVO<TaskInfo> result = new RestRspVO<TaskInfo>();
		try {
			TaskInfo oneTaskInfo = activitiService.getOneTaskInfo(taskId);
			return 	AjaxResult.success(oneTaskInfo);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return AjaxResult.error(e.getMessage().toString());
		}
	}

	/**
	 * 指派我的任务
	 *
	 * @param taskId
	 *            任务ID
	 * @param dealUser
	 *            指派处理人的userId
	 * @return
	 */
	@PostMapping("/claimMyTask")
	@ApiOperation(value = "8.指派我的任务")
	public AjaxResult claimMyTask(String taskId, String dealUser) {
		try {
			String s = activitiService.claimMyTask(taskId, dealUser);
			if(s==null){
				return  AjaxResult.success(taskId);
			}
		} catch (ActivitiObjectNotFoundException e1) {
			log.error(e1.toString());
			return AjaxResult.error(ErrMsgType.TASK_NOT_FOUND.getErrcode().toString());

		} catch (Exception e) {
			log.error(e.toString());
			return AjaxResult.error(ErrMsgType.Fail.getErrcode().toString());
		}
		return  AjaxResult.success();
	}

	/**
	 * 转办我的任务
	 *
	 * @param taskId
	 *            任务ID
	 * @param dealUser
	 *            指派处理人的userId
	 * @return
	 */
	@PostMapping("/trunTask")
	@ApiOperation(value = "9.转办我的任务")
	public AjaxResult trunTask(String taskId, String dealUser) {
		try {
			if (Utils.IsEmpty(taskId) || Utils.IsEmpty(dealUser)) {
				return AjaxResult.error("参数传入有空值，请检查！");
			}
			// 转办任务
			String s = activitiService.trunTask(taskId, dealUser);
			if(s==null){
				return  AjaxResult.success();
			}
		} catch (ActivitiObjectNotFoundException e1) {
			log.error(e1.toString());
			return AjaxResult.error("转办失败",e1);
		} catch (Exception e) {
			log.error(e.toString());
			return AjaxResult.error("转办失败",e.toString());
		}
		return AjaxResult.success();

	}
	/**
	 * 表单流程——保存任务表单
	 * @param taskId
	 * @param formVariables
	 * @param TaskVariables
	 * @return
	 */
	@PostMapping("/saveTask")
	@ApiOperation(value = "10.表单流程——保存任务表单")
	public AjaxResult saveTask(String taskId,String formVariables,String TaskVariables){
		RestRspVO<String> result = new RestRspVO<String>();
		result.setCode(ErrMsgType.SUCCESS.getErrcode());
		result.setMessage(ErrMsgType.SUCCESS.getErrmsg());
		if(formVariables==null){
			formVariables="{}";
		}
		if(TaskVariables==null){
			TaskVariables="{}";
		}
		try {
			 activitiService.saveTask(taskId, formVariables, TaskVariables);
			return AjaxResult.success("保存成功");
		}catch (Exception e){
			return AjaxResult.error(ErrMsgType.Fail.getErrmsg());
		}
	}


/*	@PostMapping("/fininshFormTask")
	@ApiOperation(value = "11.表单流程——完成任务")
	public AjaxResult fininshFormTask(String taskId,String userId,String formVariables, String TaskVariables,String excutionVariables){
		try {
			activitiService.fininshFormTask(taskId,userId,null,TaskVariables,null);
			return AjaxResult.success();
		}catch (Exception e){
			return AjaxResult.error(e.toString());
		}
	}*/

	@PostMapping("/fininshFormTask")
	@ApiOperation(value = "11.表单流程——完成任务")
	public AjaxResult fininshFormTask(String taskId, String TaskVariables){
		try {
			String s = activitiService.fininshFormTask(taskId,SecurityUtils.getUsername(), null, TaskVariables, null);
			return AjaxResult.success(s);
		}catch (Exception e){
			log.error(e.getMessage(),e);
			return AjaxResult.error(e.toString());
		}
	}

	/**
	 * 部署流程定义
	 *
	 * @return
	 */
	//@PostMapping("/deploy")
	//@ApiOperation(value = "部署流程定义")
	/*public RestRspVO<Deployment> deploy(String deploymentName, String DeployKey) {
		RestRspVO<Deployment> result = new RestRspVO<Deployment>();
		String bpmn = "";
		String png = "";
		if (DeployKey.equals(Constant.VACATION_PROCESS)) {
			// 二级经理正职请假流程
			bpmn = Constant.VACATION_FLOW_BPMN;
			png = Constant.VACATION_FLOW_PNG;
		} else if (DeployKey.equals(Constant.DEPUTY_PROCESS)) {
			// 二级经理副职请假流程
			bpmn = Constant.DEPUTY_FLOW_BPMN;
			png = Constant.DEPUTY_FLOW_PNG;
		} else if (DeployKey.equals(Constant.CANCEL_PROCESS)) {
			// 销假流程
			bpmn = Constant.CANCEL_FLOW_BPMN;
			png = Constant.CANCEL_FLOW_PNG;
		} else if (DeployKey.equals(Constant.VISIT_PROCESS)) {
			// 探亲假流程
			bpmn = Constant.VISIT_FLOW_BPMN;
			png = Constant.VISIT_FLOW_PNG;
		} else if (DeployKey.equals(Constant.JOBEVALUATION_PROCESS)) {
			// 协作满意度评分申请流程
			bpmn = Constant.JOBEVALUATION_FLOW_BPMN;
			png = Constant.JOBEVALUATION_FLOW_PNG;
		} else if (DeployKey.equals(Constant.TRIOUTOFPLAN_PROCESS)) {
			// 培训管理计划外培训管理流程
			bpmn = Constant.TRIOUTOFPLAN_FLOW_BPMN;
			png = Constant.TRIOUTOFPLAN_FLOW_PNG;
		} else {
			result.setCode(ErrMsgType.DEPLOY_FAIL.getErrcode());
			result.setMessage(ErrMsgType.DEPLOY_FAIL.getErrmsg());
			return result;
		}
		try {
			processDefinitionService.DeployByInputStream(deploymentName, bpmn, png, Constant.PROCESS_FILE_ROOT_PATH);
			result.setCode(ErrMsgType.DEPLOY_SUCCESS.getErrcode());
			result.setMessage(ErrMsgType.DEPLOY_SUCCESS.getErrmsg());
			// result.setResult(deployment);
		} catch (Exception e) {
			log.error(e.toString());
			result.setCode(ErrMsgType.DEPLOY_FAIL.getErrcode());
			result.setMessage(ErrMsgType.DEPLOY_FAIL.getErrmsg());
		}
		return result;
	}
*/

	/**
	 * 启动流程实例
	 *
	 *            用户ID
	 * @param businessId
	 *            业务主键ID
	 * @return
	 *         http://localhost:9088/leap_demo_app/hello/start?userName=发哥&businessId
	 *         =4
	 */
	//@PostMapping("/startFlow")
	//@ApiOperation(value = "启动流程实例")
	/*public RestRspVO<String> start(@RequestParam(value="processDefinitionKey",required = true)String processDefinitionKey, @RequestParam(value="userId",required = true)String userId, @RequestParam(required = true)String businessId, @RequestParam(required = true)String nextDealUser, String nextNodeName, String Info, String nextStep, String isJumpStep) {
		// if("".equals(nextStep) || "null".equals(nextDealUser) ||
		// null==nextStep){
		if ("1".equals(isJumpStep)) {
			// 如果是跳过步骤，则步骤名字取输入的，这里不变
		} else {
			nextStep = Constant.DEFULT_STEP;
		}

		RestRspVO<String> result = new RestRspVO<String>();
		if (Utils.IsEmpty(userId)) {
			result.setCode(ErrMsgType.START_USER_NULL.getErrcode());
			result.setMessage(ErrMsgType.START_USER_NULL.getErrmsg());
			return result;
		}
		if (Utils.IsEmpty(businessId)) {
			result.setCode(ErrMsgType.START_BUSINESS_NULL.getErrcode());
			result.setMessage(ErrMsgType.START_BUSINESS_NULL.getErrmsg());
			return result;
		}
		// 启动流程实例的同时，设置流程变量，使用流程变量来指定任务的办理人，对应Hello.pbmn文件中的#(userName)
		try {
			// 使用流程定义的Key启动流程实例
			org.activiti.engine.runtime.ProcessInstance processInstance = processInstanceService.StartProcess(userId, businessId, processDefinitionKey);
			// 获取当前任务,并执行
			List<Task> task = processInstanceService.getMyTasks(userId);
			boolean flag = false;
			for (Task t : task) {
				if (t.getProcessInstanceId().equals(processInstance.getProcessInstanceId())) {
					processInstanceService.completeTask(t.getId(), userId, nextDealUser,nextNodeName,nextStep, Info);
					flag = true;
					result.setResult(t.getProcessInstanceId());
				}
				if (flag)
					break;
			}
			result.setCode(ErrMsgType.START_SUCCESS.getErrcode());
			result.setMessage(ErrMsgType.START_SUCCESS.getErrmsg());

		} catch (Exception e) {
			log.error(e.toString());
			result.setCode(ErrMsgType.START_FAIL.getErrcode());
			result.setMessage(ErrMsgType.START_FAIL.getErrmsg());
		}
		return result;
	}*/




	/**
	 * 完成我的任务
	 *
	 * @param taskId
	 * @return
	 */
	//@PostMapping("/finishMyTask")
	//@ApiOperation(value = "完成我的任务")
	/*public RestRspVO<String> FinishMyTask(String processInstanceId, String taskId, String userId, String nextDealUserId,String nextNodeName, String stepInfo, String nextSeqFlow) {
		// RestRspVO<List<HistoryTaskInfo>> result =new
		// RestRspVO<List<HistoryTaskInfo>>();
		RestRspVO<String> result = new RestRspVO<String>();
		try {
			// nextSeqFlow=new String(nextSeqFlow.getBytes("utf-8"));
			if (Utils.IsEmpty(nextSeqFlow)) {
				nextSeqFlow = Constant.DEFULT_STEP;
			}
			// 完成任务
			processInstanceService.completeTask(taskId, userId, nextDealUserId, nextNodeName, nextSeqFlow, stepInfo);

			result.setCode(ErrMsgType.SUCCESS.getErrcode());
			result.setMessage(ErrMsgType.SUCCESS.getErrmsg());
			result.setResult(processInstanceId);
			*//*
	 * //获取流程记录 List<HistoryTaskInfo> list =
	 * vacationservice.getAllHistoryStep(processInstanceId, false, null,
	 * null); result.setResult(list);
	 *//*
		} catch (ActivitiObjectNotFoundException e1) {
			log.error(e1.toString());
			result.setCode(ErrMsgType.TASK_NOT_FOUND.getErrcode());
			result.setMessage(ErrMsgType.TASK_NOT_FOUND.getErrmsg());
		} catch (Exception e) {
			log.error(e.toString());
			result.setCode(ErrMsgType.Fail.getErrcode());
			result.setMessage(ErrMsgType.Fail.getErrmsg());
		}
		return result;
	}*/




	/**
	 * 根据流程实例ID获取所有流程信息
	 *
	 * @param Id
	 *            业务主键ID 获取流程实例ID
	 * @param flag
	 *            1：业务主键ID,2:流程实例ID
	 * @return
	 */
	@PostMapping("/getAllFlowStep")
	@ApiOperation(value = "12.根据流程实例ID获取所有流程信息，flag:1：业务主键ID,2:流程实例ID")
	public AjaxResult getAllFlowStepById(String Id, String flag) {
		List<HistoryTaskInfo> list = new ArrayList<HistoryTaskInfo>();
		if (Id == null || "".equals(Id)) {
			return AjaxResult.error("传入参数不正确！");
		}
		List<HistoryTaskInfo> allFlowStepById = activitiService.getAllFlowStepById(Id, flag);
		Integer myId=null;
		//多人处理的任务需要处理排序
		for(int i=0;i<allFlowStepById.size();i++){
			if(StringUtils.isEmpty(allFlowStepById.get(i).getHistoricTaskInstance().getDeleteReason()) ){
				//如果是没有处理的，则将该数据上移
				myId=i;
			}
		}
		if(myId==null){
			return AjaxResult.success(allFlowStepById);
		}else{
			list.add(allFlowStepById.get(myId));
			for(int i=0;i<allFlowStepById.size();i++){
				if(i==myId){
					continue;
				}
				list.add(allFlowStepById.get(i));
			}
			return  AjaxResult.success(list);
		}


	}

	/**
	 * 根据流程实例ID获取所有流程信息
	 *
	 * @param Id
	 *            业务主键ID 获取流程实例ID
	 * @param flag
	 *            1：业务主键ID,2:流程实例ID
	 * @return
	 */
	@PostMapping("/getAllFlowStepMany")
	@ApiOperation(value = "12.根据流程实例ID获取所有流程信息，flag:1：业务主键ID,2:流程实例ID")
	public AjaxResult getAllFlowStepMany(String Id, String flag) {
		List<HistoryTaskInfo> list = new ArrayList<HistoryTaskInfo>();
		if (Id == null || "".equals(Id)) {
			return AjaxResult.error("传入参数不正确！");
		}
		List<HistoryTaskInfo> allFlowStepById = activitiService.getAllFlowStepById(Id, flag);
		int myId =0;
		Boolean flags =false;
		if(allFlowStepById.size()>0){
			for(int i=0;i<allFlowStepById.size();i++){
				if(StringUtils.isEmpty(allFlowStepById.get(i).getHistoricTaskInstance().getDeleteReason()) &&
						SecurityUtils.getUsername().equals(allFlowStepById.get(i).getHistoricTaskInstance().getAssignee())){
						//如果当前环节和办理人一致，则将该数据放到最上边
						myId=i;
						flags=true;

				}else{

				}
			}
			//
			if(!flags){
				for(int i=0;i<allFlowStepById.size();i++){
					if(StringUtils.isEmpty(allFlowStepById.get(i).getHistoricTaskInstance().getDeleteReason()) ){
						myId=i;
					}
				}
			}
			list.add(allFlowStepById.get(myId));
			for(int i=0;i<allFlowStepById.size();i++){
				if(i==myId){
					continue;
				}
				list.add(allFlowStepById.get(i));
			}

			return AjaxResult.success(list);
		}


		return AjaxResult.success(allFlowStepById);
	}


	/**
	 * 根据流程实例ID获取历史流程步骤
	 *
	 * @param processInstanceId
	 * @return
	 */
	@ApiOperation(value = "13.根据流程实例ID获取历史流程步骤")
	@PostMapping("/getStep")
	public AjaxResult getAllHistoryStep(String processInstanceId) {
		try {
			// 获取流程记录
			List<HistoryTaskInfo> list = activitiService.getAllHistoryStep(processInstanceId);
			return AjaxResult.success(list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return AjaxResult.error(e.toString());
		}
	}


	/**
	 * 自定义流程
	 * 获取指定流程的 我的待办任务
	 * newAtdV1.0
	 * @param userId
	 * @param processInstanceId 流程ID
	 * @return
	 */
	@ApiOperation(value = "14.根据流程ID，获取我的待办任务")
	@PostMapping("/getMyTaskByProId")
	public AjaxResult queryMyTaskByProId(String userId, String processInstanceId) {
		if (Utils.IsEmpty(userId)) {
			return AjaxResult.error("userId参数不能为空!");
		}
		try {
			List<TaskInfoBean> taskInfoBeans = activitiService.queryMyTaskByProId(userId, processInstanceId);
			return AjaxResult.success(taskInfoBeans);
		} catch (Exception e) {
			log.error(e.toString());
			return AjaxResult.error(e.toString());
		}

	}


	/**
	 * 表单流程——获取启动流程表单信息
	 * @param processDefinitionId
	 * @return
	 */
	@PostMapping("/getStartFormData")
	@ApiOperation(value = "15.表单流程——获取启动流程表单信息")
	public AjaxResult getStartFormData(String processDefinitionId){
		List<FormInfo> startFormData = activitiService.getStartFormData(processDefinitionId);
		return AjaxResult.success("成功",startFormData);
	}


	/**
	 * 表单流程——获取任务表单信息
	 * @param taskId
	 * @return
	 */
	@PostMapping("/getTaskFormData")
	@ApiOperation(value = "16.表单流程——获取任务表单信息")
	public AjaxResult getTaskFormData(String taskId) {
		TaskFormDataImp taskFormData = activitiService.getTaskFormData(taskId);
		return AjaxResult.success(taskFormData);
	}



	/**
	 * 获取我的历史办理任务
	 * @param userId
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	@ApiOperation(value = "17.获取我的已办任务")
	@PostMapping("/getMyFinishTask")
	public AjaxResult getMyFinishTask(String userId,Integer firstResult,Integer maxResult) {
		RestRspVO<List<HistoryTaskInfo>> result = new RestRspVO<List<HistoryTaskInfo>>();
		try {
			// 获取流程记录
			List<HistoryTaskInfo> myFinishTask = activitiService.getMyFinishTask(userId, firstResult, maxResult);
			return AjaxResult.success(myFinishTask);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return AjaxResult.error(e.getMessage().toString());
		}
	}


	/**
	 *
	 * @param request
	 * @param response
	 * @param businessKey
	 * @throws Exception
	 */
	@GetMapping("/queryProImg")
	@ApiOperation(value = "18.获取流程图")
	public void queryProImg(HttpServletRequest request, HttpServletResponse response,String businessKey ) throws Exception {
		// 输出资源内容到相应对象
		InputStream imageStream=activitiService.queryProImg(businessKey);
		//FileOutputStream output=new FileOutputStream(new File("d:/test123.png"));
		// 输出资源内容到相应对象
		try {
			//IOUtils.copy(imageStream, output);
			byte[] b = new byte[1024];
			int len;
			response.setContentType("image/png");
			while ((len = imageStream.read(b, 0, 1024)) != -1) {
				response.getOutputStream().write(b, 0, len);
			}
		}finally {
			imageStream.close();
			response.getOutputStream().close();
		}
	}

	/**
	 * 跳转到指定任务节点
	 * @author:yabushan
	 *  @param dkey:流程定义key,businessKey:业务id,stepId:环节ID（startEvent、endEvent）
	 */
	@ApiOperation(value = "19.跳转到指定任务节点")
	@PostMapping("/stopRunProcessInstance")
	public AjaxResult jumpRunProcessInstanceTask(String dkey,String businessKey,String stepId,String taskUserName){
		RestRspVO<String> result = new RestRspVO<String>();
		result.setCode(ErrMsgType.SUCCESS.getErrcode());
		result.setMessage(ErrMsgType.SUCCESS.getErrmsg());
		try {
			activitiService.jumpRunProcessInstanceTask(dkey,businessKey,stepId,taskUserName);
			return AjaxResult.success("操作成功");
		}catch (Exception e){
			return AjaxResult.error(e.toString());
		}
	}




}
