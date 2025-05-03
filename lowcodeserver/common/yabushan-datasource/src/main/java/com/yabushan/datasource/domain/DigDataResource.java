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
 * 数据资源对象 data_resource
 *
 * @author yabushan
 * @date 2023-11-19
 */

@ApiModel(value = "数据资源对象")
public class DigDataResource extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 数据资源ID */
    @ApiModelProperty("数据资源ID")
    private String resourceId;
    /** 资源代码 */
    @Excel(name = "资源代码")
    @ApiModelProperty("资源代码")
    private String resourceCode;
    /** 数据资源名称 */
    @Excel(name = "数据资源名称")
    @ApiModelProperty("数据资源名称")
    private String resourceName;
    /** 资源表名 */
    @Excel(name = "资源表名")
    @ApiModelProperty("资源表名")
    private String resourceTableName;
    /** $column.columnComment */
    @Excel(name = "资源表名")
    @ApiModelProperty("$column.columnComment")
    private String shortName;
    /** 数据资源描述 */
    @Excel(name = "数据资源描述")
    @ApiModelProperty("数据资源描述")
    private String resourceDesp;
    /** 资源类型 */
    @Excel(name = "资源类型")
    @ApiModelProperty("资源类型")
    private String resourceType;
    /** 数据源ID */
    @Excel(name = "数据源ID")
    @ApiModelProperty("数据源ID")
    private String datasourceId;
    /** 状态：  designing：设计中，materialization：已物化，published：已发布，disabled：已下架，deleted：已删除 */
    @Excel(name = "状态：  designing：设计中，materialization：已物化，published：已发布，disabled：已下架，deleted：已删除")
    @ApiModelProperty("状态：  designing：设计中，materialization：已物化，published：已发布，disabled：已下架，deleted：已删除")
    private String status;
    /** 开放状态：   opened：完全公开，needAuthorized：需要审批 */
    @Excel(name = "开放状态：   opened：完全公开，needAuthorized：需要审批")
    @ApiModelProperty("开放状态：   opened：完全公开，needAuthorized：需要审批")
    private String openedStatus;
    /** 数据安全分级 */
    @Excel(name = "数据安全分级")
    @ApiModelProperty("数据安全分级")
    private String securityGradeId;
    /** 数据更新周期编码 */
    @Excel(name = "数据更新周期编码")
    @ApiModelProperty("数据更新周期编码")
    private String dataCycleId;
    /** 最近修改人身份标识 */
    @Excel(name = "最近修改人身份标识")
    @ApiModelProperty("最近修改人身份标识")
    private String lastUpdateUserId;
    /** 最近修改人名称 */
    @Excel(name = "最近修改人名称")
    @ApiModelProperty("最近修改人名称")
    private String lastUpdateUserName;
    /** 最后更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("最后更新时间")
    private Date lastUpdateTime;
    /** 是否置顶（1: 置顶、0: 默认） */
    @Excel(name = "是否置顶", readConverterExp = "1=:,置=顶、0:,默=认")
    @ApiModelProperty("是否置顶（1: 置顶、0: 默认）")
    private Integer isTop;
    /** 置顶时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("置顶时间")
    private Date topTime;
    /** 所属业务域id */
    @Excel(name = "所属业务域id")
    @ApiModelProperty("所属业务域id")
    private String businessDomainId;
    /** 发布状态 */
    @Excel(name = "发布状态")
    @ApiModelProperty("发布状态")
    private String publishStatus;
    /** 关联视图ID */
    @Excel(name = "关联视图ID")
    @ApiModelProperty("关联视图ID")
    private String relateViewId;
    /** 日志记录控制（0:关闭变更日志 ，1：开启变更日志） */
    @Excel(name = "日志记录控制", readConverterExp = "0=:关闭变更日志,，=1：开启变更日志")
    @ApiModelProperty("日志记录控制（0:关闭变更日志 ，1：开启变更日志）")
    private String logRecordControl;
    /** 归属空间 */
    @Excel(name = "归属空间")
    @ApiModelProperty("归属空间")
    private String belongSpaceId;
    /** 关联视图名称 */
    @Excel(name = "关联视图名称")
    @ApiModelProperty("关联视图名称")
    private String relateViewName;
    /** 目录标准化类型 */
    @Excel(name = "目录标准化类型")
    @ApiModelProperty("目录标准化类型")
    private String catalogStandardType;
    /** 数据目录标识码 */
    @Excel(name = "数据目录标识码")
    @ApiModelProperty("数据目录标识码")
    private String dataCatalogIdentificationCode;
    /** 资源管理类型 */
    @Excel(name = "资源管理类型")
    @ApiModelProperty("资源管理类型")
    private String resourceManageType;
    /** 是否标签实体 */
    @Excel(name = "是否标签实体")
    @ApiModelProperty("是否标签实体")
    private String isTagEntity;
    /** 是否指标主体 */
    @Excel(name = "是否指标主体")
    @ApiModelProperty("是否指标主体")
    private String isMetricEntity;
    /** 是否统计存储 */
    @Excel(name = "是否统计存储")
    @ApiModelProperty("是否统计存储")
    private Long storageStats;
    /** 不发布原因 */
    @Excel(name = "不发布原因")
    @ApiModelProperty("不发布原因")
    private String unPublishReason;
    /** 是否向社会开放 */
    @Excel(name = "是否向社会开放")
    @ApiModelProperty("是否向社会开放")
    private String openToSociety;
    /** 分享类型 */
    @Excel(name = "分享类型")
    @ApiModelProperty("分享类型")
    private String sharingType;
    /** 使用要求 */
    @Excel(name = "使用要求")
    @ApiModelProperty("使用要求")
    private String useRequirement;
    /** 启用状态(0:停用 , 1:启用) */
    @Excel(name = "启用状态(0:停用 , 1:启用)")
    @ApiModelProperty("启用状态(0:停用 , 1:启用)")
    private String enableStatus;
    /** 上次数据更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("上次数据更新时间")
    private Date lastDataUpdateTime;
    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("发布时间")
    private Date publishTime;
    /** 创建者租户id */
    @Excel(name = "创建者租户id")
    @ApiModelProperty("创建者租户id")
    private String createTenantId;
    /** 版本描述，默认1.0 */
    @Excel(name = "版本描述，默认1.0")
    @ApiModelProperty("版本描述，默认1.0")
    private String version;
    /** 版本描述 */
    @Excel(name = "版本描述")
    @ApiModelProperty("版本描述")
    private String versionDesp;
    /** 是否已导入平台索引库，默认否。1：是；0：否； */
    @Excel(name = "是否已导入平台索引库，默认否。1：是；0：否；")
    @ApiModelProperty("是否已导入平台索引库，默认否。1：是；0：否；")
    private String isIndexCreated;
    /** 采集状态 */
    @Excel(name = "采集状态")
    @ApiModelProperty("采集状态")
    private String collectStatus;
    /** 数据生产方式：QDLR 前端录入、SGDR 手工导入、ZDCJ 自动采集，默认自动采集 */
    @Excel(name = "数据生产方式：QDLR 前端录入、SGDR 手工导入、ZDCJ 自动采集，默认自动采集")
    @ApiModelProperty("数据生产方式：QDLR 前端录入、SGDR 手工导入、ZDCJ 自动采集，默认自动采集")
    private String productionMode;
    /** 数据种类 */
    @Excel(name = "数据种类")
    @ApiModelProperty("数据种类")
    private String dataKind;
    /** 数据结构类型 */
    @Excel(name = "数据结构类型")
    @ApiModelProperty("数据结构类型")
    private String structuredType;
    /** 所属组织 */
    @Excel(name = "所属组织")
    @ApiModelProperty("所属组织")
    private String orgId;
    /** 所属人员 */
    @Excel(name = "所属人员")
    @ApiModelProperty("所属人员")
    private String ownUserId;
    /** 数据管理者 */
    @Excel(name = "数据管理者")
    @ApiModelProperty("数据管理者")
    private String dataManagerId;
    /** 同义词名称，多个用英文逗号间隔 */
    @Excel(name = "同义词名称，多个用英文逗号间隔")
    @ApiModelProperty("同义词名称，多个用英文逗号间隔")
    private String sameName;
    /** 所属数据层 1:原始数据层,2:基础数据层,3:应用数据层 具体描述查看字典 */
    @Excel(name = "所属数据层 1:原始数据层,2:基础数据层,3:应用数据层 具体描述查看字典")
    @ApiModelProperty("所属数据层 1:原始数据层,2:基础数据层,3:应用数据层 具体描述查看字典")
    private String dataLayer;
    /** 是否删除 */
    @Excel(name = "是否删除")
    @ApiModelProperty("是否删除")
    private Integer isDeleted;
    /** 分析状态 未分析not_analyzed,分析中analyzing,已分析analyzed,分析失败failed */
    @Excel(name = "分析状态 未分析not_analyzed,分析中analyzing,已分析analyzed,分析失败failed")
    @ApiModelProperty("分析状态 未分析not_analyzed,分析中analyzing,已分析analyzed,分析失败failed")
    private String analysisStatus;
    /** 排序字段 */
    @Excel(name = "排序字段")
    @ApiModelProperty("排序字段")
    private Long sort;
    /** 所属集群 */
    @Excel(name = "所属集群")
    @ApiModelProperty("所属集群")
    private String clusterId;
    /** 所属集群数据库 */
    @Excel(name = "所属集群数据库")
    @ApiModelProperty("所属集群数据库")
    private String databaseId;
    /** 所属应用 */
    @Excel(name = "所属应用")
    @ApiModelProperty("所属应用")
    private String appId;
    /** 高级属性，以JSON存储 */
    @Excel(name = "高级属性，以JSON存储")
    @ApiModelProperty("高级属性，以JSON存储")
    private String extension;
    /** 上链状态 未上链not_chain, 上链中chaining,已上链chained,上链失败failed */
    @Excel(name = "上链状态 未上链not_chain, 上链中chaining,已上链chained,上链失败failed")
    @ApiModelProperty("上链状态 未上链not_chain, 上链中chaining,已上链chained,上链失败failed")
    private String baasStatus;
    /** 同步状态，本地文件(local) 待同步(pending) 同步中(running) 同步完成(completed) 同步失败(failed) */
    @Excel(name = "同步状态，本地文件(local) 待同步(pending) 同步中(running) 同步完成(completed) 同步失败(failed)")
    @ApiModelProperty("同步状态，本地文件(local) 待同步(pending) 同步中(running) 同步完成(completed) 同步失败(failed)")
    private String syncStatus;
    /** 数据分析JSON */
    @Excel(name = "数据分析JSON")
    @ApiModelProperty("数据分析JSON")
    private String reportAttachmentId;
    /** 资源父ID（上一级） */
    @Excel(name = "资源父ID", readConverterExp = "上=一级")
    @ApiModelProperty("资源父ID（上一级）")
    private String parentResourceId;
    /** 是否业务实体 */
    @Excel(name = "是否业务实体")
    @ApiModelProperty("是否业务实体")
    private String isDomainEntity;
    /** 是否已经知识抽取 */
    @Excel(name = "是否已经知识抽取")
    @ApiModelProperty("是否已经知识抽取")
    private String isExtractKg;
    /** 逻辑数据资源使用，包含的资源是否同源 */
    @Excel(name = "逻辑数据资源使用，包含的资源是否同源")
    @ApiModelProperty("逻辑数据资源使用，包含的资源是否同源")
    private String isSameDatasource;
    /** 逻辑数据资源使用，包含的资源是否同质（同类型） */
    @Excel(name = "逻辑数据资源使用，包含的资源是否同质", readConverterExp = "同=类型")
    @ApiModelProperty("逻辑数据资源使用，包含的资源是否同质（同类型）")
    private String isSameType;
    /** 逻辑数据资源使用，包含的资源是否同结构 */
    @Excel(name = "逻辑数据资源使用，包含的资源是否同结构")
    @ApiModelProperty("逻辑数据资源使用，包含的资源是否同结构")
    private String isSameStructure;
    /** 图片 */
    @Excel(name = "图片")
    @ApiModelProperty("图片")
    private String imageBase64;
    /** 颜色 */
    @Excel(name = "颜色")
    @ApiModelProperty("颜色")
    private String color;
    /** 形状 */
    @Excel(name = "形状")
    @ApiModelProperty("形状")
    private String shape;
    /** 继承 */
    @Excel(name = "继承")
    @ApiModelProperty("继承")
    private String extendFrom;
    /** 所有者类型 */
    @Excel(name = "所有者类型")
    @ApiModelProperty("所有者类型")
    private String ownerType;
    /** 所有者编码 */
    @Excel(name = "所有者编码")
    @ApiModelProperty("所有者编码")
    private String ownerId;
    /** 所有者名称 */
    @Excel(name = "所有者名称")
    @ApiModelProperty("所有者名称")
    private String ownerName;
    /** 创建者身份标识 */
    @Excel(name = "创建者身份标识")
    @ApiModelProperty("创建者身份标识")
    private String createUserId;
    /** 创建者名称 */
    @Excel(name = "创建者名称")
    @ApiModelProperty("创建者名称")
    private String createUserName;
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
    public void setResourceId(String resourceId)
    {
        this.resourceId = resourceId;
    }

    public String getResourceId()
    {
        return resourceId;
    }
    public void setResourceCode(String resourceCode)
    {
        this.resourceCode = resourceCode;
    }

    public String getResourceCode()
    {
        return resourceCode;
    }
    public void setResourceName(String resourceName)
    {
        this.resourceName = resourceName;
    }

    public String getResourceName()
    {
        return resourceName;
    }
    public void setResourceTableName(String resourceTableName)
    {
        this.resourceTableName = resourceTableName;
    }

    public String getResourceTableName()
    {
        return resourceTableName;
    }
    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    public String getShortName()
    {
        return shortName;
    }
    public void setResourceDesp(String resourceDesp)
    {
        this.resourceDesp = resourceDesp;
    }

    public String getResourceDesp()
    {
        return resourceDesp;
    }
    public void setResourceType(String resourceType)
    {
        this.resourceType = resourceType;
    }

    public String getResourceType()
    {
        return resourceType;
    }
    public void setDatasourceId(String datasourceId)
    {
        this.datasourceId = datasourceId;
    }

    public String getDatasourceId()
    {
        return datasourceId;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setOpenedStatus(String openedStatus)
    {
        this.openedStatus = openedStatus;
    }

    public String getOpenedStatus()
    {
        return openedStatus;
    }
    public void setSecurityGradeId(String securityGradeId)
    {
        this.securityGradeId = securityGradeId;
    }

    public String getSecurityGradeId()
    {
        return securityGradeId;
    }
    public void setDataCycleId(String dataCycleId)
    {
        this.dataCycleId = dataCycleId;
    }

    public String getDataCycleId()
    {
        return dataCycleId;
    }
    public void setLastUpdateUserId(String lastUpdateUserId)
    {
        this.lastUpdateUserId = lastUpdateUserId;
    }

    public String getLastUpdateUserId()
    {
        return lastUpdateUserId;
    }
    public void setLastUpdateUserName(String lastUpdateUserName)
    {
        this.lastUpdateUserName = lastUpdateUserName;
    }

    public String getLastUpdateUserName()
    {
        return lastUpdateUserName;
    }
    public void setLastUpdateTime(Date lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime()
    {
        return lastUpdateTime;
    }
    public void setIsTop(Integer isTop)
    {
        this.isTop = isTop;
    }

    public Integer getIsTop()
    {
        return isTop;
    }
    public void setTopTime(Date topTime)
    {
        this.topTime = topTime;
    }

    public Date getTopTime()
    {
        return topTime;
    }
    public void setBusinessDomainId(String businessDomainId)
    {
        this.businessDomainId = businessDomainId;
    }

    public String getBusinessDomainId()
    {
        return businessDomainId;
    }
    public void setPublishStatus(String publishStatus)
    {
        this.publishStatus = publishStatus;
    }

    public String getPublishStatus()
    {
        return publishStatus;
    }
    public void setRelateViewId(String relateViewId)
    {
        this.relateViewId = relateViewId;
    }

    public String getRelateViewId()
    {
        return relateViewId;
    }
    public void setLogRecordControl(String logRecordControl)
    {
        this.logRecordControl = logRecordControl;
    }

    public String getLogRecordControl()
    {
        return logRecordControl;
    }
    public void setBelongSpaceId(String belongSpaceId)
    {
        this.belongSpaceId = belongSpaceId;
    }

    public String getBelongSpaceId()
    {
        return belongSpaceId;
    }
    public void setRelateViewName(String relateViewName)
    {
        this.relateViewName = relateViewName;
    }

    public String getRelateViewName()
    {
        return relateViewName;
    }
    public void setCatalogStandardType(String catalogStandardType)
    {
        this.catalogStandardType = catalogStandardType;
    }

    public String getCatalogStandardType()
    {
        return catalogStandardType;
    }
    public void setDataCatalogIdentificationCode(String dataCatalogIdentificationCode)
    {
        this.dataCatalogIdentificationCode = dataCatalogIdentificationCode;
    }

    public String getDataCatalogIdentificationCode()
    {
        return dataCatalogIdentificationCode;
    }
    public void setResourceManageType(String resourceManageType)
    {
        this.resourceManageType = resourceManageType;
    }

    public String getResourceManageType()
    {
        return resourceManageType;
    }
    public void setIsTagEntity(String isTagEntity)
    {
        this.isTagEntity = isTagEntity;
    }

    public String getIsTagEntity()
    {
        return isTagEntity;
    }
    public void setIsMetricEntity(String isMetricEntity)
    {
        this.isMetricEntity = isMetricEntity;
    }

    public String getIsMetricEntity()
    {
        return isMetricEntity;
    }
    public void setStorageStats(Long storageStats)
    {
        this.storageStats = storageStats;
    }

    public Long getStorageStats()
    {
        return storageStats;
    }
    public void setUnPublishReason(String unPublishReason)
    {
        this.unPublishReason = unPublishReason;
    }

    public String getUnPublishReason()
    {
        return unPublishReason;
    }
    public void setOpenToSociety(String openToSociety)
    {
        this.openToSociety = openToSociety;
    }

    public String getOpenToSociety()
    {
        return openToSociety;
    }
    public void setSharingType(String sharingType)
    {
        this.sharingType = sharingType;
    }

    public String getSharingType()
    {
        return sharingType;
    }
    public void setUseRequirement(String useRequirement)
    {
        this.useRequirement = useRequirement;
    }

    public String getUseRequirement()
    {
        return useRequirement;
    }
    public void setEnableStatus(String enableStatus)
    {
        this.enableStatus = enableStatus;
    }

    public String getEnableStatus()
    {
        return enableStatus;
    }
    public void setLastDataUpdateTime(Date lastDataUpdateTime)
    {
        this.lastDataUpdateTime = lastDataUpdateTime;
    }

    public Date getLastDataUpdateTime()
    {
        return lastDataUpdateTime;
    }
    public void setPublishTime(Date publishTime)
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime()
    {
        return publishTime;
    }
    public void setCreateTenantId(String createTenantId)
    {
        this.createTenantId = createTenantId;
    }

    public String getCreateTenantId()
    {
        return createTenantId;
    }
    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getVersion()
    {
        return version;
    }
    public void setVersionDesp(String versionDesp)
    {
        this.versionDesp = versionDesp;
    }

    public String getVersionDesp()
    {
        return versionDesp;
    }
    public void setIsIndexCreated(String isIndexCreated)
    {
        this.isIndexCreated = isIndexCreated;
    }

    public String getIsIndexCreated()
    {
        return isIndexCreated;
    }
    public void setCollectStatus(String collectStatus)
    {
        this.collectStatus = collectStatus;
    }

    public String getCollectStatus()
    {
        return collectStatus;
    }
    public void setProductionMode(String productionMode)
    {
        this.productionMode = productionMode;
    }

    public String getProductionMode()
    {
        return productionMode;
    }
    public void setDataKind(String dataKind)
    {
        this.dataKind = dataKind;
    }

    public String getDataKind()
    {
        return dataKind;
    }
    public void setStructuredType(String structuredType)
    {
        this.structuredType = structuredType;
    }

    public String getStructuredType()
    {
        return structuredType;
    }
    public void setOrgId(String orgId)
    {
        this.orgId = orgId;
    }

    public String getOrgId()
    {
        return orgId;
    }
    public void setOwnUserId(String ownUserId)
    {
        this.ownUserId = ownUserId;
    }

    public String getOwnUserId()
    {
        return ownUserId;
    }
    public void setDataManagerId(String dataManagerId)
    {
        this.dataManagerId = dataManagerId;
    }

    public String getDataManagerId()
    {
        return dataManagerId;
    }
    public void setSameName(String sameName)
    {
        this.sameName = sameName;
    }

    public String getSameName()
    {
        return sameName;
    }
    public void setDataLayer(String dataLayer)
    {
        this.dataLayer = dataLayer;
    }

    public String getDataLayer()
    {
        return dataLayer;
    }
    public void setIsDeleted(Integer isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted()
    {
        return isDeleted;
    }
    public void setAnalysisStatus(String analysisStatus)
    {
        this.analysisStatus = analysisStatus;
    }

    public String getAnalysisStatus()
    {
        return analysisStatus;
    }
    public void setSort(Long sort)
    {
        this.sort = sort;
    }

    public Long getSort()
    {
        return sort;
    }
    public void setClusterId(String clusterId)
    {
        this.clusterId = clusterId;
    }

    public String getClusterId()
    {
        return clusterId;
    }
    public void setDatabaseId(String databaseId)
    {
        this.databaseId = databaseId;
    }

    public String getDatabaseId()
    {
        return databaseId;
    }
    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getAppId()
    {
        return appId;
    }
    public void setExtension(String extension)
    {
        this.extension = extension;
    }

    public String getExtension()
    {
        return extension;
    }
    public void setBaasStatus(String baasStatus)
    {
        this.baasStatus = baasStatus;
    }

    public String getBaasStatus()
    {
        return baasStatus;
    }
    public void setSyncStatus(String syncStatus)
    {
        this.syncStatus = syncStatus;
    }

    public String getSyncStatus()
    {
        return syncStatus;
    }
    public void setReportAttachmentId(String reportAttachmentId)
    {
        this.reportAttachmentId = reportAttachmentId;
    }

    public String getReportAttachmentId()
    {
        return reportAttachmentId;
    }
    public void setParentResourceId(String parentResourceId)
    {
        this.parentResourceId = parentResourceId;
    }

    public String getParentResourceId()
    {
        return parentResourceId;
    }
    public void setIsDomainEntity(String isDomainEntity)
    {
        this.isDomainEntity = isDomainEntity;
    }

    public String getIsDomainEntity()
    {
        return isDomainEntity;
    }
    public void setIsExtractKg(String isExtractKg)
    {
        this.isExtractKg = isExtractKg;
    }

    public String getIsExtractKg()
    {
        return isExtractKg;
    }
    public void setIsSameDatasource(String isSameDatasource)
    {
        this.isSameDatasource = isSameDatasource;
    }

    public String getIsSameDatasource()
    {
        return isSameDatasource;
    }
    public void setIsSameType(String isSameType)
    {
        this.isSameType = isSameType;
    }

    public String getIsSameType()
    {
        return isSameType;
    }
    public void setIsSameStructure(String isSameStructure)
    {
        this.isSameStructure = isSameStructure;
    }

    public String getIsSameStructure()
    {
        return isSameStructure;
    }
    public void setImageBase64(String imageBase64)
    {
        this.imageBase64 = imageBase64;
    }

    public String getImageBase64()
    {
        return imageBase64;
    }
    public void setColor(String color)
    {
        this.color = color;
    }

    public String getColor()
    {
        return color;
    }
    public void setShape(String shape)
    {
        this.shape = shape;
    }

    public String getShape()
    {
        return shape;
    }
    public void setExtendFrom(String extendFrom)
    {
        this.extendFrom = extendFrom;
    }

    public String getExtendFrom()
    {
        return extendFrom;
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
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getOwnerName()
    {
        return ownerName;
    }
    public void setCreateUserId(String createUserId)
    {
        this.createUserId = createUserId;
    }

    public String getCreateUserId()
    {
        return createUserId;
    }
    public void setCreateUserName(String createUserName)
    {
        this.createUserName = createUserName;
    }

    public String getCreateUserName()
    {
        return createUserName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("resourceId", getResourceId())
                .append("resourceCode", getResourceCode())
                .append("resourceName", getResourceName())
                .append("resourceTableName", getResourceTableName())
                .append("shortName", getShortName())
                .append("resourceDesp", getResourceDesp())
                .append("resourceType", getResourceType())
                .append("datasourceId", getDatasourceId())
                .append("status", getStatus())
                .append("openedStatus", getOpenedStatus())
                .append("securityGradeId", getSecurityGradeId())
                .append("dataCycleId", getDataCycleId())
                .append("lastUpdateUserId", getLastUpdateUserId())
                .append("lastUpdateUserName", getLastUpdateUserName())
                .append("lastUpdateTime", getLastUpdateTime())
                .append("isTop", getIsTop())
                .append("topTime", getTopTime())
                .append("businessDomainId", getBusinessDomainId())
                .append("publishStatus", getPublishStatus())
                .append("relateViewId", getRelateViewId())
                .append("logRecordControl", getLogRecordControl())
                .append("belongSpaceId", getBelongSpaceId())
                .append("relateViewName", getRelateViewName())
                .append("catalogStandardType", getCatalogStandardType())
                .append("dataCatalogIdentificationCode", getDataCatalogIdentificationCode())
                .append("resourceManageType", getResourceManageType())
                .append("isTagEntity", getIsTagEntity())
                .append("isMetricEntity", getIsMetricEntity())
                .append("storageStats", getStorageStats())
                .append("unPublishReason", getUnPublishReason())
                .append("openToSociety", getOpenToSociety())
                .append("sharingType", getSharingType())
                .append("useRequirement", getUseRequirement())
                .append("remark", getRemark())
                .append("enableStatus", getEnableStatus())
                .append("lastDataUpdateTime", getLastDataUpdateTime())
                .append("publishTime", getPublishTime())
                .append("createTenantId", getCreateTenantId())
                .append("version", getVersion())
                .append("versionDesp", getVersionDesp())
                .append("isIndexCreated", getIsIndexCreated())
                .append("collectStatus", getCollectStatus())
                .append("productionMode", getProductionMode())
                .append("dataKind", getDataKind())
                .append("structuredType", getStructuredType())
                .append("orgId", getOrgId())
                .append("ownUserId", getOwnUserId())
                .append("dataManagerId", getDataManagerId())
                .append("sameName", getSameName())
                .append("dataLayer", getDataLayer())
                .append("isDeleted", getIsDeleted())
                .append("analysisStatus", getAnalysisStatus())
                .append("sort", getSort())
                .append("clusterId", getClusterId())
                .append("databaseId", getDatabaseId())
                .append("appId", getAppId())
                .append("extension", getExtension())
                .append("baasStatus", getBaasStatus())
                .append("syncStatus", getSyncStatus())
                .append("reportAttachmentId", getReportAttachmentId())
                .append("parentResourceId", getParentResourceId())
                .append("isDomainEntity", getIsDomainEntity())
                .append("isExtractKg", getIsExtractKg())
                .append("isSameDatasource", getIsSameDatasource())
                .append("isSameType", getIsSameType())
                .append("isSameStructure", getIsSameStructure())
                .append("imageBase64", getImageBase64())
                .append("color", getColor())
                .append("shape", getShape())
                .append("extendFrom", getExtendFrom())
                .append("ownerType", getOwnerType())
                .append("ownerId", getOwnerId())
                .append("ownerName", getOwnerName())
                .append("createUserId", getCreateUserId())
                .append("createUserName", getCreateUserName())
                .append("createTime", getCreateTime())
                .toString();
    }
}
