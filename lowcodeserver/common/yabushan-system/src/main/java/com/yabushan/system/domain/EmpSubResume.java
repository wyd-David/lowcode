package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 员工个人工作经历子集对象 emp_sub_resume
 *
 * @author yabushan
 * @date 2021-03-21
 */
public class EmpSubResume extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private String recId;

    /** 员工ID */
    @Excel(name = "员工ID")
    private String empId;

    /** 排序显示 */
    @Excel(name = "排序显示")
    private Long sortIndex;

    /** 起始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "起始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date fromDate;

    /** 终止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "终止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date toDate;

    /** 所在单位 */
    @Excel(name = "所在单位")
    private String belongOrg;

    /** 从事工作或担任职务 */
    @Excel(name = "从事工作或担任职务")
    private String positionName;

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
    public void setSortIndex(Long sortIndex)
    {
        this.sortIndex = sortIndex;
    }

    public Long getSortIndex()
    {
        return sortIndex;
    }
    public void setFromDate(Date fromDate)
    {
        this.fromDate = fromDate;
    }

    public Date getFromDate()
    {
        return fromDate;
    }
    public void setToDate(Date toDate)
    {
        this.toDate = toDate;
    }

    public Date getToDate()
    {
        return toDate;
    }
    public void setBelongOrg(String belongOrg)
    {
        this.belongOrg = belongOrg;
    }

    public String getBelongOrg()
    {
        return belongOrg;
    }
    public void setPositionName(String positionName)
    {
        this.positionName = positionName;
    }

    public String getPositionName()
    {
        return positionName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("recId", getRecId())
            .append("empId", getEmpId())
            .append("sortIndex", getSortIndex())
            .append("fromDate", getFromDate())
            .append("toDate", getToDate())
            .append("belongOrg", getBelongOrg())
            .append("positionName", getPositionName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
