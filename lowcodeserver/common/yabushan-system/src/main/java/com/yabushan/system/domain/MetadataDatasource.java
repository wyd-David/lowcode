package com.yabushan.system.domain;

import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 数据源对象 metadata_datasource
 *
 * @author yabushan
 * @date 2021-01-16
 */
public class MetadataDatasource extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 数据源ID */
    private String datasourceId;

    /** 类型代码 */
    @Excel(name = "类型代码")
    private String typeCode;

    /** 数据源编码 */
    @Excel(name = "数据源编码")
    private String datasourceCode;

    /** 数据源名称 */
    @Excel(name = "数据源名称")
    private String datasourceName;

    /** 说明 */
    @Excel(name = "说明")
    private String description;

    /** 创建人ID */
    @Excel(name = "创建人ID")
    private String creatorId;

    /** 创建人名称 */
    @Excel(name = "创建人名称")
    private String creatorName;

    /** 所属系统 */
    @Excel(name = "所属系统")
    private String platformId;

    /** 所属厂商 */
    @Excel(name = "所属厂商")
    private String manufacturerId;

    /** 数据源分类 */
    @Excel(name = "数据源分类")
    private String classificationCode;

    /** 数据源来源 */
    @Excel(name = "数据源来源")
    private String sourceCode;

    /** 所属数据层 */
    @Excel(name = "所属数据层")
    private String layerId;

    /** 租户Id */
    @Excel(name = "租户Id")
    private String tenantId;

    /** $column.columnComment */
    @Excel(name = "租户Id")
    private String status;

    /** $column.columnComment */
    @Excel(name = "租户Id")
    private String version;

    /** 组织Id */
    @Excel(name = "组织Id")
    private String orgid;

    /** 是否仓库(true 为仓库) */
    @Excel(name = "是否仓库(true 为仓库)")
    private String isRepository;

    /** 是否取消统计(如果为true,一般影响数据统计相关的功能) */
    @Excel(name = "是否取消统计(如果为true,一般影响数据统计相关的功能)")
    private String cancelStatistic;

    public void setDatasourceId(String datasourceId)
    {
        this.datasourceId = datasourceId;
    }

    public String getDatasourceId()
    {
        return datasourceId;
    }
    public void setTypeCode(String typeCode)
    {
        this.typeCode = typeCode;
    }

    public String getTypeCode()
    {
        return typeCode;
    }
    public void setDatasourceCode(String datasourceCode)
    {
        this.datasourceCode = datasourceCode;
    }

    public String getDatasourceCode()
    {
        return datasourceCode;
    }
    public void setDatasourceName(String datasourceName)
    {
        this.datasourceName = datasourceName;
    }

    public String getDatasourceName()
    {
        return datasourceName;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }

    public String getCreatorId()
    {
        return creatorId;
    }
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }

    public String getCreatorName()
    {
        return creatorName;
    }
    public void setPlatformId(String platformId)
    {
        this.platformId = platformId;
    }

    public String getPlatformId()
    {
        return platformId;
    }
    public void setManufacturerId(String manufacturerId)
    {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerId()
    {
        return manufacturerId;
    }
    public void setClassificationCode(String classificationCode)
    {
        this.classificationCode = classificationCode;
    }

    public String getClassificationCode()
    {
        return classificationCode;
    }
    public void setSourceCode(String sourceCode)
    {
        this.sourceCode = sourceCode;
    }

    public String getSourceCode()
    {
        return sourceCode;
    }
    public void setLayerId(String layerId)
    {
        this.layerId = layerId;
    }

    public String getLayerId()
    {
        return layerId;
    }
    public void setTenantId(String tenantId)
    {
        this.tenantId = tenantId;
    }

    public String getTenantId()
    {
        return tenantId;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getVersion()
    {
        return version;
    }
    public void setOrgid(String orgid)
    {
        this.orgid = orgid;
    }

    public String getOrgid()
    {
        return orgid;
    }
    public void setIsRepository(String isRepository)
    {
        this.isRepository = isRepository;
    }

    public String getIsRepository()
    {
        return isRepository;
    }
    public void setCancelStatistic(String cancelStatistic)
    {
        this.cancelStatistic = cancelStatistic;
    }

    public String getCancelStatistic()
    {
        return cancelStatistic;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("datasourceId", getDatasourceId())
            .append("typeCode", getTypeCode())
            .append("datasourceCode", getDatasourceCode())
            .append("datasourceName", getDatasourceName())
            .append("description", getDescription())
            .append("creatorId", getCreatorId())
            .append("creatorName", getCreatorName())
            .append("createTime", getCreateTime())
            .append("platformId", getPlatformId())
            .append("manufacturerId", getManufacturerId())
            .append("classificationCode", getClassificationCode())
            .append("sourceCode", getSourceCode())
            .append("layerId", getLayerId())
            .append("tenantId", getTenantId())
            .append("status", getStatus())
            .append("version", getVersion())
            .append("orgid", getOrgid())
            .append("isRepository", getIsRepository())
            .append("cancelStatistic", getCancelStatistic())
            .toString();
    }
}
