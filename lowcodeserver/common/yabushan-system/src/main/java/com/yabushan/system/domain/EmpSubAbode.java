package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 员工家庭住址子集对象 emp_sub_abode
 *
 * @author yabushan
 * @date 2021-03-21
 */
public class EmpSubAbode extends BaseEntity
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

    /** 户口本地址 */
    @Excel(name = "户口本地址")
    private String residenceAddress;

    /** 邮编 */
    @Excel(name = "邮编")
    private String zipcode;

    /** 本人移动电话 */
    @Excel(name = "本人移动电话")
    private String mobilePhone;

    /** 家庭联系电话 */
    @Excel(name = "家庭联系电话")
    private String familyPhone;

    /** 办公联系电话 */
    @Excel(name = "办公联系电话")
    private String workPhone;

    /** EMAIL邮箱 */
    @Excel(name = "EMAIL邮箱")
    private String email;

    /** 家庭地址 */
    @Excel(name = "家庭地址")
    private String faminlyAddress;

    /** 国家 */
    @Excel(name = "国家")
    private String country;

    /** 省（直辖市） */
    @Excel(name = "省", readConverterExp = "直=辖市")
    private String province;

    /** 市 */
    @Excel(name = "市")
    private String city;

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
    public void setResidenceAddress(String residenceAddress)
    {
        this.residenceAddress = residenceAddress;
    }

    public String getResidenceAddress()
    {
        return residenceAddress;
    }
    public void setZipcode(String zipcode)
    {
        this.zipcode = zipcode;
    }

    public String getZipcode()
    {
        return zipcode;
    }
    public void setMobilePhone(String mobilePhone)
    {
        this.mobilePhone = mobilePhone;
    }

    public String getMobilePhone()
    {
        return mobilePhone;
    }
    public void setFamilyPhone(String familyPhone)
    {
        this.familyPhone = familyPhone;
    }

    public String getFamilyPhone()
    {
        return familyPhone;
    }
    public void setWorkPhone(String workPhone)
    {
        this.workPhone = workPhone;
    }

    public String getWorkPhone()
    {
        return workPhone;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }
    public void setFaminlyAddress(String faminlyAddress)
    {
        this.faminlyAddress = faminlyAddress;
    }

    public String getFaminlyAddress()
    {
        return faminlyAddress;
    }
    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getCountry()
    {
        return country;
    }
    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getProvince()
    {
        return province;
    }
    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCity()
    {
        return city;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("recId", getRecId())
            .append("empId", getEmpId())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("sortIndex", getSortIndex())
            .append("residenceAddress", getResidenceAddress())
            .append("zipcode", getZipcode())
            .append("mobilePhone", getMobilePhone())
            .append("familyPhone", getFamilyPhone())
            .append("workPhone", getWorkPhone())
            .append("email", getEmail())
            .append("faminlyAddress", getFaminlyAddress())
            .append("country", getCountry())
            .append("province", getProvince())
            .append("city", getCity())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
