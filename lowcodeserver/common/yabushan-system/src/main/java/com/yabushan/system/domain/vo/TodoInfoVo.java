package com.yabushan.system.domain.vo;


import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 待办待阅对象 todo_info
 *
 * @author 姜森焱
 * @date 2021-09-02
 */
public class TodoInfoVo extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private String todoId;

    /**
     * 业务主键
     */
    private String businessKey;

    /**
     * 代办待阅标题
     */
    private String todoTitle;

    /**
     * 应用ID
     */
    private String applyId;

    /**
     * 应用名称
     */
    private String applyName;

    /**
     * 待办内容
     */
    private String todoText;

    /**
     * 发起人/申请人
     */
    private String todoApplicant;

    /**
     * 待办URL
     */
    private String todoUrl;

    /**
     * 处理人账号
     */
    private String todoHandler;

    /**
     * 处理人名称
     */
    private String todoHandlerName;

    /**
     * 所属工作流程名称
     */
    private String todoTpName;

    /**
     * 当前处理环节
     */
    private String todoTpLink;

    /**
     * 待办完成时间
     */
    private Date modifyTime;

    /**
     * 1：已处理，0：未处理
     */
    private String todoType;

    /**
     * 1-待办，2-已办,3-待阅，4-已阅
     */
    private String todoDataType;

    /**
     * 1-未推送，2-推送，3-未开放推送权限,推送门户系统
     */
    private String pushStatus;

    /**
     * 导入状态
     */
    private String importStatus;



    /**
     * logo图片地址
     *
     * @return
     */
    private String imgPath;
    /**
     * 待办待阅总数
     */
    private String todoInfoCount;
    /**
     * 当前处理节点名称（步骤）
     */
    private String currentStepName;
    /**
     * 当前处理人
     */
    private String assignee;
    /**
     * 待办接收时间
     */
    private String startTime;
    /**
     *
     */
    private String doration;
    /**
     * 待办状态
     */
    private String deleteReason;
    /**
     * 流程名称
     */
    private String processName;
    /**
     * 上一步处理人id
     */
    private String preLoginId;
    /**
     * 上一步处理节点（步骤）
     */
    private String preStepName;
    /**
     * 多条件搜索待办
     */
    private String todoInfoOrS;
    /**
     * 实际处理人
     */
    private String realDealUser;

    private String[] applyIdList;

    private String pcUrl;

    public String getPcUrl() {
        return pcUrl;
    }

    public void setPcUrl(String pcUrl) {
        this.pcUrl = pcUrl;
    }

    public String getRealDealUser() {
        return realDealUser;
    }

    public void setRealDealUser(String realDealUser) {
        this.realDealUser = realDealUser;
    }

    public String[] getApplyIdList() {
        return applyIdList;
    }

    public void setApplyIdList(String[] applyIdList) {
        this.applyIdList = applyIdList;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getTodoInfoOrS() {
        return todoInfoOrS;
    }

    public void setTodoInfoOrS(String todoInfoOrS) {
        this.todoInfoOrS = todoInfoOrS;
    }

    public String getCurrentStepName() {
        return currentStepName;
    }

    public void setCurrentStepName(String currentStepName) {
        this.currentStepName = currentStepName;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDoration() {
        return doration;
    }

    public void setDoration(String doration) {
        this.doration = doration;
    }

    public String getDeleteReason() {
        return deleteReason;
    }

    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getPreLoginId() {
        return preLoginId;
    }

    public void setPreLoginId(String preLoginId) {
        this.preLoginId = preLoginId;
    }

    public String getPreStepName() {
        return preStepName;
    }

    public void setPreStepName(String preStepName) {
        this.preStepName = preStepName;
    }

    public String getTodoInfoCount() {
        return todoInfoCount;
    }

    public void setTodoInfoCount(String todoInfoCount) {
        this.todoInfoCount = todoInfoCount;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoApplicant(String todoApplicant) {
        this.todoApplicant = todoApplicant;
    }

    public String getTodoApplicant() {
        return todoApplicant;
    }

    public void setTodoUrl(String todoUrl) {
        this.todoUrl = todoUrl;
    }

    public String getTodoUrl() {
        return todoUrl;
    }

    public void setTodoHandler(String todoHandler) {
        this.todoHandler = todoHandler;
    }

    public String getTodoHandler() {
        return todoHandler;
    }

    public void setTodoHandlerName(String todoHandlerName) {
        this.todoHandlerName = todoHandlerName;
    }

    public String getTodoHandlerName() {
        return todoHandlerName;
    }

    public void setTodoTpName(String todoTpName) {
        this.todoTpName = todoTpName;
    }

    public String getTodoTpName() {
        return todoTpName;
    }

    public void setTodoTpLink(String todoTpLink) {
        this.todoTpLink = todoTpLink;
    }

    public String getTodoTpLink() {
        return todoTpLink;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setTodoType(String todoType) {
        this.todoType = todoType;
    }

    public String getTodoType() {
        return todoType;
    }

    public void setTodoDataType(String todoDataType) {
        this.todoDataType = todoDataType;
    }

    public String getTodoDataType() {
        return todoDataType;
    }

    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getPushStatus() {
        return pushStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("todoId", getTodoId())
                .append("businessKey", getBusinessKey())
                .append("todoTitle", getTodoTitle())
                .append("applyId", getApplyId())
                .append("todoText", getTodoText())
                .append("todoApplicant", getTodoApplicant())
                .append("todoUrl", getTodoUrl())
                .append("todoHandler", getTodoHandler())
                .append("todoHandlerName", getTodoHandlerName())
                .append("todoTpName", getTodoTpName())
                .append("todoTpLink", getTodoTpLink())
                .append("createTime", getCreateTime())
                .append("modifyTime", getModifyTime())
                .append("todoType", getTodoType())
                .append("todoDataType", getTodoDataType())
                .append("pushStatus", getPushStatus())
                .toString();
    }
}
