package com.yabushan.system.enums;

/**
 * 通用枚举信息
 * */
public enum EnumsConfig {

    // 岗位状态（1-禁用，0-启用）
    POST_STATUS_DISABLE("1","禁用"),
    POST_STATUS_ENABLE("0","启用"),

    // 部门状态（0-正常，2-删除）
    DEP_DELFLAG_NORMAL("0","正常"),
    DEP_DELFLAG_DELETE("2","删除"),

    // 日志执行状态（0-成功，1-失败）
    LOG_STATUS_SUCCESS("0","正常"),
    LOG_STATUS_FAIL("1","失败"),

    INIT_PASSWORD("Ro1054#$","初始化密码"),

    ROLE_KEY_INSIDE("general_staff","普通员工"),
    ROLE_KEY_EXTERNAL("external_personnel","外部人员"),
    ;

    EnumsConfig(String value, String text){
        this.text = text;
        this.value = value;
    }
    private String text;
    private String value;

    public String getText() {
        return text;
    }
    public String getValue() {
        return value;
    }
}
