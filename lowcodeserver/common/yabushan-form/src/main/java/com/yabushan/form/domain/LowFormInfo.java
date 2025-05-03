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
 * 基础单信息对象 low_form_info
 *
 * @author yabushan
 * @date 2022-10-10
 */

@ApiModel(value = "基础单信息对象")
public class LowFormInfo extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 主键 */
    @ApiModelProperty("主键")
    private String formUuid;
            /** 表单名称 */
    @Excel(name = "表单名称")
    @ApiModelProperty("表单名称")
    private String formName;
            /** 表单json字符串 */
    @Excel(name = "表单json字符串")
    @ApiModelProperty("表单json字符串")
    private String formJson;
            /** 表单对应实体库表名称 */
    @Excel(name = "表单对应实体库表名称")
    @ApiModelProperty("表单对应实体库表名称")
    private String formEntityName;
            /** 表单状态;状态，1：启用，0：初始，-1：删除 */
    @Excel(name = "表单状态;状态，1：启用，0：初始，-1：删除")
    @ApiModelProperty("表单状态;状态，1：启用，0：初始，-1：删除")
    private String formStatus;
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
            /** 是否一个用户只能上传一次;该表单是否一个用户只允许上传一次，1：是，0：否 */
    @Excel(name = "是否一个用户只能上传一次;该表单是否一个用户只允许上传一次，1：是，0：否")
    @ApiModelProperty("是否一个用户只能上传一次;该表单是否一个用户只允许上传一次，1：是，0：否")
    private String isSingleUser;
            /** 数据绑定方式,1:通过表单创建实体的方式，2：通过API的方式;数据绑定方式,1:通过表单创建实体的方式，2：通过API的方式 */
    @Excel(name = "数据绑定方式,1:通过表单创建实体的方式，2：通过API的方式;数据绑定方式,1:通过表单创建实体的方式，2：通过API的方式")
    @ApiModelProperty("数据绑定方式,1:通过表单创建实体的方式，2：通过API的方式;数据绑定方式,1:通过表单创建实体的方式，2：通过API的方式")
    private String bindDataWay;
            /** API接口ID;API接口ID */
    @Excel(name = "API接口ID;API接口ID")
    @ApiModelProperty("API接口ID;API接口ID")
    private String apiInfo;
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
                        public void setFormUuid(String formUuid)
            {
            this.formUuid = formUuid;
            }

    public String getFormUuid()
            {
            return formUuid;
            }
                            public void setFormName(String formName)
            {
            this.formName = formName;
            }

    public String getFormName()
            {
            return formName;
            }
                            public void setFormJson(String formJson)
            {
            this.formJson = formJson;
            }

    public String getFormJson()
            {
            return formJson;
            }
                            public void setFormEntityName(String formEntityName)
            {
            this.formEntityName = formEntityName;
            }

    public String getFormEntityName()
            {
            return formEntityName;
            }
                            public void setFormStatus(String formStatus)
            {
            this.formStatus = formStatus;
            }

    public String getFormStatus()
            {
            return formStatus;
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
                            public void setIsSingleUser(String isSingleUser)
            {
            this.isSingleUser = isSingleUser;
            }

    public String getIsSingleUser()
            {
            return isSingleUser;
            }
                            public void setBindDataWay(String bindDataWay)
            {
            this.bindDataWay = bindDataWay;
            }

    public String getBindDataWay()
            {
            return bindDataWay;
            }
                            public void setApiInfo(String apiInfo)
            {
            this.apiInfo = apiInfo;
            }

    public String getApiInfo()
            {
            return apiInfo;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("formUuid", getFormUuid())
                                .append("formName", getFormName())
                                .append("formJson", getFormJson())
                                .append("formEntityName", getFormEntityName())
                                .append("formStatus", getFormStatus())
                                .append("createdBy", getCreatedBy())
                                .append("createdTime", getCreatedTime())
                                .append("updatedBy", getUpdatedBy())
                                .append("updatedTime", getUpdatedTime())
                                .append("tenantId", getTenantId())
                                .append("isSingleUser", getIsSingleUser())
                                .append("bindDataWay", getBindDataWay())
                                .append("apiInfo", getApiInfo())
            .toString();
        }
        }
