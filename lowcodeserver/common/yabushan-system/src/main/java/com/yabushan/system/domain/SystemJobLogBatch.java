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
 * 日志数据批次 对象 system_job_log_batch
 *
 * @author yabushan
 * @date 2021-11-16
 */

@ApiModel(value = "日志数据批次 对象")
public class SystemJobLogBatch extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String id;
    /**
     * 批次ID
     */
    @Excel(name = "批次ID")
    @ApiModelProperty("批次ID")
    private String dataBatchId;
    /**
     * 数据ID
     */
    @Excel(name = "数据ID")
    @ApiModelProperty("数据ID")
    private String dataId;
    /**
     * 数据JSON字符串
     */
    @Excel(name = "数据JSON字符串")
    @ApiModelProperty("数据JSON字符串")
    private String dataString;

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
     * 创建人
     */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private String createdBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, format = "yyyy-MM-dd")
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

    public void setDataBatchId(String dataBatchId) {
        this.dataBatchId = dataBatchId;
    }

    public String getDataBatchId() {
        return dataBatchId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataString(String dataString) {
        this.dataString = dataString;
    }

    public String getDataString() {
        return dataString;
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

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobExceptionInfo() {
        return jobExceptionInfo;
    }

    public void setJobExceptionInfo(String jobExceptionInfo) {
        this.jobExceptionInfo = jobExceptionInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("dataBatchId", getDataBatchId())
                .append("dataId", getDataId())
                .append("dataString", getDataString())
                .append("createdBy", getCreatedBy())
                .append("createdTime", getCreatedTime())
                .append("updatedBy", getUpdatedBy())
                .append("updatedTime", getUpdatedTime())
                .toString();
    }
}
