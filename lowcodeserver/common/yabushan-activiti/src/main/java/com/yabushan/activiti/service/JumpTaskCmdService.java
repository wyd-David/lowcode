package com.yabushan.activiti.service;

import com.yabushan.common.utils.SecurityUtils;
import com.yabushan.system.domain.TodoInfo;
import com.yabushan.system.mapper.TodoInfoMapper;
import com.yabushan.system.service.ITodoInfoPushService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author yabushan
 * @Date 2021/6/23 12:32
 * @Version 1.0
 */
@Service
public class JumpTaskCmdService implements Command<Comment> {

    protected String executionId;  //当前任务的executionID
    protected String activityId;  //跳转目标activityID

    @Autowired
    private TodoInfoMapper todoInfoMapper;

    @Autowired
    private EngineService engineService;
    @Autowired
    private ITodoInfoPushService iTodoInfoPushService;

    //存放流程对应的所有activity
    private static Map<String,List<ActivityImpl>> processActivitiMap = new HashMap<>();

    public JumpTaskCmdService() {

    }

    public JumpTaskCmdService(String executionId, String activityId) {
        this.executionId = executionId;
        this.activityId = activityId;
    }

    public Comment execute(CommandContext commandContext) {
        for (TaskEntity taskEntity : Context.getCommandContext().getTaskEntityManager().findTasksByExecutionId(executionId)) {
            Context.getCommandContext().getTaskEntityManager().deleteTask(taskEntity, "jump", false);
        }
        ExecutionEntity executionEntity = Context.getCommandContext().getExecutionEntityManager().findExecutionById(executionId);
        ProcessDefinitionImpl processDefinition = executionEntity.getProcessDefinition();
        ActivityImpl activity = processDefinition.findActivity(activityId);
        executionEntity.executeActivity(activity);
        return null;
    }

    public String getEndEventActivityId(String dkey,String stepId){
        String endActivityId = null;
        List<ActivityImpl> allActivities = getAllActivities(dkey);
        for(ActivityImpl activity : allActivities){
            String type = activity.getProperty("type").toString();
            if(type.equals(stepId)){//"endEvent"
                endActivityId = activity.getId();
                break;
            }
            String id = activity.getId();
            if(id.equals(stepId)){//"endEvent"
                endActivityId = activity.getId();
                break;
            }
        }
        return endActivityId;
    }

    public List<ActivityImpl> getAllActivities(String dkey) {
        List<ActivityImpl> activities = processActivitiMap.get(dkey);
        if(activities == null){
            RepositoryService repositoryService = engineService.getRepositoryService();
            ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().processDefinitionId(dkey).singleResult();
            ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                    .getDeployedProcessDefinition(definition.getId());

            //获取所有的activity
            activities = processDefinition.getActivities();
            processActivitiMap.put(dkey,activities);
        }

        return activities;
    }


    /**
     * 终止正在运行的流程实例
     * @author:kaka
     * @param dkey:流程定义key,businessKey:业务id,stepId:环节ID（startEvent、endEvent）
     */
    public void stopRunProcessInstance( String dkey,String businessKey,String stepId,String taskUserName) {
        if( null != businessKey ) {
            HistoricProcessInstance processInstanceId = engineService.getHistoryService().createHistoricProcessInstanceQuery().processInstanceBusinessKey( businessKey ).singleResult();
            Execution execution = engineService.getRuntimeService().createExecutionQuery().processInstanceBusinessKey(businessKey).singleResult();
            engineService.getRuntimeService().setVariable(execution.getId(),"nextDealUser",taskUserName);
            Task task = engineService.getTaskService().createTaskQuery().processInstanceId( processInstanceId.getId() ).singleResult();
            if(null != task){
                task.setAssignee(taskUserName);
                String eventActivityId = getEndEventActivityId(dkey,stepId);
                org.activiti.engine.impl.TaskServiceImpl taskServiceImpl = (org.activiti.engine.impl.TaskServiceImpl) engineService.getTaskService();
                taskServiceImpl.getCommandExecutor().execute( new JumpTaskCmdService( task.getExecutionId(), eventActivityId) );

                //将原先的待办设置为已取消
                TodoInfo todoInfo = new TodoInfo();
                todoInfo.setBusinessKey(businessKey);
                todoInfo.setTaskId(task.getId());
                List<TodoInfo> todoInfos = todoInfoMapper.selectTodoInfoList(todoInfo);
                if(todoInfos.size()>0){
                    for (TodoInfo todoinfo:todoInfos ) {
                        todoinfo.setRealDealUser(taskUserName);
                        todoinfo.setTodoType("2");//已取消
                        todoinfo.setTodoDataType("2");//已办
                        todoinfo.setRealDealUserName(SecurityUtils.getLoginUser().getUser().getNickName());
                        todoinfo.setModifyTime(new Date());
                        //更新为已取消
                        todoInfoMapper.updateTodoInfo(todoinfo);
                        //插入推送待办记录表
                        iTodoInfoPushService.insertTodoInfoPushByTodoInfo(todoinfo,false,task.getId());

                    }
                }


            }




        }
    }

    /**
     * 将指定任务进行委托处理
     *
     * @param taskId
     * @param assignee
     */
    public  void delegateTask(String taskId, String assignee) {
        engineService.getTaskService().delegateTask(taskId, assignee);
    }



    /**
     * 根据taskId解决被委托的任务,动态传值设置变量
     */
    public  String resolveTask(String taskId, Map<String, Object> variables) {
        //根据taskId提取任务
        Task task = engineService.getTaskService().createTaskQuery().taskId(taskId).singleResult();
        if (task.getOwner() != null && !task.getOwner().equals("null")) {
            DelegationState delegationState = task.getDelegationState();
            if (delegationState.toString().equals("RESOLVED")) {
                return "此委托任务已是完结状态";
            } else if (delegationState.toString().equals("PENDING")) {
                //如果是委托任务需要做处理
                engineService.getTaskService().resolveTask(taskId, variables);
                return "success";
            } else {
                return "此任务不是委托任务";
            }
        }
        return null;
    }

}
