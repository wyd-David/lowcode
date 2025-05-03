package com.yabushan.activiti.service;

import com.alibaba.fastjson.JSON;
import com.yabushan.activiti.domain.*;
import com.yabushan.activiti.util.*;
import com.yabushan.common.core.domain.entity.SysUser;
import com.yabushan.common.utils.DateUtils;
import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.common.utils.StringUtils;
import com.yabushan.common.utils.spring.SpringUtils;
import com.yabushan.system.domain.ActXunchaProcinst;
import com.yabushan.system.domain.TodoInfo;
import com.yabushan.system.domain.TodoInfoPush;
import com.yabushan.system.domain.ViewEmpInfo;
import com.yabushan.system.domain.vo.TodoInfoParam;
import com.yabushan.system.service.*;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.form.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;

@Service
@Slf4j
public class ActivitiService {
    @Autowired
    private ProcessDefinitionService processDefinitionService;

    @Autowired
    private ProcessInstanceService processInstanceService;

    @Autowired
    private HistoryQueryService historyQueryService;

    @Autowired
    private FlowUtils flowUtils;
    @Autowired
    private workflowUtilService workflowUtilService;
    @Autowired
    private VacationService vacationService;

    @Autowired
    private FormService formService;

    @Autowired
    private JumpTaskCmdService jumpTaskCmdService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ITodoInfoService todoInfoService;
    @Autowired
    private ITodoInfoPushService iTodoInfoPushService;

    @Autowired
    private VariablesService variablesService;
    @Autowired
    private IViewEmpInfoService viewEmpInfoService;



    /**
     * 应用的凭证密钥
     */
    @Value("${domain.xunchaApi}")
    private String domain;
   /* @Value("${domain.xzfwPc}")
    private String domainPC;*/

    public List<ProcessDefinitionBean> queryDefinition(String definitionKey, String isNew) {
        Boolean isOnlyNew = true;
        if ("false".equals(isNew)) {
            isOnlyNew = false;
        }

        List<ProcessDefinitionBean> definitionBeanList = new ArrayList<ProcessDefinitionBean>();
        ProcessDefinitionBean processDefinitionBean = null;
        List<ProcessDefinition> list = processDefinitionService.queryDefinitionsByDefinitionKey(definitionKey, isOnlyNew);
        // flowEngineService.queryDefinitions();
        if (list != null && list.size() > 0) {
            for (ProcessDefinition processDefinition : list) {
                processDefinitionBean = new ProcessDefinitionBean();
                processDefinitionBean.setProcessDefinitionId(processDefinition.getId());
                processDefinitionBean.setProcessDefinitionKey(processDefinition.getKey());
                processDefinitionBean.setProcessDefinitionName(processDefinition.getName());
                processDefinitionBean.setProcessDefinitionResourceBpmn(processDefinition.getResourceName());
                processDefinitionBean.setProcessDefinitionResourcePng(processDefinition.getDiagramResourceName());
                processDefinitionBean.setProcessDefinitionVersion(processDefinition.getVersion());
                processDefinitionBean.setProcessDeploymentId(processDefinition.getDeploymentId());
                definitionBeanList.add(processDefinitionBean);
            }
        }
        return definitionBeanList;
    }


    public List<StepCodeName> getAllFlowNode(String processDefId) {
        List<StepCodeName> list = workflowUtilService.getJbpmNode(processDefId);
        return list;

    }

