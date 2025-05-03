package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 客户礼物对象 ymx_customer_gift_info
 *
 * @author yabushan
 * @date 2021-04-02
 */
public class YmxCustomerGiftInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 客户礼物表主键 */
    private String customerGiftId;

    /** 客户表主键 */
    @Excel(name = "客户表主键")
    private String customerInformationId;

    /** 礼物表主键 */
    @Excel(name = "礼物表主键")
    private String giftId;

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

    public void setCustomerGiftId(String customerGiftId)
    {
        this.customerGiftId = customerGiftId;
    }

    public String getCustomerGiftId()
    {
        return customerGiftId;
    }
    public void setCustomerInformationId(String customerInformationId)
    {
        this.customerInformationId = customerInformationId;
    }

    public String getCustomerInformationId()
    {
        return customerInformationId;
    }
    public void setGiftId(String giftId)
    {
        this.giftId = giftId;
    }

    public String getGiftId()
    {
        return giftId;
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
            .append("customerGiftId", getCustomerGiftId())
            .append("customerInformationId", getCustomerInformationId())
            .append("giftId", getGiftId())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("lastUpdatedBy", getLastUpdatedBy())
            .append("lastUpdatedTime", getLastUpdatedTime())
            .toString();
    }
}
