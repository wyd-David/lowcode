package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 员工教育子集对象 emp_sub_education
 *
 * @author yabushan
 * @date 2021-03-21
 */
public class EmpSubEducation extends BaseEntity
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

    /** 学历 */
    @Excel(name = "学历")
    private Long education;

    /** 入学时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入学时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date entranceDate;

    /** 学习形式 */
    @Excel(name = "学习形式")
    private Long studyForm;

    /** 毕(肄)业时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "毕(肄)业时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date graduateDate;

    /** 毕业学校名称 */
    @Excel(name = "毕业学校名称")
    private String graduateSchoolName;

    /** 学位 */
    @Excel(name = "学位")
    private Long degree;

    /** 学位授予时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "学位授予时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date awardDate;

    /** 所学专业 */
    @Excel(name = "所学专业")
    private String specialty;

    /** 参加工作时学历标识 */
    @Excel(name = "参加工作时学历标识")
    private Long joinWorkFlag;

    /** 最高学历标识 */
    @Excel(name = "最高学历标识")
    private Long highestFlag;

    /** 毕业情况 */
    @Excel(name = "毕业情况")
    private Long graduateCondition;

    /** 学位类别 */
    @Excel(name = "学位类别")
    private String degreeClass;

    /** 学制 */
    @Excel(name = "学制")
    private String studySystem;

    /** 所学专业类别 */
    @Excel(name = "所学专业类别")
    private String majorType;

    /** 是否全日制 */
    @Excel(name = "是否全日制")
    private Long isFulltime;

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
    public void setEducation(Long education)
    {
        this.education = education;
    }

    public Long getEducation()
    {
        return education;
    }
    public void setEntranceDate(Date entranceDate)
    {
        this.entranceDate = entranceDate;
    }

    public Date getEntranceDate()
    {
        return entranceDate;
    }
    public void setStudyForm(Long studyForm)
    {
        this.studyForm = studyForm;
    }

    public Long getStudyForm()
    {
        return studyForm;
    }
    public void setGraduateDate(Date graduateDate)
    {
        this.graduateDate = graduateDate;
    }

    public Date getGraduateDate()
    {
        return graduateDate;
    }
    public void setGraduateSchoolName(String graduateSchoolName)
    {
        this.graduateSchoolName = graduateSchoolName;
    }

    public String getGraduateSchoolName()
    {
        return graduateSchoolName;
    }
    public void setDegree(Long degree)
    {
        this.degree = degree;
    }

    public Long getDegree()
    {
        return degree;
    }
    public void setAwardDate(Date awardDate)
    {
        this.awardDate = awardDate;
    }

    public Date getAwardDate()
    {
        return awardDate;
    }
    public void setSpecialty(String specialty)
    {
        this.specialty = specialty;
    }

    public String getSpecialty()
    {
        return specialty;
    }
    public void setJoinWorkFlag(Long joinWorkFlag)
    {
        this.joinWorkFlag = joinWorkFlag;
    }

    public Long getJoinWorkFlag()
    {
        return joinWorkFlag;
    }
    public void setHighestFlag(Long highestFlag)
    {
        this.highestFlag = highestFlag;
    }

    public Long getHighestFlag()
    {
        return highestFlag;
    }
    public void setGraduateCondition(Long graduateCondition)
    {
        this.graduateCondition = graduateCondition;
    }

    public Long getGraduateCondition()
    {
        return graduateCondition;
    }
    public void setDegreeClass(String degreeClass)
    {
        this.degreeClass = degreeClass;
    }

    public String getDegreeClass()
    {
        return degreeClass;
    }
    public void setStudySystem(String studySystem)
    {
        this.studySystem = studySystem;
    }

    public String getStudySystem()
    {
        return studySystem;
    }
    public void setMajorType(String majorType)
    {
        this.majorType = majorType;
    }

    public String getMajorType()
    {
        return majorType;
    }
    public void setIsFulltime(Long isFulltime)
    {
        this.isFulltime = isFulltime;
    }

    public Long getIsFulltime()
    {
        return isFulltime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("recId", getRecId())
            .append("empId", getEmpId())
            .append("sortIndex", getSortIndex())
            .append("education", getEducation())
            .append("entranceDate", getEntranceDate())
            .append("studyForm", getStudyForm())
            .append("graduateDate", getGraduateDate())
            .append("graduateSchoolName", getGraduateSchoolName())
            .append("degree", getDegree())
            .append("awardDate", getAwardDate())
            .append("specialty", getSpecialty())
            .append("joinWorkFlag", getJoinWorkFlag())
            .append("highestFlag", getHighestFlag())
            .append("graduateCondition", getGraduateCondition())
            .append("degreeClass", getDegreeClass())
            .append("studySystem", getStudySystem())
            .append("majorType", getMajorType())
            .append("isFulltime", getIsFulltime())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
