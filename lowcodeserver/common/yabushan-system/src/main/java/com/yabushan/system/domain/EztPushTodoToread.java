package com.yabushan.system.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 推送统一待办消息（包括待办、待阅）对象 ezt_push_todo_toread
 *
 * @author yabushan
 * @date 2022-04-28
 */

@ApiModel(value = "推送统一待办消息（包括待办、待阅）对象")
public class EztPushTodoToread extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private String id;
    /**
     * 任务id
     */
    @Excel(name = "任务id")
    @ApiModelProperty("任务id")
    private String taskId;
    /**
     * 业务编号 (源系统唯一)
     */
    @Excel(name = "业务编号 (源系统唯一)")
    @ApiModelProperty("业务编号 (源系统唯一)")
    private String bid;
    /**
     * 预警工单汇总id
     */
    @Excel(name = "预警工单汇总id")
    @ApiModelProperty("预警工单汇总id")
    private String orderId;
    /**
     * 预警工单标题
     */
    @Excel(name = "预警工单标题")
    @ApiModelProperty("预警工单标题")
    private String title;
    /**
     * 业务类型 （10=待办，11=已办，20=待阅，21=已阅）
     */
    @ApiModelProperty("业务类型 （10=待办，11=已办，20=待阅，21=已阅）")
    private String type;
    /**
     * 发起人uid （portal账号）
     */
    @ApiModelProperty("发起人uid （portal账号）")
    private String launcher;
    /**
     * 发起时间 （yyyymmddhh24miss）
     */
    @ApiModelProperty("发起时间 （yyyymmddhh24miss）")
    private String launchTime;
    /**
     * 处理人uid （portal账号）
     */
    @ApiModelProperty("处理人uid （portal账号）")
    private String processor;
    /**
     * 业务操作 （1=新增，2	=修改，3=删除）
     */
    @ApiModelProperty("业务操作 （1=新增，2	=修改，3=删除）")
    private String action;
    /**
     * 发起人意见
     */
    @Excel(name = "发起人意见")
    @ApiModelProperty("发起人意见")
    private String opinion;
    /**
     * 消息内容 (用于消息内容展示)
     */
    @Excel(name = "消息内容 (用于消息内容展示)")
    @ApiModelProperty("消息内容 (用于消息内容展示)")
    private String content;
    /**
     * 消息跳转地址
     */
    @Excel(name = "消息跳转地址")
    @ApiModelProperty("消息跳转地址")
    private String url;
    /**
     * 待办处理时间（yyyymmddhh24miss）
     */
    @ApiModelProperty("待办处理时间（yyyymmddhh24miss）")
    private String dealTime;
    /**
     * 推送状态 （1-未推送，2-推送）
     */
    @ApiModelProperty("推送状态 （1-未推送，2-推送）")
    private String pushStatus;
    /**
     * 重推次数
     */
    @Excel(name = "重推次数")
    @ApiModelProperty("重推次数")
    private Long retryCount;
    /**
     * 推送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "推送时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("推送时间")
    private Date pushTime;
    @Excel(name = "导入状态")
    private String importStatus;

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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBid() {
        return bid;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setLauncher(String launcher) {
        this.launcher = launcher;
    }

    public String getLauncher() {
        return launcher;
    }

    public void setLaunchTime(String launchTime) {
        this.launchTime = launchTime;
    }

    public String getLaunchTime() {
        return launchTime;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getProcessor() {
        return processor;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    public String getDealTime() {
        return dealTime;
    }

    public void setPushStatus(String pushStatus) {
        this.pushStatus = pushStatus;
    }

    public String getPushStatus() {
        return pushStatus;
    }

    public void setRetryCount(Long retryCount) {
        this.retryCount = retryCount;
    }

    public Long getRetryCount() {
        return retryCount;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Date getPushTime() {
        return pushTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("taskId", getTaskId())
                .append("bid", getBid())
                .append("orderId", getOrderId())
                .append("title", getTitle())
                .append("type", getType())
                .append("launcher", getLauncher())
                .append("launchTime", getLaunchTime())
                .append("processor", getProcessor())
                .append("action", getAction())
                .append("opinion", getOpinion())
                .append("content", getContent())
                .append("url", getUrl())
                .append("dealTime", getDealTime())
                .append("pushStatus", getPushStatus())
                .append("retryCount", getRetryCount())
                .append("pushTime", getPushTime())
                .append("createTime", getCreateTime())
                .toString();
    }
}
