package com.yabushan.common.core.domain.model;

import lombok.Data;

@Data
public class MsgCode {

    /**
     * 电话号码
     */
    private String  phoneNumber;
    /**
     * 应用token
     */
    private String appToken;
    /**
     * 短信模板编码
     */
    private String smsCode;

    /**
     * 短信模板类型
     * 1、短信模板
     * 2、通知模板
     */
    private  Integer smsType;

    /**
     * 验证码
     */
    private String code;

    /**
     * 推送短信后发送的唯一编码
     */
    private String  msgId;

    @Override
    public String toString() {
        return "MsgCode{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", appToken='" + appToken + '\'' +
                ", SmsCode='" + smsCode + '\'' +
                ", smsType=" + smsType +
                ", code='" + code + '\'' +
                ", msgId='" + msgId + '\'' +
                '}';
    }
}
