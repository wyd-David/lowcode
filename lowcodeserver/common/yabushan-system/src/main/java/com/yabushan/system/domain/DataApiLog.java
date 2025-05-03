package com.yabushan.system.domain;

import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * api日志对象 data_api_log
 *
 * @author yabushan
 * @date 2021-06-13
 */
public class DataApiLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long logId;

    /** apiCode */
    @Excel(name = "apiCode")
    private String apiCode;

    /** 请求参数 */
    @Excel(name = "请求参数")
    private String paramInfo;

    /** 返回结果 */
    @Excel(name = "返回结果")
    private String resultInfo;

    /** 请求状态，成功，失败 */
    @Excel(name = "请求状态，成功，失败")
    private String resultCode;

    /** 请求用户 */
    @Excel(name = "请求用户")
    private String requestUser;

    public void setLogId(Long logId)
    {
        this.logId = logId;
    }

    public Long getLogId()
    {
        return logId;
    }
    public void setApiCode(String apiCode)
    {
        this.apiCode = apiCode;
    }

    public String getApiCode()
    {
        return apiCode;
    }
    public void setParamInfo(String paramInfo)
    {
        this.paramInfo = paramInfo;
    }

    public String getParamInfo()
    {
        return paramInfo;
    }
    public void setResultInfo(String resultInfo)
    {
        this.resultInfo = resultInfo;
    }

    public String getResultInfo()
    {
        return resultInfo;
    }
    public void setResultCode(String resultCode)
    {
        this.resultCode = resultCode;
    }

    public String getResultCode()
    {
        return resultCode;
    }
    public void setRequestUser(String requestUser)
    {
        this.requestUser = requestUser;
    }

    public String getRequestUser()
    {
        return requestUser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("logId", getLogId())
            .append("apiCode", getApiCode())
            .append("paramInfo", getParamInfo())
            .append("resultInfo", getResultInfo())
            .append("resultCode", getResultCode())
            .append("createTime", getCreateTime())
            .append("requestUser", getRequestUser())
            .toString();
    }
}
