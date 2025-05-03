package com.yabushan.datasource.domain;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 作业运行实例对象 dataflow_job_instance
 *
 * @author yabushan
 * @date 2023-11-19
 */

@ApiModel(value = "作业运行实例对象")
public class DataflowJobInstance extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 作业实例ID */
    @ApiModelProperty("作业实例ID")
    private String jobInstanceId;
            /** 作业ID */
    @Excel(name = "作业ID")
    @ApiModelProperty("作业ID")
    private String jobId;
                /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("开始时间")
    private Date startTime;
            /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("结束时间")
    private Date endTime;
            /** 任务状态 */
    @Excel(name = "任务状态")
    @ApiModelProperty("任务状态")
    private String status;
            /** $column.columnComment */
    @Excel(name = "任务状态")
    @ApiModelProperty("$column.columnComment")
    private String errorMessage;
            /** 版本，创建作业实例时与Job版本对应 */
    @Excel(name = "版本，创建作业实例时与Job版本对应")
    @ApiModelProperty("版本，创建作业实例时与Job版本对应")
    private Long versionNo;
            /** 首次运行时间，按照作业的业务时间规则推算出来 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("首次运行时间，按照作业的业务时间规则推算出来")
    private Date firstRunTime;
            /** 事件时间（业务时间） */
    @Excel(name = "事件时间", readConverterExp = "业=务时间")
    @ApiModelProperty("事件时间（业务时间）")
    private Date eventTime;
            /** 重试次数 */
    @Excel(name = "重试次数")
    @ApiModelProperty("重试次数")
    private Long retryCount;
            /** 重新调度次数 */
    @Excel(name = "重新调度次数")
    @ApiModelProperty("重新调度次数")
    private Long rescheduleCount;
            /** 重试时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("重试时间")
    private Date nextRetryTime;
            /** 是否取消重试 */
    @Excel(name = "是否取消重试")
    @ApiModelProperty("是否取消重试")
    private String isCancelRetry;
            /** 运行的服务器执行节点 */
    @Excel(name = "运行的服务器执行节点")
    @ApiModelProperty("运行的服务器执行节点")
    private String runExecutor;
            /** 日志存储路径 */
    @Excel(name = "日志存储路径")
    @ApiModelProperty("日志存储路径")
    private String logPath;
            /** $column.columnComment */
    @Excel(name = "日志存储路径")
    @ApiModelProperty("$column.columnComment")
    private String createUserId;
            /** $column.columnComment */
    @Excel(name = "日志存储路径")
    @ApiModelProperty("$column.columnComment")
    private Long runType;
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
                        public void setJobInstanceId(String jobInstanceId)
            {
            this.jobInstanceId = jobInstanceId;
            }

    public String getJobInstanceId()
            {
            return jobInstanceId;
            }
                            public void setJobId(String jobId)
            {
            this.jobId = jobId;
            }

    public String getJobId()
            {
            return jobId;
            }
                                public void setStartTime(Date startTime)
            {
            this.startTime = startTime;
            }

    public Date getStartTime()
            {
            return startTime;
            }
                            public void setEndTime(Date endTime)
            {
            this.endTime = endTime;
            }

    public Date getEndTime()
            {
            return endTime;
            }
                            public void setStatus(String status)
            {
            this.status = status;
            }

    public String getStatus()
            {
            return status;
            }
                            public void setErrorMessage(String errorMessage)
            {
            this.errorMessage = errorMessage;
            }

    public String getErrorMessage()
            {
            return errorMessage;
            }
                            public void setVersionNo(Long versionNo)
            {
            this.versionNo = versionNo;
            }

    public Long getVersionNo()
            {
            return versionNo;
            }
                            public void setFirstRunTime(Date firstRunTime)
            {
            this.firstRunTime = firstRunTime;
            }

    public Date getFirstRunTime()
            {
            return firstRunTime;
            }
                            public void setEventTime(Date eventTime)
            {
            this.eventTime = eventTime;
            }

    public Date getEventTime()
            {
            return eventTime;
            }
                            public void setRetryCount(Long retryCount)
            {
            this.retryCount = retryCount;
            }

    public Long getRetryCount()
            {
            return retryCount;
            }
                            public void setRescheduleCount(Long rescheduleCount)
            {
            this.rescheduleCount = rescheduleCount;
            }

    public Long getRescheduleCount()
            {
            return rescheduleCount;
            }
                            public void setNextRetryTime(Date nextRetryTime)
            {
            this.nextRetryTime = nextRetryTime;
            }

    public Date getNextRetryTime()
            {
            return nextRetryTime;
            }
                            public void setIsCancelRetry(String isCancelRetry)
            {
            this.isCancelRetry = isCancelRetry;
            }

    public String getIsCancelRetry()
            {
            return isCancelRetry;
            }
                            public void setRunExecutor(String runExecutor)
            {
            this.runExecutor = runExecutor;
            }

    public String getRunExecutor()
            {
            return runExecutor;
            }
                            public void setLogPath(String logPath)
            {
            this.logPath = logPath;
            }

    public String getLogPath()
            {
            return logPath;
            }
                            public void setCreateUserId(String createUserId)
            {
            this.createUserId = createUserId;
            }

    public String getCreateUserId()
            {
            return createUserId;
            }
                            public void setRunType(Long runType)
            {
            this.runType = runType;
            }

    public Long getRunType()
            {
            return runType;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("jobInstanceId", getJobInstanceId())
                                .append("jobId", getJobId())
                                .append("createTime", getCreateTime())
                                .append("startTime", getStartTime())
                                .append("endTime", getEndTime())
                                .append("status", getStatus())
                                .append("errorMessage", getErrorMessage())
                                .append("versionNo", getVersionNo())
                                .append("firstRunTime", getFirstRunTime())
                                .append("eventTime", getEventTime())
                                .append("retryCount", getRetryCount())
                                .append("rescheduleCount", getRescheduleCount())
                                .append("nextRetryTime", getNextRetryTime())
                                .append("isCancelRetry", getIsCancelRetry())
                                .append("runExecutor", getRunExecutor())
                                .append("logPath", getLogPath())
                                .append("createUserId", getCreateUserId())
                                .append("runType", getRunType())
            .toString();
        }
        }
