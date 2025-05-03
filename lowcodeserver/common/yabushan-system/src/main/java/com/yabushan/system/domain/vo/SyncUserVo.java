package com.yabushan.system.domain.vo;

import com.yabushan.system.domain.SyncUser;

/**
 * 查询用户信息
 *
 * @author leij
 */

public class SyncUserVo extends SyncUser {
    /** 部门全名称 */
    private String dpFullName;

    public SyncUserVo(){}

    public SyncUserVo(String dpId,String username){
        this.setDpid(dpId);
        this.setUsername(username);
    }

    public String getDpFullName() {
        return dpFullName;
    }

    public void setDpFullName(String dpFullName) {
        this.dpFullName = dpFullName;
    }
}
