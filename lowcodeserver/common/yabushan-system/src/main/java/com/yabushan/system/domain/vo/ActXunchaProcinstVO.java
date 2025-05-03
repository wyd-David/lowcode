package com.yabushan.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.DataSource;

import java.util.Date;

/**
 * @author Lch
 * @Date 2022-07-08 14:19
 * @Description：
 */
public class ActXunchaProcinstVO {

    // 主键
    private Long procinstId;

    // 单号
    private String businessKey;

    // 类型
    private String businessType;
    // 类型名称
    private String businessTypeName;

    // 名称
    private String businessName;

    // 当前状态
    private String status;

    // 发起者
    private String createdBy;

    // 发起时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdTime;

    // 当前处理人
    private String todoHandlerName;

    // 最后更新时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastUpdateTime;

    // 待办路径
    private String todoUrl;

    private String todoType;

    private Integer countNum;

    private boolean delete;

    public Integer getCountNum() {
        return countNum;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public String getTodoType() {
        return todoType;
    }

    public void setTodoType(String todoType) {
        this.todoType = todoType;
    }

    public String getTodoUrl() {
        return todoUrl;
    }

    public void setTodoUrl(String todoUrl) {
        this.todoUrl = todoUrl;
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
}
