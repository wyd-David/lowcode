package com.yabushan.activiti.tasklistener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yabushan
 * @Date 2021/6/20 15:23
 * @Version 1.0
 */
public class MultiInstanceExcutionListener implements ExecutionListener {

    @Override
    public void notify(DelegateExecution execution) throws Exception {

        if(null!=execution.getVariable("nextDealUserList")) {
            String nextDealUserList = execution.getVariable("nextDealUserList").toString();
            String nextDealParamList = execution.getVariable("nextDealParam").toString();
            String[] nextDealUserGroupList = nextDealUserList.split("-");
            String[] nextDealParamGroupList = nextDealParamList.split("-");

            for (int i = 0; i < nextDealUserGroupList.length; i++) {
                String[] userIds = nextDealUserGroupList[i].split(",");
                String nextAssaginee = nextDealParamGroupList[i];
                List list = new ArrayList();
                for (String userId : userIds) {
                    list.add(userId);
                }
                execution.setVariable(nextAssaginee, list);
            }
        }
    }
}
