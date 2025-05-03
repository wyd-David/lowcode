package com.yabushan.system.domain;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 短信模型对象 warn_sms_model
 *
 * @author yabushan
 * @date 2022-05-10
 */

@ApiModel(value = "短信模型对象")
public class WarnSmsModel extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String id;
    /**
     * 模板名称
     */
    @Excel(name = "模板名称")
    @ApiModelProperty("模板名称")
    private String name;
    /**
     * 模型id
     */
    @Excel(name = "模型id")
    @ApiModelProperty("模型id")
    private String modelId;
    /**
     * 模板内容
     */
    @Excel(name = "模板内容")
    @ApiModelProperty("模板内容")
    private String content;
    /**
     * 模板状态（1：启用  0：禁用）
     */
    @ApiModelProperty("模板状态（1：启用  0：禁用）")
    private String status;
    /**
     * _data
     */
    @Excel(name = "_data")
    @ApiModelProperty("_data")
    private String exData;
    /**
     * 所属租户
     */
    @Excel(name = "所属租户")
    @ApiModelProperty("所属租户")
    private String tenantId;
    /**
     * 当前处理人
     */
    @Excel(name = "当前处理人")
    @ApiModelProperty("当前处理人")
    private String innerHandler;
    /**
     * 标题
     */
    @Excel(name = "标题")
    @ApiModelProperty("标题")
    private String title;
    /**
     * 状态
     */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String innerStatus;
    /**
     * 创建人
     */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private String createdBy;
    /**
     * 修改人
     */
    @Excel(name = "修改人")
    @ApiModelProperty("修改人")
    private String updatedBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private Date createdAt;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "修改时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("修改时间")
    private Date updatedAt;
    /**
     * 所属部门
     */
    @Excel(name = "所属部门")
    @ApiModelProperty("所属部门")
    private String orgId;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelId() {
        return modelId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setExData(String exData) {
        this.exData = exData;
    }

    public String getExData() {
        return exData;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setInnerHandler(String innerHandler) {
        this.innerHandler = innerHandler;
    }

    public String getInnerHandler() {
        return innerHandler;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setInnerStatus(String innerStatus) {
        this.innerStatus = innerStatus;
    }

    public String getInnerStatus() {
        return innerStatus;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgId() {
        return orgId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("modelId", getModelId())
                .append("content", getContent())
                .append("createTime", getCreateTime())
                .append("status", getStatus())
                .append("exData", getExData())
                .append("tenantId", getTenantId())
                .append("innerHandler", getInnerHandler())
                .append("title", getTitle())
                .append("innerStatus", getInnerStatus())
                .append("createdBy", getCreatedBy())
                .append("updatedBy", getUpdatedBy())
                .append("createdAt", getCreatedAt())
                .append("updatedAt", getUpdatedAt())
                .append("orgId", getOrgId())
                .toString();
    }
}
