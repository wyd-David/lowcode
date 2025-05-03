package com.yabushan.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 员工家庭关系子集对象 emp_sub_socity
 *
 * @author yabushan
 * @date 2021-03-21
 */
public class EmpSubSocity extends BaseEntity
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

    /** 成员学历 */
    @Excel(name = "成员学历")
    private Long memberEducation;

    /** 成员姓名 */
    @Excel(name = "成员姓名")
    private String memberName;

    /** 与本人的关系 */
    @Excel(name = "与本人的关系")
    private Long relationWith;

    /** 成员出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "成员出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date memberBirthday;

    /** 成员所在单位 */
    @Excel(name = "成员所在单位")
    private String memberInOrg;

    /** 成员政治面貌 */
    @Excel(name = "成员政治面貌")
    private Long memberPolityFace;

    /** 成员民族 */
    @Excel(name = "成员民族")
    private Long memberFolk;

    /** 职位职务 */
    @Excel(name = "职位职务")
    private String headship;

    /** 成员性别 */
    @Excel(name = "成员性别")
    private Long memberSex;

    /** 是否紧急情况第一联系人 */
    @Excel(name = "是否紧急情况第一联系人")
    private Long isEmergencyFirstLinkman;

    /** 成员联系电话 */
    @Excel(name = "成员联系电话")
    private String memberPhone;

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
    public void setMemberEducation(Long memberEducation)
    {
        this.memberEducation = memberEducation;
    }

    public Long getMemberEducation()
    {
        return memberEducation;
    }
    public void setMemberName(String memberName)
    {
        this.memberName = memberName;
    }

    public String getMemberName()
    {
        return memberName;
    }
    public void setRelationWith(Long relationWith)
    {
        this.relationWith = relationWith;
    }

    public Long getRelationWith()
    {
        return relationWith;
    }
    public void setMemberBirthday(Date memberBirthday)
    {
        this.memberBirthday = memberBirthday;
    }

    public Date getMemberBirthday()
    {
        return memberBirthday;
    }
    public void setMemberInOrg(String memberInOrg)
    {
        this.memberInOrg = memberInOrg;
    }

    public String getMemberInOrg()
    {
        return memberInOrg;
    }
    public void setMemberPolityFace(Long memberPolityFace)
    {
        this.memberPolityFace = memberPolityFace;
    }

    public Long getMemberPolityFace()
    {
        return memberPolityFace;
    }
    public void setMemberFolk(Long memberFolk)
    {
        this.memberFolk = memberFolk;
    }

    public Long getMemberFolk()
    {
        return memberFolk;
    }
    public void setHeadship(String headship)
    {
        this.headship = headship;
    }

    public String getHeadship()
    {
        return headship;
    }
    public void setMemberSex(Long memberSex)
    {
        this.memberSex = memberSex;
    }

    public Long getMemberSex()
    {
        return memberSex;
    }
    public void setIsEmergencyFirstLinkman(Long isEmergencyFirstLinkman)
    {
        this.isEmergencyFirstLinkman = isEmergencyFirstLinkman;
    }

    public Long getIsEmergencyFirstLinkman()
    {
        return isEmergencyFirstLinkman;
    }
    public void setMemberPhone(String memberPhone)
    {
        this.memberPhone = memberPhone;
    }

    public String getMemberPhone()
    {
        return memberPhone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("recId", getRecId())
            .append("empId", getEmpId())
            .append("sortIndex", getSortIndex())
            .append("memberEducation", getMemberEducation())
            .append("memberName", getMemberName())
            .append("relationWith", getRelationWith())
            .append("memberBirthday", getMemberBirthday())
            .append("memberInOrg", getMemberInOrg())
            .append("memberPolityFace", getMemberPolityFace())
            .append("memberFolk", getMemberFolk())
            .append("headship", getHeadship())
            .append("memberSex", getMemberSex())
            .append("isEmergencyFirstLinkman", getIsEmergencyFirstLinkman())
            .append("memberPhone", getMemberPhone())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
