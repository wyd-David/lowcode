package com.yabushan.datasource.domain;


import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 作业脏数据对象 dataflow_job_task_instance_bad_data
 *
 * @author yabushan
 * @date 2023-11-19
 */

@ApiModel(value = "作业脏数据对象")
public class DataflowJobTaskInstanceBadData extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String badDataId;
            /** 任务实例id */
    @Excel(name = "任务实例id")
    @ApiModelProperty("任务实例id")
    private String taskInstanceId;
            /** 规则id */
    @Excel(name = "规则id")
    @ApiModelProperty("规则id")
    private String ruleId;
            /** 规则名称 */
    @Excel(name = "规则名称")
    @ApiModelProperty("规则名称")
    private String ruleName;
            /** 脏数据文件地址 */
    @Excel(name = "脏数据文件地址")
    @ApiModelProperty("脏数据文件地址")
    private String badDataFilePath;
            /** 数据条数 */
    @Excel(name = "数据条数")
    @ApiModelProperty("数据条数")
    private Long recordCount;
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
                        public void setBadDataId(String badDataId)
            {
            this.badDataId = badDataId;
            }

    public String getBadDataId()
            {
            return badDataId;
            }
                            public void setTaskInstanceId(String taskInstanceId)
            {
            this.taskInstanceId = taskInstanceId;
            }

    public String getTaskInstanceId()
            {
            return taskInstanceId;
            }
                            public void setRuleId(String ruleId)
            {
            this.ruleId = ruleId;
            }

    public String getRuleId()
            {
            return ruleId;
            }
                            public void setRuleName(String ruleName)
            {
            this.ruleName = ruleName;
            }

    public String getRuleName()
            {
            return ruleName;
            }
                            public void setBadDataFilePath(String badDataFilePath)
            {
            this.badDataFilePath = badDataFilePath;
            }

    public String getBadDataFilePath()
            {
            return badDataFilePath;
            }
                            public void setRecordCount(Long recordCount)
            {
            this.recordCount = recordCount;
            }

    public Long getRecordCount()
            {
            return recordCount;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("badDataId", getBadDataId())
                                .append("taskInstanceId", getTaskInstanceId())
                                .append("ruleId", getRuleId())
                                .append("ruleName", getRuleName())
                                .append("badDataFilePath", getBadDataFilePath())
                                .append("recordCount", getRecordCount())
            .toString();
        }
        }
