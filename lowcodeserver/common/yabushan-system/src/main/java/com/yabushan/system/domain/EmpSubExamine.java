package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 员工考核情况子集对象 emp_sub_examine
 *
 * @author yabushan
 * @date 2021-03-21
 */
public class EmpSubExamine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 乐观锁 */
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

    /** 考核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date examineDate;

    /** 考核综合得分 */
    @Excel(name = "考核综合得分")
    private String examineScore;

    /** 考核等级 */
    @Excel(name = "考核等级")
    private String examineLevel;

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
    public void setExamineDate(Date examineDate)
    {
        this.examineDate = examineDate;
    }

    public Date getExamineDate()
    {
        return examineDate;
    }
    public void setExamineScore(String examineScore)
    {
        this.examineScore = examineScore;
    }

    public String getExamineScore()
    {
        return examineScore;
    }
    public void setExamineLevel(String examineLevel)
    {
        this.examineLevel = examineLevel;
    }

    public String getExamineLevel()
    {
        return examineLevel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("recId", getRecId())
            .append("empId", getEmpId())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("sortIndex", getSortIndex())
            .append("examineDate", getExamineDate())
            .append("examineScore", getExamineScore())
            .append("examineLevel", getExamineLevel())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
