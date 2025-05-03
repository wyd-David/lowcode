package com.yabushan.system.domain;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 推送待阅已阅对象 push_pro_toread_read
 *
 * @author yabushan
 * @date 2022-04-28
 */

@ApiModel(value = "推送待阅已阅对象")
public class PushProToreadRead extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** id(待阅、已阅一样) */
    @Excel(name = "id(待阅、已阅一样)")
    @ApiModelProperty("id(待阅、已阅一样)")
    private String toreadId;
    /** 预警工单汇总id */
    @Excel(name = "预警工单汇总id")
    @ApiModelProperty("预警工单汇总id")
    private String orderId;
    /** 应用名称 */
    @Excel(name = "应用名称")
    @ApiModelProperty("应用名称")
    private String appName;
    /** 当前处理人 */
    @Excel(name = "当前处理人")
    @ApiModelProperty("当前处理人")
    private String owner;
    /** 任务名 */
    @Excel(name = "任务名")
    @ApiModelProperty("任务名")
    private String taskName;
    /** 处理地址 */
    @Excel(name = "处理地址")
    @ApiModelProperty("处理地址")
    private String url;
    /** 格式为’yyyy-MM-dd HH:mm:ss’, 如果记录是新增的，modifytime则为新增时间 */
    @Excel(name = "格式为’yyyy-MM-dd HH:mm:ss’, 如果记录是新增的，modifytime则为新增时间")
    @ApiModelProperty("格式为’yyyy-MM-dd HH:mm:ss’, 如果记录是新增的，modifytime则为新增时间")
    private String modifyTime;
    /** A/M/D，表示增、改、删 */
    @Excel(name = "A/M/D，表示增、改、删")
    @ApiModelProperty("A/M/D，表示增、改、删")
    private String type;
    /** 1-未推送，2-推送，3-未开放推送权限 */
    @Excel(name = "1-未推送，2-推送，3-未开放推送权限")
    @ApiModelProperty("1-未推送，2-推送，3-未开放推送权限")
    private String pushStatus;
    /** 1-待阅，2-已阅 */
    @Excel(name = "1-待阅，2-已阅")
    @ApiModelProperty("1-待阅，2-已阅")
    private String dataType;
    /** 重推次数 */
    @Excel(name = "重推次数")
    @ApiModelProperty("重推次数")
    private Long retryCount;
    /** 入库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("入库时间")
    private Date insertTime;
    @Excel(name = "导入状态")
    private String importStatus;

    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }
    /**
     * 禁用状态  0启用,1禁用
     */
    private String disableEnableState;

    public String getDisableEnableState() {
        return disableEnableState;
    }

    public void setDisableEnableState(String disableEnableState) {
        this.disableEnableState = disableEnableState;
    }
    public void setToreadId(String toreadId)
    {
        this.toreadId = toreadId;
    }

    public String getToreadId()
    {
        return toreadId;
    }
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }

    public String getOrderId()
    {
        return orderId;
    }
    public void setAppName(String appName)
    {
        this.appName = appName;
    }

    public String getAppName()
    {
        return appName;
    }
    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getOwner()
    {
        return owner;
    }
    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public String getTaskName()
    {
        return taskName;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
    public void setModifyTime(String modifyTime)
    {
        this.modifyTime = modifyTime;
    }

    public String getModifyTime()
    {
        return modifyTime;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setPushStatus(String pushStatus)
    {
        this.pushStatus = pushStatus;
    }

    public String getPushStatus()
    {
        return pushStatus;
    }
    public void setDataType(String dataType)
    {
        this.dataType = dataType;
    }

    public String getDataType()
    {
        return dataType;
    }
    public void setRetryCount(Long retryCount)
    {
        this.retryCount = retryCount;
    }

    public Long getRetryCount()
    {
        return retryCount;
    }
    public void setInsertTime(Date insertTime)
    {
        this.insertTime = insertTime;
    }

    public Date getInsertTime()
    {
        return insertTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("toreadId", getToreadId())
                .append("orderId", getOrderId())
                .append("appName", getAppName())
                .append("owner", getOwner())
                .append("taskName", getTaskName())
                .append("url", getUrl())
                .append("createTime", getCreateTime())
                .append("modifyTime", getModifyTime())
                .append("type", getType())
                .append("pushStatus", getPushStatus())
                .append("dataType", getDataType())
                .append("retryCount", getRetryCount())
                .append("insertTime", getInsertTime())
                .toString();
    }
}
