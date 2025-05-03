package com.yabushan.system.domain.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 待办待阅对象 手动插入待办时，必须填写的字段信息
 *
 * @author 姜森焱
 * @date 2021-09-16
 */

@ApiModel(value = "待办待阅对象 手动插入待办")
public class TodoInfoParam
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Excel(name = "主键")
    @ApiModelProperty("主键")
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


    /** 任务ID,同一个任务有多个人处理时，多条记录的待办ID一致 */
    @Excel(name = "任务ID,同一个任务有多个人处理时，多条记录的待办ID一致")
    @ApiModelProperty("任务ID,同一个任务有多个人处理时，多条记录的待办ID一致")
    private String taskId;
    /** PC端的URL地址 */
    @Excel(name = "PC端的URL地址")
    @ApiModelProperty("PC端的URL地址")
    private String pcUrl;

    public TodoInfoParam(String businessKey, String todoTitle, String applyId, String todoText, String todoApplicant, String todoApplicantName, String todoUrl, String todoHandler, String todoHandlerName, String todoTpName, String todoTpLink, String taskId, String pcUrl) {
        this.businessKey = businessKey;
        this.todoTitle = todoTitle;
        this.applyId = applyId;
        this.todoText = todoText;
        this.todoApplicant = todoApplicant;
        this.todoApplicantName = todoApplicantName;
        this.todoUrl = todoUrl;
        this.todoHandler = todoHandler;
        this.todoHandlerName = todoHandlerName;
        this.todoTpName = todoTpName;
        this.todoTpLink = todoTpLink;
        this.taskId = taskId;
        this.pcUrl = pcUrl;
    }

    public TodoInfoParam() {

    }


    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    public String getTodoApplicant() {
        return todoApplicant;
    }

    public void setTodoApplicant(String todoApplicant) {
        this.todoApplicant = todoApplicant;
    }

    public String getTodoApplicantName() {
        return todoApplicantName;
    }

    public void setTodoApplicantName(String todoApplicantName) {
        this.todoApplicantName = todoApplicantName;
    }

    public String getTodoUrl() {
        return todoUrl;
    }

    public void setTodoUrl(String todoUrl) {
        this.todoUrl = todoUrl;
    }

    public String getTodoHandler() {
        return todoHandler;
    }

    public void setTodoHandler(String todoHandler) {
        this.todoHandler = todoHandler;
    }

    public String getTodoHandlerName() {
        return todoHandlerName;
    }

    public void setTodoHandlerName(String todoHandlerName) {
        this.todoHandlerName = todoHandlerName;
    }

    public String getTodoTpName() {
        return todoTpName;
    }

    public void setTodoTpName(String todoTpName) {
        this.todoTpName = todoTpName;
    }

    public String getTodoTpLink() {
        return todoTpLink;
    }

    public void setTodoTpLink(String todoTpLink) {
        this.todoTpLink = todoTpLink;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPcUrl() {
        return pcUrl;
    }

    public void setPcUrl(String pcUrl) {
        this.pcUrl = pcUrl;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }
}
