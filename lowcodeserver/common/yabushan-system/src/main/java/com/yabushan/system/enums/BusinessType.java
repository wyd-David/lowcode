package com.yabushan.system.enums;

/**
 * @Deseription
 * @Author zhoucp
 * @Date 2021/4/16 16:06
 */
public enum BusinessType {
    TODO("1","todo"),
    DONE("2","done"),
    TOREAD("1","toread"),
    READ("2","read");
    private String code;
    private String val;

    BusinessType(String code,String val) {
        this.code = code;
        this.val = val;
    }

    public String getCode() {
        return code;
    }

    public String getVal() {
        return val;
    }

    public static String getTodoDoneValByCode(String code) {
        if(code.equals(TODO.code)) {
            return TODO.getVal();
        }
        return DONE.getVal();
    }
    public static String getToreadReadValByCode(String code) {
        if(code.equals(TOREAD.code)) {
            return TOREAD.getVal();
        }
        return READ.getVal();
    }
}
