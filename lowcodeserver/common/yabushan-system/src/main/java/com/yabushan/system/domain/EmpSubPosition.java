package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 员工从事岗位子集对象 emp_sub_position
 *
 * @author yabushan
 * @date 2021-03-21
 */
public class EmpSubPosition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private String recId;

    /** 员工ID */
    @Excel(name = "员工ID")
    private String empId;

    /** 排序显示 */
    @Excel(name = "排序显示")
    private String sortIndex;

    /** 从事何专业工作 */
    @Excel(name = "从事何专业工作")
    private String jobKind;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String positionName;

    /** 岗位ID */
    @Excel(name = "岗位ID")
    private String positionId;

    /** 专业岗位起始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "专业岗位起始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date positionFromDate;

    /** 专业岗位截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "专业岗位截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date positionToDate;

    /** 岗位部门名称 */
    @Excel(name = "岗位部门名称")
    private String deptName;

    /** 岗位职级 */
    @Excel(name = "岗位职级")
    private String positionLevelName;

    public void setRecId(String recId)
    {
        this.recId = recId;
    }

    public String getRecId()
    {
        return recId;
    }
    public void setEmpId(String empId)
    {
        this.empId = empId;
    }

    public String getEmpId()
    {
        return empId;
    }
    public void setSortIndex(String sortIndex)
    {
        this.sortIndex = sortIndex;
    }

    public String getSortIndex()
    {
        return sortIndex;
    }
    public void setJobKind(String jobKind)
    {
        this.jobKind = jobKind;
    }

    public String getJobKind()
    {
        return jobKind;
    }
    public void setPositionName(String positionName)
    {
        this.positionName = positionName;
    }

    public String getPositionName()
    {
        return positionName;
    }
    public void setPositionId(String positionId)
    {
        this.positionId = positionId;
    }

    public String getPositionId()
    {
        return positionId;
    }
    public void setPositionFromDate(Date positionFromDate)
    {
        this.positionFromDate = positionFromDate;
    }

    public Date getPositionFromDate()
    {
        return positionFromDate;
    }
    public void setPositionToDate(Date positionToDate)
    {
        this.positionToDate = positionToDate;
    }

    public Date getPositionToDate()
    {
        return positionToDate;
    }
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getDeptName()
    {
        return deptName;
    }
    public void setPositionLevelName(String positionLevelName)
    {
        this.positionLevelName = positionLevelName;
    }

    public String getPositionLevelName()
    {
        return positionLevelName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("recId", getRecId())
            .append("empId", getEmpId())
            .append("sortIndex", getSortIndex())
            .append("jobKind", getJobKind())
            .append("positionName", getPositionName())
            .append("positionId", getPositionId())
            .append("positionFromDate", getPositionFromDate())
            .append("positionToDate", getPositionToDate())
            .append("deptName", getDeptName())
            .append("positionLevelName", getPositionLevelName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