    @Transactional(rollbackFor = {Exception.class})
    public String starkFormTask(String processDefinitionKey, String requestUserId, String businessKey, String variables, String isAutoFinishFirstStep) {
        if (StringUtils.isNull(variables) || variables.equals("")) {
            variables = "{}";
        }
        ProcessInstance processInstance = processInstanceService.starkFormTask(processDefinitionKey, requestUserId, businessKey, variables);
        if ("1".equals(isAutoFinishFirstStep)) {
            List<Task> tasks = processInstanceService.queryMySingleTasks(requestUserId, processInstance.getProcessInstanceId());
            if (tasks.size() != 1) {
                String re = "流程启动成功，无法完成第一步！获取到的流程任务数为：" + tasks.size() + ">>>" + tasks.toString();
                //log.error(re, new Exception(re));
                return null;
            } else {
                // fininshFormTask(tasks.get(0).getId(),requestUserId,variables);
                Map request = JSON.parseObject(variables);
                request.put("processInstanceId", processInstance.getId());
                processInstanceService.completeFormTask(tasks.get(0).getId(), requestUserId, request, request, request);
                /**
                 * 封装给申请人自己的一条已办
                 */
                TodoInfo todoInfoFist = new TodoInfo();
                todoInfoFist.setTodoId(StringUtils.getUUID());
                todoInfoFist.setBusinessKey(businessKey);
                todoInfoFist.setTodoTitle(request.get("title").toString());
                todoInfoFist.setApplyId(request.get("applyId").toString());
                todoInfoFist.setTodoText(request.get("content").toString());
                todoInfoFist.setTodoApplicant(requestUserId);
                String urlParam=request.get("urlParam").toString();
                if(StringUtils.isNotEmpty(urlParam) && !"null".equals(urlParam)  && !"".equals(urlParam)){
                    if(!urlParam.startsWith("?")){
                        urlParam="?"+urlParam+"&";
                    }else{
                        urlParam=urlParam+"&";
                    }
                }
                else{
                    urlParam="?";
                }
                todoInfoFist.setTodoUrl(request.get("todoUrl").toString() + urlParam + "todoInfoId=" + todoInfoFist.getTodoId());

                todoInfoFist.setPcUrl(domain+request.get("pcTodoUrl").toString() + urlParam +"applyId="+todoInfoFist.getApplyId()+ "&todoInfoId=" + todoInfoFist.getTodoId());
                todoInfoFist.setTodoHandler(requestUserId);
                String nickName = userService.selectUserByUserName(requestUserId).getNickName();
                todoInfoFist.setTodoHandlerName(nickName);
                todoInfoFist.setRealDealUser(requestUserId);
                todoInfoFist.setRealDealUserName(nickName);
                todoInfoFist.setModifyTime(new Date());
                //根据业务ID获取当前正在进行的环节
                List<HistoryTaskInfo> allHistoryStep = getAllHistoryStep(tasks.get(0).getProcessInstanceId());
                if (allHistoryStep.size() > 0) {
                    for (HistoryTaskInfo stepInfo : allHistoryStep
                    ) {
                        if (StringUtils.isNotEmpty(stepInfo.getHistoricTaskInstance().getDeleteReason()) &&
                                "completed".equals(stepInfo.getHistoricTaskInstance().getDeleteReason())) {
                            //当前任务，获取当前任务的名称信息
                            todoInfoFist.setTodoTpLink(stepInfo.getHistoricTaskInstance().getName());
                            todoInfoFist.setTodoType("1");//已处理
                            todoInfoFist.setTodoDataType("2");//已办
                            todoInfoFist.setPushStatus("1");//推送状态：未推送
                            todoInfoFist.setTaskType("1");//任务类型，1：工作流待办
                            todoInfoFist.setTaskId(stepInfo.getHistoricTaskInstance().getId());

                            //插入待办
                            todoInfoService.insertTodoInfo(todoInfoFist);

                            iTodoInfoPushService.insertTodoInfoPushByTodoInfo(todoInfoFist,true,"");
                        }

                    }
                }


                /**
                 * 发送待办
                 */
                //获取流程办理人数据信息
                //获取下一步办理人
                TodoInfo todoInfo = null;
                String nextDealUser = (String) request.get("nextDealUser");
                String[] userIdInfo = null;
                if(StringUtils.isEmpty(nextDealUser) || "".equals(nextDealUser) || "null".equals(nextDealUser)){
                    String nextDealUserList = (String) request.get("nextDealUserList");
                    userIdInfo = nextDealUserList.split(",");
                } else {
                    userIdInfo = nextDealUser.split(",");
                }
                if (userIdInfo.length > 0) {
                    for (String userLoginId : userIdInfo) {
                        todoInfo = new TodoInfo();
                        todoInfo.setCreateTime(new Date());
                        todoInfo.setTodoId(StringUtils.getUUID());
                        todoInfo.setBusinessKey(businessKey);
                        todoInfo.setTodoTitle(request.get("title").toString());
                        todoInfo.setApplyId(request.get("applyId").toString());
                        todoInfo.setTodoText(request.get("content").toString());
                        todoInfo.setTodoApplicant(requestUserId);
                        todoInfo.setTodoUrl(request.get("todoUrl").toString() + urlParam + "todoInfoId=" + todoInfo.getTodoId());
                        todoInfo.setPcUrl(domain+request.get("pcTodoUrl").toString() + urlParam +"applyId="+todoInfo.getApplyId()+ "&todoInfoId=" + todoInfo.getTodoId());
                        todoInfo.setTodoHandler(userLoginId);
                        todoInfo.setTodoHandlerName(userService.selectUserByUserName(userLoginId).getNickName());
                        //根据业务ID获取当前正在进行的环节
                        //List<HistoryTaskInfo> allHistoryStep = getAllHistoryStep(tasks.get(0).getProcessInstanceId());
                        if (allHistoryStep.size() > 0) {
                            for (HistoryTaskInfo stepInfo : allHistoryStep
                            ) {
                                if (StringUtils.isEmpty(stepInfo.getHistoricTaskInstance().getDeleteReason())) {
                                    //当前任务，获取当前任务的名称信息
                                    todoInfo.setTodoTpLink(stepInfo.getHistoricTaskInstance().getName());
                                    todoInfo.setTodoType("0");//未处理
                                    todoInfo.setTodoDataType("1");//待办
                                    todoInfo.setPushStatus("1");//推送状态：未推送
                                    todoInfo.setTaskType("1");//任务类型，1：工作流待办
                                    todoInfo.setTaskId(stepInfo.getHistoricTaskInstance().getId());
                                    //插入待办
                                    todoInfoService.insertTodoInfo(todoInfo);

                                    iTodoInfoPushService.insertTodoInfoPushByTodoInfo(todoInfo,false,"");
                                }

                            }
                        }
                    }
                }

            }
        }
        //草稿状态的，只给自己发一条待办数据
        else {
            List<Task> tasks = processInstanceService.queryMySingleTasks(requestUserId, processInstance.getProcessInstanceId());
            if (tasks.size() != 1) {
                String re = "流程启动成功，无法完成第一步！获取到的流程任务数为：" + tasks.size() + ">>>" + tasks.toString();
                //log.error(re, new Exception(re));
                return null;
            } else {
                Map request = JSON.parseObject(variables);
                /**
                 * 封装给申请人自己的一条已办
                 */
                TodoInfo todoInfoFist = new TodoInfo();
                todoInfoFist.setTodoId(StringUtils.getUUID());
                todoInfoFist.setBusinessKey(businessKey);
                todoInfoFist.setTodoTitle(request.get("title").toString());
                todoInfoFist.setApplyId(request.get("applyId").toString());
                todoInfoFist.setTodoText(request.get("content").toString());
                todoInfoFist.setTodoApplicant(requestUserId);
                String urlParam=request.get("urlParam").toString();
                if(StringUtils.isNotEmpty(urlParam) && !"null".equals(urlParam)  && !"".equals(urlParam)){
                    if(!urlParam.startsWith("?")){
                        urlParam="?"+urlParam+"&";
                    }else{
                        urlParam=urlParam+"&";
                    }
                }
                else{
                    urlParam="?";
                }
                todoInfoFist.setTodoUrl(request.get("todoUrl").toString() + urlParam + "todoInfoId=" + todoInfoFist.getTodoId());
                todoInfoFist.setPcUrl(domain+request.get("pcTodoUrl").toString() + urlParam +"applyId="+todoInfoFist.getApplyId()+ "&todoInfoId=" + todoInfoFist.getTodoId());
                todoInfoFist.setTodoHandler(requestUserId);
                String nickName = userService.selectUserByUserName(requestUserId).getNickName();
                todoInfoFist.setTodoHandlerName(nickName);
                todoInfoFist.setRealDealUser(requestUserId);
                todoInfoFist.setRealDealUserName(nickName);
                todoInfoFist.setModifyTime(new Date());
                //根据业务ID获取当前正在进行的环节
                List<HistoryTaskInfo> allHistoryStep = getAllHistoryStep(tasks.get(0).getProcessInstanceId());
                if (allHistoryStep.size() > 0) {
                    for (HistoryTaskInfo stepInfo : allHistoryStep
                    ) {
                            //当前任务，获取当前任务的名称信息
                            todoInfoFist.setTodoTpLink(stepInfo.getHistoricTaskInstance().getName());
                            todoInfoFist.setTodoType("0");//未处理
                            todoInfoFist.setTodoDataType("1");//待办
                            todoInfoFist.setPushStatus("1");//推送状态：未推送
                            todoInfoFist.setTaskType("1");//任务类型，1：工作流待办
                            todoInfoFist.setTaskId(stepInfo.getHistoricTaskInstance().getId());
                            //插入待办
                            todoInfoService.insertTodoInfo(todoInfoFist);
                            //iTodoInfoPushService.insertTodoInfoPushByTodoInfo(todoInfoFist,true,"");

                    }
                }
            }
        }
        IActXunchaProcinstService actXunchaProcinstService = SpringUtils.getBean(IActXunchaProcinstService.class);
        Map variablesMap = JSON.parseObject(variables);
        ActXunchaProcinst actXunchaProcinst = new ActXunchaProcinst();
        actXunchaProcinst.setBusinessKey(businessKey);
        actXunchaProcinst.setBusinessName(String.valueOf(variablesMap.get("title")));
        actXunchaProcinst.setBusinessType(String.valueOf(variablesMap.get("applyId")));
        actXunchaProcinst.setBusinessTypeName(String.valueOf(variablesMap.get("applyName")));
        actXunchaProcinstService.insertActXunchaProcinst(actXunchaProcinst);
        return processInstance.getId();
    }

