package com.yabushan.system.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 数据服务API对象 data_api_infos
 *
 * @author yabushan
 * @date 2021-03-28
 */

@ApiModel(value = "数据服务API对象")
public class DataApiInfos extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private Long id;
    /** 资源ID */
    @cn.afterturn.easypoi.excel.annotation.Excel(name = "资源ID")
    @ApiModelProperty("资源ID")
    private String resourceId;
    /** 资源编码 */
    @cn.afterturn.easypoi.excel.annotation.Excel(name = "资源编码")
    @ApiModelProperty("资源编码")
    private String resourceCode;
    /** 资源名称 */
    @cn.afterturn.easypoi.excel.annotation.Excel(name = "资源名称")
    @ApiModelProperty("资源名称")
    private String resourceName;
    /** 数据源ID */
    @cn.afterturn.easypoi.excel.annotation.Excel(name = "数据源ID")
    @ApiModelProperty("数据源ID")
    private String datasourceId;
    /** sql表达式 */
    @cn.afterturn.easypoi.excel.annotation.Excel(name = "sql表达式")
    @ApiModelProperty("sql表达式")
    private String apiDefination;
    /** 数据编码 */
    @cn.afterturn.easypoi.excel.annotation.Excel(name = "数据编码")
    @ApiModelProperty("数据编码")
    private String apiCode;
    /** api状态,1停用，0：启用 */
    @cn.afterturn.easypoi.excel.annotation.Excel(name = "api状态,1停用，0：启用")
    @ApiModelProperty("api状态,1停用，0：启用")
    private String apiStatus;
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
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
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
    public void setDatasourceId(String datasourceId)
    {
        this.datasourceId = datasourceId;
    }

    public String getDatasourceId()
    {
        return datasourceId;
    }
    public void setApiDefination(String apiDefination)
    {
        this.apiDefination = apiDefination;
    }

    public String getApiDefination()
    {
        return apiDefination;
    }
    public void setApiCode(String apiCode)
    {
        this.apiCode = apiCode;
    }

    public String getApiCode()
    {
        return apiCode;
    }
    public void setApiStatus(String apiStatus)
    {
        this.apiStatus = apiStatus;
    }

    public String getApiStatus()
    {
        return apiStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("resourceId", getResourceId())
                .append("resourceCode", getResourceCode())
                .append("resourceName", getResourceName())
                .append("datasourceId", getDatasourceId())
                .append("apiDefination", getApiDefination())
                .append("apiCode", getApiCode())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .append("apiStatus", getApiStatus())
                .toString();
    }
}
