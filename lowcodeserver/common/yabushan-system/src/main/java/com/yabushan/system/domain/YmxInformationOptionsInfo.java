package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 个人信息选项对象 ymx_information_options_info
 *
 * @author yabushan
 * @date 2021-04-02
 */
public class YmxInformationOptionsInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 个人信息选项表主键 */
    private String optionsId;

    /** 选项类型 */
    @Excel(name = "选项类型")
    private String optionsType;

    /** 选项名称 */
    @Excel(name = "选项名称")
    private String optionsName;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private String lastUpdatedBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdatedTime;

    public void setOptionsId(String optionsId)
    {
        this.optionsId = optionsId;
    }

    public String getOptionsId()
    {
        return optionsId;
    }
    public void setOptionsType(String optionsType)
    {
        this.optionsType = optionsType;
    }

    public String getOptionsType()
    {
        return optionsType;
    }
    public void setOptionsName(String optionsName)
    {
        this.optionsName = optionsName;
    }

    public String getOptionsName()
    {
        return optionsName;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }
    public void setLastUpdatedBy(String lastUpdatedBy)
    {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedBy()
    {
        return lastUpdatedBy;
    }
    public void setLastUpdatedTime(Date lastUpdatedTime)
    {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Date getLastUpdatedTime()
    {
        return lastUpdatedTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("optionsId", getOptionsId())
            .append("optionsType", getOptionsType())
            .append("optionsName", getOptionsName())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("lastUpdatedBy", getLastUpdatedBy())
            .append("lastUpdatedTime", getLastUpdatedTime())
            .toString();
    }
}
