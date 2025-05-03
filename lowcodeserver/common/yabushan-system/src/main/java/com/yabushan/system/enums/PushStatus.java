package com.yabushan.system.enums;

/**
 * @Deseription
 * @Author zhoucp
 * @Date 2021/4/16 15:22
 */
public enum PushStatus {
    NO_PUSH("1","未推送"),
    PUSHED("2","已推送"),
    NO_OPEN_PUSH("3","未开放推送权限");

    private String status;
    private String desc;

    PushStatus(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
