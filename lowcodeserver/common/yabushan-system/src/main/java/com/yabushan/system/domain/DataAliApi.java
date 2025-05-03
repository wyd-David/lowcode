package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 聚合数据对象 data_ali_api
 *
 * @author yabushan
 * @date 2021-08-08
 */
public class DataAliApi extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** NG_KEY */
    private String keyId;

    /** HOST信息 */
    @Excel(name = "HOST信息")
    private String hostInfo;

    /** 请求方式,METHOD_INFO */
    @Excel(name = "请求方式,METHOD_INFO")
    private String methodInfo;

    /** PATH_INFO */
    @Excel(name = "PATH_INFO")
    private String pathInfo;

    /** appcode */
    @Excel(name = "appcode")
    private String appCode;

    /** CONTENT_TYPE */
    @Excel(name = "CONTENT_TYPE")
    private String contentType;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 状态，Y：启用，N：停用 */
    @Excel(name = "状态，Y：启用，N：停用")
    private String apiStatus;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updatedBy;

    /** 是否只返回body,Y:是，N:不是 */
    @Excel(name = "是否只返回body,Y:是，N:不是")
    private String returnBody;

    /** 剩余请求次数 */
    @Excel(name = "剩余请求次数")
    private Long levelNum;

    /** 服务提供商 */
    @Excel(name = "服务提供商")
    private String serverWay;

    /** 总请求次数 */
    @Excel(name = "总请求次数")
    private Long reqNum;

    /** 备注 */
    @Excel(name = "备注")
    private String memoInfo;

    /** 是否被授权，Y:是，N：自己的 */
    @Excel(name = "是否被授权，Y:是，N：自己的")
    private String isGrant;

    /** 使用方式 */
    @Excel(name = "使用方式")
    private String userWay;

    /** 拥有者 */
    @Excel(name = "拥有者")
    private String ownerName;

    public void setKeyId(String keyId)
    {
        this.keyId = keyId;
    }

    public String getKeyId()
    {
        return keyId;
    }
    public void setHostInfo(String hostInfo)
    {
        this.hostInfo = hostInfo;
    }

    public String getHostInfo()
    {
        return hostInfo;
    }
    public void setMethodInfo(String methodInfo)
    {
        this.methodInfo = methodInfo;
    }

    public String getMethodInfo()
    {
        return methodInfo;
    }
    public void setPathInfo(String pathInfo)
    {
        this.pathInfo = pathInfo;
    }

    public String getPathInfo()
    {
        return pathInfo;
    }
    public void setAppCode(String appCode)
    {
        this.appCode = appCode;
    }

    public String getAppCode()
    {
        return appCode;
    }
    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }

    public String getContentType()
    {
        return contentType;
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
    public void setApiStatus(String apiStatus)
    {
        this.apiStatus = apiStatus;
    }

    public String getApiStatus()
    {
        return apiStatus;
    }
    public void setUpdatedTime(Date updatedTime)
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime()
    {
        return updatedTime;
    }
    public void setUpdatedBy(String updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy()
    {
        return updatedBy;
    }
    public void setReturnBody(String returnBody)
    {
        this.returnBody = returnBody;
    }

    public String getReturnBody()
    {
        return returnBody;
    }
    public void setLevelNum(Long levelNum)
    {
        this.levelNum = levelNum;
    }

    public Long getLevelNum()
    {
        return levelNum;
    }
    public void setServerWay(String serverWay)
    {
        this.serverWay = serverWay;
    }

    public String getServerWay()
    {
        return serverWay;
    }
    public void setReqNum(Long reqNum)
    {
        this.reqNum = reqNum;
    }

    public Long getReqNum()
    {
        return reqNum;
    }
    public void setMemoInfo(String memoInfo)
    {
        this.memoInfo = memoInfo;
    }

    public String getMemoInfo()
    {
        return memoInfo;
    }
    public void setIsGrant(String isGrant)
    {
        this.isGrant = isGrant;
    }

    public String getIsGrant()
    {
        return isGrant;
    }
    public void setUserWay(String userWay)
    {
        this.userWay = userWay;
    }

    public String getUserWay()
    {
        return userWay;
    }
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("keyId", getKeyId())
            .append("hostInfo", getHostInfo())
            .append("methodInfo", getMethodInfo())
            .append("pathInfo", getPathInfo())
            .append("appCode", getAppCode())
            .append("contentType", getContentType())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("apiStatus", getApiStatus())
            .append("updatedTime", getUpdatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("returnBody", getReturnBody())
            .append("levelNum", getLevelNum())
            .append("serverWay", getServerWay())
            .append("reqNum", getReqNum())
            .append("memoInfo", getMemoInfo())
            .append("isGrant", getIsGrant())
            .append("userWay", getUserWay())
            .append("ownerName", getOwnerName())
            .toString();
    }
}
