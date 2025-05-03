package com.yabushan.common.bpm;

/**
 * Created by lintiannan on 2020/12/29.
 */


import cn.hutool.json.JSONArray;

public interface Process001Service {

    /**
     * 保存流程、分组信息、人员流程、参与评价人信息、流程关联关系
     * @param processId
     * @param processName
     * @param currentErpNo
     * @param evaluationedErpId
     * @param processNumber
     * @param processYear
     * @param chooseModule
     * @param currentActivityId
     * @param currentActivityName
     * @param versionId
     * @param relationProcessId
     * @param evaluationPersons
     * @return
     */
    void saveProcessAllData(String processId, String processName, Integer currentErpNo, String evaluationedErpId, String processNumber, String processYear, Integer chooseModule, String currentActivityId, String currentActivityName, Integer versionId, String relationProcessId, JSONArray evaluationPersons);

    /**
     * 撤回流程多线程异步处理任务
     * @param processNumber  流程编号
     * @param processId  流程id
     * @param currentOaId 当前用户oaId
     * */
    void asynWithdrawAndCancel(String processNumber, String processId, String currentOaId);

    /**
     * 撤销流程相关数据清除
     * @param processId  流程id
     * */
    void updateForCancleProcess(String processId);
}