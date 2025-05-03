package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 员工外国语子集对象 emp_sub_foreign_language
 *
 * @author yabushan
 * @date 2021-03-21
 */
public class EmpSubForeignLanguage extends BaseEntity
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

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private String sortIndex;

    /** 语种 */
    @Excel(name = "语种")
    private Long languageType;

    /** 外语情况说明 */
    @Excel(name = "外语情况说明")
    private String circsDescription;

    /** 掌握语种的级别 */
    @Excel(name = "掌握语种的级别")
    private Long masteryLevel;

    /** 获得时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "获得时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date gainDate;

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
    public void setSortIndex(String sortIndex)
    {
        this.sortIndex = sortIndex;
    }

    public String getSortIndex()
    {
        return sortIndex;
    }
    public void setLanguageType(Long languageType)
    {
        this.languageType = languageType;
    }

    public Long getLanguageType()
    {
        return languageType;
    }
    public void setCircsDescription(String circsDescription)
    {
        this.circsDescription = circsDescription;
    }

    public String getCircsDescription()
    {
        return circsDescription;
    }
    public void setMasteryLevel(Long masteryLevel)
    {
        this.masteryLevel = masteryLevel;
    }

    public Long getMasteryLevel()
    {
        return masteryLevel;
    }
    public void setGainDate(Date gainDate)
    {
        this.gainDate = gainDate;
    }

    public Date getGainDate()
    {
        return gainDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("recId", getRecId())
            .append("empId", getEmpId())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("sortIndex", getSortIndex())
            .append("languageType", getLanguageType())
            .append("circsDescription", getCircsDescription())
            .append("masteryLevel", getMasteryLevel())
            .append("gainDate", getGainDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
