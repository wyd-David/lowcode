package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 员工计算机能力子集对象 emp_sub_computer
 *
 * @author yabushan
 * @date 2021-03-21
 */
public class EmpSubComputer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private String recId;

    /** 员工ID */
    @Excel(name = "员工ID")
    private String empId;

    /** 开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 排序显示 */
    @Excel(name = "排序显示")
    private Long sortIndex;

    /** 类别 */
    @Excel(name = "类别")
    private String abilityType;

    /** 能力等级 */
    @Excel(name = "能力等级")
    private String abilityLevel;

    /** 能力获得时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "能力获得时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gainDate;

    /** 名称 */
    @Excel(name = "名称")
    private String abilityName;

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
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }
    public void setSortIndex(Long sortIndex)
    {
        this.sortIndex = sortIndex;
    }

    public Long getSortIndex()
    {
        return sortIndex;
    }
    public void setAbilityType(String abilityType)
    {
        this.abilityType = abilityType;
    }

    public String getAbilityType()
    {
        return abilityType;
    }
    public void setAbilityLevel(String abilityLevel)
    {
        this.abilityLevel = abilityLevel;
    }

    public String getAbilityLevel()
    {
        return abilityLevel;
    }
    public void setGainDate(Date gainDate)
    {
        this.gainDate = gainDate;
    }

    public Date getGainDate()
    {
        return gainDate;
    }
    public void setAbilityName(String abilityName)
    {
        this.abilityName = abilityName;
    }

    public String getAbilityName()
    {
        return abilityName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("recId", getRecId())
            .append("empId", getEmpId())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("sortIndex", getSortIndex())
            .append("abilityType", getAbilityType())
            .append("abilityLevel", getAbilityLevel())
            .append("gainDate", getGainDate())
            .append("abilityName", getAbilityName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
