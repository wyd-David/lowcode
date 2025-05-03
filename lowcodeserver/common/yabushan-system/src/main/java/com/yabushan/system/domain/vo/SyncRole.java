package com.yabushan.system.domain.vo;

/**
 * 同步角色信息
 *
 * @author leij
 */

public class SyncRole{
    /** 角色id */
    private String roleid;
    /** 角色名称 */
    private String rolename;

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
