package com.yabushan.datasource.domain;


import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 应用实例连接器属性对象 app_connector_attr
 *
 * @author yabushan
 * @date 2023-11-25
 */

@ApiModel(value = "应用实例连接器属性对象")
public class AppConnectorAttr extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 属性ID */
    @ApiModelProperty("属性ID")
    private String attrId;
            /** 应用实例连接器ID */
    @Excel(name = "应用实例连接器ID")
    @ApiModelProperty("应用实例连接器ID")
    private String connectorId;
            /** 属性编码 */
    @Excel(name = "属性编码")
    @ApiModelProperty("属性编码")
    private String attrCode;
            /** 属性值 */
    @Excel(name = "属性值")
    @ApiModelProperty("属性值")
    private String attrValue;
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
                        public void setAttrId(String attrId)
            {
            this.attrId = attrId;
            }

    public String getAttrId()
            {
            return attrId;
            }
                            public void setConnectorId(String connectorId)
            {
            this.connectorId = connectorId;
            }

    public String getConnectorId()
            {
            return connectorId;
            }
                            public void setAttrCode(String attrCode)
            {
            this.attrCode = attrCode;
            }

    public String getAttrCode()
            {
            return attrCode;
            }
                            public void setAttrValue(String attrValue)
            {
            this.attrValue = attrValue;
            }

    public String getAttrValue()
            {
            return attrValue;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("attrId", getAttrId())
                                .append("connectorId", getConnectorId())
                                .append("attrCode", getAttrCode())
                                .append("attrValue", getAttrValue())
            .toString();
        }
        }
