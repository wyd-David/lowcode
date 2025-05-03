package com.yabushan.datasource.domain;


import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 作业任务对象 dataflow_job_task
 *
 * @author yabushan
 * @date 2023-11-19
 */

@ApiModel(value = "作业任务对象")
public class DataflowJobTask extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 作业任务唯一ID */
    @ApiModelProperty("作业任务唯一ID")
    private String jobTaskId;
            /** 作业ID */
    @Excel(name = "作业ID")
    @ApiModelProperty("作业ID")
    private String jobId;
            /** 任务类型编码 */
    @Excel(name = "任务类型编码")
    @ApiModelProperty("任务类型编码")
    private String taskTypeCode;
            /** 任务名称 */
    @Excel(name = "任务名称")
    @ApiModelProperty("任务名称")
    private String taskName;
            /** $column.columnComment */
    @Excel(name = "任务名称")
    @ApiModelProperty("$column.columnComment")
    private String taskInputParams;
            /** 任务输出，使用JSON数组存储 */
    @Excel(name = "任务输出，使用JSON数组存储")
    @ApiModelProperty("任务输出，使用JSON数组存储")
    private String taskOutputParams;
            /** 执行器的插件类型 */
    @Excel(name = "执行器的插件类型")
    @ApiModelProperty("执行器的插件类型")
    private String executorType;
            /** 参数映射，json对象存储 */
    @Excel(name = "参数映射，json对象存储")
    @ApiModelProperty("参数映射，json对象存储")
    private String paramsMapping;
            /** 外部关联对象，如模型、数据资源、数据源等 */
    @Excel(name = "外部关联对象，如模型、数据资源、数据源等")
    @ApiModelProperty("外部关联对象，如模型、数据资源、数据源等")
    private String externalObject;
            /** $column.columnComment */
    @Excel(name = "外部关联对象，如模型、数据资源、数据源等")
    @ApiModelProperty("$column.columnComment")
    private String ruleId;
            /** $column.columnComment */
    @Excel(name = "外部关联对象，如模型、数据资源、数据源等")
    @ApiModelProperty("$column.columnComment")
    private String datasourceId;
            /** $column.columnComment */
    @Excel(name = "外部关联对象，如模型、数据资源、数据源等")
    @ApiModelProperty("$column.columnComment")
    private String resourceId;
            /** $column.columnComment */
    @Excel(name = "外部关联对象，如模型、数据资源、数据源等")
    @ApiModelProperty("$column.columnComment")
    private String businessKey;
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
                        public void setJobTaskId(String jobTaskId)
            {
            this.jobTaskId = jobTaskId;
            }

    public String getJobTaskId()
            {
            return jobTaskId;
            }
                            public void setJobId(String jobId)
            {
            this.jobId = jobId;
            }

    public String getJobId()
            {
            return jobId;
            }
                            public void setTaskTypeCode(String taskTypeCode)
            {
            this.taskTypeCode = taskTypeCode;
            }

    public String getTaskTypeCode()
            {
            return taskTypeCode;
            }
                            public void setTaskName(String taskName)
            {
            this.taskName = taskName;
            }

    public String getTaskName()
            {
            return taskName;
            }
                            public void setTaskInputParams(String taskInputParams)
            {
            this.taskInputParams = taskInputParams;
            }

    public String getTaskInputParams()
            {
            return taskInputParams;
            }
                            public void setTaskOutputParams(String taskOutputParams)
            {
            this.taskOutputParams = taskOutputParams;
            }

    public String getTaskOutputParams()
            {
            return taskOutputParams;
            }
                            public void setExecutorType(String executorType)
            {
            this.executorType = executorType;
            }

    public String getExecutorType()
            {
            return executorType;
            }
                            public void setParamsMapping(String paramsMapping)
            {
            this.paramsMapping = paramsMapping;
            }

    public String getParamsMapping()
            {
            return paramsMapping;
            }
                            public void setExternalObject(String externalObject)
            {
            this.externalObject = externalObject;
            }

    public String getExternalObject()
            {
            return externalObject;
            }
                            public void setRuleId(String ruleId)
            {
            this.ruleId = ruleId;
            }

    public String getRuleId()
            {
            return ruleId;
            }
                            public void setDatasourceId(String datasourceId)
            {
            this.datasourceId = datasourceId;
            }

    public String getDatasourceId()
            {
            return datasourceId;
            }
                            public void setResourceId(String resourceId)
            {
            this.resourceId = resourceId;
            }

    public String getResourceId()
            {
            return resourceId;
            }
                            public void setBusinessKey(String businessKey)
            {
            this.businessKey = businessKey;
            }

    public String getBusinessKey()
            {
            return businessKey;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("jobTaskId", getJobTaskId())
                                .append("jobId", getJobId())
                                .append("taskTypeCode", getTaskTypeCode())
                                .append("taskName", getTaskName())
                                .append("taskInputParams", getTaskInputParams())
                                .append("taskOutputParams", getTaskOutputParams())
                                .append("executorType", getExecutorType())
                                .append("paramsMapping", getParamsMapping())
                                .append("externalObject", getExternalObject())
                                .append("ruleId", getRuleId())
                                .append("datasourceId", getDatasourceId())
                                .append("resourceId", getResourceId())
                                .append("businessKey", getBusinessKey())
            .toString();
        }
        }
