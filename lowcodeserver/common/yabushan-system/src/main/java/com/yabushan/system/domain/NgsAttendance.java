package com.yabushan.system.domain;

import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 考勤记录对象 ngs_attendance
 *
 * @author yabushan
 * @date 2021-06-06
 */
public class NgsAttendance extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String atdId;

    /** 老师微信端ID */
    @Excel(name = "老师微信端ID")
    private Long mangoUserId;

    /** 学生ID */
    @Excel(name = "学生ID")
    private String stId;

    /** 后台用户ID */
    @Excel(name = "后台用户ID")
    private Long secUserId;

    /** 使用课程节数 */
    @Excel(name = "使用课程节数")
    private Long useClassNum;

    /** 剩余课程节数 */
    @Excel(name = "剩余课程节数")
    private Long leftClassNum;


    /** 使用课程节数 */
    @Excel(name = "微信端老师名称")
    private String mangoUserIdName;

    /** 剩余课程节数 */
    @Excel(name = "PC端老师名称")
    private String secUserIdName;


    @Excel(name = "学生名称")
    private String stIdName;

    @Excel(name = "班级名称")
    private String orgName;


    public void setAtdId(String atdId)
    {
        this.atdId = atdId;
    }

    public String getAtdId()
    {
        return atdId;
    }
    public void setMangoUserId(Long mangoUserId)
    {
        this.mangoUserId = mangoUserId;
    }

    public Long getMangoUserId()
    {
        return mangoUserId;
    }
    public void setStId(String stId)
    {
        this.stId = stId;
    }

    public String getStId()
    {
        return stId;
    }
    public void setSecUserId(Long secUserId)
    {
        this.secUserId = secUserId;
    }

    public Long getSecUserId()
    {
        return secUserId;
    }
    public void setUseClassNum(Long useClassNum)
    {
        this.useClassNum = useClassNum;
    }

    public Long getUseClassNum()
    {
        return useClassNum;
    }
    public void setLeftClassNum(Long leftClassNum)
    {
        this.leftClassNum = leftClassNum;
    }

    public Long getLeftClassNum()
    {
        return leftClassNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("atdId", getAtdId())
            .append("mangoUserId", getMangoUserId())
            .append("stId", getStId())
            .append("secUserId", getSecUserId())
            .append("createTime", getCreateTime())
            .append("useClassNum", getUseClassNum())
            .append("leftClassNum", getLeftClassNum())
            .toString();
    }

    public String getMangoUserIdName() {
        return mangoUserIdName;
    }

    public void setMangoUserIdName(String mangoUserIdName) {
        this.mangoUserIdName = mangoUserIdName;
    }

    public String getSecUserIdName() {
        return secUserIdName;
    }

    public void setSecUserIdName(String secUserIdName) {
        this.secUserIdName = secUserIdName;
    }

    public String getStIdName() {
        return stIdName;
    }

    public void setStIdName(String stIdName) {
        this.stIdName = stIdName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
