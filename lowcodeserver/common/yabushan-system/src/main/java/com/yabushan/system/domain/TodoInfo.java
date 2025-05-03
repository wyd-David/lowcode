package com.yabushan.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.yabushan.common.core.domain.BaseEntity;
import com.yabushan.common.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 待办待阅对象 todo_info
 *
 * @author 姜森焱
 * @date 2021-09-16
 */

@ApiModel(value = "待办待阅对象")
public class TodoInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** id */
    @ApiModelProperty("id")
    private String todoId;
    /** 业务主键 */
    @Excel(name = "业务主键")
    @ApiModelProperty("业务主键")
    private String businessKey;
    /** 代办待阅标题 */
    @Excel(name = "代办待阅标题")
    @ApiModelProperty("代办待阅标题")
    private String todoTitle;
    /** 应用ID */
    @Excel(name = "应用ID")
    @ApiModelProperty("应用ID")
    private String applyId;
    /** 待办内容 */
    @Excel(name = "待办内容")
    @ApiModelProperty("待办内容")
    private String todoText;
    /** 发起人/申请人 */
    @Excel(name = "发起人/申请人")
    @ApiModelProperty("发起人/申请人")
    private String todoApplicant;

    /** 发起人/申请人 */
    @Excel(name = "发起人/申请人名称")
    @ApiModelProperty("发起人/申请人名称")
    private String todoApplicantName;

    /** 待办URL */
    @Excel(name = "待办URL")
    @ApiModelProperty("待办URL")
    private String todoUrl;
    /** 处理人账号 */
    @Excel(name = "处理人账号")
    @ApiModelProperty("处理人账号")
    private String todoHandler;
    /** 处理人名称 */
    @Excel(name = "处理人名称")
    @ApiModelProperty("处理人名称")
    private String todoHandlerName;
    /** 所属工作流程名称 */
    @Excel(name = "所属工作流程名称")
    @ApiModelProperty("所属工作流程名称")
    private String todoTpName;
    /** 当前处理环节 */
    @Excel(name = "当前处理环节")
    @ApiModelProperty("当前处理环节")
    private String todoTpLink;
    /** 待办完成时间 */
    @ApiModelProperty("待办完成时间")
    /** 更新时间 */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    /** 1：已处理，0：未处理 */
    @Excel(name = "1：已处理，0：未处理")
    @ApiModelProperty("1：已处理，0：未处理")
    private String todoType;
    /** 1-待办，2-已办,3-待阅，4-已阅 */
    @Excel(name = "1-待办，2-已办,3-待阅，4-已阅")
    @ApiModelProperty("1-待办，2-已办,3-待阅，4-已阅，9-删除")
    private String todoDataType;
    /** 1-未推送，2-推送，3-未开放推送权限,推送门户系统 */
    @Excel(name = "1-未推送，2-推送，3-未开放推送权限,推送门户系统")
    @ApiModelProperty("1-未推送，2-推送，3-未开放推送权限,推送门户系统")
    private String pushStatus;
    /** 任务类型1：activiti,2:非流程待办待阅，3：bpm流程 */
    @Excel(name = "任务类型1：activiti,2:非流程待办待阅，3：bpm流程")
    @ApiModelProperty("任务类型1：activiti,2:非流程待办待阅，3：bpm流程")
    private String taskType;
    /** 任务ID,同一个任务有多个人处理时，多条记录的待办ID一致 */
    @Excel(name = "任务ID,同一个任务有多个人处理时，多条记录的待办ID一致")
    @ApiModelProperty("任务ID,同一个任务有多个人处理时，多条记录的待办ID一致")
    private String taskId;
    /** PC端的URL地址 */
    @Excel(name = "PC端的URL地址")
    @ApiModelProperty("PC端的URL地址")
    private String pcUrl;
    /** 实际处理人 */
    @Excel(name = "实际处理人")
    @ApiModelProperty("实际处理人")
    private String realDealUser;
    /** 实际处理人姓名 */
    @Excel(name = "实际处理人姓名")
    @ApiModelProperty("实际处理人姓名")
    private String realDealUserName;
    @Excel(name = "导入状态")
    private String importStatus;

    private String createTimeText;

    private String[] applyIdList;

    /**
     * 应用类型
     * @return
     */
    private String applyType;

    /**
     * 多条件搜索待办
     */
    private String todoInfoOrS;

    public String getTodoInfoOrS() {
        return todoInfoOrS;
    }

    public void setTodoInfoOrS(String todoInfoOrS) {
        this.todoInfoOrS = todoInfoOrS;
    }

    /**
     * 应用表父id
     * @return
     */
    private String parentId;

    /**
     * 查询条件
     */
    private String condition;

    private String todoUrlLike;


    public String getTodoUrlLike() {
        return todoUrlLike;
    }

    public void setTodoUrlLike(String todoUrlLike) {
        this.todoUrlLike = todoUrlLike;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setCreateTimeText(String createTimeText) {
        this.createTimeText = createTimeText;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String[] getApplyIdList() {
        return applyIdList;
    }

    public void setApplyIdList(String[] applyIdList) {
        this.applyIdList = applyIdList;
    }

    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }
    /**
     * 禁用状态  0启用,1禁用
     */
    private String disableEnableState;

    public String getDisableEnableState() {
        return disableEnableState;
    }

    public void setDisableEnableState(String disableEnableState) {
        this.disableEnableState = disableEnableState;
    }
    public void setTodoId(String todoId)
    {
        this.todoId = todoId;
    }

    public String getTodoId()
    {
        return todoId;
    }
    public void setBusinessKey(String businessKey)
    {
        this.businessKey = businessKey;
    }

    public String getBusinessKey()
    {
        return businessKey;
    }
    public void setTodoTitle(String todoTitle)
    {
        this.todoTitle = todoTitle;
    }

    public String getTodoTitle()
    {
        return todoTitle;
    }
    public void setApplyId(String applyId)
    {
        this.applyId = applyId;
    }

    public String getApplyId()
    {
        return applyId;
    }
    public void setTodoText(String todoText)
    {
        this.todoText = todoText;
    }

    public String getTodoText()
    {
        return todoText;
    }
    public void setTodoApplicant(String todoApplicant)
    {
        this.todoApplicant = todoApplicant;
    }

    public String getTodoApplicant()
    {
        return todoApplicant;
    }
    public void setTodoUrl(String todoUrl)
    {
        this.todoUrl = todoUrl;
    }

    public String getTodoUrl()
    {
        return todoUrl;
    }
    public void setTodoHandler(String todoHandler)
    {
        this.todoHandler = todoHandler;
    }

    public String getTodoHandler()
    {
        return todoHandler;
    }
    public void setTodoHandlerName(String todoHandlerName)
    {
        this.todoHandlerName = todoHandlerName;
    }

    public String getTodoHandlerName()
    {
        return todoHandlerName;
    }
    public void setTodoTpName(String todoTpName)
    {
        this.todoTpName = todoTpName;
    }

    public String getTodoTpName()
    {
        return todoTpName;
    }
    public void setTodoTpLink(String todoTpLink)
    {
        this.todoTpLink = todoTpLink;
    }

    public String getTodoTpLink()
    {
        return todoTpLink;
    }
    public void setModifyTime(Date modifyTime)
    {
        this.modifyTime = modifyTime;
    }

    public Date getModifyTime()
    {
        return modifyTime;
    }
    public void setTodoType(String todoType)
    {
        this.todoType = todoType;
    }

    public String getTodoType()
    {
        return todoType;
    }
    public void setTodoDataType(String todoDataType)
    {
        this.todoDataType = todoDataType;
    }

    public String getTodoDataType()
    {
        return todoDataType;
    }
    public void setPushStatus(String pushStatus)
    {
        this.pushStatus = pushStatus;
    }

    public String getPushStatus()
    {
        return pushStatus;
    }
    public void setTaskType(String taskType)
    {
        this.taskType = taskType;
    }

    public String getTaskType()
    {
        return taskType;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    public String getTaskId()
    {
        return taskId;
    }
    public void setPcUrl(String pcUrl)
    {
        this.pcUrl = pcUrl;
    }

    public String getPcUrl()
    {
        return pcUrl;
    }
    public void setRealDealUser(String realDealUser)
    {
        this.realDealUser = realDealUser;
    }

    public String getRealDealUser()
    {
        return realDealUser;
    }
    public void setRealDealUserName(String realDealUserName)
    {
        this.realDealUserName = realDealUserName;
    }

    public String getRealDealUserName()
    {
        return realDealUserName;
    }

    public String getTodoApplicantName() {
        return todoApplicantName;
    }

    public void setTodoApplicantName(String todoApplicantName) {
        this.todoApplicantName = todoApplicantName;
    }

    public String getCreateTimeText(){
        if(this.getCreateTime()!=null){
            return DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss",this.getCreateTime());
        }else{
            return "";
        }

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
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
                .append("createBy", getCreateBy())
                .append("taskType", getTaskType())
                .append("taskId", getTaskId())
                .append("pcUrl", getPcUrl())
                .append("realDealUser", getRealDealUser())
                .append("realDealUserName", getRealDealUserName())
                .toString();
    }
}
