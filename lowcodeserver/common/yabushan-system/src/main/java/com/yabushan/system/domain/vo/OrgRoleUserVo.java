package com.yabushan.system.domain.vo;

public class OrgRoleUserVo {

    //组织ID
    private String orgId;
    //角色、流程角色名称
    private String roleName;
    /**
     * 角色类型，1：角色，2：流程角色
     */
    private String roleType;



    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
