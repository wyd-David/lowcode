package com.yabushan.form.domain;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 基础表单字段信息对象 low_form_filed_info
 *
 * @author yabushan
 * @date 2022-10-10
 */

@ApiModel(value = "基础表单字段信息对象")
public class LowFormFiledInfo extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 字段主键ID */
    @ApiModelProperty("字段主键ID")
    private String filedId;
            /** FORM表单主键 */
    @Excel(name = "FORM表单主键")
    @ApiModelProperty("FORM表单主键")
    private String formUuid;
            /** 表单字段名称 */
    @Excel(name = "表单字段名称")
    @ApiModelProperty("表单字段名称")
    private String filedName;
            /** 表单字段标签 */
    @Excel(name = "表单字段标签")
    @ApiModelProperty("表单字段标签")
    private String filedLabel;
            /** 字段类型 */
    @Excel(name = "字段类型")
    @ApiModelProperty("字段类型")
    private String filedType;
            /** 字段ICON */
    @Excel(name = "字段ICON")
    @ApiModelProperty("字段ICON")
    private String filedIcon;
            /** 字段标识 */
    @Excel(name = "字段标识")
    @ApiModelProperty("字段标识")
    private String formItemFlag;
            /** 实体字段名称 */
    @Excel(name = "实体字段名称")
    @ApiModelProperty("实体字段名称")
    private String entityName;
            /** 实体字段类型 */
    @Excel(name = "实体字段类型")
    @ApiModelProperty("实体字段类型")
    private String entityType;
            /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private String createdBy;
            /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private Date createdTime;
            /** 更新人 */
    @Excel(name = "更新人")
    @ApiModelProperty("更新人")
    private String updatedBy;
            /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("更新时间")
    private Date updatedTime;
            /** 租户ID */
    @Excel(name = "租户ID")
    @ApiModelProperty("租户ID")
    private String tenantId;
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
                        public void setFiledId(String filedId)
            {
            this.filedId = filedId;
            }

    public String getFiledId()
            {
            return filedId;
            }
                            public void setFormUuid(String formUuid)
            {
            this.formUuid = formUuid;
            }

    public String getFormUuid()
            {
            return formUuid;
            }
                            public void setFiledName(String filedName)
            {
            this.filedName = filedName;
            }

    public String getFiledName()
            {
            return filedName;
            }
                            public void setFiledLabel(String filedLabel)
            {
            this.filedLabel = filedLabel;
            }

    public String getFiledLabel()
            {
            return filedLabel;
            }
                            public void setFiledType(String filedType)
            {
            this.filedType = filedType;
            }

    public String getFiledType()
            {
            return filedType;
            }
                            public void setFiledIcon(String filedIcon)
            {
            this.filedIcon = filedIcon;
            }

    public String getFiledIcon()
            {
            return filedIcon;
            }
                            public void setFormItemFlag(String formItemFlag)
            {
            this.formItemFlag = formItemFlag;
            }

    public String getFormItemFlag()
            {
            return formItemFlag;
            }
                            public void setEntityName(String entityName)
            {
            this.entityName = entityName;
            }

    public String getEntityName()
            {
            return entityName;
            }
                            public void setEntityType(String entityType)
            {
            this.entityType = entityType;
            }

    public String getEntityType()
            {
            return entityType;
            }
                            public void setCreatedBy(String createdBy)
            {
            this.createdBy = createdBy;
            }

    public String getCreatedBy()
            {
            return createdBy;
            }
                            public void setCreatedTime(Date createdTime)
            {
            this.createdTime = createdTime;
            }

    public Date getCreatedTime()
            {
            return createdTime;
            }
                            public void setUpdatedBy(String updatedBy)
            {
            this.updatedBy = updatedBy;
            }

    public String getUpdatedBy()
            {
            return updatedBy;
            }
                            public void setUpdatedTime(Date updatedTime)
            {
            this.updatedTime = updatedTime;
            }

    public Date getUpdatedTime()
            {
            return updatedTime;
            }
                            public void setTenantId(String tenantId)
            {
            this.tenantId = tenantId;
            }

    public String getTenantId()
            {
            return tenantId;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("filedId", getFiledId())
                                .append("formUuid", getFormUuid())
                                .append("filedName", getFiledName())
                                .append("filedLabel", getFiledLabel())
                                .append("filedType", getFiledType())
                                .append("filedIcon", getFiledIcon())
                                .append("formItemFlag", getFormItemFlag())
                                .append("entityName", getEntityName())
                                .append("entityType", getEntityType())
                                .append("createdBy", getCreatedBy())
                                .append("createdTime", getCreatedTime())
                                .append("updatedBy", getUpdatedBy())
                                .append("updatedTime", getUpdatedTime())
                                .append("tenantId", getTenantId())
            .toString();
        }
        }
