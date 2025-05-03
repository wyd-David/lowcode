package com.yabushan.system.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 消息推送记录对象 sys_news_push_log
 *
 * @author 姜森焱
 * @date 2021-09-06
 */

@ApiModel(value = "消息推送记录对象")
public class SysNewsPushLog extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private Long id;
    /**
     * 模板ID
     */
    @Excel(name = "模板ID")
    @ApiModelProperty("模板ID")
    private String templateId;
    /**
     * 发送内容
     */
    @Excel(name = "发送内容")
    @ApiModelProperty("发送内容")
    private String sendOutText;
    /**
     * 发送人账号
     */
    @Excel(name = "发送人账号")
    @ApiModelProperty("发送人账号")
    private String sendOutBy;
    /**
     * 发送人名称
     */
    @Excel(name = "发送人名称")
    @ApiModelProperty("发送人名称")
    private String sendOutByName;
    /**
     * 发送手机号
     */
    @Excel(name = "发送手机号")
    @ApiModelProperty("发送手机号")
    private String sendOutPhone;
    /**
     * 对应跳转详情的url
     */
    @Excel(name = "对应跳转详情的url")
    @ApiModelProperty("对应跳转详情的url")
    private String sendOutUrl;
    /**
     * 流水号
     */
    @Excel(name = "流水号")
    @ApiModelProperty("流水号")
    private String sendOutNumber;
    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "发送时间")
    @ApiModelProperty("发送时间")
    private Date sendOutTime;
    /**
     * 图片
     */
    private String imgKey;
    /**
     * 已读未读状态：1：未读，2：已读
     */
    private String readType;
    /**
     * 扩展字段1
     */
    private String extendK1;
    /**
     * 扩展字段2
     */
    private String extendK2;

    /**
     * 发送状态
     */
    @Excel(name = "发送状态")
    @ApiModelProperty("发送状态")
    private String sendOutState;
    @Excel(name = "导入状态")
    private String importStatus;

    public SysNewsPushLog(){}

    public SysNewsPushLog(String sendOutText,String sendOutBy){
        this.sendOutText = sendOutText;
        this.sendOutBy = sendOutBy;
    }

    public String getImgKey() {
        return imgKey;
    }

    public void setImgKey(String imgKey) {
        this.imgKey = imgKey;
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public String getExtendK1() {
        return extendK1;
    }

    public void setExtendK1(String extendK1) {
        this.extendK1 = extendK1;
    }

    public String getExtendK2() {
        return extendK2;
    }

    public void setExtendK2(String extendK2) {
        this.extendK2 = extendK2;
    }

    public Date getSendOutTime() {
        return sendOutTime;
    }

    public void setSendOutTime(Date sendOutTime) {
        this.sendOutTime = sendOutTime;
    }

    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setSendOutText(String sendOutText) {
        this.sendOutText = sendOutText;
    }

    public String getSendOutText() {
        return sendOutText;
    }

    public void setSendOutBy(String sendOutBy) {
        this.sendOutBy = sendOutBy;
    }

    public String getSendOutBy() {
        return sendOutBy;
    }

    public void setSendOutByName(String sendOutByName) {
        this.sendOutByName = sendOutByName;
    }

    public String getSendOutByName() {
        return sendOutByName;
    }

    public void setSendOutPhone(String sendOutPhone) {
        this.sendOutPhone = sendOutPhone;
    }

    public String getSendOutPhone() {
        return sendOutPhone;
    }

    public void setSendOutUrl(String sendOutUrl) {
        this.sendOutUrl = sendOutUrl;
    }

    public String getSendOutUrl() {
        return sendOutUrl;
    }

    public void setSendOutNumber(String sendOutNumber) {
        this.sendOutNumber = sendOutNumber;
    }

    public String getSendOutNumber() {
        return sendOutNumber;
    }

    public void setSendOutState(String sendOutState) {
        this.sendOutState = sendOutState;
    }

    public String getSendOutState() {
        return sendOutState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("templateId", getTemplateId())
                .append("sendOutText", getSendOutText())
                .append("sendOutBy", getSendOutBy())
                .append("sendOutByName", getSendOutByName())
                .append("sendOutPhone", getSendOutPhone())
                .append("sendOutUrl", getSendOutUrl())
                .append("sendOutNumber", getSendOutNumber())
                .append("sendOutState", getSendOutState())
                .append("createTime", getCreateTime())
                .append("createBy", getCreateBy())
                .append("updateTime", getUpdateTime())
                .append("updateBy", getUpdateBy())
                .append("sendOutTime", getSendOutTime())
                .toString();
    }
}
