package com.yabushan.datasource.domain;


import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 数据源类型对象 metadata_datasource_type
 *
 * @author yabushan
 * @date 2023-11-19
 */

@ApiModel(value = "数据源类型对象")
public class MetadataDatasourceType extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 类型代码 */
    @ApiModelProperty("类型代码")
    private String typeCode;
            /** 类型名称 */
    @Excel(name = "类型名称")
    @ApiModelProperty("类型名称")
    private String typeName;
            /** 是否数据库 */
    @Excel(name = "是否数据库")
    @ApiModelProperty("是否数据库")
    private String isDatabase;
            /** 数据库类型 */
    @Excel(name = "数据库类型")
    @ApiModelProperty("数据库类型")
    private String dbType;
            /** 分类代码 */
    @Excel(name = "分类代码")
    @ApiModelProperty("分类代码")
    private String classCode;
            /** 图标ID */
    @Excel(name = "图标ID")
    @ApiModelProperty("图标ID")
    private String iconId;
            /** 排序 */
    @Excel(name = "排序")
    @ApiModelProperty("排序")
    private Long order;
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
                        public void setTypeCode(String typeCode)
            {
            this.typeCode = typeCode;
            }

    public String getTypeCode()
            {
            return typeCode;
            }
                            public void setTypeName(String typeName)
            {
            this.typeName = typeName;
            }

    public String getTypeName()
            {
            return typeName;
            }
                            public void setIsDatabase(String isDatabase)
            {
            this.isDatabase = isDatabase;
            }

    public String getIsDatabase()
            {
            return isDatabase;
            }
                            public void setDbType(String dbType)
            {
            this.dbType = dbType;
            }

    public String getDbType()
            {
            return dbType;
            }
                            public void setClassCode(String classCode)
            {
            this.classCode = classCode;
            }

    public String getClassCode()
            {
            return classCode;
            }
                            public void setIconId(String iconId)
            {
            this.iconId = iconId;
            }

    public String getIconId()
            {
            return iconId;
            }
                            public void setOrder(Long order)
            {
            this.order = order;
            }

    public Long getOrder()
            {
            return order;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("typeCode", getTypeCode())
                                .append("typeName", getTypeName())
                                .append("isDatabase", getIsDatabase())
                                .append("dbType", getDbType())
                                .append("classCode", getClassCode())
                                .append("iconId", getIconId())
                                .append("order", getOrder())
            .toString();
        }
        }
