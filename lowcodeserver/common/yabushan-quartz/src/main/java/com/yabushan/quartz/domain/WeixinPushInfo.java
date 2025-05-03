package com.yabushan.quartz.domain;


import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 微信小程序消息推送内容对象 weixin_push_info
 *
 * @author CJH
 * @date 2022-03-04
 */

@ApiModel(value = "微信小程序消息推送内容对象")
public class WeixinPushInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String id;
    /**
     * 联系人id
     */
    @Excel(name = "联系人id")
    @ApiModelProperty("联系人id")
    private String contactUserId;
    /**
     * 联系人姓名
     */
    @Excel(name = "联系人姓名")
    @ApiModelProperty("联系人姓名")
    private String contactUserName;
    /**
     * 推送返回结果
     */
    @Excel(name = "推送返回结果")
    @ApiModelProperty("推送返回结果")
    private String pushResult;
    /**
     * 推送内容
     */
    @Excel(name = "推送内容")
    @ApiModelProperty("推送内容")
    private String pushContent;
    /**
     * 待办待阅ID
     */
    @Excel(name = "待办待阅ID")
    @ApiModelProperty("待办待阅ID")
    private String todoId;
    /**
     * 推送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "推送时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("推送时间")
    private Date pushTime;
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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setContactUserId(String contactUserId) {
        this.contactUserId = contactUserId;
    }

    public String getContactUserId() {
        return contactUserId;
    }

    public void setContactUserName(String contactUserName) {
        this.contactUserName = contactUserName;
    }

    public String getContactUserName() {
        return contactUserName;
    }

    public void setPushResult(String pushResult) {
        this.pushResult = pushResult;
    }

    public String getPushResult() {
        return pushResult;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public Date getPushTime() {
        return pushTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("contactUserId", getContactUserId())
                .append("contactUserName", getContactUserName())
                .append("pushResult", getPushResult())
                .append("pushContent", getPushContent())
                .append("todoId", getTodoId())
                .append("pushTime", getPushTime())
                .toString();
    }
}
