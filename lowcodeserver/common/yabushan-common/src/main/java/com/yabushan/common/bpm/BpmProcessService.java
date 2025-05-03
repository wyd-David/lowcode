package com.yabushan.common.bpm;

/**
 * Created by lintiannan on 2020/11/09.
 */

import cn.hutool.json.JSONObject;

public interface BpmProcessService {

    /**
     * 模拟拟稿，获取流程表头信息
     *
     * @param service 流程编号
     * @param user    拟稿人账号
     * @return
     */
    JSONObject draft(String service, String user);

    /**
     * 发起流程(第一次拟稿提交)
     *
     * @param service        流程编号
     * @param title          表单标题
     * @param user           当前用户
     * @param nextActivityId 下个节点id
     * @param nextUsers      下一处理人---"a","b"
     * @param approveResult  处理结果（非必填）
     * @param approveNote    处理意见（非必填）
     * @param chain          审批链默认[]（非必填）---["a","b"]
     */
    JSONObject start(String service, String title, String user, String nextActivityId, String nextUsers, String approveResult, String approveNote, String chain);

    /**
     * 提交流程
     *
     * @param documentId     流程编号
     * @param title          表单标题
     * @param user           当前用户
     * @param nextActivityId 下一环节ID
     * @param nextUsers      下一处理人---"a","b"
     * @param approveResult  处理结果（非必填）
     * @param approveNote    处理意见（非必填）
     * @param breaked        是否中断会签直接到下一个环节默认false（非必填）
     * @param chain          审批链默认[]（非必填）---["a","b"]
     */
    JSONObject commit(String documentId, String title, String user, String nextActivityId, String nextUsers, String approveResult, String approveNote, boolean breaked, String chain);

    /**
     * 打开文档
     *
     * @param documentId 流程id
     * @param user       当前用户
     * @param returnData 是否返回文档数据，默认false（非必填）
     */
    JSONObject open(String documentId, String user, boolean returnData);

    /**
     * 更新BPM文档标题（用户点击保存按钮时）
     *
     * @param documentId 流程id
     * @param title      表单标题
     * @param user       当前用户
     */
    JSONObject update(String documentId, String title, String user);

    /**
     * 查询文档
     *
     * @param documentId 流程id
     */
    JSONObject query(String documentId);

    /**
     * 创建新文档，保存和生成文档ID（第一次拟稿时）
     *
     * @param service   流程编号
     * @param title     表单标题
     * @param user      创建人账号
     * @param data      业务数据（非必填）
     * @param attribute 参数数据（非必填）
     * @param corpId    集团代码（非必填）
     * @return
     */
    JSONObject create(String service, String title, String user, String data, String attribute, String corpId);

    /**
     * 撤回任务
     *
     * @param documentId  流程id
     * @param currentUser 当前用户
     */
    JSONObject withdraw(String documentId, String currentUser);

    /**
     * 撤销流程
     *
     * @param documentId  流程id
     * @param user 当前用户
     */
    JSONObject cancel(String documentId, String user);

    /**
     * 转办任务
     *
     * @param documentId  流程id
     * @param currentUser 当前用户
     * @param targetUser 目标用户
     */
    JSONObject assign(String documentId, String currentUser , String targetUser);

    /**
     * 退回任务
     *
     * @param documentId  流程id
     * @param currentUser 当前用户
     */
    JSONObject reject(String documentId, String currentUser);

    /**
     * 查询人员信息
     */
    JSONObject geUserMsg(String user);

    /**
     * 添加关联流程
     */
    JSONObject createRelationDocument(String fromDocumentId, String toDocumentId, String createdBy);

    /**
     * 获取当前用户task任务
     *
     * @param documentId 流程id
     * @param user       当前用户
     */
    JSONObject getUserTask(String documentId, String user);

    /**
     * 根据流程类型指定下一节点Id(适用于002流程)
     *
     * @param processType 流程类型
     */
    String getNextActivityByProcessType(String processType);

    /**
     * 根据流程编号和流程类型查询流程线路及审批记录
     *
     * @param service     流程编号
     * @param documentId  流程docId
     * @param processType 流程类型
     * @param user        当前用户
     */
    JSONObject queryProcessLineAndRecords(String service, String documentId, String processType, String user);

    /**
     * 根据docId查询流程所有节点
     *
     * @param documentId 流程id
     */
    JSONObject queryActivityList(String documentId);

    /**
     * 根据docId查询流程审批记录
     *
     * @param documentId 流程id
     * @param user       当前用户
     */
    JSONObject queryRecords(String documentId, String user);

    /**
     * 根据评价人选择模式指定下一节点Id(适用于003流程)
     *
     * @param evaluationerChooseModule 评价人选择模式
     * @param currentActivityId        当前节点
     */
    String getNextActivityByModule(String evaluationerChooseModule, String currentActivityId);

    /**
     * 关闭流程
     *
     * @param documentId 流程id
     * @param user       当前用户
     */
    JSONObject close(String documentId, String user);

    /**
     * 查询当前用户是否是当前流程管理员
     *
     * @param service  流程编号
     * @param userCode 当前用户
     */
    Boolean processAdmin(String service, String userCode);

    /**
     * 查询用户流程待办
     *
     * @param user        当前用户
     * @param serviceCode 流程编号
     * @param activityId  流程节点
     * @param page        页数
     * @param size        数量
     */
    JSONObject queryWorkOnTasks(String user, String serviceCode, String activityId, Integer page, Integer size);

    /**
     * 查询用户流程已办
     *
     * @param user        当前用户
     * @param serviceCode 流程编号
     * @param page        页数
     * @param size        数量
     */
    JSONObject queryCompletedTasks(String user, String serviceCode, Integer page, Integer size);

    /**
     * 查询上一节点信息
     * @param documentId 流程id
     * @param user       当前用户
     */
    JSONObject getPreAvtivity(String documentId, String user);

    /**
     * 查询BPM流程发起oa账号
     *
     * @param documentId 流程id
     * @param user       当前用户
     */
    String getCreatorUserName(String documentId, String user);
}
