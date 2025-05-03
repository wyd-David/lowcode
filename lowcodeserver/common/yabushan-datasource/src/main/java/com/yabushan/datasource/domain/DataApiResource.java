package com.yabushan.datasource.domain;


import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据服务对象 data_api_resource
 *
 * @author yabushan
 * @date 2023-11-19
 */

@ApiModel(value = "数据服务对象")
public class DataApiResource extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String resourceId;
    /** API服务编码 */
    @Excel(name = "API服务编码")
    @ApiModelProperty("API服务编码")
    private String apiCode;
    /** rowset、listMap */
    @Excel(name = "rowset、listMap")
    @ApiModelProperty("rowset、listMap")
    private String apiResultType;
    /** $column.columnComment */
    @Excel(name = "rowset、listMap")
    @ApiModelProperty("$column.columnComment")
    private String apiDefination;
    /** 创建方式 可视化 visual 自定义 define */
    @Excel(name = "创建方式 可视化 visual 自定义 define")
    @ApiModelProperty("创建方式 可视化 visual 自定义 define")
    private String createType;
    /** 可视化创建时where的配置 */
    @Excel(name = "可视化创建时where的配置")
    @ApiModelProperty("可视化创建时where的配置")
    private String visualWhereJson;
    /** 可视化时字段配置 */
    @Excel(name = "可视化时字段配置")
    @ApiModelProperty("可视化时字段配置")
    private String visualColumnJson;
    /** 可视化时分组配置 */
    @Excel(name = "可视化时分组配置")
    @ApiModelProperty("可视化时分组配置")
    private String visualGroupJson;
    /** 是否nginx代理 */
    @Excel(name = "是否nginx代理")
    @ApiModelProperty("是否nginx代理")
    private String isNginxProxy;
    /** $column.columnComment */
    @Excel(name = "是否nginx代理")
    @ApiModelProperty("$column.columnComment")
    private String callLimit;
    /** 忽略资源权限，默认不忽略 */
    @Excel(name = "忽略资源权限，默认不忽略")
    @ApiModelProperty("忽略资源权限，默认不忽略")
    private String ignoreResourceAuth;
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
    public void setApiCode(String apiCode)
    {
        this.apiCode = apiCode;
    }

    public String getApiCode()
    {
        return apiCode;
    }
    public void setApiResultType(String apiResultType)
    {
        this.apiResultType = apiResultType;
    }

    public String getApiResultType()
    {
        return apiResultType;
    }
    public void setApiDefination(String apiDefination)
    {
        this.apiDefination = apiDefination;
    }

    public String getApiDefination()
    {
        return apiDefination;
    }
    public void setCreateType(String createType)
    {
        this.createType = createType;
    }

    public String getCreateType()
    {
        return createType;
    }
    public void setVisualWhereJson(String visualWhereJson)
    {
        this.visualWhereJson = visualWhereJson;
    }

    public String getVisualWhereJson()
    {
        return visualWhereJson;
    }
    public void setVisualColumnJson(String visualColumnJson)
    {
        this.visualColumnJson = visualColumnJson;
    }

    public String getVisualColumnJson()
    {
        return visualColumnJson;
    }
    public void setVisualGroupJson(String visualGroupJson)
    {
        this.visualGroupJson = visualGroupJson;
    }

    public String getVisualGroupJson()
    {
        return visualGroupJson;
    }
    public void setIsNginxProxy(String isNginxProxy)
    {
        this.isNginxProxy = isNginxProxy;
    }

    public String getIsNginxProxy()
    {
        return isNginxProxy;
    }
    public void setCallLimit(String callLimit)
    {
        this.callLimit = callLimit;
    }

    public String getCallLimit()
    {
        return callLimit;
    }
    public void setIgnoreResourceAuth(String ignoreResourceAuth)
    {
        this.ignoreResourceAuth = ignoreResourceAuth;
    }

    public String getIgnoreResourceAuth()
    {
        return ignoreResourceAuth;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("resourceId", getResourceId())
                .append("apiCode", getApiCode())
                .append("apiResultType", getApiResultType())
                .append("apiDefination", getApiDefination())
                .append("createType", getCreateType())
                .append("visualWhereJson", getVisualWhereJson())
                .append("visualColumnJson", getVisualColumnJson())
                .append("visualGroupJson", getVisualGroupJson())
                .append("isNginxProxy", getIsNginxProxy())
                .append("callLimit", getCallLimit())
                .append("ignoreResourceAuth", getIgnoreResourceAuth())
                .toString();
    }
}
