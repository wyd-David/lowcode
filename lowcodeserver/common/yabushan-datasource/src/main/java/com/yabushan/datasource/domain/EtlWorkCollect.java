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
 * 作业开发对象 etl_work_collect
 *
 * @author yabushan
 * @date 2024-01-21
 */

@ApiModel(value = "作业开发对象")
public class EtlWorkCollect extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 主键ID */
    @ApiModelProperty("主键ID")
    private String collectId;
            /** 采集作业名称 */
    @Excel(name = "采集作业名称")
    @ApiModelProperty("采集作业名称")
    private String collectName;
            /** 被采集表Id */
    @Excel(name = "被采集表Id")
    @ApiModelProperty("被采集表Id")
    private String collectResouceId;
            /** 被采集表名称 */
    @Excel(name = "被采集表名称")
    @ApiModelProperty("被采集表名称")
    private String collectTableName;
            /** 采集入库到的数据源ID */
    @Excel(name = "采集入库到的数据源ID")
    @ApiModelProperty("采集入库到的数据源ID")
    private String collectToDatasourceid;
            /** 采集入库到的表名称 */
    @Excel(name = "采集入库到的表名称")
    @ApiModelProperty("采集入库到的表名称")
    private String collectToTableName;
                    /** 作业类型，1:采集，2：抽取 */
    @Excel(name = "作业类型，1:采集，2：抽取")
    @ApiModelProperty("作业类型，1:采集，2：抽取")
    private String collectType;
            /** 是否增量,1:是,2:否 */
    @Excel(name = "是否增量,1:是,2:否")
    @ApiModelProperty("是否增量,1:是,2:否")
    private String collectAdd;
            /** 增量字段名称 */
    @Excel(name = "增量字段名称")
    @ApiModelProperty("增量字段名称")
    private String fieldName;
            /** 增量字段值 */
    @Excel(name = "增量字段值")
    @ApiModelProperty("增量字段值")
    private String fieldValue;
            /** 增量字段类型 */
    @Excel(name = "增量字段类型")
    @ApiModelProperty("增量字段类型")
    private String fieldType;
            /** 最后运行时间 */
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @Excel(name = "最后运行时间", width = 30)
    @ApiModelProperty("最后运行时间")
    private Date lastUpdateTime;
    @ApiModelProperty("抽取前执行脚本")
    private String beforeSql;
    @ApiModelProperty("抽取后执行脚本")
    private String afterSql;
    
    
    
    /** 最后运行状态0:初始，1:运行中,2:成功,3:失败，4:等待运行,5:依赖条件未发生 */
    @Excel(name = "最后运行状态0:初始，1:运行中,2:成功,3:失败，4:等待运行,5:依赖条件未发生")
    @ApiModelProperty("最后运行状态0:初始，1:运行中,2:成功,3:失败，4:等待运行,5:依赖条件未发生")
    private String lastStatus;
            /** 抽取时读取节点的api_code */
    @Excel(name = "抽取时读取节点的api_code")
    @ApiModelProperty("抽取时读取节点的api_code")
    private String apiCodeIn;
            /** 抽取时输出节点的api_code */
    @Excel(name = "抽取时输出节点的api_code")
    @ApiModelProperty("抽取时输出节点的api_code")
    private String apiCodeOut;
            /** 最后执行结果 */
    @Excel(name = "最后执行结果")
    @ApiModelProperty("最后执行结果")
    private String collectResult;
            /** 运行节点 */
    @Excel(name = "运行节点")
    @ApiModelProperty("运行节点")
    private String runNodeId;
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
                        public void setCollectId(String collectId)
            {
            this.collectId = collectId;
            }

    public String getCollectId()
            {
            return collectId;
            }
                            public void setCollectName(String collectName)
            {
            this.collectName = collectName;
            }

    public String getCollectName()
            {
            return collectName;
            }
                            public void setCollectResouceId(String collectResouceId)
            {
            this.collectResouceId = collectResouceId;
            }

    public String getCollectResouceId()
            {
            return collectResouceId;
            }
                            public void setCollectTableName(String collectTableName)
            {
            this.collectTableName = collectTableName;
            }

    public String getCollectTableName()
            {
            return collectTableName;
            }
                            public void setCollectToDatasourceid(String collectToDatasourceid)
            {
            this.collectToDatasourceid = collectToDatasourceid;
            }

    public String getCollectToDatasourceid()
            {
            return collectToDatasourceid;
            }
                            public void setCollectToTableName(String collectToTableName)
            {
            this.collectToTableName = collectToTableName;
            }

    public String getCollectToTableName()
            {
            return collectToTableName;
            }
                                    public void setCollectType(String collectType)
            {
            this.collectType = collectType;
            }

    public String getCollectType()
            {
            return collectType;
            }
                            public void setCollectAdd(String collectAdd)
            {
            this.collectAdd = collectAdd;
            }

    public String getCollectAdd()
            {
            return collectAdd;
            }
                            public void setFieldName(String fieldName)
            {
            this.fieldName = fieldName;
            }

    public String getFieldName()
            {
            return fieldName;
            }
                            public void setFieldValue(String fieldValue)
            {
            this.fieldValue = fieldValue;
            }

    public String getFieldValue()
            {
            return fieldValue;
            }
                            public void setFieldType(String fieldType)
            {
            this.fieldType = fieldType;
            }

    public String getFieldType()
            {
            return fieldType;
            }
                            public void setLastUpdateTime(Date lastUpdateTime)
            {
            this.lastUpdateTime = lastUpdateTime;
            }

    public Date getLastUpdateTime()
            {
            return lastUpdateTime;
            }
                            public void setLastStatus(String lastStatus)
            {
            this.lastStatus = lastStatus;
            }

    public String getLastStatus()
            {
            return lastStatus;
            }
                            public void setApiCodeIn(String apiCodeIn)
            {
            this.apiCodeIn = apiCodeIn;
            }

    public String getApiCodeIn()
            {
            return apiCodeIn;
            }
                            public void setApiCodeOut(String apiCodeOut)
            {
            this.apiCodeOut = apiCodeOut;
            }

    public String getApiCodeOut()
            {
            return apiCodeOut;
            }
                            public void setCollectResult(String collectResult)
            {
            this.collectResult = collectResult;
            }

    public String getCollectResult()
            {
            return collectResult;
            }
                            public void setRunNodeId(String runNodeId)
            {
            this.runNodeId = runNodeId;
            }

    public String getRunNodeId()
            {
            return runNodeId;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("collectId", getCollectId())
                                .append("collectName", getCollectName())
                                .append("collectResouceId", getCollectResouceId())
                                .append("collectTableName", getCollectTableName())
                                .append("collectToDatasourceid", getCollectToDatasourceid())
                                .append("collectToTableName", getCollectToTableName())
                                .append("createBy", getCreateBy())
                                .append("createTime", getCreateTime())
                                .append("collectType", getCollectType())
                                .append("collectAdd", getCollectAdd())
                                .append("fieldName", getFieldName())
                                .append("fieldValue", getFieldValue())
                                .append("fieldType", getFieldType())
                                .append("lastUpdateTime", getLastUpdateTime())
                                .append("lastStatus", getLastStatus())
                                .append("apiCodeIn", getApiCodeIn())
                                .append("apiCodeOut", getApiCodeOut())
                                .append("collectResult", getCollectResult())
                                .append("runNodeId", getRunNodeId())
            .toString();
        }

            public String getBeforeSql() {
                return beforeSql;
            }

            public void setBeforeSql(String beforeSql) {
                this.beforeSql = beforeSql;
            }

            public String getAfterSql() {
                return afterSql;
            }

            public void setAfterSql(String afterSql) {
                this.afterSql = afterSql;
            }
        }
