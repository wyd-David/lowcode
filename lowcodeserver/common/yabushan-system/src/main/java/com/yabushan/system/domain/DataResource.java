package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 数据资源对象 data_resource
 *
 * @author yabushan
 * @date 2021-01-16
 */
public class DataResource extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 数据资源ID */
    private String resourceId;

    /** 资源代码 */
    @Excel(name = "资源代码")
    private String resourceCode;

    /** 数据资源名称 */
    @Excel(name = "数据资源名称")
    private String resourceName;

    /** 数据资源描述 */
    @Excel(name = "数据资源描述")
    private String resourceDesp;

    /** 资源类型 */
    @Excel(name = "资源类型")
    private String resourceType;

    /** 数据源ID */
    @Excel(name = "数据源ID")
    private String datasourceId;

    /** 状态：  designing：设计中，materialization：已物化，published：已发布，disabled：已下架，deleted：已删除 */
    @Excel(name = "状态：  designing：设计中，materialization：已物化，published：已发布，disabled：已下架，deleted：已删除")
    private String status;

    /** 开放状态：   opened：完全公开，needAuthorized：需要审批 */
    @Excel(name = "开放状态：   opened：完全公开，needAuthorized：需要审批")
    private String openedStatus;

    /** 数据安全分级 */
    @Excel(name = "数据安全分级")
    private String securityGradeId;

    /** 数据更新周期编码 */
    @Excel(name = "数据更新周期编码")
    private String dataCycleId;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishTime;

    /** 最后更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdateTime;

    /** 创建者身份标识 */
    @Excel(name = "创建者身份标识")
    private String createUserId;

    /** 创建者名称 */
    @Excel(name = "创建者名称")
    private String createUserName;

    /** 创建者租户id */
    @Excel(name = "创建者租户id")
    private String createTenantId;

    /** 版本描述，默认1.0 */
    @Excel(name = "版本描述，默认1.0")
    private String version;

    /** 版本描述 */
    @Excel(name = "版本描述")
    private String versionDesp;

    /** 是否已导入平台索引库，默认否。1：是；0：否； */
    @Excel(name = "是否已导入平台索引库，默认否。1：是；0：否；")
    private String isIndexCreated;

    /** 采集状态 */
    @Excel(name = "采集状态")
    private String collectStatus;

    /** 数据生产方式：QDLR 前端录入、SGDR 手工导入、ZDCJ 自动采集，默认自动采集 */
    @Excel(name = "数据生产方式：QDLR 前端录入、SGDR 手工导入、ZDCJ 自动采集，默认自动采集")
    private String productionMode;

    /** 数据种类 */
    @Excel(name = "数据种类")
    private String dataKind;

    /** 数据结构类型 */
    @Excel(name = "数据结构类型")
    private String structuredType;

    /** 所属组织 */
    @Excel(name = "所属组织")
    private String orgId;

    /** 所属人员 */
    @Excel(name = "所属人员")
    private String ownUserId;

    /** 数据管理者 */
    @Excel(name = "数据管理者")
    private String dataManagerId;

    /** 同义词名称，多个用英文逗号间隔 */
    @Excel(name = "同义词名称，多个用英文逗号间隔")
    private String sameName;

    /** 所属数据层 1:原始数据层,2:基础数据层,3:应用数据层 具体描述查看字典 */
    @Excel(name = "所属数据层 1:原始数据层,2:基础数据层,3:应用数据层 具体描述查看字典")
    private String dataLayer;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer isDeleted;

    /** 分析状态 未分析not_analyzed,分析中analyzing,已分析analyzed,分析失败failed */
    @Excel(name = "分析状态 未分析not_analyzed,分析中analyzing,已分析analyzed,分析失败failed")
    private String analysisStatus;

    /** 排序字段 */
    @Excel(name = "排序字段")
    private Long sort;

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
    public void setPublishTime(Date publishTime)
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime()
    {
        return publishTime;
    }
    public void setLastUpdateTime(Date lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime()
    {
        return lastUpdateTime;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("resourceId", getResourceId())
            .append("resourceCode", getResourceCode())
            .append("resourceName", getResourceName())
            .append("resourceDesp", getResourceDesp())
            .append("resourceType", getResourceType())
            .append("datasourceId", getDatasourceId())
            .append("status", getStatus())
            .append("openedStatus", getOpenedStatus())
            .append("securityGradeId", getSecurityGradeId())
            .append("dataCycleId", getDataCycleId())
            .append("createTime", getCreateTime())
            .append("publishTime", getPublishTime())
            .append("lastUpdateTime", getLastUpdateTime())
            .append("createUserId", getCreateUserId())
            .append("createUserName", getCreateUserName())
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
            .toString();
    }
}
