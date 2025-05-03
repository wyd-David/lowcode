package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * step对象 service_step_conf
 *
 * @author yabushan
 * @date 2021-04-21
 */
public class ServiceStepConf extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long keyId;

    /** 步骤ID */
    @Excel(name = "步骤ID")
    private String stepId;

    /** 流程实例ID */
    @Excel(name = "流程实例ID")
    private String procdefId;

    /** 步骤名 */
    @Excel(name = "步骤名")
    private String stepName;

    /** 步骤id */
    @Excel(name = "步骤id")
    private String stepCode;

    /** 处理类型：1：具体到人，2：直接上级，3：按角色，4：按流程角色根据组织递归 */
    @Excel(name = "处理类型：1：具体到人，2：直接上级，3：按角色，4：按流程角色根据组织递归")
    private String dealType;

    /** 处理角色名称 */
    @Excel(name = "处理角色名称")
    private String dealRoleName;

    /** 处理角色ID */
    @Excel(name = "处理角色ID")
    private String dealRoleId;

    /** 处理人名称 */
    @Excel(name = "处理人名称")
    private String dealUserName;

    /** 处理人账号ID */
    @Excel(name = "处理人账号ID")
    private String dealUserId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 处理人账号ID */
    @Excel(name = "系统角色")
    private String systemRole;


    private String flowRole;

    public void setKeyId(Long keyId)
    {
        this.keyId = keyId;
    }

    public Long getKeyId()
    {
        return keyId;
    }
    public void setStepId(String stepId)
    {
        this.stepId = stepId;
    }

    public String getStepId()
    {
        return stepId;
    }
    public void setProcdefId(String procdefId)
    {
        this.procdefId = procdefId;
    }

    public String getProcdefId()
    {
        return procdefId;
    }
    public void setStepName(String stepName)
    {
        this.stepName = stepName;
    }

    public String getStepName()
    {
        return stepName;
    }
    public void setStepCode(String stepCode)
    {
        this.stepCode = stepCode;
    }

    public String getStepCode()
    {
        return stepCode;
    }
    public void setDealType(String dealType)
    {
        this.dealType = dealType;
    }

    public String getDealType()
    {
        return dealType;
    }
    public void setDealRoleName(String dealRoleName)
    {
        this.dealRoleName = dealRoleName;
    }

    public String getDealRoleName()
    {
        return dealRoleName;
    }
    public void setDealRoleId(String dealRoleId)
    {
        this.dealRoleId = dealRoleId;
    }

    public String getDealRoleId()
    {
        return dealRoleId;
    }
    public void setDealUserName(String dealUserName)
    {
        this.dealUserName = dealUserName;
    }

    public String getDealUserName()
    {
        return dealUserName;
    }
    public void setDealUserId(String dealUserId)
    {
        this.dealUserId = dealUserId;
    }

    public String getDealUserId()
    {
        return dealUserId;
    }
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("keyId", getKeyId())
            .append("stepId", getStepId())
            .append("procdefId", getProcdefId())
            .append("stepName", getStepName())
            .append("stepCode", getStepCode())
            .append("dealType", getDealType())
            .append("dealRoleName", getDealRoleName())
            .append("dealRoleId", getDealRoleId())
            .append("dealUserName", getDealUserName())
            .append("dealUserId", getDealUserId())
            .append("createDate", getCreateDate())
            .toString();
    }

    public String getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(String systemRole) {
        this.systemRole = systemRole;
    }

    public String getFlowRole() {
        return flowRole;
    }

    public void setFlowRole(String flowRole) {
        this.flowRole = flowRole;
    }
}
