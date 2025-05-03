package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 客户信息对象 ymx_customer_information_info
 *
 * @author yabushan
 * @date 2021-04-02
 */
public class YmxCustomerInformationInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 客户信息表主键 */
    private String customerInformationId;

    /** 邮箱地址 */
    @Excel(name = "邮箱地址")
    private String customerEmail;

    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;

    /** facebookId */
    @Excel(name = "facebookId")
    private String customerFacebookId;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String lastUpdatedBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdatedTime;

    public void setCustomerInformationId(String customerInformationId)
    {
        this.customerInformationId = customerInformationId;
    }

    public String getCustomerInformationId()
    {
        return customerInformationId;
    }
    public void setCustomerEmail(String customerEmail)
    {
        this.customerEmail = customerEmail;
    }

    public String getCustomerEmail()
    {
        return customerEmail;
    }
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getCustomerName()
    {
        return customerName;
    }
    public void setCustomerFacebookId(String customerFacebookId)
    {
        this.customerFacebookId = customerFacebookId;
    }

    public String getCustomerFacebookId()
    {
        return customerFacebookId;
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
            .append("customerInformationId", getCustomerInformationId())
            .append("customerEmail", getCustomerEmail())
            .append("customerName", getCustomerName())
            .append("customerFacebookId", getCustomerFacebookId())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("lastUpdatedBy", getLastUpdatedBy())
            .append("lastUpdatedTime", getLastUpdatedTime())
            .toString();
    }
}
