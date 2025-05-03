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
 * 作业任务实例对象 dataflow_job_task_instance
 *
 * @author yabushan
 * @date 2023-11-19
 */

@ApiModel(value = "作业任务实例对象")
public class DataflowJobTaskInstance extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 任务实例ID */
    @ApiModelProperty("任务实例ID")
    private String jobTaskInstanceId;
    /** 作业实例ID */
    @Excel(name = "作业实例ID")
    @ApiModelProperty("作业实例ID")
    private String jobInstanceId;
    /** 任务ID */
    @Excel(name = "任务ID")
    @ApiModelProperty("任务ID")
    private String jobTaskId;
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
    private Long inputByteSize;
    /** $column.columnComment */
    @Excel(name = "任务状态")
    @ApiModelProperty("$column.columnComment")
    private Long inputRecordCount;
    /** $column.columnComment */
    @Excel(name = "任务状态")
    @ApiModelProperty("$column.columnComment")
    private Long outputByteSize;
    /** $column.columnComment */
    @Excel(name = "任务状态")
    @ApiModelProperty("$column.columnComment")
    private Long outputRecordCount;
    /** $column.columnComment */
    @Excel(name = "任务状态")
    @ApiModelProperty("$column.columnComment")
    private Long discardRecordCount;
    /** $column.columnComment */
    @Excel(name = "任务状态")
    @ApiModelProperty("$column.columnComment")
    private Long affectRecordCount;
    /** $column.columnComment */
    @Excel(name = "任务状态")
    @ApiModelProperty("$column.columnComment")
    private String errorMessage;
    /** 业务数据的最大时间 */
    @Excel(name = "业务数据的最大时间")
    @ApiModelProperty("业务数据的最大时间")
    private String dataMaxBusinessTime;
    /** 业务数据的最小时间 */
    @Excel(name = "业务数据的最小时间")
    @ApiModelProperty("业务数据的最小时间")
    private String dataMinBusinessTime;
    /** 数据文件目录 */
    @Excel(name = "数据文件目录")
    @ApiModelProperty("数据文件目录")
    private String dataPath;
    /** 脏数据文件路径 */
    @Excel(name = "脏数据文件路径")
    @ApiModelProperty("脏数据文件路径")
    private String badDataPath;
    /** 日志文件路径 */
    @Excel(name = "日志文件路径")
    @ApiModelProperty("日志文件路径")
    private String logPath;
    /** 输入数据每秒速度 */
    @Excel(name = "输入数据每秒速度")
    @ApiModelProperty("输入数据每秒速度")
    private Long inputSpeedSecond;
    /** 节点运行时变量 */
    @Excel(name = "节点运行时变量")
    @ApiModelProperty("节点运行时变量")
    private String variables;
    /** $column.columnComment */
    @Excel(name = "节点运行时变量")
    @ApiModelProperty("$column.columnComment")
    private Long badDataRecordCount;
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
    public void setJobTaskInstanceId(String jobTaskInstanceId)
    {
        this.jobTaskInstanceId = jobTaskInstanceId;
    }

    public String getJobTaskInstanceId()
    {
        return jobTaskInstanceId;
    }
    public void setJobInstanceId(String jobInstanceId)
    {
        this.jobInstanceId = jobInstanceId;
    }

    public String getJobInstanceId()
    {
        return jobInstanceId;
    }
    public void setJobTaskId(String jobTaskId)
    {
        this.jobTaskId = jobTaskId;
    }

    public String getJobTaskId()
    {
        return jobTaskId;
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
    public void setInputByteSize(Long inputByteSize)
    {
        this.inputByteSize = inputByteSize;
    }

    public Long getInputByteSize()
    {
        return inputByteSize;
    }
    public void setInputRecordCount(Long inputRecordCount)
    {
        this.inputRecordCount = inputRecordCount;
    }

    public Long getInputRecordCount()
    {
        return inputRecordCount;
    }
    public void setOutputByteSize(Long outputByteSize)
    {
        this.outputByteSize = outputByteSize;
    }

    public Long getOutputByteSize()
    {
        return outputByteSize;
    }
    public void setOutputRecordCount(Long outputRecordCount)
    {
        this.outputRecordCount = outputRecordCount;
    }

    public Long getOutputRecordCount()
    {
        return outputRecordCount;
    }
    public void setDiscardRecordCount(Long discardRecordCount)
    {
        this.discardRecordCount = discardRecordCount;
    }

    public Long getDiscardRecordCount()
    {
        return discardRecordCount;
    }
    public void setAffectRecordCount(Long affectRecordCount)
    {
        this.affectRecordCount = affectRecordCount;
    }

    public Long getAffectRecordCount()
    {
        return affectRecordCount;
    }
    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }
    public void setDataMaxBusinessTime(String dataMaxBusinessTime)
    {
        this.dataMaxBusinessTime = dataMaxBusinessTime;
    }

    public String getDataMaxBusinessTime()
    {
        return dataMaxBusinessTime;
    }
    public void setDataMinBusinessTime(String dataMinBusinessTime)
    {
        this.dataMinBusinessTime = dataMinBusinessTime;
    }

    public String getDataMinBusinessTime()
    {
        return dataMinBusinessTime;
    }
    public void setDataPath(String dataPath)
    {
        this.dataPath = dataPath;
    }

    public String getDataPath()
    {
        return dataPath;
    }
    public void setBadDataPath(String badDataPath)
    {
        this.badDataPath = badDataPath;
    }

    public String getBadDataPath()
    {
        return badDataPath;
    }
    public void setLogPath(String logPath)
    {
        this.logPath = logPath;
    }

    public String getLogPath()
    {
        return logPath;
    }
    public void setInputSpeedSecond(Long inputSpeedSecond)
    {
        this.inputSpeedSecond = inputSpeedSecond;
    }

    public Long getInputSpeedSecond()
    {
        return inputSpeedSecond;
    }
    public void setVariables(String variables)
    {
        this.variables = variables;
    }

    public String getVariables()
    {
        return variables;
    }
    public void setBadDataRecordCount(Long badDataRecordCount)
    {
        this.badDataRecordCount = badDataRecordCount;
    }

    public Long getBadDataRecordCount()
    {
        return badDataRecordCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("jobTaskInstanceId", getJobTaskInstanceId())
                .append("jobInstanceId", getJobInstanceId())
                .append("jobTaskId", getJobTaskId())
                .append("createTime", getCreateTime())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("status", getStatus())
                .append("inputByteSize", getInputByteSize())
                .append("inputRecordCount", getInputRecordCount())
                .append("outputByteSize", getOutputByteSize())
                .append("outputRecordCount", getOutputRecordCount())
                .append("discardRecordCount", getDiscardRecordCount())
                .append("affectRecordCount", getAffectRecordCount())
                .append("errorMessage", getErrorMessage())
                .append("dataMaxBusinessTime", getDataMaxBusinessTime())
                .append("dataMinBusinessTime", getDataMinBusinessTime())
                .append("dataPath", getDataPath())
                .append("badDataPath", getBadDataPath())
                .append("logPath", getLogPath())
                .append("inputSpeedSecond", getInputSpeedSecond())
                .append("variables", getVariables())
                .append("badDataRecordCount", getBadDataRecordCount())
                .toString();
    }
}
