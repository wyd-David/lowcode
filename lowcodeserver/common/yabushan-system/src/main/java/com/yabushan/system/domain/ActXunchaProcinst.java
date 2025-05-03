package com.yabushan.system.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 我的工单对象 act_xuncha_procinst
 *
 * @author yabushan
 * @date 2022-07-07
 */
@ApiModel(value = "我的工单对象")
public class ActXunchaProcinst extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long procinstId;
    /**
     * 单号
     */
    @Excel(name = "单号")
    @ApiModelProperty("单号")
    private String businessKey;
    /**
     * 类型
     */
    @Excel(name = "类型编码")
    @ApiModelProperty("类型编码")
    private String businessType;
    /**
     * 类型
     */
    @Excel(name = "类型名称")
    @ApiModelProperty("类型名称")
    private String businessTypeName;
    /**
     * 名称
     */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String businessName;
    /**
     * 状态
     */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String status;
    /**
     * 创建人
     */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private String createdBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private Date createdTime;
    /**
     * 创建者名称
     */
    @Excel(name = "创建者名称")
    @ApiModelProperty("创建者名称")
    private String createdByName;
    /**
     * 更新人
     */
    @Excel(name = "更新人")
    @ApiModelProperty("更新人")
    private String updatedBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("更新时间")
    private Date updatedTime;
    /**
     * 更新者名称
     */
    @Excel(name = "更新者名称")
    @ApiModelProperty("更新者名称")
    private String updatedByName;
    /**
     * 删除标识（0存在，1删除）
     */
    @ApiModelProperty("删除标识（0存在，1删除）")
    private Integer delFlag;
    @Excel(name = "导入状态")
    private String importStatus;

    /**
     * 禁用状态  0启用,1禁用
     */
    private String disableEnableState;

    private String todoHandler;

    // 当前处理人
    private String todoHandlerName;

    // 最后更新时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastUpdateTime;

    // 待办路径
    private String todoUrl;

    private String todoType;

    private boolean delete;

    public String getTodoHandlerName() {
        return todoHandlerName;
    }

    public void setTodoHandlerName(String todoHandlerName) {
        this.todoHandlerName = todoHandlerName;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getTodoUrl() {
        return todoUrl;
    }

    public void setTodoUrl(String todoUrl) {
        this.todoUrl = todoUrl;
    }

    public String getTodoType() {
        return todoType;
    }

    public void setTodoType(String todoType) {
        this.todoType = todoType;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public String getTodoHandler() {
        return todoHandler;
    }

    public void setTodoHandler(String todoHandler) {
        this.todoHandler = todoHandler;
    }

    public String getBusinessTypeName() {
        return businessTypeName;
    }

    public void setBusinessTypeName(String businessTypeName) {
        this.businessTypeName = businessTypeName;
    }

    public Long getProcinstId() {
        return procinstId;
    }

    public void setProcinstId(Long procinstId) {
        this.procinstId = procinstId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedByName() {
        return updatedByName;
    }

    public void setUpdatedByName(String updatedByName) {
        this.updatedByName = updatedByName;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    public String getDisableEnableState() {
        return disableEnableState;
    }

    public void setDisableEnableState(String disableEnableState) {
        this.disableEnableState = disableEnableState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("procinstId", getProcinstId())
                .append("businessKey", getBusinessKey())
                .append("businessType", getBusinessType())
                .append("businessName", getBusinessName())
                .append("status", getStatus())
                .append("createdBy", getCreatedBy())
                .append("createdTime", getCreatedTime())
                .append("updatedBy", getUpdatedBy())
                .append("updatedTime", getUpdatedTime())
                .toString();
    }
}
