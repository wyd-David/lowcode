package com.yabushan.system.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * service_step_btn_conf
 * @author
 */
public class ServiceStepBtnConf implements Serializable {
    /**
     * 主键
     */
    private Long keyId;

    /**
     * 步骤ID
     */
    private String stepId;

    /**
     * 流程实例ID
     */
    private String procdefId;

    /**
     * 步骤名
     */
    private String stepName;

    /**
     * 步骤id
     */
    private String stepCode;

    /**
     * 按钮编码
     */
    private String btnCode;

    /**
     * 按钮名称
     */
    private String btnName;

    /**
     * 说明
     */
    private String remark;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdTime;

    private static final long serialVersionUID = 1L;


    public Long getKeyId() {
        return keyId;
    }

    public void setKeyId(Long keyId) {
        this.keyId = keyId;
    }

    public String getStepId() {
        return stepId;
    }

    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public String getProcdefId() {
        return procdefId;
    }

    public void setProcdefId(String procdefId) {
        this.procdefId = procdefId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getStepCode() {
        return stepCode;
    }

    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
    }

    public String getBtnCode() {
        return btnCode;
    }

    public void setBtnCode(String btnCode) {
        this.btnCode = btnCode;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
}
