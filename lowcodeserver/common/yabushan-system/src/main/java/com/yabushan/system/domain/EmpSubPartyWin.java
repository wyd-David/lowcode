package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 员工奖惩子集对象 emp_sub_party_win
 *
 * @author yabushan
 * @date 2021-03-21
 */
public class EmpSubPartyWin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 乐观锁 */
    private String recId;

    /** 员工ID */
    @Excel(name = "员工ID")
    private String empId;

    /** 排序 */
    @Excel(name = "排序")
    private Long sortIndex;

    /** 奖惩批准时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "奖惩批准时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date confirmDate;

    /** 奖励类别 */
    @Excel(name = "奖励类别")
    private Long hortationType;

    /** 奖励原因 */
    @Excel(name = "奖励原因")
    private String hortationReason;

    /** 奖励时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "奖励时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date hortationDate;

    /** 奖励惩罚批准机关 */
    @Excel(name = "奖励惩罚批准机关")
    private String confirmOrg;

    /** 奖罚类型 */
    @Excel(name = "奖罚类型")
    private Long hortationClass;

    /** 奖励内容 */
    @Excel(name = "奖励内容")
    private String hortationContent;

    /** 惩罚时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "惩罚时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date punishDate;

    /** 惩罚类别 */
    @Excel(name = "惩罚类别")
    private Long punishType;

    /** 惩罚原因 */
    @Excel(name = "惩罚原因")
    private String punishReason;

    /** 惩罚内容 */
    @Excel(name = "惩罚内容")
    private String punishContent;

    /** 处罚撤消时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "处罚撤消时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cancelPunishDate;

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
    public void setConfirmDate(Date confirmDate)
    {
        this.confirmDate = confirmDate;
    }

    public Date getConfirmDate()
    {
        return confirmDate;
    }
    public void setHortationType(Long hortationType)
    {
        this.hortationType = hortationType;
    }

    public Long getHortationType()
    {
        return hortationType;
    }
    public void setHortationReason(String hortationReason)
    {
        this.hortationReason = hortationReason;
    }

    public String getHortationReason()
    {
        return hortationReason;
    }
    public void setHortationDate(Date hortationDate)
    {
        this.hortationDate = hortationDate;
    }

    public Date getHortationDate()
    {
        return hortationDate;
    }
    public void setConfirmOrg(String confirmOrg)
    {
        this.confirmOrg = confirmOrg;
    }

    public String getConfirmOrg()
    {
        return confirmOrg;
    }
    public void setHortationClass(Long hortationClass)
    {
        this.hortationClass = hortationClass;
    }

    public Long getHortationClass()
    {
        return hortationClass;
    }
    public void setHortationContent(String hortationContent)
    {
        this.hortationContent = hortationContent;
    }

    public String getHortationContent()
    {
        return hortationContent;
    }
    public void setPunishDate(Date punishDate)
    {
        this.punishDate = punishDate;
    }

    public Date getPunishDate()
    {
        return punishDate;
    }
    public void setPunishType(Long punishType)
    {
        this.punishType = punishType;
    }

    public Long getPunishType()
    {
        return punishType;
    }
    public void setPunishReason(String punishReason)
    {
        this.punishReason = punishReason;
    }

    public String getPunishReason()
    {
        return punishReason;
    }
    public void setPunishContent(String punishContent)
    {
        this.punishContent = punishContent;
    }

    public String getPunishContent()
    {
        return punishContent;
    }
    public void setCancelPunishDate(Date cancelPunishDate)
    {
        this.cancelPunishDate = cancelPunishDate;
    }

    public Date getCancelPunishDate()
    {
        return cancelPunishDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("recId", getRecId())
            .append("empId", getEmpId())
            .append("sortIndex", getSortIndex())
            .append("confirmDate", getConfirmDate())
            .append("hortationType", getHortationType())
            .append("hortationReason", getHortationReason())
            .append("hortationDate", getHortationDate())
            .append("confirmOrg", getConfirmOrg())
            .append("hortationClass", getHortationClass())
            .append("hortationContent", getHortationContent())
            .append("punishDate", getPunishDate())
            .append("punishType", getPunishType())
            .append("punishReason", getPunishReason())
            .append("punishContent", getPunishContent())
            .append("cancelPunishDate", getCancelPunishDate())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
