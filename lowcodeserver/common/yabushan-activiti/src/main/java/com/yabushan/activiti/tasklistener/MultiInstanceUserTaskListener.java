package com.yabushan.activiti.tasklistener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yabushan
 * @Date 2021/6/20 15:23
 * @Version 1.0
 */
public class MultiInstanceUserTaskListener implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        String nextDealUserList = delegateTask.getVariable("nextDealUserList").toString();
        String nextDealParam = delegateTask.getVariable("nextDealParam").toString();
        List list = new ArrayList();
        if(nextDealUserList!=null){
            String[] userIds=nextDealUserList.split(",");
            if(userIds.length==1){
                list.add(userIds[0]);
            }else{
                for (String userId : userIds) {
                    list.add(userId);
                }
            }
        }
        delegateTask.setVariable(nextDealParam,list);
    }
}
