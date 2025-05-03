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
 * 作业运行的最后状态对象 dataflow_job_instance_last_state
 *
 * @author yabushan
 * @date 2023-11-19
 */

@ApiModel(value = "作业运行的最后状态对象")
public class DataflowJobInstanceLastState extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 作业ID */
    @ApiModelProperty("作业ID")
    private String jobId;
            /** 最后执行的实例 */
    @Excel(name = "最后执行的实例")
    @ApiModelProperty("最后执行的实例")
    private String lastJobInstanceId;
            /** 作业最后执行状态 */
    @Excel(name = "作业最后执行状态")
    @ApiModelProperty("作业最后执行状态")
    private String status;
            /** 最大事件时间 */
    @Excel(name = "最大事件时间")
    @ApiModelProperty("最大事件时间")
    private String maxEventTime;
            /** 最后执行时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("最后执行时间")
    private Date lastExecutionTime;
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
                        public void setJobId(String jobId)
            {
            this.jobId = jobId;
            }

    public String getJobId()
            {
            return jobId;
            }
                            public void setLastJobInstanceId(String lastJobInstanceId)
            {
            this.lastJobInstanceId = lastJobInstanceId;
            }

    public String getLastJobInstanceId()
            {
            return lastJobInstanceId;
            }
                            public void setStatus(String status)
            {
            this.status = status;
            }

    public String getStatus()
            {
            return status;
            }
                            public void setMaxEventTime(String maxEventTime)
            {
            this.maxEventTime = maxEventTime;
            }

    public String getMaxEventTime()
            {
            return maxEventTime;
            }
                            public void setLastExecutionTime(Date lastExecutionTime)
            {
            this.lastExecutionTime = lastExecutionTime;
            }

    public Date getLastExecutionTime()
            {
            return lastExecutionTime;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("jobId", getJobId())
                                .append("lastJobInstanceId", getLastJobInstanceId())
                                .append("status", getStatus())
                                .append("maxEventTime", getMaxEventTime())
                                .append("lastExecutionTime", getLastExecutionTime())
            .toString();
        }
        }
