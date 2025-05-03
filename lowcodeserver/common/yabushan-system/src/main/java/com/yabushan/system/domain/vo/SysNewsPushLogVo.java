package com.yabushan.system.domain.vo;


import com.yabushan.common.core.domain.BaseEntity;


/**
 * 消息推送记录对象Vo sys_news_push_log
 *
 * @author 姜森焱
 * @date 2021-09-06
 */

public class SysNewsPushLogVo extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 已读数量
     */
    private String readCount;
    /**
     * 未读数量
     */
    private String UnreadCount;

    /**
     * $column.columnComment
     */
    private String id;
    /**
     * 发送内容
     */
    private String sendOutText;
    /**
     * 发送人账号
     */
    private String sendOutBy;
    /**
     * 发送人名称
     */
    private String sendOutByName;
    /**
     * 发送手机号
     */
    private String sendOutPhone;
    /**
     * 对应跳转详情的url
     */
    private String sendOutUrl;
    /**
     * 已读未读状态：1：未读，2：已读
     */
    private String readType;

    /**
     * 发送状态
     */
    private String sendOutState;
    /**
     * 流水号
     */
    private String sendOutNumber;
    /**
     * 发送时间
     */
    private String sendOutTime;

    /**
     * 模板标题
     */
    private String templateTitle;
    /**
     * 业务类型
     */
    private String businessType;

    /** 应用名称 */
    private String applyName;

    /**
     * 扩展字段1(简介)
     */
    private String extendK1;

    public String getExtendK1() {
        return extendK1;
    }

    public void setExtendK1(String extendK1) {
        this.extendK1 = extendK1;
    }

    public String getId() {
        return id;
    }

    public String getReadType() {
        return readType;
    }

    public void setReadType(String readType) {
        this.readType = readType;
    }

    public String getSendOutState() {
        return sendOutState;
    }

    public void setSendOutState(String sendOutState) {
        this.sendOutState = sendOutState;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSendOutText() {
        return sendOutText;
    }

    public void setSendOutText(String sendOutText) {
        this.sendOutText = sendOutText;
    }

    public String getSendOutBy() {
        return sendOutBy;
    }

    public void setSendOutBy(String sendOutBy) {
        this.sendOutBy = sendOutBy;
    }

    public String getSendOutByName() {
        return sendOutByName;
    }

    public void setSendOutByName(String sendOutByName) {
        this.sendOutByName = sendOutByName;
    }

    public String getSendOutPhone() {
        return sendOutPhone;
    }

    public void setSendOutPhone(String sendOutPhone) {
        this.sendOutPhone = sendOutPhone;
    }

    public String getSendOutUrl() {
        return sendOutUrl;
    }

    public void setSendOutUrl(String sendOutUrl) {
        this.sendOutUrl = sendOutUrl;
    }

    public String getSendOutNumber() {
        return sendOutNumber;
    }

    public void setSendOutNumber(String sendOutNumber) {
        this.sendOutNumber = sendOutNumber;
    }

    public String getSendOutTime() {
        return sendOutTime;
    }

    public void setSendOutTime(String sendOutTime) {
        this.sendOutTime = sendOutTime;
    }

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getReadCount() {
        return readCount;
    }

    public void setReadCount(String readCount) {
        this.readCount = readCount;
    }

    public String getUnreadCount() {
        return UnreadCount;
    }

    public void setUnreadCount(String unreadCount) {
        UnreadCount = unreadCount;
    }
}
