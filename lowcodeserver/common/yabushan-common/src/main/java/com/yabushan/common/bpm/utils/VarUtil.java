package com.yabushan.common.bpm.utils;


import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: shiguiwu
 * @Date: 2019/7/2 10:17
 * @Description: 获取变量的值
 */
public class VarUtil {


    private VarUtil() {

    }

    /**
     *  根据vid获取变量的值，如果没有就返回默认值
     * @param vid
     * @param defaultValue
     * @return
     */
    public static String get(String vid,String defaultValue) {
        String value = "";
        return StringUtils.isNotBlank(value) ? value : defaultValue;

    }

    /***
     * 获取字符串的值，如果是空就返回默认值
     * @param value
     * @param defaultValue
     * @return
     */
    public static String getStr(String value, String defaultValue) {

        return StringUtils.isNotBlank(value) ? value : defaultValue;

    }



    public static String ObjToString(Object object, Class<?> type) {
        if (object instanceof Number) {
            Number number = (Number) object;
            if (type.equals(byte.class) || type.equals(Byte.class)) {
                return String.valueOf(number);
            }
            if (type.equals(short.class) || type.equals(Short.class)) {
                return String.valueOf(number);
            }
            if (type.equals(int.class) || type.equals(Integer.class)) {
                return String.valueOf(number);
            }
            if (type.equals(long.class) || type.equals(Long.class)) {
                return String.valueOf(number);
            }
            if (type.equals(float.class) || type.equals(Float.class)) {
                return String.valueOf(number);
            }
            if (type.equals(double.class) || type.equals(Double.class)) {
                return String.valueOf(number);
            }

        }
        if (type.equals(BigDecimal.class)) {
            BigDecimal bigDecimal = (BigDecimal) object;
            return bigDecimal.toString();
        }
        if (type.equals(String.class)) {
            return object == null ? "" : object.toString();
        }
        if (object instanceof Date) {
         //   return DateUtils.dateToString((Date) object, DateUtils.YYYY_MM_DD);
        }
        return "";
    }


    public static void main(String[] args) {
        Integer i = 8;

        String res = underlineToCamel("SDADS_DSADSA");

        System.out.println(res);

    }


    //下划线转驼峰
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        Boolean flag = false; // "_" 后转大写标志,默认字符前面没有"_"
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == '_') {
                flag = true;
                continue;   //标志设置为true,跳过
            } else {
                if (flag == true) {
                    //表示当前字符前面是"_" ,当前字符转大写
                    sb.append(Character.toUpperCase(param.charAt(i)));
                    flag = false;  //重置标识
                } else {
                    sb.append(Character.toLowerCase(param.charAt(i)));
                }
            }
        }
        return sb.toString();
    }

}