    public NextStepAndUser getFirstDealUserByKey(String processKey, String userId, String stepName) {
        return vacationService.getFirstUser(processKey, userId, stepName);

    }

    public List<TaskInfoBean> queryAllTask(String userId) {
        List<Task> list = new ArrayList<Task>();
        List<TaskInfoBean> taskinfo = new ArrayList<TaskInfoBean>();
        // 个人任务
        List<Task> taskList = processInstanceService.queryMySingleTasks(userId);
        if (taskList != null && taskList.size() > 0) {
            list.addAll(taskList);
        }
        // 组任务
        List<Task> groupTaskList = processInstanceService.queryMyGroupTasks(userId);
        if (groupTaskList != null && groupTaskList.size() > 0) {
            list.addAll(groupTaskList);
        }

        for (Task task : list) {
            taskinfo.add(flowUtils.returnTaskInfo(task));
        }
        return taskinfo;

    }


    public TaskInfo getOneTaskInfo(String taskId) {
        TaskInfo taskInfo = new TaskInfo();
        // 获取所有流出线
        List<NextStepAndUser> outLineList = processInstanceService.queryOutComeListByTaskId(taskId);
        // 获取任务
        Task task = processInstanceService.queryTaskInfo(taskId);
        // 根据当前任务获取下一步任务处理人
        List<NextStepAndUser> nextDealUser = null;// vacationService.getNextStepAndUser(task);
        // 获取当前任务处理人
        List<IdentityLink> identityLinks = processInstanceService.getIdentityLinks(taskId);
        StringBuffer buf = new StringBuffer();
        String currentuserId = null;
        if (identityLinks != null && identityLinks.size() != 0) {
            for (IdentityLink identityLink : identityLinks) {
                buf.append(identityLink.getUserId()).append(Constant.SEPARATOR);
            }
        }
        if (StringUtils.isNotEmpty(buf)) {
            currentuserId = buf.toString().substring(0, buf.toString().length() - 1);
            taskInfo.setCurrentDealUserId(currentuserId);
        }

        taskInfo.setNextStepAndUser(outLineList);
        taskInfo.setTaskInfoBean(flowUtils.returnTaskInfo(task));
        taskInfo.setAssignee(vacationService.getRequestUserId(taskId));
        return taskInfo;

    }

    public String claimMyTask(String taskId, String dealUser) {

        if (Utils.IsEmpty(taskId) || Utils.IsEmpty(dealUser)) {
            return "参数传入有空值，请检查！";
        }
        // 指派任务
        processInstanceService.claim(taskId, dealUser);
        return null;
    }

    public String trunTask(String taskId, String dealUser) {
        if (Utils.IsEmpty(taskId) || Utils.IsEmpty(dealUser)) {
            return "参数传入有空值，请检查！";
        }
        // 转办任务
        processInstanceService.trunTask(taskId, dealUser);
        return null;
    }

    @Transactional(rollbackFor = {Exception.class})
    public String saveTask(String taskId, String formVariables, String TaskVariables) {
        if (formVariables == null) {
            formVariables = "{}";
        }
        if (TaskVariables == null) {
            TaskVariables = "{}";
        }
        Map formVariablesMap = JSON.parseObject(formVariables);
        Map TaskVariablesMap = JSON.parseObject(TaskVariables);
        processInstanceService.saveTask(taskId, formVariablesMap, TaskVariablesMap);
        return null;
    }

    @Transactional(rollbackFor = {Exception.class})
    public String fininshFormTask(String taskId, String userId, String formVariables, String TaskVariables, String excutionVariables) {
        if (StringUtils.isEmpty(formVariables) || "".equals(formVariables) || formVariables.length() == 0 || formVariables == null) {
            formVariables = "{}";
        }
        if (StringUtils.isEmpty(TaskVariables) || "".equals(TaskVariables) || TaskVariables.length() == 0 || TaskVariables == null) {
            TaskVariables = "{}";
        }
        if (StringUtils.isEmpty(excutionVariables) || "".equals(excutionVariables) || excutionVariables.length() == 0 || excutionVariables == null) {
            excutionVariables = "{}";
        }
        Map formVariablesMap = JSON.parseObject(formVariables);
        Map TaskVariablesMap = JSON.parseObject(TaskVariables);
        if(TaskVariablesMap!=null ){
            if(TaskVariablesMap.containsKey("btnMessage")){
                //如果是重新提交将btnMessage的值赋值给message
                if(TaskVariablesMap.get("message").equals("ttt重新提交")){
                    TaskVariablesMap.put("message",TaskVariablesMap.get("btnMessage"));
                }

            }
        }
        Map excutionVariablesMaps = JSON.parseObject(excutionVariables);
        processInstanceService.completeFormTask(taskId, userId, formVariablesMap, TaskVariablesMap, excutionVariablesMaps);

        //处理待办
        Map map = buildTodoInfo("NORMAL", taskId, TaskVariables, null, null);
        return JSON.toJSONString(map);
    }

