package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 员工出国出境子集对象 emp_sub_abroad
 *
 * @author yabushan
 * @date 2021-03-21
 */
public class EmpSubAbroad extends BaseEntity
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

    /** 出国日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出国日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date abroadDate;

    /** 前往国家(地区) 数据字典 */
    @Excel(name = "前往国家(地区) 数据字典")
    private Long abroadArea;

    /** 所去单位 */
    @Excel(name = "所去单位")
    private String abroadOrg;

    /** 团组名称 */
    @Excel(name = "团组名称")
    private String groupName;

    /** 经费来源 数据字典 */
    @Excel(name = "经费来源 数据字典")
    private Long foundSource;

    /** 政审编号 */
    @Excel(name = "政审编号")
    private String auditingNo;

    /** 出国任务 */
    @Excel(name = "出国任务")
    private String abroadTask;

    /** 团组类别 数据字典 */
    @Excel(name = "团组类别 数据字典")
    private Long groupType;

    /** 境外停留天数 */
    @Excel(name = "境外停留天数")
    private Long stayDays;

    /** 回国鉴定 */
    @Excel(name = "回国鉴定")
    private String repatriateIdentify;

    /** 政审有效期 */
    @Excel(name = "政审有效期")
    private String auditingValidTo;

    /** 团组性质 */
    @Excel(name = "团组性质")
    private String groupProperty;

    /** 回国日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "回国日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date repatriateDate;

    /** 政审记录 数据字典 */
    @Excel(name = "政审记录 数据字典")
    private Long auditingRecord;

    /** 其它国家名称 */
    @Excel(name = "其它国家名称")
    private String otherCountryName;

    /** 备案日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "备案日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recordDate;

    /** 异常情况 */
    @Excel(name = "异常情况")
    private String abnormityCircs;

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
    public void setAbroadDate(Date abroadDate)
    {
        this.abroadDate = abroadDate;
    }

    public Date getAbroadDate()
    {
        return abroadDate;
    }
    public void setAbroadArea(Long abroadArea)
    {
        this.abroadArea = abroadArea;
    }

    public Long getAbroadArea()
    {
        return abroadArea;
    }
    public void setAbroadOrg(String abroadOrg)
    {
        this.abroadOrg = abroadOrg;
    }

    public String getAbroadOrg()
    {
        return abroadOrg;
    }
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getGroupName()
    {
        return groupName;
    }
    public void setFoundSource(Long foundSource)
    {
        this.foundSource = foundSource;
    }

    public Long getFoundSource()
    {
        return foundSource;
    }
    public void setAuditingNo(String auditingNo)
    {
        this.auditingNo = auditingNo;
    }

    public String getAuditingNo()
    {
        return auditingNo;
    }
    public void setAbroadTask(String abroadTask)
    {
        this.abroadTask = abroadTask;
    }

    public String getAbroadTask()
    {
        return abroadTask;
    }
    public void setGroupType(Long groupType)
    {
        this.groupType = groupType;
    }

    public Long getGroupType()
    {
        return groupType;
    }
    public void setStayDays(Long stayDays)
    {
        this.stayDays = stayDays;
    }

    public Long getStayDays()
    {
        return stayDays;
    }
    public void setRepatriateIdentify(String repatriateIdentify)
    {
        this.repatriateIdentify = repatriateIdentify;
    }

    public String getRepatriateIdentify()
    {
        return repatriateIdentify;
    }
    public void setAuditingValidTo(String auditingValidTo)
    {
        this.auditingValidTo = auditingValidTo;
    }

    public String getAuditingValidTo()
    {
        return auditingValidTo;
    }
    public void setGroupProperty(String groupProperty)
    {
        this.groupProperty = groupProperty;
    }

    public String getGroupProperty()
    {
        return groupProperty;
    }
    public void setRepatriateDate(Date repatriateDate)
    {
        this.repatriateDate = repatriateDate;
    }

    public Date getRepatriateDate()
    {
        return repatriateDate;
    }
    public void setAuditingRecord(Long auditingRecord)
    {
        this.auditingRecord = auditingRecord;
    }

    public Long getAuditingRecord()
    {
        return auditingRecord;
    }
    public void setOtherCountryName(String otherCountryName)
    {
        this.otherCountryName = otherCountryName;
    }

    public String getOtherCountryName()
    {
        return otherCountryName;
    }
    public void setRecordDate(Date recordDate)
    {
        this.recordDate = recordDate;
    }

    public Date getRecordDate()
    {
        return recordDate;
    }
    public void setAbnormityCircs(String abnormityCircs)
    {
        this.abnormityCircs = abnormityCircs;
    }

    public String getAbnormityCircs()
    {
        return abnormityCircs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("recId", getRecId())
            .append("empId", getEmpId())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("sortIndex", getSortIndex())
            .append("abroadDate", getAbroadDate())
            .append("abroadArea", getAbroadArea())
            .append("abroadOrg", getAbroadOrg())
            .append("groupName", getGroupName())
            .append("foundSource", getFoundSource())
            .append("auditingNo", getAuditingNo())
            .append("abroadTask", getAbroadTask())
            .append("groupType", getGroupType())
            .append("stayDays", getStayDays())
            .append("repatriateIdentify", getRepatriateIdentify())
            .append("auditingValidTo", getAuditingValidTo())
            .append("groupProperty", getGroupProperty())
            .append("repatriateDate", getRepatriateDate())
            .append("auditingRecord", getAuditingRecord())
            .append("otherCountryName", getOtherCountryName())
            .append("recordDate", getRecordDate())
            .append("abnormityCircs", getAbnormityCircs())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
