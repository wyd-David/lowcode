package com.yabushan.system.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 组织架构角色对象 sync_portal_role
 *
 * @author yabushan
 * @date 2021-09-17
 */

@ApiModel(value = "组织架构角色对象")
public class SyncPortalRole extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 架构角色ID
     */
    @ApiModelProperty("架构角色ID")
    private String portalRoleId;
    /**
     * 名称
     */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String personName;
    /**
     * 登录账号
     */
    @Excel(name = "登录账号")
    @ApiModelProperty("登录账号")
    private String personLogin;
    /**
     * 职位
     */
    @Excel(name = "职位")
    @ApiModelProperty("职位")
    private String bpmperson;
    /**
     * 部门
     */
    @Excel(name = "部门")
    @ApiModelProperty("部门")
    private String dpname;
    /**
     * 部门Id
     */
    @ApiModelProperty("部门Id")
    private String dpid;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String isab;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String xh;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String orgid;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String name2;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String loginid2;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String bpmperson2;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String dpname2;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String dpid2;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String isab2;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String xh2;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String orgid2;
    /**
     * 租户
     */
    @ApiModelProperty("租户")
    private String tenancy;
    /**
     * 创建用户
     */
    @ApiModelProperty("创建用户")
    private String createUser;
    /**
     * 最后修改用户
     */
    @ApiModelProperty("最后修改用户")
    private String lastUpdateUser;
    /**
     * 最后修改时间
     */
    @ApiModelProperty("最后修改时间")
    private Date lastUpdateTime;
    @Excel(name = "导入状态")
    private String importStatus;

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


    public SyncPortalRole(){}

    public SyncPortalRole(String portalRoleId,String personName){
        this.portalRoleId = portalRoleId;
        this.personName = personName;
    }

    public String getDisableEnableState() {
        return disableEnableState;
    }

    public void setDisableEnableState(String disableEnableState) {
        this.disableEnableState = disableEnableState;
    }

    public void setPortalRoleId(String portalRoleId) {
        this.portalRoleId = portalRoleId;
    }

    public String getPortalRoleId() {
        return portalRoleId;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonLogin(String personLogin) {
        this.personLogin = personLogin;
    }

    public String getPersonLogin() {
        return personLogin;
    }

    public void setBpmperson(String bpmperson) {
        this.bpmperson = bpmperson;
    }

    public String getBpmperson() {
        return bpmperson;
    }

    public void setDpname(String dpname) {
        this.dpname = dpname;
    }

    public String getDpname() {
        return dpname;
    }

    public void setDpid(String dpid) {
        this.dpid = dpid;
    }

    public String getDpid() {
        return dpid;
    }

    public void setIsab(String isab) {
        this.isab = isab;
    }

    public String getIsab() {
        return isab;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getXh() {
        return xh;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName2() {
        return name2;
    }

    public void setLoginid2(String loginid2) {
        this.loginid2 = loginid2;
    }

    public String getLoginid2() {
        return loginid2;
    }

    public void setBpmperson2(String bpmperson2) {
        this.bpmperson2 = bpmperson2;
    }

    public String getBpmperson2() {
        return bpmperson2;
    }

    public void setDpname2(String dpname2) {
        this.dpname2 = dpname2;
    }

    public String getDpname2() {
        return dpname2;
    }

    public void setDpid2(String dpid2) {
        this.dpid2 = dpid2;
    }

    public String getDpid2() {
        return dpid2;
    }

    public void setIsab2(String isab2) {
        this.isab2 = isab2;
    }

    public String getIsab2() {
        return isab2;
    }

    public void setXh2(String xh2) {
        this.xh2 = xh2;
    }

    public String getXh2() {
        return xh2;
    }

    public void setOrgid2(String orgid2) {
        this.orgid2 = orgid2;
    }

    public String getOrgid2() {
        return orgid2;
    }

    public void setTenancy(String tenancy) {
        this.tenancy = tenancy;
    }

    public String getTenancy() {
        return tenancy;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setLastUpdateUser(String lastUpdateUser) {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getLastUpdateUser() {
        return lastUpdateUser;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("portalRoleId", getPortalRoleId())
                .append("personName", getPersonName())
                .append("personLogin", getPersonLogin())
                .append("bpmperson", getBpmperson())
                .append("dpname", getDpname())
                .append("dpid", getDpid())
                .append("isab", getIsab())
                .append("xh", getXh())
                .append("orgid", getOrgid())
                .append("name2", getName2())
                .append("loginid2", getLoginid2())
                .append("bpmperson2", getBpmperson2())
                .append("dpname2", getDpname2())
                .append("dpid2", getDpid2())
                .append("isab2", getIsab2())
                .append("xh2", getXh2())
                .append("orgid2", getOrgid2())
                .append("tenancy", getTenancy())
                .append("createUser", getCreateUser())
                .append("createTime", getCreateTime())
                .append("lastUpdateUser", getLastUpdateUser())
                .append("lastUpdateTime", getLastUpdateTime())
                .toString();
    }
}
