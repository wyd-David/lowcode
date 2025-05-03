package com.yabushan.system.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author yabushan
 * @date 2021-09-24
 */

@ApiModel(value = "部门角色用户对象")
public class HtGfRolePerson extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String id;
    /**
     * loginid
     */
    @ApiModelProperty("loginid")
    private String personLoginId;

    @ApiModelProperty("loginid")
    private String personLogin;
    /**
     * 姓名
     */
    @Excel(name = "姓名")
    @ApiModelProperty("姓名")
    private String personName;
    /**
     * 角色名称
     */
    @Excel(name = "角色名称")
    @ApiModelProperty("角色名称")
    private String bpmPerson;
    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    private String bpmPersonCode;
    /**
     * 部门名称
     */
    @Excel(name = "部门名称")
    @ApiModelProperty("部门名称")
    private String dpName;
    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private String dpId;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String isAb;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String xh;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String orgId;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String name2;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String loginId2;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String bpmPerson2;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String dpName2;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String dpId2;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String isAb2;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String xh2;
    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String orgId2;
    /**
     * 同步标识,1已同步,0待同步
     */
    @ApiModelProperty("同步标识,1已同步,0待同步")
    private String synthFlag;
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

    public String getDisableEnableState() {
        return disableEnableState;
    }

    public void setDisableEnableState(String disableEnableState) {
        this.disableEnableState = disableEnableState;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setPersonLoginId(String personLoginId) {
        this.personLoginId = personLoginId;
    }

    public String getPersonLoginId() {
        return personLoginId;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setBpmPerson(String bpmPerson) {
        this.bpmPerson = bpmPerson;
    }

    public String getBpmPerson() {
        return bpmPerson;
    }

    public void setBpmPersonCode(String bpmPersonCode) {
        this.bpmPersonCode = bpmPersonCode;
    }

    public String getBpmPersonCode() {
        return bpmPersonCode;
    }

    public void setDpName(String dpName) {
        this.dpName = dpName;
    }

    public String getDpName() {
        return dpName;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getDpId() {
        return dpId;
    }

    public void setIsAb(String isAb) {
        this.isAb = isAb;
    }

    public String getIsAb() {
        return isAb;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getXh() {
        return xh;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName2() {
        return name2;
    }

    public void setLoginId2(String loginId2) {
        this.loginId2 = loginId2;
    }

    public String getLoginId2() {
        return loginId2;
    }

    public void setBpmPerson2(String bpmPerson2) {
        this.bpmPerson2 = bpmPerson2;
    }

    public String getBpmPerson2() {
        return bpmPerson2;
    }

    public void setDpName2(String dpName2) {
        this.dpName2 = dpName2;
    }

    public String getDpName2() {
        return dpName2;
    }

    public void setDpId2(String dpId2) {
        this.dpId2 = dpId2;
    }

    public String getDpId2() {
        return dpId2;
    }

    public void setIsAb2(String isAb2) {
        this.isAb2 = isAb2;
    }

    public String getIsAb2() {
        return isAb2;
    }

    public void setXh2(String xh2) {
        this.xh2 = xh2;
    }

    public String getXh2() {
        return xh2;
    }

    public void setOrgId2(String orgId2) {
        this.orgId2 = orgId2;
    }

    public String getOrgId2() {
        return orgId2;
    }

    public void setSynthFlag(String synthFlag) {
        this.synthFlag = synthFlag;
    }

    public String getSynthFlag() {
        return synthFlag;
    }

    public String getPersonLogin() {
        return personLogin;
    }

    public void setPersonLogin(String personLogin) {
        this.personLogin = personLogin;
        this.personLoginId = personLogin;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("personLoginId", getPersonLoginId())
                .append("personName", getPersonName())
                .append("bpmPerson", getBpmPerson())
                .append("bpmPersonCode", getBpmPersonCode())
                .append("dpName", getDpName())
                .append("dpId", getDpId())
                .append("isAb", getIsAb())
                .append("xh", getXh())
                .append("orgId", getOrgId())
                .append("name2", getName2())
                .append("loginId2", getLoginId2())
                .append("bpmPerson2", getBpmPerson2())
                .append("dpName2", getDpName2())
                .append("dpId2", getDpId2())
                .append("isAb2", getIsAb2())
                .append("xh2", getXh2())
                .append("orgId2", getOrgId2())
                .append("synthFlag", getSynthFlag())
                .toString();
    }
}
