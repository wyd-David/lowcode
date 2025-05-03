package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 员工培训子集对象 emp_sub_training
 *
 * @author yabushan
 * @date 2021-03-21
 */
public class EmpSubTraining extends BaseEntity
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

    /** 培训途径 */
    @Excel(name = "培训途径")
    private Long trainingMethod;

    /** 培训项目名称 */
    @Excel(name = "培训项目名称")
    private String projectName;

    /** 主办单位 */
    @Excel(name = "主办单位")
    private String doOrg;

    /** 资格证号 */
    @Excel(name = "资格证号")
    private String qualificationNo;

    /** 培训内容类别 */
    @Excel(name = "培训内容类别")
    private Long contentType;

    /** 考试成绩 */
    @Excel(name = "考试成绩")
    private String examScore;

    /** 课时数（小时） */
    @Excel(name = "课时数", readConverterExp = "小=时")
    private String examHour;

    /** 发证机关 */
    @Excel(name = "发证机关")
    private String grantOrg;

    /** 培训项目地点 */
    @Excel(name = "培训项目地点")
    private String projectAddress;

    /** 培训类别 */
    @Excel(name = "培训类别")
    private Long trainingType;

    /** 培训项目编号 */
    @Excel(name = "培训项目编号")
    private String projectCode;

    /** 培训目的 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "培训目的", width = 30, dateFormat = "yyyy-MM-dd")
    private Date projectGoal;

    /** 项目培训费用 */
    @Excel(name = "项目培训费用")
    private String projectCost;

    /** 个人培训费用 */
    @Excel(name = "个人培训费用")
    private String personalCost;

    /** 培训师资来源 */
    @Excel(name = "培训师资来源")
    private String teachSource;

    /** 是否海外师资 */
    @Excel(name = "是否海外师资")
    private Long foreignTeachFlag;

    /** 培训合同 */
    @Excel(name = "培训合同")
    private Long trainingContract;

    /** 培训文号 */
    @Excel(name = "培训文号")
    private String trainingWritNo;

    /** 培训项目周期（天） */
    @Excel(name = "培训项目周期", readConverterExp = "天=")
    private String cycleDay;

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
    public void setTrainingMethod(Long trainingMethod)
    {
        this.trainingMethod = trainingMethod;
    }

    public Long getTrainingMethod()
    {
        return trainingMethod;
    }
    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getProjectName()
    {
        return projectName;
    }
    public void setDoOrg(String doOrg)
    {
        this.doOrg = doOrg;
    }

    public String getDoOrg()
    {
        return doOrg;
    }
    public void setQualificationNo(String qualificationNo)
    {
        this.qualificationNo = qualificationNo;
    }

    public String getQualificationNo()
    {
        return qualificationNo;
    }
    public void setContentType(Long contentType)
    {
        this.contentType = contentType;
    }

    public Long getContentType()
    {
        return contentType;
    }
    public void setExamScore(String examScore)
    {
        this.examScore = examScore;
    }

    public String getExamScore()
    {
        return examScore;
    }
    public void setExamHour(String examHour)
    {
        this.examHour = examHour;
    }

    public String getExamHour()
    {
        return examHour;
    }
    public void setGrantOrg(String grantOrg)
    {
        this.grantOrg = grantOrg;
    }

    public String getGrantOrg()
    {
        return grantOrg;
    }
    public void setProjectAddress(String projectAddress)
    {
        this.projectAddress = projectAddress;
    }

    public String getProjectAddress()
    {
        return projectAddress;
    }
    public void setTrainingType(Long trainingType)
    {
        this.trainingType = trainingType;
    }

    public Long getTrainingType()
    {
        return trainingType;
    }
    public void setProjectCode(String projectCode)
    {
        this.projectCode = projectCode;
    }

    public String getProjectCode()
    {
        return projectCode;
    }
    public void setProjectGoal(Date projectGoal)
    {
        this.projectGoal = projectGoal;
    }

    public Date getProjectGoal()
    {
        return projectGoal;
    }
    public void setProjectCost(String projectCost)
    {
        this.projectCost = projectCost;
    }

    public String getProjectCost()
    {
        return projectCost;
    }
    public void setPersonalCost(String personalCost)
    {
        this.personalCost = personalCost;
    }

    public String getPersonalCost()
    {
        return personalCost;
    }
    public void setTeachSource(String teachSource)
    {
        this.teachSource = teachSource;
    }

    public String getTeachSource()
    {
        return teachSource;
    }
    public void setForeignTeachFlag(Long foreignTeachFlag)
    {
        this.foreignTeachFlag = foreignTeachFlag;
    }

    public Long getForeignTeachFlag()
    {
        return foreignTeachFlag;
    }
    public void setTrainingContract(Long trainingContract)
    {
        this.trainingContract = trainingContract;
    }

    public Long getTrainingContract()
    {
        return trainingContract;
    }
    public void setTrainingWritNo(String trainingWritNo)
    {
        this.trainingWritNo = trainingWritNo;
    }

    public String getTrainingWritNo()
    {
        return trainingWritNo;
    }
    public void setCycleDay(String cycleDay)
    {
        this.cycleDay = cycleDay;
    }

    public String getCycleDay()
    {
        return cycleDay;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("recId", getRecId())
            .append("empId", getEmpId())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("sortIndex", getSortIndex())
            .append("trainingMethod", getTrainingMethod())
            .append("projectName", getProjectName())
            .append("doOrg", getDoOrg())
            .append("qualificationNo", getQualificationNo())
            .append("contentType", getContentType())
            .append("examScore", getExamScore())
            .append("examHour", getExamHour())
            .append("grantOrg", getGrantOrg())
            .append("projectAddress", getProjectAddress())
            .append("trainingType", getTrainingType())
            .append("projectCode", getProjectCode())
            .append("projectGoal", getProjectGoal())
            .append("projectCost", getProjectCost())
            .append("personalCost", getPersonalCost())
            .append("teachSource", getTeachSource())
            .append("foreignTeachFlag", getForeignTeachFlag())
            .append("trainingContract", getTrainingContract())
            .append("trainingWritNo", getTrainingWritNo())
            .append("cycleDay", getCycleDay())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
