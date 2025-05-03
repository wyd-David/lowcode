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
 * 作业对象 dataflow_job
 *
 * @author yabushan
 * @date 2023-11-19
 */

@ApiModel(value = "作业对象")
public class DataflowJob extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 作业唯一ID */
    @ApiModelProperty("作业唯一ID")
    private String jobId;
            /** 作业名称 */
    @Excel(name = "作业名称")
    @ApiModelProperty("作业名称")
    private String jobName;
            /** 1=平台内置流程，2=自定义流程，3=组合算法，4=内置模板，5=自定义模板 */
    @Excel(name = "1=平台内置流程，2=自定义流程，3=组合算法，4=内置模板，5=自定义模板")
    @ApiModelProperty("1=平台内置流程，2=自定义流程，3=组合算法，4=内置模板，5=自定义模板")
    private Long jobType;
            /** 作业状态：1、启用；2、停用；0、删除； */
    @Excel(name = "作业状态：1、启用；2、停用；0、删除；")
    @ApiModelProperty("作业状态：1、启用；2、停用；0、删除；")
    private Long status;
            /** 作业优先级，数值越大优先级越高 */
    @Excel(name = "作业优先级，数值越大优先级越高")
    @ApiModelProperty("作业优先级，数值越大优先级越高")
    private Long priority;
            /** 数据未到达，重试次数，默认0 */
    @Excel(name = "数据未到达，重试次数，默认0")
    @ApiModelProperty("数据未到达，重试次数，默认0")
    private Long tryCount;
            /** 重试频率。1、分钟；2、小时； */
    @Excel(name = "重试频率。1、分钟；2、小时；")
    @ApiModelProperty("重试频率。1、分钟；2、小时；")
    private Long tryFrequency;
            /** 重试间隔，多久重试一次 */
    @Excel(name = "重试间隔，多久重试一次")
    @ApiModelProperty("重试间隔，多久重试一次")
    private Long tryItel;
            /** 作业可以并行运行的数量，大于等于1 */
    @Excel(name = "作业可以并行运行的数量，大于等于1")
    @ApiModelProperty("作业可以并行运行的数量，大于等于1")
    private Long concurrentCount;
            /** 作业流程配置 */
    @Excel(name = "作业流程配置")
    @ApiModelProperty("作业流程配置")
    private String flowConfigJson;
            /** 作业周期：1、实时作业；2、分钟级作业；3、小时级作业；4、每日级作业；5、周级作业；6、月级作业；7、事件（手动）调度； */
    @Excel(name = "作业周期：1、实时作业；2、分钟级作业；3、小时级作业；4、每日级作业；5、周级作业；6、月级作业；7、事件", readConverterExp = "手=动")
    @ApiModelProperty("作业周期：1、实时作业；2、分钟级作业；3、小时级作业；4、每日级作业；5、周级作业；6、月级作业；7、事件（手动）调度；")
    private Long cycle;
            /** 作业版本号默认1，每保存一次，增加1 */
    @Excel(name = "作业版本号默认1，每保存一次，增加1")
    @ApiModelProperty("作业版本号默认1，每保存一次，增加1")
    private Long versionNo;
            /** corn表达式 */
    @Excel(name = "corn表达式")
    @ApiModelProperty("corn表达式")
    private String scheduleConfigJson;
            /** 下一次执行时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("下一次执行时间")
    private Date nextScheduleTime;
            /** 任务分组编码 */
    @Excel(name = "任务分组编码")
    @ApiModelProperty("任务分组编码")
    private String jobWorkGroupCode;
            /** 有效开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("有效开始时间")
    private Date startDate;
            /** 有效结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("有效结束时间")
    private Date endDate;
            /** 失败自动停用作业 */
    @Excel(name = "失败自动停用作业")
    @ApiModelProperty("失败自动停用作业")
    private Integer autoStop;
            /** 排序号 */
    @Excel(name = "排序号")
    @ApiModelProperty("排序号")
    private Long sortId;
            /** 创建用户 */
    @Excel(name = "创建用户")
    @ApiModelProperty("创建用户")
    private String createUserId;
            /** 创建租户 */
    @Excel(name = "创建租户")
    @ApiModelProperty("创建租户")
    private String createTenantId;
                /** 最后更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("最后更新时间")
    private Date lastUpdateTime;
            /** 最后更新人 */
    @Excel(name = "最后更新人")
    @ApiModelProperty("最后更新人")
    private String lastUpdateUserId;
            /** $column.columnComment */
    @Excel(name = "最后更新人")
    @ApiModelProperty("$column.columnComment")
    private String businessType;
            /** 显示类型 */
    @Excel(name = "显示类型")
    @ApiModelProperty("显示类型")
    private Long displayType;
            /** 应用id */
    @Excel(name = "应用id")
    @ApiModelProperty("应用id")
    private String appId;
            /** $column.columnComment */
    @Excel(name = "应用id")
    @ApiModelProperty("$column.columnComment")
    private String catalogId;
            /** $column.columnComment */
    @Excel(name = "应用id")
    @ApiModelProperty("$column.columnComment")
    private String securityLevel;
            /** 作业描述 */
    @Excel(name = "作业描述")
    @ApiModelProperty("作业描述")
    private String jobDesc;
            /** 所有者类型 */
    @Excel(name = "所有者类型")
    @ApiModelProperty("所有者类型")
    private String ownerType;
            /** 所有者id */
    @Excel(name = "所有者id")
    @ApiModelProperty("所有者id")
    private String ownerId;
            /** 归属空间编码 */
    @Excel(name = "归属空间编码")
    @ApiModelProperty("归属空间编码")
    private String belongSpaceId;
            /** $column.columnComment */
    @Excel(name = "归属空间编码")
    @ApiModelProperty("$column.columnComment")
    private String executorId;
            /** $column.columnComment */
    @Excel(name = "归属空间编码")
    @ApiModelProperty("$column.columnComment")
    private Long isRecover;
            /** $column.columnComment */
    @Excel(name = "归属空间编码")
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
                        public void setJobId(String jobId)
            {
            this.jobId = jobId;
            }

    public String getJobId()
            {
            return jobId;
            }
                            public void setJobName(String jobName)
            {
            this.jobName = jobName;
            }

    public String getJobName()
            {
            return jobName;
            }
                            public void setJobType(Long jobType)
            {
            this.jobType = jobType;
            }

    public Long getJobType()
            {
            return jobType;
            }
                            public void setStatus(Long status)
            {
            this.status = status;
            }

    public Long getStatus()
            {
            return status;
            }
                            public void setPriority(Long priority)
            {
            this.priority = priority;
            }

    public Long getPriority()
            {
            return priority;
            }
                            public void setTryCount(Long tryCount)
            {
            this.tryCount = tryCount;
            }

    public Long getTryCount()
            {
            return tryCount;
            }
                            public void setTryFrequency(Long tryFrequency)
            {
            this.tryFrequency = tryFrequency;
            }

    public Long getTryFrequency()
            {
            return tryFrequency;
            }
                            public void setTryItel(Long tryItel)
            {
            this.tryItel = tryItel;
            }

    public Long getTryItel()
            {
            return tryItel;
            }
                            public void setConcurrentCount(Long concurrentCount)
            {
            this.concurrentCount = concurrentCount;
            }

    public Long getConcurrentCount()
            {
            return concurrentCount;
            }
                            public void setFlowConfigJson(String flowConfigJson)
            {
            this.flowConfigJson = flowConfigJson;
            }

    public String getFlowConfigJson()
            {
            return flowConfigJson;
            }
                            public void setCycle(Long cycle)
            {
            this.cycle = cycle;
            }

    public Long getCycle()
            {
            return cycle;
            }
                            public void setVersionNo(Long versionNo)
            {
            this.versionNo = versionNo;
            }

    public Long getVersionNo()
            {
            return versionNo;
            }
                            public void setScheduleConfigJson(String scheduleConfigJson)
            {
            this.scheduleConfigJson = scheduleConfigJson;
            }

    public String getScheduleConfigJson()
            {
            return scheduleConfigJson;
            }
                            public void setNextScheduleTime(Date nextScheduleTime)
            {
            this.nextScheduleTime = nextScheduleTime;
            }

    public Date getNextScheduleTime()
            {
            return nextScheduleTime;
            }
                            public void setJobWorkGroupCode(String jobWorkGroupCode)
            {
            this.jobWorkGroupCode = jobWorkGroupCode;
            }

    public String getJobWorkGroupCode()
            {
            return jobWorkGroupCode;
            }
                            public void setStartDate(Date startDate)
            {
            this.startDate = startDate;
            }

    public Date getStartDate()
            {
            return startDate;
            }
                            public void setEndDate(Date endDate)
            {
            this.endDate = endDate;
            }

    public Date getEndDate()
            {
            return endDate;
            }
                            public void setAutoStop(Integer autoStop)
            {
            this.autoStop = autoStop;
            }

    public Integer getAutoStop()
            {
            return autoStop;
            }
                            public void setSortId(Long sortId)
            {
            this.sortId = sortId;
            }

    public Long getSortId()
            {
            return sortId;
            }
                            public void setCreateUserId(String createUserId)
            {
            this.createUserId = createUserId;
            }

    public String getCreateUserId()
            {
            return createUserId;
            }
                            public void setCreateTenantId(String createTenantId)
            {
            this.createTenantId = createTenantId;
            }

    public String getCreateTenantId()
            {
            return createTenantId;
            }
                                public void setLastUpdateTime(Date lastUpdateTime)
            {
            this.lastUpdateTime = lastUpdateTime;
            }

    public Date getLastUpdateTime()
            {
            return lastUpdateTime;
            }
                            public void setLastUpdateUserId(String lastUpdateUserId)
            {
            this.lastUpdateUserId = lastUpdateUserId;
            }

    public String getLastUpdateUserId()
            {
            return lastUpdateUserId;
            }
                            public void setBusinessType(String businessType)
            {
            this.businessType = businessType;
            }

    public String getBusinessType()
            {
            return businessType;
            }
                            public void setDisplayType(Long displayType)
            {
            this.displayType = displayType;
            }

    public Long getDisplayType()
            {
            return displayType;
            }
                            public void setAppId(String appId)
            {
            this.appId = appId;
            }

    public String getAppId()
            {
            return appId;
            }
                            public void setCatalogId(String catalogId)
            {
            this.catalogId = catalogId;
            }

    public String getCatalogId()
            {
            return catalogId;
            }
                            public void setSecurityLevel(String securityLevel)
            {
            this.securityLevel = securityLevel;
            }

    public String getSecurityLevel()
            {
            return securityLevel;
            }
                            public void setJobDesc(String jobDesc)
            {
            this.jobDesc = jobDesc;
            }

    public String getJobDesc()
            {
            return jobDesc;
            }
                            public void setOwnerType(String ownerType)
            {
            this.ownerType = ownerType;
            }

    public String getOwnerType()
            {
            return ownerType;
            }
                            public void setOwnerId(String ownerId)
            {
            this.ownerId = ownerId;
            }

    public String getOwnerId()
            {
            return ownerId;
            }
                            public void setBelongSpaceId(String belongSpaceId)
            {
            this.belongSpaceId = belongSpaceId;
            }

    public String getBelongSpaceId()
            {
            return belongSpaceId;
            }
                            public void setExecutorId(String executorId)
            {
            this.executorId = executorId;
            }

    public String getExecutorId()
            {
            return executorId;
            }
                            public void setIsRecover(Long isRecover)
            {
            this.isRecover = isRecover;
            }

    public Long getIsRecover()
            {
            return isRecover;
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
                                .append("jobId", getJobId())
                                .append("jobName", getJobName())
                                .append("jobType", getJobType())
                                .append("status", getStatus())
                                .append("priority", getPriority())
                                .append("tryCount", getTryCount())
                                .append("tryFrequency", getTryFrequency())
                                .append("tryItel", getTryItel())
                                .append("concurrentCount", getConcurrentCount())
                                .append("flowConfigJson", getFlowConfigJson())
                                .append("cycle", getCycle())
                                .append("versionNo", getVersionNo())
                                .append("scheduleConfigJson", getScheduleConfigJson())
                                .append("nextScheduleTime", getNextScheduleTime())
                                .append("jobWorkGroupCode", getJobWorkGroupCode())
                                .append("startDate", getStartDate())
                                .append("endDate", getEndDate())
                                .append("autoStop", getAutoStop())
                                .append("sortId", getSortId())
                                .append("createUserId", getCreateUserId())
                                .append("createTenantId", getCreateTenantId())
                                .append("createTime", getCreateTime())
                                .append("lastUpdateTime", getLastUpdateTime())
                                .append("lastUpdateUserId", getLastUpdateUserId())
                                .append("businessType", getBusinessType())
                                .append("displayType", getDisplayType())
                                .append("appId", getAppId())
                                .append("catalogId", getCatalogId())
                                .append("securityLevel", getSecurityLevel())
                                .append("jobDesc", getJobDesc())
                                .append("ownerType", getOwnerType())
                                .append("ownerId", getOwnerId())
                                .append("belongSpaceId", getBelongSpaceId())
                                .append("executorId", getExecutorId())
                                .append("isRecover", getIsRecover())
                                .append("businessKey", getBusinessKey())
            .toString();
        }
        }
