package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户注册数据源信息对象 metadata_datasource_customer
 *
 * @author yabushan
 * @date 2021-06-13
 */
public class MetadataDatasourceCustomer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 名称 */
    @Excel(name = "名称")
    private String customerName;

    /** 主键 */
    private Long customerId;

    /** 数据源key */
    @Excel(name = "数据源key")
    private String datasourceId;

    /** 数据库类型 */
    @Excel(name = "数据库类型")
    private String datasrouceType;

    /** 数据库名称 */
    @Excel(name = "数据库名称")
    private String datasourceName;

    /** IP地址 */
    @Excel(name = "IP地址")
    private String datasourceIp;

    /** 数据源端口 */
    @Excel(name = "数据源端口")
    private String datasourcePort;

    /** 数据库账号 */
    @Excel(name = "数据库账号")
    private String datasourceAccount;

    /** 数据库密码 */
    @Excel(name = "数据库密码")
    private String datasourcePassword;

    /** 数据库连接串 */
    @Excel(name = "数据库连接串")
    private String datasourceUrl;

    /** 备注说明 */
    @Excel(name = "备注说明")
    private String datasourceMemo;

    /** 用户表id */
    @Excel(name = "用户表id")
    private Long createId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date creteTime;

    /** 数据库状态 */
    @Excel(name = "数据库状态")
    private String datasourceStatus;

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getCustomerName()
    {
        return customerName;
    }
    public void setCustomerId(Long customerId)
    {
        this.customerId = customerId;
    }

    public Long getCustomerId()
    {
        return customerId;
    }
    public void setDatasourceId(String datasourceId)
    {
        this.datasourceId = datasourceId;
    }

    public String getDatasourceId()
    {
        return datasourceId;
    }
    public void setDatasrouceType(String datasrouceType)
    {
        this.datasrouceType = datasrouceType;
    }

    public String getDatasrouceType()
    {
        return datasrouceType;
    }
    public void setDatasourceName(String datasourceName)
    {
        this.datasourceName = datasourceName;
    }

    public String getDatasourceName()
    {
        return datasourceName;
    }
    public void setDatasourceIp(String datasourceIp)
    {
        this.datasourceIp = datasourceIp;
    }

    public String getDatasourceIp()
    {
        return datasourceIp;
    }
    public void setDatasourcePort(String datasourcePort)
    {
        this.datasourcePort = datasourcePort;
    }

    public String getDatasourcePort()
    {
        return datasourcePort;
    }
    public void setDatasourceAccount(String datasourceAccount)
    {
        this.datasourceAccount = datasourceAccount;
    }

    public String getDatasourceAccount()
    {
        return datasourceAccount;
    }
    public void setDatasourcePassword(String datasourcePassword)
    {
        this.datasourcePassword = datasourcePassword;
    }

    public String getDatasourcePassword()
    {
        return datasourcePassword;
    }
    public void setDatasourceUrl(String datasourceUrl)
    {
        this.datasourceUrl = datasourceUrl;
    }

    public String getDatasourceUrl()
    {
        return datasourceUrl;
    }
    public void setDatasourceMemo(String datasourceMemo)
    {
        this.datasourceMemo = datasourceMemo;
    }

    public String getDatasourceMemo()
    {
        return datasourceMemo;
    }
    public void setCreateId(Long createId)
    {
        this.createId = createId;
    }

    public Long getCreateId()
    {
        return createId;
    }
    public void setCreteTime(Date creteTime)
    {
        this.creteTime = creteTime;
    }

    public Date getCreteTime()
    {
        return creteTime;
    }
    public void setDatasourceStatus(String datasourceStatus)
    {
        this.datasourceStatus = datasourceStatus;
    }

    public String getDatasourceStatus()
    {
        return datasourceStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("customerName", getCustomerName())
            .append("customerId", getCustomerId())
            .append("datasourceId", getDatasourceId())
            .append("datasrouceType", getDatasrouceType())
            .append("datasourceName", getDatasourceName())
            .append("datasourceIp", getDatasourceIp())
            .append("datasourcePort", getDatasourcePort())
            .append("datasourceAccount", getDatasourceAccount())
            .append("datasourcePassword", getDatasourcePassword())
            .append("datasourceUrl", getDatasourceUrl())
            .append("datasourceMemo", getDatasourceMemo())
            .append("createId", getCreateId())
            .append("createBy", getCreateBy())
            .append("creteTime", getCreteTime())
            .append("datasourceStatus", getDatasourceStatus())
            .toString();
    }
}
