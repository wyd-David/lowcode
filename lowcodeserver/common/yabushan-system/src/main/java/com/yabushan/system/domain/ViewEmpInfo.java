package com.yabushan.system.domain;


import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * VIEW对象 view_emp_info
 *
 * @author 姜森焱
 * @date 2021-09-09
 */

@ApiModel(value = "VIEW对象")
public class ViewEmpInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 用户的登录ID
     */
    @Excel(name = "用户的登录ID")
    @ApiModelProperty("用户的登录ID")
    private String loginid;
    /**
     * 用户中文名
     */
    @Excel(name = "用户中文名")
    @ApiModelProperty("用户中文名")
    private String username;
    /**
     * 座机号
     */
    @Excel(name = "座机号")
    @ApiModelProperty("座机号")
    private String phone;
    /**
     * 生日
     */
    @JSONField(format = "yyyy-MM-dd")
    @Excel(name = "生日", width = 30, databaseFormat = "yyyy-MM-dd")
    @ApiModelProperty("生日")
    private Date birthday;
    /**
     * 岗位
     */
    @Excel(name = "岗位")
    @ApiModelProperty("岗位")
    private String position;
    /**
     * 岗位名称
     */
    @Excel(name = "岗位名称")
    @ApiModelProperty("岗位名称")
    private String positioncat;

    @Excel(name = "岗位职级")
    @ApiModelProperty("岗位职级")
    private String userposilevel;

    /**
     * 旧的用户ID,从ERP数据中取过来
     */
    @Excel(name = "旧的用户ID,从ERP数据中取过来")
    @ApiModelProperty("旧的用户ID,从ERP数据中取过来")
    private String erpid;
    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    @ApiModelProperty("手机号码")
    private String mobile;
    /**
     * 性别
     */
    @Excel(name = "性别")
    @ApiModelProperty("性别")
    private String sex;
    /**
     * 工作地点
     */
    @Excel(name = "工作地点")
    @ApiModelProperty("工作地点")
    private String workingplace;
    /**
     * QQ 或 MSN
     */
    @Excel(name = "QQ 或 MSN")
    @ApiModelProperty("QQ 或 MSN")
    private String qqormsn;
    /**
     * 入职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入职日期", width = 30, databaseFormat = "yyyy-MM-dd")
    @ApiModelProperty("入职日期")
    private Date employeddate;
    /**
     * 上级领导(为ERP保留的扩展)
     */
    @Excel(name = "上级领导(为ERP保留的扩展)")
    @ApiModelProperty("上级领导(为ERP保留的扩展)")
    private String superiorleader;
    /**
     * 员工类别(正式员工,临时员工,营销人员等)
     */
    @Excel(name = "员工类别(正式员工,临时员工,营销人员等)")
    @ApiModelProperty("员工类别(正式员工,临时员工,营销人员等)")
    private String stafftype;
    /**
     * 用户中文名
     */
    @Excel(name = "用户中文名")
    @ApiModelProperty("用户中文名")
    private String pUsername;
    /**
     * 用户的登录ID
     */
    @Excel(name = "用户的登录ID")
    @ApiModelProperty("用户的登录ID")
    private String pLoginid;
    /**
     * 用户所属部门id
     */
    @Excel(name = "用户所属部门id")
    @ApiModelProperty("用户所属部门id")
    private String dpid;
    /**
     * 部门全称
     */
    @Excel(name = "部门全称")
    @ApiModelProperty("部门全称")
    private String dpfullname;
    /**
     * 部门名称
     */
    @Excel(name = "部门名称")
    @ApiModelProperty("部门名称")
    private String dpname;
    /**
     * $column.columnComment
     */
    @Excel(name = "部门名称")
    @ApiModelProperty("$column.columnComment")
    private String companyDpid;

    @ApiModelProperty("邮箱")
    private String email;

    /**
     * $column.columnComment
     */
    @Excel(name = "部门名称")
    @ApiModelProperty("$column.columnComment")
    private String companyDpname;
    @Excel(name = "导入状态")
    private String importStatus;

    @ApiModelProperty("名字拼接")
    private String userText;

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

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPositioncat(String positioncat) {
        this.positioncat = positioncat;
    }

    public String getPositioncat() {
        return positioncat;
    }

    public void setErpid(String erpid) {
        this.erpid = erpid;
    }

    public String getErpid() {
        return erpid;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setWorkingplace(String workingplace) {
        this.workingplace = workingplace;
    }

    public String getWorkingplace() {
        return workingplace;
    }

    public void setQqormsn(String qqormsn) {
        this.qqormsn = qqormsn;
    }

    public String getQqormsn() {
        return qqormsn;
    }

    public void setEmployeddate(Date employeddate) {
        this.employeddate = employeddate;
    }

    public Date getEmployeddate() {
        return employeddate;
    }

    public void setSuperiorleader(String superiorleader) {
        this.superiorleader = superiorleader;
    }

    public String getSuperiorleader() {
        return superiorleader;
    }

    public void setStafftype(String stafftype) {
        this.stafftype = stafftype;
    }

    public String getStafftype() {
        return stafftype;
    }

    public void setpUsername(String pUsername) {
        this.pUsername = pUsername;
    }

    public String getpUsername() {
        return pUsername;
    }

    public void setpLoginid(String pLoginid) {
        this.pLoginid = pLoginid;
    }

    public String getpLoginid() {
        return pLoginid;
    }

    public void setDpid(String dpid) {
        this.dpid = dpid;
    }

    public String getDpid() {
        return dpid;
    }

    public void setDpfullname(String dpfullname) {
        this.dpfullname = dpfullname;
    }

    public String getDpfullname() {
        return dpfullname;
    }

    public void setDpname(String dpname) {
        this.dpname = dpname;
    }

    public String getDpname() {
        return dpname;
    }

    public void setCompanyDpid(String companyDpid) {
        this.companyDpid = companyDpid;
    }

    public String getCompanyDpid() {
        return companyDpid;
    }

    public void setCompanyDpname(String companyDpname) {
        this.companyDpname = companyDpname;
    }

    public String getCompanyDpname() {
        return companyDpname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserposilevel() {
        return userposilevel;
    }

    public void setUserposilevel(String userposilevel) {
        this.userposilevel = userposilevel;
    }

    public String getUserText(){
        this.userText = this.username + "-"+this.loginid + "-"+this.dpname;
        return this.userText;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("loginid", getLoginid())
                .append("username", getUsername())
                .append("phone", getPhone())
                .append("birthday", getBirthday())
                .append("position", getPosition())
                .append("positioncat", getPositioncat())
                .append("erpid", getErpid())
                .append("mobile", getMobile())
                .append("sex", getSex())
                .append("workingplace", getWorkingplace())
                .append("qqormsn", getQqormsn())
                .append("employeddate", getEmployeddate())
                .append("superiorleader", getSuperiorleader())
                .append("stafftype", getStafftype())
                .append("pUsername", getpUsername())
                .append("pLoginid", getpLoginid())
                .append("dpid", getDpid())
                .append("dpfullname", getDpfullname())
                .append("dpname", getDpname())
                .append("companyDpid", getCompanyDpid())
                .append("companyDpname", getCompanyDpname())
                .toString();
    }
}
