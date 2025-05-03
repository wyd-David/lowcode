package com.yabushan.datasource.domain;


import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据源类型参数对象 metadata_datasource_type_param
 *
 * @author yabushan
 * @date 2023-11-19
 */

@ApiModel(value = "数据源类型参数对象")
public class MetadataDatasourceTypeParam extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 参数ID */
    @ApiModelProperty("参数ID")
    private String paramId;
            /** 数据源类型ID */
    @Excel(name = "数据源类型ID")
    @ApiModelProperty("数据源类型ID")
    private String dbTypeId;
            /** 参数代码 */
    @Excel(name = "参数代码")
    @ApiModelProperty("参数代码")
    private String paramCode;
            /** 参数值 */
    @Excel(name = "参数值")
    @ApiModelProperty("参数值")
    private String paramValue;
            /** 参数描述 */
    @Excel(name = "参数描述")
    @ApiModelProperty("参数描述")
    private String paramDesc;
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
                        public void setParamId(String paramId)
            {
            this.paramId = paramId;
            }

    public String getParamId()
            {
            return paramId;
            }
                            public void setDbTypeId(String dbTypeId)
            {
            this.dbTypeId = dbTypeId;
            }

    public String getDbTypeId()
            {
            return dbTypeId;
            }
                            public void setParamCode(String paramCode)
            {
            this.paramCode = paramCode;
            }

    public String getParamCode()
            {
            return paramCode;
            }
                            public void setParamValue(String paramValue)
            {
            this.paramValue = paramValue;
            }

    public String getParamValue()
            {
            return paramValue;
            }
                            public void setParamDesc(String paramDesc)
            {
            this.paramDesc = paramDesc;
            }

    public String getParamDesc()
            {
            return paramDesc;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("paramId", getParamId())
                                .append("dbTypeId", getDbTypeId())
                                .append("paramCode", getParamCode())
                                .append("paramValue", getParamValue())
                                .append("paramDesc", getParamDesc())
            .toString();
        }
        }
