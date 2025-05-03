package com.yabushan.system.domain.vo;

import com.yabushan.common.core.domain.entity.SysUser;

/**
 * 查询用户信息
 *
 * @author leij
 */

public class SysUserVo extends SysUser {
    /** 部门名称 */
    private String depName;
    /** 负责人 */
    private String leader;
    /** 父级部门id */
    private String parentId;
    /** 组织Id */
    private String orgId;
    /** 组织名称 */
    private String orgName;
    /** 组织级别 */
    private String orgLv;
    /** 角色id */
    private String roleId;
    /** 角色名称 */
    private String roleName;
    /** 岗位Id */
    private Long postId;
    /** 岗位名称 */
    private String postName;

    private String erpid;

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgLv() {
        return orgLv;
    }

    public void setOrgLv(String orgLv) {
        this.orgLv = orgLv;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getErpid() {
        return erpid;
    }

    public void setErpid(String erpid) {
        this.erpid = erpid;
    }
}
