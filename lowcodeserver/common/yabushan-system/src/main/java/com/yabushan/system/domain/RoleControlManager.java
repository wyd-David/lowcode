package com.yabushan.system.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 角色授权控制管理对象 role_control_manager
 *
 * @author zcm
 * @date 2021-09-22
 */

@ApiModel(value = " 角色授权控制管理对象")
public class RoleControlManager extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 表id
     */
    @ApiModelProperty("表id")
    private String roleControlManagerId;
    /**
     * 角色控制表id
     */
    @Excel(name = "角色控制表id")
    @ApiModelProperty("角色控制表id")
    private String roleControlId;

    @ApiModelProperty("角色控制表名称")
    private String roleControlName;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    @ApiModelProperty("用户id")
    private String userId;
    /**
     * 用户登录id
     */
    @Excel(name = "用户登录id")
    @ApiModelProperty("用户登录id")
    private String userLoginId;
    /**
     * 用户名
     */
    @Excel(name = "用户名")
    @ApiModelProperty("用户名")
    private String userName;
    /**
     * 作用域
     */
    @Excel(name = "作用域")
    @ApiModelProperty("作用域")
    private String scope;
    /**
     * 用户部门id
     */
    @Excel(name = "用户部门id")
    @ApiModelProperty("用户部门id")
    private String userDeptId;
    /**
     * 用户部门名称
     */
    @Excel(name = "用户部门名称")
    @ApiModelProperty("用户部门名称")
    private String userDeptName;
    /**
     * 删除标识 0删除1不删除
     */
    @Excel(name = "删除标识 0删除1不删除")
    @ApiModelProperty("删除标识 0删除1不删除")
    private Long delSign;
    @Excel(name = "导入状态")
    private String importStatus;
    @ApiModelProperty("岗位信息")
    private String position;

    @ApiModelProperty("作用域信息")
    private String scopeText;

    @ApiModelProperty("erp")
    private String erpid;

    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    /**
     * 禁用状态  0启用,1禁用
     */
    private String disableEnableState;

    public String getDisableEnableState() {
        return disableEnableState;
    }

    public void setDisableEnableState(String disableEnableState) {
        this.disableEnableState = disableEnableState;
    }

    public void setRoleControlManagerId(String roleControlManagerId) {
        this.roleControlManagerId = roleControlManagerId;
    }

    public String getRoleControlManagerId() {
        return roleControlManagerId;
    }

    public void setRoleControlId(String roleControlId) {
        this.roleControlId = roleControlId;
    }

    public String getRoleControlId() {
        return roleControlId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }

    public String getUserLoginId() {
        return userLoginId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public void setUserDeptId(String userDeptId) {
        this.userDeptId = userDeptId;
    }

    public String getUserDeptId() {
        return userDeptId;
    }

    public void setUserDeptName(String userDeptName) {
        this.userDeptName = userDeptName;
    }

    public String getUserDeptName() {
        return userDeptName;
    }

    public void setDelSign(Long delSign) {
        this.delSign = delSign;
    }

    public Long getDelSign() {
        return delSign;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getScopeText() {
        return scopeText;
    }

    public void setScopeText(String scopeText) {
        this.scopeText = scopeText;
    }

    public String getRoleControlName() {
        return roleControlName;
    }

    public void setRoleControlName(String roleControlName) {
        this.roleControlName = roleControlName;
    }

    public String getErpid() {
        return erpid;
    }

    public void setErpid(String erpid) {
        this.erpid = erpid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("roleControlManagerId", getRoleControlManagerId())
                .append("roleControlId", getRoleControlId())
                .append("userId", getUserId())
                .append("userLoginId", getUserLoginId())
                .append("userName", getUserName())
                .append("scope", getScope())
                .append("userDeptId", getUserDeptId())
                .append("userDeptName", getUserDeptName())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("delSign", getDelSign())
                .toString();
    }
}
