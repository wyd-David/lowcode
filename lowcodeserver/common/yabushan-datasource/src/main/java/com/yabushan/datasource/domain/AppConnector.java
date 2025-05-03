package com.yabushan.datasource.domain;


import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 应用实例连接器记录对象 app_connector
 *
 * @author yabushan
 * @date 2023-11-25
 */

@ApiModel(value = "应用实例连接器记录对象")
public class AppConnector extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 连接器主键 */
    @ApiModelProperty("连接器主键")
    private String connectorId;
            /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty("$column.columnComment")
    private String appId;
            /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    @ApiModelProperty("$column.columnComment")
    private String connectorType;
            /** 连接器名称 */
    @Excel(name = "连接器名称")
    @ApiModelProperty("连接器名称")
    private String connectorName;
            /** 描述 */
    @Excel(name = "描述")
    @ApiModelProperty("描述")
    private String description;
            /** 应用实例连接状态 */
    @Excel(name = "应用实例连接状态")
    @ApiModelProperty("应用实例连接状态")
    private Long connectStatus;
            /** 是否已删除 0否 1是 */
    @Excel(name = "是否已删除 0否 1是")
    @ApiModelProperty("是否已删除 0否 1是")
    private Long isDeleted;
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
                        public void setConnectorId(String connectorId)
            {
            this.connectorId = connectorId;
            }

    public String getConnectorId()
            {
            return connectorId;
            }
                            public void setAppId(String appId)
            {
            this.appId = appId;
            }

    public String getAppId()
            {
            return appId;
            }
                            public void setConnectorType(String connectorType)
            {
            this.connectorType = connectorType;
            }

    public String getConnectorType()
            {
            return connectorType;
            }
                            public void setConnectorName(String connectorName)
            {
            this.connectorName = connectorName;
            }

    public String getConnectorName()
            {
            return connectorName;
            }
                            public void setDescription(String description)
            {
            this.description = description;
            }

    public String getDescription()
            {
            return description;
            }
                            public void setConnectStatus(Long connectStatus)
            {
            this.connectStatus = connectStatus;
            }

    public Long getConnectStatus()
            {
            return connectStatus;
            }
                            public void setIsDeleted(Long isDeleted)
            {
            this.isDeleted = isDeleted;
            }

    public Long getIsDeleted()
            {
            return isDeleted;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("connectorId", getConnectorId())
                                .append("appId", getAppId())
                                .append("connectorType", getConnectorType())
                                .append("connectorName", getConnectorName())
                                .append("description", getDescription())
                                .append("connectStatus", getConnectStatus())
                                .append("isDeleted", getIsDeleted())
            .toString();
        }
        }
