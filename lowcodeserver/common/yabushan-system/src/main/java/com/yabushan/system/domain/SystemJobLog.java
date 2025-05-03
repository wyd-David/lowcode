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
 * 定时任务日志 对象 system_job_log
 *
 * @author yabushan
 * @date 2021-11-16
 */

@ApiModel(value = "定时任务日志 对象")
public class SystemJobLog extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String id;
    /**
     * 应用名称
     */
    @Excel(name = "应用名称")
    @ApiModelProperty("应用名称")
    private String applyName;
    /**
     * 具体描述
     */
    @ApiModelProperty("具体描述")
    private String applyMiaos;
    /**
     * 任务名称
     */
    @Excel(name = "任务名称")
    @ApiModelProperty("任务名称")
    private String jobName;
    /**
     * 日志信息
     */
    @ApiModelProperty("日志信息")
    private String jobMessage;
    /**
     * 执行状态 0：成功   1：失败
     */
    @Excel(name = "执行状态 0：成功   1：失败")
    @ApiModelProperty("执行状态 0：成功   1：失败")
    private String jobStatus;
    /**
     * 异常信息
     */
    @ApiModelProperty("异常信息")
    private String jobExceptionInfo;
    /**
     * IP地址
     */
    @Excel(name = "IP地址")
    @ApiModelProperty("IP地址")
    private String jobIpAddr;
    /**
     * 业务数据数量
     */
    @Excel(name = "业务数据数量")
    @ApiModelProperty("业务数据数量")
    private String dataNum;
    /**
     * 数据批次ID
     */
    @ApiModelProperty("数据批次ID")
    private String dataBatchId;
    /**
     * 执行方法
     */
    @Excel(name = "执行方法")
    @ApiModelProperty("执行方法")
    private String exeMethod;
    /**
     * 任务耗时
     */
    @ApiModelProperty("任务耗时")
    private String jobTimeConsuming;
    /**
     * 创建人
     */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private String createdBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createdTime;
    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private String updatedBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updatedTime;
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

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyMiaos(String applyMiaos) {
        this.applyMiaos = applyMiaos;
    }

    public String getApplyMiaos() {
        return applyMiaos;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobMessage(String jobMessage) {
        this.jobMessage = jobMessage;
    }

    public String getJobMessage() {
        return jobMessage;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobExceptionInfo(String jobExceptionInfo) {
        this.jobExceptionInfo = jobExceptionInfo;
    }

    public String getJobExceptionInfo() {
        return jobExceptionInfo;
    }

    public void setJobIpAddr(String jobIpAddr) {
        this.jobIpAddr = jobIpAddr;
    }

    public String getJobIpAddr() {
        return jobIpAddr;
    }

    public void setDataNum(String dataNum) {
        this.dataNum = dataNum;
    }

    public String getDataNum() {
        return dataNum;
    }

    public void setDataBatchId(String dataBatchId) {
        this.dataBatchId = dataBatchId;
    }

    public String getDataBatchId() {
        return dataBatchId;
    }

    public void setExeMethod(String exeMethod) {
        this.exeMethod = exeMethod;
    }

    public String getExeMethod() {
        return exeMethod;
    }

    public void setJobTimeConsuming(String jobTimeConsuming) {
        this.jobTimeConsuming = jobTimeConsuming;
    }

    public String getJobTimeConsuming() {
        return jobTimeConsuming;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("applyName", getApplyName())
                .append("applyMiaos", getApplyMiaos())
                .append("jobName", getJobName())
                .append("jobMessage", getJobMessage())
                .append("jobStatus", getJobStatus())
                .append("jobExceptionInfo", getJobExceptionInfo())
                .append("jobIpAddr", getJobIpAddr())
                .append("dataNum", getDataNum())
                .append("dataBatchId", getDataBatchId())
                .append("exeMethod", getExeMethod())
                .append("jobTimeConsuming", getJobTimeConsuming())
                .append("createdBy", getCreatedBy())
                .append("createdTime", getCreatedTime())
                .append("updatedBy", getUpdatedBy())
                .append("updatedTime", getUpdatedTime())
                .toString();
    }
}
