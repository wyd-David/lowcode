package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 员工证书子集对象 emp_sub_qua_certify
 *
 * @author yabushan
 * @date 2021-03-21
 */
public class EmpSubQuaCertify extends BaseEntity
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

    /** 证书名称 */
    @Excel(name = "证书名称")
    private String certificateName;

    /** 证书等级 */
    @Excel(name = "证书等级")
    private String certificateLevel;

    /** 发证机构 */
    @Excel(name = "发证机构")
    private String certificateOrg;

    /** 证书号码 */
    @Excel(name = "证书号码")
    private String certificateNumber;

    /** 通过时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "通过时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date passDate;

    /** 证书有效期 */
    @Excel(name = "证书有效期")
    private String effectiveDate;

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
    public void setCertificateName(String certificateName)
    {
        this.certificateName = certificateName;
    }

    public String getCertificateName()
    {
        return certificateName;
    }
    public void setCertificateLevel(String certificateLevel)
    {
        this.certificateLevel = certificateLevel;
    }

    public String getCertificateLevel()
    {
        return certificateLevel;
    }
    public void setCertificateOrg(String certificateOrg)
    {
        this.certificateOrg = certificateOrg;
    }

    public String getCertificateOrg()
    {
        return certificateOrg;
    }
    public void setCertificateNumber(String certificateNumber)
    {
        this.certificateNumber = certificateNumber;
    }

    public String getCertificateNumber()
    {
        return certificateNumber;
    }
    public void setPassDate(Date passDate)
    {
        this.passDate = passDate;
    }

    public Date getPassDate()
    {
        return passDate;
    }
    public void setEffectiveDate(String effectiveDate)
    {
        this.effectiveDate = effectiveDate;
    }

    public String getEffectiveDate()
    {
        return effectiveDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("recId", getRecId())
            .append("empId", getEmpId())
            .append("sortIndex", getSortIndex())
            .append("certificateName", getCertificateName())
            .append("certificateLevel", getCertificateLevel())
            .append("certificateOrg", getCertificateOrg())
            .append("certificateNumber", getCertificateNumber())
            .append("passDate", getPassDate())
            .append("effectiveDate", getEffectiveDate())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
