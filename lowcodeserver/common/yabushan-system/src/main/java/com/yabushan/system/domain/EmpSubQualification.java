package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 员工职业资格认证子集对象 emp_sub_qualification
 *
 * @author yabushan
 * @date 2021-03-21
 */
public class EmpSubQualification extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 乐观锁 */
    private String recId;

    /** 员工ID */
    @Excel(name = "员工ID")
    private String empId;

    /** 排序显示 */
    @Excel(name = "排序显示")
    private Long sortIndex;

    /** 职业资格等级 */
    @Excel(name = "职业资格等级")
    private Long qualificationLevel;

    /** 资格名称 */
    @Excel(name = "资格名称")
    private String qualificationName;

    /** 通过日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "通过日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date passDate;

    /** 资格鉴定单位 */
    @Excel(name = "资格鉴定单位")
    private String qualificationOrg;

    /** 鉴定证号 */
    @Excel(name = "鉴定证号")
    private String identifyNo;

    /** 国家标准职级 */
    @Excel(name = "国家标准职级")
    private Long standardQualificationLevel;

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
    public void setQualificationLevel(Long qualificationLevel)
    {
        this.qualificationLevel = qualificationLevel;
    }

    public Long getQualificationLevel()
    {
        return qualificationLevel;
    }
    public void setQualificationName(String qualificationName)
    {
        this.qualificationName = qualificationName;
    }

    public String getQualificationName()
    {
        return qualificationName;
    }
    public void setPassDate(Date passDate)
    {
        this.passDate = passDate;
    }

    public Date getPassDate()
    {
        return passDate;
    }
    public void setQualificationOrg(String qualificationOrg)
    {
        this.qualificationOrg = qualificationOrg;
    }

    public String getQualificationOrg()
    {
        return qualificationOrg;
    }
    public void setIdentifyNo(String identifyNo)
    {
        this.identifyNo = identifyNo;
    }

    public String getIdentifyNo()
    {
        return identifyNo;
    }
    public void setStandardQualificationLevel(Long standardQualificationLevel)
    {
        this.standardQualificationLevel = standardQualificationLevel;
    }

    public Long getStandardQualificationLevel()
    {
        return standardQualificationLevel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("recId", getRecId())
            .append("empId", getEmpId())
            .append("sortIndex", getSortIndex())
            .append("qualificationLevel", getQualificationLevel())
            .append("qualificationName", getQualificationName())
            .append("passDate", getPassDate())
            .append("qualificationOrg", getQualificationOrg())
            .append("identifyNo", getIdentifyNo())
            .append("standardQualificationLevel", getStandardQualificationLevel())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