    @Transactional(rollbackFor = {Exception.class})
    public String autoFininshFormTask(String taskId, String userId, String formVariables, String TaskVariables, String excutionVariables, String actUserId, String actUserName) {
        if (StringUtils.isEmpty(formVariables) || "".equals(formVariables) || formVariables.length() == 0 || formVariables == null) {
            formVariables = "{}";
        }
        if (StringUtils.isEmpty(TaskVariables) || "".equals(TaskVariables) || TaskVariables.length() == 0 || TaskVariables == null) {
            TaskVariables = "{}";
        }
        if (StringUtils.isEmpty(excutionVariables) || "".equals(excutionVariables) || excutionVariables.length() == 0 || excutionVariables == null) {
            excutionVariables = "{}";
        }
        Map formVariablesMap = JSON.parseObject(formVariables);
        Map TaskVariablesMap = JSON.parseObject(TaskVariables);
        Map excutionVariablesMaps = JSON.parseObject(excutionVariables);
        processInstanceService.completeFormTask(taskId, userId, formVariablesMap, TaskVariablesMap, excutionVariablesMaps);

        //处理待办
        Map map = buildTodoInfo("auto", taskId, TaskVariables, actUserId, actUserName);
        return JSON.toJSONString(map);
    }


    /**
     * @param type          null:正常办理，auto：定时器自动办理，需要传入人员信息，上下文无法获取到当前用户信息
     * @param taskId
     * @param TaskVariables
     * @param actUserId
     * @param actUserName
     * @return
     */
    private Map buildTodoInfo(String type, String taskId, String TaskVariables, String actUserId, String actUserName) {
        String currentStep = null;
        Map result = new HashMap();
        //根据任务ID获取任务人员信息
        TodoInfo todoInfo = new TodoInfo();
        todoInfo.setTaskId(taskId);
        List<TodoInfo> todoInfos = todoInfoService.selectTodoInfoList(todoInfo);
        if (todoInfos.size() > 0) {
            for (TodoInfo info : todoInfos
            ) {
                if (type != null && !"auto".equals(type)) {
                    //遍历该任务的所有待办，如果待办处理人与当前处理人为同一个人，则设置待办为已处理
                    if (info.getTodoHandler().equals(SecurityUtils.getUsername())) {
                        info.setTodoType("1");//已处理
                    } else {
                        //其他人的待办设置为已取消
                        info.setTodoType("2");//已取消
                    }
                    info.setTodoDataType("2");//已办
                    info.setModifyTime(new Date());
                    String  pcurlDo=info.getPcUrl();

                    info.setPcUrl(pcurlDo);

                    // 将当前处理人更改为改任务的实际审批人
                    info.setRealDealUserName(SecurityUtils.getNickName());
                    info.setRealDealUser(SecurityUtils.getUsername());
                } else {
                    /**
                     * 定时器传入人员自动启动结束流程
                     */
                    //遍历该任务的所有待办，如果待办处理人与当前处理人为同一个人，则设置待办为已处理
                    if (info.getTodoHandler().equals(actUserId)) {
                        info.setTodoType("1");//已处理
                    } else {
                        //其他人的待办设置为已取消
                        info.setTodoType("2");//已取消
                    }
                    info.setTodoDataType("2");//已办
                    info.setModifyTime(new Date());
                    // 将当前处理人更改为改任务的实际审批人
                    info.setRealDealUserName(actUserName);
                    info.setRealDealUser(actUserId);
                }

                //更新待办
                todoInfoService.updateTodoInfo(info);

                //插入推送待办记录表
                iTodoInfoPushService.insertTodoInfoPushByTodoInfo(info,false,taskId);
            }


            //获取下一步审批人，遍历账号生成下一步审批人的待办
            //TODO
            //发送待办
            //获取流程办理人数据信息
            //获取下一步办理人
            Map request = JSON.parseObject(TaskVariables);
            String nextDealUser = (String) request.get("nextDealUser");
            String nextDealUserList=null;
            String[] userIdInfo = null;
            Boolean flag = true;//区别是否多实例
            Boolean isTodo =true;//判断是否可提交待办
            if(StringUtils.isEmpty(nextDealUser) || "".equals(nextDealUser) || "null".equals(nextDealUser)){
                nextDealUserList =(String) request.get("nextDealUserList");
                if(StringUtils.isEmpty(nextDealUserList) || "".equals(nextDealUserList) || "null".equals(nextDealUserList)){
                    isTodo=false;
                }else{
                    userIdInfo = nextDealUserList.split(",");
                    flag= false;//多实例节点
                }

            }else{
                userIdInfo = nextDealUser.split(",");
            }
            if (isTodo) {
                // userIdInfo = nextDealUser.trim().split("\\,");
                //根据业务ID获取当前正在进行的环节
                List<HistoryTaskInfo> allHistoryStep = getAllFlowStepById(todoInfos.get(0).getBusinessKey(), "1");
                for (String userLoginId : userIdInfo) {
                    if (StringUtils.isEmpty(userLoginId)) {
                        continue;
                    }
                    todoInfo = new TodoInfo();
                    todoInfo.setCreateTime(new Date());
                    todoInfo.setTodoId(StringUtils.getUUID());
                    todoInfo.setBusinessKey(todoInfos.get(0).getBusinessKey());
                    todoInfo.setTodoTitle(todoInfos.get(0).getTodoTitle());
                    todoInfo.setApplyId(todoInfos.get(0).getApplyId());
                    todoInfo.setTodoText(todoInfos.get(0).getTodoText());
                    todoInfo.setTodoApplicant(todoInfos.get(0).getTodoApplicant());
                    if (request.containsKey("todoUrl") && request.containsKey("urlParam")) {
                        todoInfo.setTodoUrl(request.get("todoUrl").toString() + request.get("urlParam").toString() + "&todoInfoId=" + todoInfo.getTodoId());
                    } else {
                        String todoUrl=todoInfos.get(0).getTodoUrl();
                        int i = todoUrl.indexOf("&todoInfoId");
                        if(i!=-1){
                            todoUrl=todoUrl.substring(0,i);
                        }
                        // todoUrl= todoUrl.replace("todoInfoId","oldurl");
                        todoUrl= todoUrl+ "&todoInfoId=" + todoInfo.getTodoId();
                        todoInfo.setTodoUrl(todoUrl);
                    }

                    //  if (request.containsKey("pcTodoUrl") && request.containsKey("urlParam")) {
                    //  todoInfo.setPcUrl(request.get("pcTodoUrl").toString() + request.get("urlParam").toString() + "&todoInfoId=" + todoInfo.getTodoId());
                    //} else {
                    String pcurl=todoInfos.get(0).getPcUrl();
                    int i = pcurl.indexOf("&todoInfoId");
                    if (i != -1){
                        pcurl=pcurl.substring(0,i).toString();
                    }
                    // pcurl= pcurl.replace("todoInfoId","oldurl");
                    pcurl=pcurl+ "&todoInfoId=" + todoInfo.getTodoId();
                    todoInfo.setPcUrl(pcurl);
                    //  }

                    todoInfo.setTodoHandler(userLoginId);
                    todoInfo.setTodoHandlerName(userService.selectUserByUserName(userLoginId).getNickName());

                    if (allHistoryStep.size() > 0) {
                        for (HistoryTaskInfo stepInfo : allHistoryStep
                        ) {

                            if (StringUtils.isEmpty(stepInfo.getHistoricTaskInstance().getDeleteReason())) {
                                if(!flag){
                                    //如果是多实例，则需要对应上人才可以插入待办
                                    if(!userLoginId.equals(stepInfo.getHistoricTaskInstance().getAssignee())){
                                        continue;
                                    }
                                }else{
                                    //如果这个历史轨迹已经有人了，则跳过
                                    if(!StringUtils.isEmpty(stepInfo.getTodoUserLoginId())){
                                        continue;
                                    }
                                }
                                //当前任务，获取当前任务的名称信息
                                currentStep = stepInfo.getHistoricTaskInstance().getName();
                                todoInfo.setTodoTpLink(currentStep);
                                todoInfo.setTodoType("0");//未处理
                                todoInfo.setTodoDataType("1");//待办
                                todoInfo.setPushStatus("1");//推送状态：未推送
                                todoInfo.setTaskType("1");//任务类型，1：工作流待办
                                todoInfo.setTaskId(stepInfo.getHistoricTaskInstance().getId());
                                //插入待办
                                todoInfoService.insertTodoInfo(todoInfo);


                                //插入推送待办记录表
                                iTodoInfoPushService.insertTodoInfoPushByTodoInfo(todoInfo,false,taskId);


                                //插入申请单表
                                IActXunchaProcinstService actXunchaProcinstService = SpringUtils.getBean(IActXunchaProcinstService.class);
                                ActXunchaProcinst actXunchaProcinst = new ActXunchaProcinst();
                                actXunchaProcinst.setBusinessKey(todoInfo.getBusinessKey());
                                actXunchaProcinst.setBusinessName(todoInfo.getTodoTitle());
                                actXunchaProcinst.setBusinessType(todoInfo.getApplyId());
                                actXunchaProcinst.setBusinessTypeName(todoInfo.getTodoApplicantName());
                                actXunchaProcinst.setStatus(todoInfo.getTodoTpLink());
                                actXunchaProcinstService.insertActXunchaProcinst(actXunchaProcinst);

                            }

                        }
                    }
                }
            }
        }
        //获取当前环节
        result.put("currentStep", currentStep);

        return result;

    }


