package com.yabushan.system.domain;

import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 学生对象 ngs_student
 *
 * @author yabushan
 * @date 2021-06-05
 */
public class NgsStudent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String stId;

    /** 学生姓名 */
    @Excel(name = "学生姓名")
    private String stName;

    /** 学生手机 */
    @Excel(name = "学生手机")
    private String stMobile;

    /** 学生所在组织ID */
    @Excel(name = "学生所在组织ID")
    private String stOrgId;

    /** 剩余课程数 */
    @Excel(name = "剩余课程数")
    private Long stClassNum;

    /** 初始化课程数 */
    @Excel(name = "初始化课程数")
    private Long stAllNum;

    /** 备注 */
    @Excel(name = "备注")
    private String stMemo;

    /** 备注 */
    @Excel(name = "班级名称")
    private String orgName;

    public void setStId(String stId)
    {
        this.stId = stId;
    }

    public String getStId()
    {
        return stId;
    }
    public void setStName(String stName)
    {
        this.stName = stName;
    }

    public String getStName()
    {
        return stName;
    }
    public void setStMobile(String stMobile)
    {
        this.stMobile = stMobile;
    }

    public String getStMobile()
    {
        return stMobile;
    }
    public void setStOrgId(String stOrgId)
    {
        this.stOrgId = stOrgId;
    }

    public String getStOrgId()
    {
        return stOrgId;
    }
    public void setStClassNum(Long stClassNum)
    {
        this.stClassNum = stClassNum;
    }

    public Long getStClassNum()
    {
        return stClassNum;
    }
    public void setStAllNum(Long stAllNum)
    {
        this.stAllNum = stAllNum;
    }

    public Long getStAllNum()
    {
        return stAllNum;
    }
    public void setStMemo(String stMemo)
    {
        this.stMemo = stMemo;
    }

    public String getStMemo()
    {
        return stMemo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("stId", getStId())
            .append("stName", getStName())
            .append("stMobile", getStMobile())
            .append("stOrgId", getStOrgId())
            .append("stClassNum", getStClassNum())
            .append("stAllNum", getStAllNum())
            .append("stMemo", getStMemo())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
