package com.yabushan.system.domain.vo;

import com.yabushan.system.domain.SyncOrg;

import java.util.List;

/**
 * 查询用户信息
 *
 * @author leij
 */
public class DepUserVo {

    /** 部门名称 */
    private String depName;
    /** 部门编号 */
    private String deptId;
    /** 用户登录名 */
    private String userName;
    /** 角色id */
    private String roleId;
    /** 公司id */
    private String company;
    /** 组织信息 */
    private List<SyncOrg> orgList;
    /** 用户信息 */
    private List<SyncUserVo> userList;
    /** 用户名称 */
    private String nickName;

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<SyncOrg> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<SyncOrg> orgList) {
        this.orgList = orgList;
    }

    public List<SyncUserVo> getUserList() {
        return userList;
    }

    public void setUserList(List<SyncUserVo> userList) {
        this.userList = userList;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
