package com.yabushan.quartz.task;

import com.yabushan.common.utils.http.HttpClientUtil;
import com.yabushan.system.domain.TodoInfo;
import com.yabushan.system.domain.TodoInfoPush;
import com.yabushan.system.domain.vo.TodoInfoVo;
import com.yabushan.system.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * Created by 姜森焱 on 2021/9/13 15:33  待办待阅
 */
@Component("todoInfoPushTask")
public class TodoInfoPushTask {
    @Autowired
    private ITodoInfoService iTodoInfoService;
    @Autowired
    private ITodoInfoPushService iTodoInfoPushService;
    @Autowired
    private ISysConfigService iSysConfigService;
    @Autowired
    private TodoInfoPushService todoInfoPushService;
    @Autowired
    private ISystemJobLogService systemJobLogService;

    @Value("${domain.xzfw}")
    private String xzfw;

    /**
     * 待办同步到推送表定时任务
     */
    public void getTodoInfoTask() {
        //待办同步
        getTodoInfo("1");
        //已办同步
        getTodoInfo("2");
    }
    public void  getTodoInfo(String todoDateType){
        try {
            String todo_push_applyId = iSysConfigService.selectConfigByKey("todo_Push_applyId");

            //通过待办创建时间来定位最新的记录
            TodoInfoPush todoInfoPush = iTodoInfoPushService.selectTodoInfoPushListBytodoDataType(todoDateType);
            if (todoInfoPush == null) {
                //查找待办的记录进行转换
                TodoInfo todoInfo = new TodoInfo();
                todoInfo.setTodoDataType(todoDateType);
                todoInfo.setApplyIdList(todo_push_applyId.split(",",-1));
//                todoInfo.setApplyId(todo_push_applyId);
                List<TodoInfo> todoInfos = iTodoInfoService.selectTodoInfoList(todoInfo);
//                if (StringUtils.isNotNull(todoInfos) && todoInfos.size() >= 1) {
//                    SystemJobLog jobLog = new SystemJobLog();
//                    jobLog.setJobName("根据当前时间（已预约状态），修改为已爽约状态");
//                    jobLog.setApplyName("健身预约");
//                    jobLog.setApplyMiaos("根据当前时间（已预约状态），修改为已爽约状态");
//                    jobLog.setExeMethod("selectAppointmentRecordList");
//                    String batchId = StringUtils.getUUID();
//                    jobLog.setDataBatchId(batchId);
//                    for(TodoInfo vo : todoInfos){
//                        SystemJobLogBatch batch = new SystemJobLogBatch();
//                        batch.setDataString(vo.toString());
//                        batch.setDataId(vo.getTodoId());
//                        batch.setDataBatchId(batchId);
//                        systemJobLogBatchService.insertSystemJobLogBatch(batch);
//                    }
//                    jobLog.setDataNum(String.valueOf(todoInfos.size()));
//                    systemJobLogService.insertSystemJobLog(jobLog);
//                }
                for (int i = 0; i < todoInfos.size(); i++) {
                    //推送表
                    TodoInfoPush todoInfoPushinfo = new TodoInfoPush();
                    //获取待办待阅实体
                    TodoInfo todoInfo1 = todoInfos.get(i);
                    //拷贝实体
                    BeanUtils.copyProperties(todoInfo1, todoInfoPushinfo);
                    //重新set推送表UUID
                    todoInfoPushinfo.setId(UUID.randomUUID().toString());
                    //插入推送表记录
                    iTodoInfoPushService.insertTodoInfoPush(todoInfoPushinfo);
                }
            } else {
                //查找待办的记录进行转换
                TodoInfo todoInfo = new TodoInfo();
                todoInfo.setTodoDataType(todoDateType);
                todoInfo.setCreateTime(todoInfoPush.getCreateTime());
                List<TodoInfo> todoInfos = iTodoInfoService.selectTodoInfoList(todoInfo);
                for (int i = 0; i < todoInfos.size(); i++) {
                    //推送表
                    TodoInfoPush todoInfoPushinfo = new TodoInfoPush();
                    //获取待办待阅实体
                    TodoInfo todoInfo1 = todoInfos.get(i);
                    //拷贝实体
                    BeanUtils.copyProperties(todoInfo1, todoInfoPushinfo);
                    //重新set推送表UUID
                    todoInfoPushinfo.setId(UUID.randomUUID().toString());
                    //插入推送表记录
                    iTodoInfoPushService.insertTodoInfoPush(todoInfoPushinfo);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void  getNewTodoInfo(String todoDateType){
        try {
            String todo_push_applyId = iSysConfigService.selectConfigByKey("todo_Push_applyId");
            //通过待办创建时间来定位最新的记录
            TodoInfoPush todoInfoPush = iTodoInfoPushService.selectTodoInfoPushListBytodoDataType(todoDateType);
            if (todoInfoPush == null) {
                //查找待办的记录进行转换
                TodoInfo todoInfo = new TodoInfo();
                todoInfo.setTodoDataType(todoDateType);
//                todoInfo.setApplyId(todo_push_applyId);
                List<TodoInfo> todoInfos = iTodoInfoService.selectTodoInfoList(todoInfo);
                for (int i = 0; i < todoInfos.size(); i++) {
                    //根据taskid获取上一步处理人信息
                    TodoInfoVo todoInfoVo = iTodoInfoService.selectTodoInfoByTaskId(todoInfos.get(i).getTaskId());
                    //推送表
                    TodoInfoPush todoInfoPushinfo = new TodoInfoPush();
                    //获取待办待阅实体
                    TodoInfo todoInfo1 = todoInfos.get(i);
                    //拷贝实体
                    BeanUtils.copyProperties(todoInfo1, todoInfoPushinfo);
                    //把上一步处理人信息拷贝到推送表记录
//                    todoInfoPushinfo.setCurrentStepName(todoInfoVo.getCurrentStepName());
//                    todoInfoPushinfo.setAssignee(todoInfoVo.getAssignee());
//                    todoInfoPushinfo.setStartTime(todoInfoVo.getStartTime());
//                    todoInfoPushinfo.setDeleteReason(todoInfoVo.getDeleteReason());
//                    todoInfoPushinfo.setProcessName(todoInfoVo.getProcessName());
//                    todoInfoPushinfo.setPreLoginId(todoInfoVo.getPreLoginId());
//                    todoInfoPushinfo.setPreStepName(todoInfoVo.getPreStepName());
//                    todoInfoPushinfo.setDoration(todoInfoVo.getDoration());

                    //重新set推送表UUID
                    todoInfoPushinfo.setId(UUID.randomUUID().toString());
                    //插入推送表记录
                    iTodoInfoPushService.insertTodoInfoPush(todoInfoPushinfo);
                }
            } else {
                //查找待办的记录进行转换
                TodoInfo todoInfo = new TodoInfo();
                todoInfo.setTodoDataType(todoDateType);
                todoInfo.setCreateTime(todoInfoPush.getCreateTime());
                List<TodoInfo> todoInfos = iTodoInfoService.selectTodoInfoList(todoInfo);
                for (int i = 0; i < todoInfos.size(); i++) {
                    //根据taskid获取上一步处理人信息
                    TodoInfoVo todoInfoVo = iTodoInfoService.selectTodoInfoByTaskId(todoInfos.get(i).getTaskId());
                    //推送表
                    TodoInfoPush todoInfoPushinfo = new TodoInfoPush();
                    //获取待办待阅实体
                    TodoInfo todoInfo1 = todoInfos.get(i);
                    //拷贝实体
                    BeanUtils.copyProperties(todoInfo1, todoInfoPushinfo);
                    //把上一步处理人信息拷贝到推送表记录
                    todoInfoPushinfo.setCurrentStepName(todoInfoVo.getCurrentStepName());
                    todoInfoPushinfo.setAssignee(todoInfoVo.getAssignee());
                    todoInfoPushinfo.setStartTime(todoInfoVo.getStartTime());
                    todoInfoPushinfo.setDeleteReason(todoInfoVo.getDeleteReason());
                    todoInfoPushinfo.setProcessName(todoInfoVo.getProcessName());
                    todoInfoPushinfo.setPreLoginId(todoInfoVo.getPreLoginId());
                    todoInfoPushinfo.setPreStepName(todoInfoVo.getPreStepName());
                    todoInfoPushinfo.setDoration(todoInfoVo.getDoration());
                    //重新set推送表UUID
                    todoInfoPushinfo.setId(UUID.randomUUID().toString());
                    //插入推送表记录
                    iTodoInfoPushService.insertTodoInfoPush(todoInfoPushinfo);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 推送待办已办(旧门户)
     */
    public void  pushTOdoInfo(String config){
        if("uat".equals(config)){
            String s = HttpClientUtil.doGet(xzfw + "/TodoInfoPushGFController/pushNewLogInfo");
        }else if("prod".equals(config)){
            todoInfoPushService.pushNewLogInfo();
        }
    }
    /**
     * 推送待办已办(新门户)
     */
    public void  newPushNewLogInfo(String config){
        if("uat".equals(config)){
            String s = HttpClientUtil.doGet(xzfw + "/TodoInfoPushGFController/newPushNewLogInfo");
        }else if("prod".equals(config)){
            todoInfoPushService.newPushNewLogInfo();
        }
    }

}
