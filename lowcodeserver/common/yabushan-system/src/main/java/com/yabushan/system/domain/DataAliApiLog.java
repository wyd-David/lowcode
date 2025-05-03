package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 聚合日志对象 data_ali_api_log
 *
 * @author yabushan
 * @date 2021-08-08
 */
public class DataAliApiLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日志ID */
    private Long logId;

    /** API标识 */
    @Excel(name = "API标识")
    private String keyId;

    /** 请求用户表示 */
    @Excel(name = "请求用户表示")
    private String userkey;

    /** 请求参数 */
    @Excel(name = "请求参数")
    private String reqparam;

    /** 请求时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "请求时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 请求结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "请求结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 返回结果 */
    @Excel(name = "返回结果")
    private String reqResult;

    public void setLogId(Long logId)
    {
        this.logId = logId;
    }

    public Long getLogId()
    {
        return logId;
    }
    public void setKeyId(String keyId)
    {
        this.keyId = keyId;
    }

    public String getKeyId()
    {
        return keyId;
    }
    public void setUserkey(String userkey)
    {
        this.userkey = userkey;
    }

    public String getUserkey()
    {
        return userkey;
    }
    public void setReqparam(String reqparam)
    {
        this.reqparam = reqparam;
    }

    public String getReqparam()
    {
        return reqparam;
    }
    public void setCreatedTime(Date createdTime)
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime()
    {
        return createdTime;
    }
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }


    public void setReqResult(String reqResult)
    {
        this.reqResult = reqResult;
    }

    public String getReqResult()
    {
        return reqResult;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("logId", getLogId())
            .append("keyId", getKeyId())
            .append("userkey", getUserkey())
            .append("reqparam", getReqparam())
            .append("createdTime", getCreatedTime())

            .append("reqResult", getReqResult())
            .toString();
    }
}
