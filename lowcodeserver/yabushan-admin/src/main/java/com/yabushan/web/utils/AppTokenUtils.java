package com.yabushan.web.utils;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class AppTokenUtils {
    public static String[] validateValue(String appToken) throws Exception {
        String[] keyValues  = AESUtil.aesDecrypt(appToken).split("@");

        String yyyyMMdd = DateUtil.format(new Date(),"yyyyMMdd");
        if(keyValues.length != 3){
            return null;
        }else{
            if(!keyValues[2].equals(yyyyMMdd)){
                return null;
            }
        }
        return keyValues;

    }




    /**
     * 获取APPkey
     * @param values
     * @return
     */
    public  static String getAppKey(String[] values){
        return  values[0];
    }
}
