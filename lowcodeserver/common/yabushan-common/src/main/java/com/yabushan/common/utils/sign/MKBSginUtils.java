package com.yabushan.common.utils.sign;

import java.math.BigDecimal;

/**
 * 满客宝验签工具类
 * */
public class MKBSginUtils {

    //消费记录查询接口拼装验签
    public static String counterSgin(String cardCode, String startDate, String endDate, Long requestTime){
        MD5 md5 = new MD5();
        String verifgStartDate = "";
        String verifgEndDate = "";
        if(isNullOrEmptyStr(startDate)){
            verifgStartDate="NULL";
        }else{
            verifgStartDate=startDate.replace("-","");
        }
        if(isNullOrEmptyStr(endDate)){
            verifgEndDate="NULL";
        }else{
            verifgEndDate=endDate.replace("-","");
        }
        return counterSgin(cardCode,verifgStartDate+verifgEndDate,requestTime);
    }

    public static String counterSgin(String cardCode, String verifyString, Long requestTime){
        MD5 md5 = new MD5();
        return md5.getMD5ofStr(md5.getMD5ofStr(cardCode)+md5.getMD5ofStr(verifyString)+requestTime);
    }

    //充值接口拼装验签
    public static String counterSgin(String cardCode, String rechargeCode,Double rechargeMoney, Long requestTime){
        MD5 md5 = new MD5();
        Long moneyLong = BigDecimal.valueOf(rechargeMoney*100).longValue();
        return md5.getMD5ofStr(md5.getMD5ofStr(cardCode)+md5.getMD5ofStr(rechargeCode)+md5.getMD5ofStr(moneyLong.toString())+requestTime);
    }
    public static boolean isNullOrEmptyStr(String value){
        if(value == null){
            return true;
        }else if("".equals(value)){
            return true;
        }else{
            return false;
        }
    }
}
