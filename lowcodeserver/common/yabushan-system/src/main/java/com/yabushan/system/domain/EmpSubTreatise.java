package com.yabushan.system.domain;

import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 员工论文著作子集对象 emp_sub_treatise
 *
 * @author yabushan
 * @date 2021-03-21
 */
public class EmpSubTreatise extends BaseEntity
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

    /** 合（独）著译 */
    @Excel(name = "合", readConverterExp = "独=")
    private Long translations;

    /** 论文著作名称 */
    @Excel(name = "论文著作名称")
    private String treatiseName;

    /** 发表时间 */
    @Excel(name = "发表时间")
    private String publishDate;

    /** 出版刊登者社或刊物名称 */
    @Excel(name = "出版刊登者社或刊物名称")
    private String publishOrgName;

    /** 论文著作标识 */
    @Excel(name = "论文著作标识")
    private Long thesisIdentifier;

    /** 刊物级别 */
    @Excel(name = "刊物级别")
    private Long publicationLevel;

    /** 获奖及交流情况 */
    @Excel(name = "获奖及交流情况")
    private String intercourseCircs;

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
    public void setTranslations(Long translations)
    {
        this.translations = translations;
    }

    public Long getTranslations()
    {
        return translations;
    }
    public void setTreatiseName(String treatiseName)
    {
        this.treatiseName = treatiseName;
    }

    public String getTreatiseName()
    {
        return treatiseName;
    }
    public void setPublishDate(String publishDate)
    {
        this.publishDate = publishDate;
    }

    public String getPublishDate()
    {
        return publishDate;
    }
    public void setPublishOrgName(String publishOrgName)
    {
        this.publishOrgName = publishOrgName;
    }

    public String getPublishOrgName()
    {
        return publishOrgName;
    }
    public void setThesisIdentifier(Long thesisIdentifier)
    {
        this.thesisIdentifier = thesisIdentifier;
    }

    public Long getThesisIdentifier()
    {
        return thesisIdentifier;
    }
    public void setPublicationLevel(Long publicationLevel)
    {
        this.publicationLevel = publicationLevel;
    }

    public Long getPublicationLevel()
    {
        return publicationLevel;
    }
    public void setIntercourseCircs(String intercourseCircs)
    {
        this.intercourseCircs = intercourseCircs;
    }

    public String getIntercourseCircs()
    {
        return intercourseCircs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("recId", getRecId())
            .append("empId", getEmpId())
            .append("sortIndex", getSortIndex())
            .append("translations", getTranslations())
            .append("treatiseName", getTreatiseName())
            .append("publishDate", getPublishDate())
            .append("publishOrgName", getPublishOrgName())
            .append("thesisIdentifier", getThesisIdentifier())
            .append("publicationLevel", getPublicationLevel())
            .append("intercourseCircs", getIntercourseCircs())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
