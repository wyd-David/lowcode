package com.yabushan.common.core.domain.model;

import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;

/**
 * 用户对象 sys_user
 *
 * @author ruoyi
 */
public class SysUserRoleVo extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 用户账号 */
    @Excel(name = "登录名称")
    private String userName;

    /** 角色id */
    @Excel(name = "角色id")
    private String roleId;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