    public List<HistoryTaskInfo> getAllFlowStepById(String Id, String flag) {
        List<HistoryTaskInfo> list = new ArrayList<HistoryTaskInfo>();
        List<HistoricProcessInstance> historicProcessInstances = new ArrayList<HistoricProcessInstance>();
        if (Id == null || "".equals(Id)) {
            return null;
        } else {
            if ("1".equals(flag)) {
                // 根据业务主键ID获取历史任务
                // 需要根据业务ID获取所有流程实例
                historicProcessInstances = historyQueryService.getHistoricProcessInstanceByKey(Id, "bid", false, null, null);
                for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
                    list.addAll(vacationService.getAllHistoryStep(historicProcessInstance.getId(), false, null, null));
                }
            } else if ("2".equals(flag)) {
                // 根据流程实例ID获取任务
                list = vacationService.getAllHistoryStep(Id, false, null, null);
            } else if ("3".equals(flag)) {
                //根据待办任务ID 获取任务
                //1、根据待办任务ID获取业务ID
                TodoInfo todoInfo = todoInfoService.selectTodoInfoById(Id);
                //2、根据业务ID获取流程实例
                historicProcessInstances = historyQueryService.getHistoricProcessInstanceByKey(todoInfo.getBusinessKey(), "bid", false, null, null);
                for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
                    list.addAll(vacationService.getAllHistoryStep(historicProcessInstance.getId(), false, null, null));
                }

            }
            boolean isEnd =true;//是否需要添加结束节点
            if (list.size() != 0) {
                for (int i = 0; i < list.size(); i++) {
                    //优化开始时间开始时间
                    String start = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, list.get(i).getHistoricTaskInstance().getStartTime());
                    list.get(i).setStartDateTime(start);
                    if (StringUtils.isEmpty(list.get(i).getHistoricTaskInstance().getDeleteReason())) {
                        isEnd=false;
                        list.get(i).setSORT(1);//设置排序
                        StringBuffer userInfo = new StringBuffer();

                        //当前任务节点
                        List<IdentityLink> identityLinks = processInstanceService.getIdentityLinks(list.get(i).getHistoricTaskInstance().getId());
                        if(null!= identityLinks && identityLinks.size()>0 ){
                            TodoInfo todoInfo =new TodoInfo();
                            todoInfo.setTaskId(identityLinks.get(0).getTaskId());
                            List<TodoInfo> todoList = todoInfoService.selectTodoInfoList(todoInfo);
                            if(null!= todoList && todoList.size()>0){
                                for(TodoInfo info :todoList){
                                    if("0".equals(info.getTodoType())&&"1".equals(info.getTodoDataType())){
                                        if(!userInfo.toString().contains(info.getTodoHandlerName())) {
                                            userInfo.append(info.getTodoHandlerName())
                                                    .append(",");
                                        }
                                    }
                                }
                            }

                        }
//                        for (int j = 0; j < identityLinks.size(); j++) {
//                            todoInfoService.selectMyTodoInfoList(identityLinks.get(i).getTaskId())
////                            String userId = identityLinks.get(j).getUserId();
////                            SysUser sysUser = userService.selectUserByUserName(userId);
////                            if(identityLinks.size()>1){
////                                if( "assignee".equals(identityLinks.get(j).getType())){
////                                    userInfo = new StringBuffer();
////                                    userInfo.append(sysUser.getNickName())
////                                            .append(",");
////                                    break;
////                                }
////                                else {
////                                    userInfo.append(sysUser.getNickName())
////                                            .append(",");
////                                }
////                            }else {
////                                userInfo.append(sysUser.getNickName())
////                                        .append(",");
////                            }
//                          /*  userInfo.append(sysUser.getNickName())
//                                    .append(" \\ ")
//                                    .append(identityLinks.get(j).getUserId())
//                                    .append(",");*/
//                        }
                        String info = null;
                        if(userInfo.length()>0){
                            info = userInfo.substring(0, userInfo.length() - 1);
                        }

                        list.get(i).setTodoUserLoginId(info);
                    } else {

                        if("jump".equals(list.get(i).getHistoricTaskInstance().getDeleteReason())){
                            String[] split = list.get(i).getHistoricTaskInstance().getName().split("-");
                            list.get(i).getHistoricTaskInstance().setLocalizedName(split[0]);
                            list.get(i).setTodoUserLoginId(split[1]);
                        }
                        list.get(i).setSORT(i+2);//设置排序
                        //已完成任务节点
                        String assignee = list.get(i).getHistoricTaskInstance().getAssignee();
                        //格式化时间
                        String t = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, list.get(i).getHistoricTaskInstance().getEndTime());
                        list.get(i).setEndDateTime(t);
                        //根据账号获取人员姓名
                        SysUser sysUser = userService.selectUserByUserName(assignee);
                        if (null != sysUser) {
                            list.get(i).setTodoUserLoginId(sysUser.getNickName() );
                            // list.get(i).setTodoUserLoginId(sysUser.getNickName() + " \\ " + assignee + " ");
                        }
                    }
                    if (i == 0 || StringUtils.isEmpty(list.get(i).getHistoricTaskInstance().getDeleteReason())) {
                        //最新节点，不需要添加下一步处理人
                    } else {
                        //获取下一步处理人封装到当前节点任务i-1
                        list.get(i).setNextStep(list.get(i - 1).getHistoricTaskInstance().getName());
                        list.get(i).setNextTodoUser(list.get(i - 1).getTodoUserLoginId());

                    }
                }
            }

            if(isEnd){
                //流程已完结，需要添加结束节点
                HistoryTaskInfo historyTaskInfo1 = new HistoryTaskInfo();
                HistoricTaskInstance historicTaskInstance = new HistoricTaskInstance() {
                    @Override
                    public String getId() {
                        return null;
                    }

                    @Override
                    public String getName() {
                        return "结束";
                    }

                    @Override
                    public String getDescription() {
                        return null;
                    }

                    @Override
                    public int getPriority() {
                        return 0;
                    }

                    @Override
                    public String getOwner() {
                        return null;
                    }

                    @Override
                    public String getAssignee() {
                        return null;
                    }

                    @Override
                    public String getProcessInstanceId() {
                        return null;
                    }

                    @Override
                    public String getExecutionId() {
                        return null;
                    }

                    @Override
                    public String getProcessDefinitionId() {
                        return null;
                    }

                    @Override
                    public Date getCreateTime() {
                        return null;
                    }

                    @Override
                    public String getTaskDefinitionKey() {
                        return null;
                    }

                    @Override
                    public Date getDueDate() {
                        return null;
                    }

                    @Override
                    public String getCategory() {
                        return null;
                    }

                    @Override
                    public String getParentTaskId() {
                        return null;
                    }

                    @Override
                    public String getTenantId() {
                        return null;
                    }

                    @Override
                    public String getFormKey() {
                        return null;
                    }

                    @Override
                    public Map<String, Object> getTaskLocalVariables() {
                        return null;
                    }

                    @Override
                    public Map<String, Object> getProcessVariables() {
                        return null;
                    }

                    @Override
                    public Date getTime() {
                        return null;
                    }

                    @Override
                    public String getDeleteReason() {
                        return "completed";
                    }

                    @Override
                    public Date getStartTime() {
                        return null;
                    }

                    @Override
                    public Date getEndTime() {
                        return null;
                    }

                    @Override
                    public Long getDurationInMillis() {
                        return null;
                    }

                    @Override
                    public Long getWorkTimeInMillis() {
                        return null;
                    }

                    @Override
                    public Date getClaimTime() {
                        return null;
                    }

                    @Override
                    public void setLocalizedName(String s) {

                    }

                    @Override
                    public void setLocalizedDescription(String s) {

                    }
                };
                if(list.size()>0){
                    List<HistoryTaskInfo> resultALL = new ArrayList<HistoryTaskInfo>();

                    historyTaskInfo1.setHistoricTaskInstance(historicTaskInstance);
                    historyTaskInfo1.setEndDateTime(list.get(0).getEndDateTime());
                    Map varialbes = new HashMap();
                    varialbes.put("stepName","结束");
                    varialbes.put("option","");
                    historyTaskInfo1.setVarialbes(varialbes);
                    resultALL.add(historyTaskInfo1);
                    resultALL.addAll(list);
                    return resultALL;
                }



            }

            return list;
        }
    }

    public List<HistoryTaskInfo> getAllHistoryStep(String processInstanceId) {
        List<HistoryTaskInfo> allHistoryStep = vacationService.getAllHistoryStep(processInstanceId, false, null, null);
        return allHistoryStep;
    }


    public List<TaskInfoBean> queryMyTaskByProId(String userId, String processInstanceId) {
        List<TaskInfoBean> taskinfo = new ArrayList<TaskInfoBean>();
        // 个人任务
        List<Task> taskList = processInstanceService.queryMySingleTasks(userId, processInstanceId);
        for (Task task : taskList) {
            taskinfo.add(flowUtils.returnTaskInfo(task));
        }
        return taskinfo;
    }

    public List<FormInfo> getStartFormData(String processDefinitionId) {
        StartFormData startFormData = formService.getStartFormData(processDefinitionId);
        List<FormProperty> formProperties = startFormData.getFormProperties();
        List<FormInfo> list = new ArrayList<>();
        FormInfo formInfo = null;
        for (int i = 0; i < formProperties.size(); i++) {
            formInfo = new FormInfo();
            formInfo.setId(formProperties.get(i).getId());
            formInfo.setName(formProperties.get(i).getName());
            formInfo.setReadable(formProperties.get(i).isReadable());
            formInfo.setRequired(formProperties.get(i).isRequired());
            formInfo.setValue(formProperties.get(i).getValue());
            if (formProperties.get(i).getType() instanceof DateFormType) {
                formInfo.setType("date");
            } else if (formProperties.get(i).getType() instanceof StringFormType) {
                formInfo.setType("string");
            } else if (formProperties.get(i).getType() instanceof LongFormType) {
                formInfo.setType("long");
            } else if (formProperties.get(i).getType() instanceof EnumFormType) {
                formInfo.setType("enum");
            } else if (formProperties.get(i).getType() instanceof BooleanFormType) {
                formInfo.setType("boolean");
            }
            list.add(formInfo);

        }
        return list;
    }

    public TaskFormDataImp getTaskFormData(String taskId) {
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        TaskFormDataImp taskFormDataImp = new TaskFormDataImp();
        taskFormDataImp.setDeploymentId(taskFormData.getDeploymentId());
        taskFormDataImp.setFormKey(taskFormData.getFormKey());
        taskFormDataImp.setAssignee(taskFormData.getTask().getAssignee());
        taskFormDataImp.setCreateTime(taskFormData.getTask().getCreateTime());//
        taskFormDataImp.setId(taskFormData.getTask().getId());
        taskFormDataImp.setName(taskFormData.getTask().getName());
        taskFormDataImp.setProcessInstanceId(taskFormData.getTask().getProcessInstanceId());
        taskFormDataImp.setProcessDefinitionId(taskFormData.getTask().getProcessDefinitionId());
        taskFormDataImp.setFormPropertyImpl(taskFormData.getFormProperties());
        taskFormDataImp.setExecutionId(taskFormData.getTask().getExecutionId());
        taskFormDataImp.setParentTaskId(taskFormData.getTask().getParentTaskId());
        taskFormDataImp.setTaskDefinitionKey(taskFormData.getTask().getTaskDefinitionKey());
        taskFormDataImp.setVariables(taskFormData.getTask().getProcessVariables());
        return taskFormDataImp;
    }

    public List<HistoryTaskInfo> getMyFinishTask(String userId, Integer firstResult, Integer maxResult) {
        List<HistoricTaskInstance> list = historyQueryService.getMyFinishTask(userId, firstResult, maxResult);
        List<HistoryTaskInfo> historyInfo = vacationService.getHistoryInfo(list);
        return historyInfo;
    }

    public InputStream queryProImg(String businessKey) {
        InputStream imageStream = vacationService.queryProImg(businessKey);
        return imageStream;

    }

    public void jumpRunProcessInstanceTask(String dkey, String businessKey, String stepId, String taskUserName) {
        jumpTaskCmdService.stopRunProcessInstance(dkey, businessKey, stepId, taskUserName);
    }

    public void jumpRunProcessInstanceTaskVariables(String businessKey, String stepId, String taskUserName,String taskId,Map<String,String> param) {
        List<HistoryTaskInfo> taskInfoList = getAllFlowStepById(businessKey, "1");
        if (null != taskInfoList && taskInfoList.size() > 0) {
            String processDefinitionId = taskInfoList.get(0).getHistoricTaskInstance().getProcessDefinitionId();
            variablesService.setVariablesByTaskId(param,taskId,true);
            jumpTaskCmdService.stopRunProcessInstance(processDefinitionId, businessKey, stepId, taskUserName);
        }
    }


    @Transactional(rollbackFor = {Exception.class})
    public void accidentDeal(String todoId, String dealUser) {
        //根据待办id获取待办任务信息
        //更新原先的流程状态为已取消
        TodoInfo todoInfo = todoInfoService.selectTodoInfoById(todoId);
        TodoInfo newTodoInfo=new TodoInfo();
        BeanUtils.copyProperties(todoInfo, newTodoInfo);
        todoInfo.setRealDealUserName(SecurityUtils.getNickName());
        todoInfo.setRealDealUser(SecurityUtils.getUsername());
        todoInfo.setTodoType("2");//已取消
        todoInfo.setTodoDataType("2");//已办
        todoInfo.setModifyTime(new Date());
        todoInfoService.updateTodoInfo(todoInfo);
        //取消原先推送表中的待办
        TodoInfoPush info=new TodoInfoPush();
        info.setTodoId(todoId);
        List<TodoInfoPush> todoInfoPushes = iTodoInfoPushService.selectTodoInfoPushList(info);
        //取原先推送表中的todoId
        if(null!=todoInfoPushes && todoInfoPushes.size()>0){
            BeanUtils.copyProperties(todoInfo, info);
            info.setJobId(todoInfoPushes.get(0).getJobId());
            info.setId(StringUtils.getUUID());
            info.setTaskType("1");
            iTodoInfoPushService.insertTodoInfoPush(info);
        }
        //给选择的人发送一条待办
        ViewEmpInfo viewEmpInfo = viewEmpInfoService.selectViewEmpInfoById(dealUser);
        newTodoInfo.setTodoHandler(dealUser);
        newTodoInfo.setTodoHandlerName(viewEmpInfo.getUsername());
        String todoInfoId=StringUtils.getUUID();
        newTodoInfo.setTodoId(todoInfoId);
        newTodoInfo.setTaskType("1");
        String pcUrl = newTodoInfo.getPcUrl();
        //重新拼接p待办地址
        if(StringUtils.isNotEmpty(pcUrl)){
            pcUrl=pcUrl.substring(0,pcUrl.length()-32);
            newTodoInfo.setPcUrl(pcUrl+todoInfoId);
        }
        String todoUrl=newTodoInfo.getTodoUrl();
        if(StringUtils.isNotEmpty(todoUrl)){
            todoUrl=todoUrl.substring(0,todoUrl.length()-32);
            newTodoInfo.setTodoUrl(todoUrl+todoInfoId);
        }
        todoInfoService.insertTodoInfo(newTodoInfo);
        //插入推送表
        iTodoInfoPushService.insertTodoInfoPushByTodoInfo(newTodoInfo,false,"");
        //转办
        trunTask(todoInfo.getTaskId(), dealUser);
        //推送表
//        TodoInfoPush todoInfoPushinfo = new TodoInfoPush();
//        BeanUtils.copyProperties(todoInfo, todoInfoPushinfo);
//        //插入推送待办记录表
//        //插入待办
//        //重新set推送表UUID
//        todoInfoPushinfo.setId(UUID.randomUUID().toString());
//        //根据taskid获取上一步处理人信息
//        TodoInfoVo todoInfoVo = todoInfoService.selectTodoInfoByTaskId(todoInfo.getTaskId());
//        if(todoInfoVo!=null) {
//            //把上一步处理人信息拷贝到推送表记录
//            todoInfoPushinfo.setCurrentStepName(todoInfoVo.getCurrentStepName());
//            todoInfoPushinfo.setAssignee(dealUser);
//            todoInfoPushinfo.setStartTime(DateUtils.getTime());
//            todoInfoPushinfo.setDeleteReason(todoInfoVo.getDeleteReason());
//            todoInfoPushinfo.setProcessName(todoInfoVo.getProcessName());
//            todoInfoPushinfo.setPreLoginId(todoInfoVo.getPreLoginId());
//            todoInfoPushinfo.setPreStepName(todoInfoVo.getPreStepName());
//            todoInfoPushinfo.setDoration(todoInfoVo.getDoration());
//            iTodoInfoPushService.insertTodoInfoPush(todoInfoPushinfo);
//        }
    }
    @Transactional(rollbackFor = {Exception.class})
    public void recycleProcess(String todoId) {
        //根据待办id获取待办任务信息
        TodoInfo todoInfo = todoInfoService.selectTodoInfoById(todoId);
        todoInfo.setTodoType("2");//已取消
        todoInfo.setTodoDataType("2");//已办
        todoInfo.setRealDealUserName(SecurityUtils.getLoginUser().getUser().getNickName());
        todoInfo.setModifyTime(new Date());
        //取消自己表的待办
        todoInfoService.updateTodoInfo(todoInfo);
        //取消原先推送表中的待办
        TodoInfoPush info=new TodoInfoPush();
        info.setTodoId(todoId);
        List<TodoInfoPush> todoInfoPushes = iTodoInfoPushService.selectTodoInfoPushList(info);
        //取原先推送表中的todoId
        if(null!=todoInfoPushes && todoInfoPushes.size()>0){
            BeanUtils.copyProperties(todoInfo, info);
            info.setJobId(todoInfoPushes.get(0).getJobId());
            info.setId(StringUtils.getUUID());
            info.setTaskType("1");
            iTodoInfoPushService.insertTodoInfoPush(info);
        }
    }


    /**
     * 手动插入待办
     * @param todoInfoParam
     */
    public void UserInsertOneTodoInfo(TodoInfoParam todoInfoParam){
        TodoInfo todoInfo = new TodoInfo();
        BeanUtils.copyProperties(todoInfoParam,todoInfo );
        todoInfo.setTodoType("0");//未处理
        todoInfo.setTodoDataType("1");//待办
        todoInfo.setPushStatus("1");//推送状态：未推送
        todoInfo.setTaskType("2");//任务类型，2:非流程待办待阅
        if(todoInfo.getTodoId()==null){
            todoInfo.setTodoId(StringUtils.getUUID());
        }

        todoInfo.setCreateTime(new Date());
        todoInfo.setCreateBy(SecurityUtils.getUsername());
        //生成待办
        todoInfoService.insertTodoInfo(todoInfo);
        //插入推送表
        iTodoInfoPushService.insertTodoInfoPushByTodoInfo(todoInfo,false,"");

    }

    /**
     * 手动将待办 转成已办
     * @param todoId
     */
    public void UserUpdateOneTodoToDone(String todoId){

        TodoInfo todoInfo = todoInfoService.selectTodoInfoById(todoId);
        if(todoInfo!=null){
            todoInfo.setTodoType("1");//1：已处理，0：未处理,2:已取消
            todoInfo.setTodoDataType("2");//1-待办，2-已办,3-待阅，4-已阅
            todoInfo.setPushStatus("1");//推送状态：未推送
            todoInfo.setTaskType("2");//任务类型，2:非流程待办待阅

            todoInfo.setUpdateBy(SecurityUtils.getUsername());
            todoInfo.setUpdateTime(new Date());
            todoInfo.setRealDealUserName(SecurityUtils.getNickName());
            todoInfo.setRealDealUser(SecurityUtils.getUsername());
            //生成待办
            todoInfoService.updateTodoInfo(todoInfo);
            //插入推送表
            iTodoInfoPushService.insertTodoInfoPushByTodoInfo(todoInfo,false,"");
        }

    }


    /**
     * 手动插入待阅
     * @param todoInfoParam
     */
    public void UserInsertOneToReadInfo(TodoInfoParam todoInfoParam){
        TodoInfo todoInfo = new TodoInfo();
        BeanUtils.copyProperties(todoInfoParam,todoInfo );
        todoInfo.setTodoType("0");//1：已处理，0：未处理,2:已取消
        todoInfo.setTodoDataType("3");//1-待办，2-已办,3-待阅，4-已阅
        todoInfo.setPushStatus("1");//推送状态：未推送
        todoInfo.setTaskType("2");//任务类型1：activiti,2:非流程待办待阅，3：bpm流程
        if(todoInfo.getTodoId()==null){
            todoInfo.setTodoId(StringUtils.getUUID());
        }

        todoInfo.setCreateTime(new Date());
        todoInfo.setCreateBy(SecurityUtils.getUsername());
        //生成待办
        todoInfoService.insertTodoInfo(todoInfo);
        //插入推送表
        iTodoInfoPushService.insertTodoInfoPushByTodoInfo(todoInfo,false,"");
    }



    /**
     *  //将待阅设置为已阅
     * @param todoId
     */
    public void UserUpdateOneToReadToDone(String todoId){
        TodoInfo todoInfo = todoInfoService.selectTodoInfoById(todoId);
        if(todoInfo!=null){
            todoInfo.setTodoType("1");//1：已处理，0：未处理,2:已取消
            todoInfo.setTodoDataType("4");//1-待办，2-已办,3-待阅，4-已阅
            todoInfo.setPushStatus("1");//推送状态：未推送
            todoInfo.setTaskType("2");//任务类型，2:非流程待办待阅

            todoInfo.setUpdateBy(SecurityUtils.getUsername());
            todoInfo.setUpdateTime(new Date());
            todoInfo.setRealDealUserName(SecurityUtils.getNickName());
            todoInfo.setRealDealUser(SecurityUtils.getUsername());
            //生成待办
            todoInfoService.updateTodoInfo(todoInfo);
            //插入推送表
            iTodoInfoPushService.insertTodoInfoPushByTodoInfo(todoInfo,false,"");
        }

    }


}
