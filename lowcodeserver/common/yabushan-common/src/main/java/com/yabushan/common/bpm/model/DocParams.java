package com.yabushan.common.bpm.model;

import io.swagger.annotations.ApiParam;

/**
 * @ClassName: DocParams.java
 * @Description: 发起流程表单需要的参数
 * @author: yl
 * @version: V1.0
 * @Date: 2019年3月7日 下午4:48:38
 */
public class DocParams {
    private String service = "";// 文档编号(流程编号/类型)
    private String title = "";// 操作类型、关联流程也要标题（普通流程不用传）
    private String legacy = "false";// 版本号向后兼容
    private String user = "";// 操作用户
    private String data = "";// 业务数据
    private String attribute = "";// 参数数据
    //查询
    private String documentId = "";//文档编号
    private boolean returnData = false;//是否返回文档数据
    private String oa_urgency = "";//1=加急

    //更新
    private long version = 1l;//版本号
    private boolean merge = true;//	是否合并模式

    //业务处理字段不需要对接BPM的参数
    private Object businessParams = "";//预留业务参数


    //传阅share
    private String fromUserId = "";//用户账号
    private String toUserIds = "";//目标用户账号
    private String message = "";//传阅意见
    private String aclCode = "";//权限字符串
    private String noDuplicate = "false";//避免重复阅知
    private String withAcl = "false";//权限传递

    @ApiParam(required = true, name = "目标用户账号", value = "目标用户账号")
    private String toUserId = "";
    private String allowShare = "true";//是否允许传阅

    //2020-03-04添加关联流程接口
    private String dispatchFlag = "0";//1发文标识2title
    private String fromDocumentId = "";//3主流程ID
    private String toDocumentId = "";//4关联流程ID
    private String activeTime = "9999-12-31 23:59:59";//5生效时间
    private String createdBy = "";//6创建人
    private String deletedBy = "";//1删除人
    private String queryBy = "";//1查询人
    private String createdTime = "";//1创建时间
    private String updatedBy = "";//2更新人

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLegacy() {
        return legacy;
    }

    public void setLegacy(String legacy) {
        this.legacy = legacy;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public boolean isReturnData() {
        return returnData;
    }

    public void setReturnData(boolean returnData) {
        this.returnData = returnData;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public boolean isMerge() {
        return merge;
    }

    public void setMerge(boolean merge) {
        this.merge = merge;
    }

    public DocParams(String service, String title, String legacy, String user,
                     String data, String attribute, String documentId, boolean returnData) {
        super();
        this.service = service;
        this.title = title;
        this.legacy = legacy;
        this.user = user;
        this.data = data;
        this.attribute = attribute;
        this.documentId = documentId;
        this.returnData = returnData;
    }

    public DocParams() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Object getBusinessParams() {
        return businessParams;
    }

    public void setBusinessParams(Object businessParams) {
        this.businessParams = businessParams;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserIds() {
        return toUserIds;
    }

    public void setToUserIds(String toUserIds) {
        this.toUserIds = toUserIds;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAclCode() {
        return aclCode;
    }

    public void setAclCode(String aclCode) {
        this.aclCode = aclCode;
    }

    public String getNoDuplicate() {
        return noDuplicate;
    }

    public void setNoDuplicate(String noDuplicate) {
        this.noDuplicate = noDuplicate;
    }

    public String getWithAcl() {
        return withAcl;
    }

    public void setWithAcl(String withAcl) {
        this.withAcl = withAcl;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getAllowShare() {
        return allowShare;
    }

    public void setAllowShare(String allowShare) {
        this.allowShare = allowShare;
    }

    public String getDispatchFlag() {
        return dispatchFlag;
    }

    public void setDispatchFlag(String dispatchFlag) {
        this.dispatchFlag = dispatchFlag;
    }

    public String getFromDocumentId() {
        return fromDocumentId;
    }

    public void setFromDocumentId(String fromDocumentId) {
        this.fromDocumentId = fromDocumentId;
    }

    public String getToDocumentId() {
        return toDocumentId;
    }

    public void setToDocumentId(String toDocumentId) {
        this.toDocumentId = toDocumentId;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getQueryBy() {
        return queryBy;
    }

    public void setQueryBy(String queryBy) {
        this.queryBy = queryBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getOa_urgency() {
        return oa_urgency;
    }

    public void setOa_urgency(String oa_urgency) {
        this.oa_urgency = oa_urgency;
    }
}
