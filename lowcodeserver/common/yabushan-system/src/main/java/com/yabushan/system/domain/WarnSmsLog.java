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
 * 短信日志记录对象 warn_sms_log
 *
 * @author yabushan
 * @date 2022-05-10
 */

@ApiModel(value = "短信日志记录对象")
public class WarnSmsLog extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private String id;
    /**
     * 姓名
     */
    @Excel(name = "姓名")
    @ApiModelProperty("姓名")
    private String name;
    /**
     * 账号
     */
    @Excel(name = "账号")
    @ApiModelProperty("账号")
    private String loginId;
    /**
     * 电话
     */
    @Excel(name = "电话")
    @ApiModelProperty("电话")
    private String phone;
    /**
     * 工单号
     */
    @Excel(name = "工单号")
    @ApiModelProperty("工单号")
    private String orderNum;
    /**
     * 内容
     */
    @Excel(name = "内容")
    @ApiModelProperty("内容")
    private String content;
    /**
     * 状态（1：已发送  0：未发送  2：草稿(号码为空时)）
     */
    @ApiModelProperty("状态（1：已发送  0：未发送  2：草稿(号码为空时)）")
    private String status;
    /**
     * 短信类型(1：催办短信)
     */
    @Excel(name = "短信类型(1：催办短信)")
    @ApiModelProperty("短信类型(1：催办短信)")
    private String type;
    /**
     * 发送次数
     */
    @Excel(name = "发送次数")
    @ApiModelProperty("发送次数")
    private Long num;
    /**
     * 当前处理人
     */
    @Excel(name = "当前处理人")
    @ApiModelProperty("当前处理人")
    private String innerHandler;
    /**
     * _data
     */
    @Excel(name = "_data")
    @ApiModelProperty("_data")
    private String exData;
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
    /**
     * 所属租户
     */
    @Excel(name = "所属租户")
    @ApiModelProperty("所属租户")
    private String tenantId;
    @Excel(name = "导入状态")
    private String importStatus;


    private String sendType;//是否立即发送，1：是，2：按setTime时间点发送
    private Date setTime;//当send_type 为2时，则set_time小于当前时间时，则发送

    public WarnSmsLog(){}

    public WarnSmsLog(String name,String loginId,String phone,String content){
        this.name = name;this.loginId = loginId;this.phone = phone;this.content = content;
    }

    public WarnSmsLog(String name,String loginId,String phone,String content,String sendType,Date setTime){
        this.name = name;this.loginId = loginId;this.phone = phone;this.content = content;this.sendType = sendType;this.setTime = setTime;
    }

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

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderNum() {
        return orderNum;
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

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getNum() {
        return num;
    }

    public void setInnerHandler(String innerHandler) {
        this.innerHandler = innerHandler;
    }

    public String getInnerHandler() {
        return innerHandler;
    }

    public void setExData(String exData) {
        this.exData = exData;
    }

    public String getExData() {
        return exData;
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

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantId() {
        return tenantId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("loginId", getLoginId())
                .append("phone", getPhone())
                .append("orderNum", getOrderNum())
                .append("content", getContent())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("status", getStatus())
                .append("type", getType())
                .append("num", getNum())
                .append("innerHandler", getInnerHandler())
                .append("exData", getExData())
                .append("title", getTitle())
                .append("innerStatus", getInnerStatus())
                .append("createdBy", getCreatedBy())
                .append("updatedBy", getUpdatedBy())
                .append("createdAt", getCreatedAt())
                .append("updatedAt", getUpdatedAt())
                .append("orgId", getOrgId())
                .append("tenantId", getTenantId())
                .toString();
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public Date getSetTime() {
        return setTime;
    }

    public void setSetTime(Date setTime) {
        this.setTime = setTime;
    }
}
