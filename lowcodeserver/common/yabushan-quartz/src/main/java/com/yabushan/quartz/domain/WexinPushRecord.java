package com.yabushan.quartz.domain;

import java.util.Date;

public class WexinPushRecord {
    private String id;

    private Date pushTime;

    private int pushTotal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPushTime() {
        return pushTime;
    }

    public void setPushTime(Date pushTime) {
        this.pushTime = pushTime;
    }

    public int getPushTotal() {
        return pushTotal;
    }

    public void setPushTotal(int pushTotal) {
        this.pushTotal = pushTotal;
    }
}
