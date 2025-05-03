package com.yabushan.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 流程列表对象 act_hi_taskinst
 *
 * @author yabushan
 * @date 2021-04-25
 */
public class ActHiTaskinst extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private String id;

    /** 流程部署ID */
    @Excel(name = "流程部署ID")
    private String procDefId;

    /** 任务节点 */
    @Excel(name = "任务节点")
    private String taskDefKey;

    /** 流程实例 */
    @Excel(name = "流程实例")
    private String procInstId;

    /** 执行器 */
    @Excel(name = "执行器")
    private String executionId;

    /** 流程名称 */
    @Excel(name = "流程名称")
    private String name;

    /** 父节点名称 */
    @Excel(name = "父节点名称")
    private String parentTaskId;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 拥有者 */
    @Excel(name = "拥有者")
    private String owner;

    /** 处理人 */
    @Excel(name = "处理人")
    private String assignee;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 签收时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签收时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date claimTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 耗时 */
    @Excel(name = "耗时")
    private Long duration;

    /** 完成状态 */
    @Excel(name = "完成状态")
    private String deleteReason;

    /** 优先级 */
    @Excel(name = "优先级")
    private Long priority;

    /** $column.columnComment */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "优先级", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** $column.columnComment */
    @Excel(name = "优先级")
    private String formKey;

    /** $column.columnComment */
    @Excel(name = "优先级")
    private String category;

    /** 租户 */
    @Excel(name = "租户")
    private String tenantId;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setProcDefId(String procDefId)
    {
        this.procDefId = procDefId;
    }

    public String getProcDefId()
    {
        return procDefId;
    }
    public void setTaskDefKey(String taskDefKey)
    {
        this.taskDefKey = taskDefKey;
    }

    public String getTaskDefKey()
    {
        return taskDefKey;
    }
    public void setProcInstId(String procInstId)
    {
        this.procInstId = procInstId;
    }

    public String getProcInstId()
    {
        return procInstId;
    }
    public void setExecutionId(String executionId)
    {
        this.executionId = executionId;
    }

    public String getExecutionId()
    {
        return executionId;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setParentTaskId(String parentTaskId)
    {
        this.parentTaskId = parentTaskId;
    }

    public String getParentTaskId()
    {
        return parentTaskId;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getOwner()
    {
        return owner;
    }
    public void setAssignee(String assignee)
    {
        this.assignee = assignee;
    }

    public String getAssignee()
    {
        return assignee;
    }
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getStartTime()
    {
        return startTime;
    }
    public void setClaimTime(Date claimTime)
    {
        this.claimTime = claimTime;
    }

    public Date getClaimTime()
    {
        return claimTime;
    }

    public void setDuration(Long duration)
    {
        this.duration = duration;
    }

    public Long getDuration()
    {
        return duration;
    }
    public void setDeleteReason(String deleteReason)
    {
        this.deleteReason = deleteReason;
    }

    public String getDeleteReason()
    {
        return deleteReason;
    }
    public void setPriority(Long priority)
    {
        this.priority = priority;
    }

    public Long getPriority()
    {
        return priority;
    }
    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    public Date getDueDate()
    {
        return dueDate;
    }
    public void setFormKey(String formKey)
    {
        this.formKey = formKey;
    }

    public String getFormKey()
    {
        return formKey;
    }
    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getCategory()
    {
        return category;
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
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("procDefId", getProcDefId())
            .append("taskDefKey", getTaskDefKey())
            .append("procInstId", getProcInstId())
            .append("executionId", getExecutionId())
            .append("name", getName())
            .append("parentTaskId", getParentTaskId())
            .append("description", getDescription())
            .append("owner", getOwner())
            .append("assignee", getAssignee())
            .append("startTime", getStartTime())
            .append("claimTime", getClaimTime())
            .append("duration", getDuration())
            .append("deleteReason", getDeleteReason())
            .append("priority", getPriority())
            .append("dueDate", getDueDate())
            .append("formKey", getFormKey())
            .append("category", getCategory())
            .append("tenantId", getTenantId())
            .toString();
    }
}
