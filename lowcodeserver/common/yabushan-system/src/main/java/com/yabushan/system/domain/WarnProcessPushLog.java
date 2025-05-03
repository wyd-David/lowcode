package com.yabushan.system.domain;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 待办推送记录对象 warn_process_push_log
 *
 * @author yabushan
 * @date 2022-04-28
 */

@ApiModel(value = "待办推送记录对象")
public class WarnProcessPushLog extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private Long id;
    /**
     * 工单id
     */
    @Excel(name = "工单id")
    @ApiModelProperty("工单id")
    private String orderId;
    /**
     * 任务id
     */
    @Excel(name = "任务id")
    @ApiModelProperty("任务id")
    private String taskId;
    /**
     * 已办
     */
    @Excel(name = "已办")
    @ApiModelProperty("已办")
    private String isDone;
    /**
     * 已读
     */
    @Excel(name = "已读")
    @ApiModelProperty("已读")
    private String isRead;
    /**
     * 节点任务
     */
    @Excel(name = "节点任务")
    @ApiModelProperty("节点任务")
    private String taskName;
    /**
     * 类型
     */
    @Excel(name = "类型")
    @ApiModelProperty("类型")
    private String type;
    /**
     * 任务名称
     */
    @Excel(name = "任务名称")
    @ApiModelProperty("任务名称")
    private String bussinessName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private Date createdDate;
    /**
     * 处理人
     */
    @Excel(name = "处理人")
    @ApiModelProperty("处理人")
    private String realHandler;
    /**
     * 处理人
     */
    @Excel(name = "处理人")
    @ApiModelProperty("处理人")
    private String realHandlerName;
    /**
     * 意见
     */
    @Excel(name = "意见")
    @ApiModelProperty("意见")
    private String opinion;
    /**
     * 状态
     */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String status;
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setIsDone(String isDone) {
        this.isDone = isDone;
    }

    public String getIsDone() {
        return isDone;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getBussinessName() {
        return bussinessName;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setRealHandler(String realHandler) {
        this.realHandler = realHandler;
    }

    public String getRealHandler() {
        return realHandler;
    }

    public void setRealHandlerName(String realHandlerName) {
        this.realHandlerName = realHandlerName;
    }

    public String getRealHandlerName() {
        return realHandlerName;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderId", getOrderId())
                .append("taskId", getTaskId())
                .append("isDone", getIsDone())
                .append("isRead", getIsRead())
                .append("taskName", getTaskName())
                .append("type", getType())
                .append("bussinessName", getBussinessName())
                .append("createdDate", getCreatedDate())
                .append("realHandler", getRealHandler())
                .append("realHandlerName", getRealHandlerName())
                .append("opinion", getOpinion())
                .append("status", getStatus())
                .toString();
    }
}
