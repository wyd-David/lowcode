package com.yabushan.capacity.utils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jsms.api.SendSMSResult;
import cn.jsms.api.ValidSMSResult;
import cn.jsms.api.common.SMSClient;
import cn.jsms.api.common.model.SMSPayload;

import java.util.Random;

public class SendCodeUtils {

    /**
     * 极关随机发送的验证码短信
     * @param masterSecret 密钥
     * @param appKey 应用的key值
     * @param TempId 模板ID
     * @param signId 签名
     * @param phoneNumber 发送验证码的手机号
     * @return
     */
    public static String  testSendTemplateSMS(String masterSecret,String appKey,Integer TempId,Integer signId,String phoneNumber) {
        SMSClient client = new SMSClient(masterSecret, appKey);
        SMSPayload payload = SMSPayload.newBuilder()
                .setMobileNumber(phoneNumber)
                .setTempId(TempId)
                .setSignId(signId)
                .build();
        try {
            SendSMSResult res = client.sendSMSCode(payload);
            return  res.getMessageId();
        } catch (APIRequestException e) {
            System.out.println("Error response from JPush server. Should review and fix it. "+e.toString());
            System.out.println("HTTP Status: " + e.getStatus());
            System.out.println("Error Message: " + e.getMessage());
        } catch (APIConnectionException e) {
            System.out.println("Connection error. Should retry later. "+ e);
        }
        return null;
    }

    /**
     * 
     * @param masterSecret 密钥
     * @param appKey 应用的key值
     * @param msgId   发送短信后返回的消息ID
     * @param code 用户输入的验证码
     * @return
     */
    public static Boolean checkSms(String masterSecret, String appKey , String msgId, String code) {
        SMSClient client = new SMSClient(masterSecret, appKey);
        try {
            ValidSMSResult res = client.sendValidSMSCode(msgId,code);
            return res.getIsValid();
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 通知短信
     * @param masterSecret 密钥
     * @param appKey 应用的key值
     * @param TempId 模板ID
     * @param signId  签名
     * @param code 待发送的验证码
     * @param phoneNumber  发送验证码的手机号
     */
    public static String testSendNoiceTemplateSMS(String masterSecret,String appKey,Integer TempId,Integer signId,String code,String phoneNumber) {
        SMSClient client = new SMSClient(masterSecret, appKey);
        SMSPayload payload = SMSPayload.newBuilder()
                .setMobileNumber(phoneNumber)
                .setTempId(TempId)
                .addTempPara("code", code)
                .setSignId(signId)
                .build();
        try {
            SendSMSResult res = client.sendTemplateSMS(payload);
            return res.getMessageId();
        } catch (APIRequestException e) {
            System.out.println("Error response from JPush server. Should review and fix it. "+e.toString());
            System.out.println("HTTP Status: " + e.getStatus());
            System.out.println("Error Message: " + e.getMessage());
        } catch (APIConnectionException e) {
            System.out.println("Connection error. Should retry later. "+ e);
        }
        return null;
    }


    public static Integer getRandomInteger(){
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000; // 生成一个介于1000到9999之间的随机数
        return randomNumber;
    }

    public static void main(String[] args) {
        System.out.println(getRandomInteger());
    }


}
