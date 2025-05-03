package com.yabushan.system.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 同步用户信息对象 uum_userinfo
 *
 * @author yabushan
 * @date 2022-04-27
 */

@ApiModel(value = "同步用户信息对象")
public class UumUserinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String employee;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String userid;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String fullname;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String ouid;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String ouguid;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String workouguid;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String region;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String regionid;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String company;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String companyid;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String branch;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String branchid;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String department;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String departmentid;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String usergroup;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String usergroupid;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String hall;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String hallid;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String salepoint;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String salepointid;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String teams;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String jobtype;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String usertype;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String workphone;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String telephone;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String email;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String address;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private Date userbirthday;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String sex;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String title;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String shortmobile;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String userposilevel;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String currentlevel;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String employeeclass;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String usergrade;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String cmccaccount;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String orderid;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private Date userjoinindate;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String usernation;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String userreligion;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private Date userquitdate;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String userdn;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String dutydesc;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String changetime;
    /** $column.columnComment */

    @ApiModelProperty("创建时间")
    private Date createtime;

    @ApiModelProperty("$column.columnComment")
    private Date lastmodifytime;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String phone3g;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String empstarttime;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String posttitle;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String headshipcode;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String cmccorgcode;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String cmccorgcodetwo;
    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String status;
    /** 当前处理人 */
    @Excel(name = "当前处理人")
    @ApiModelProperty("当前处理人")
    private String innerHandler;
    /** _data */
    @Excel(name = "_data")
    @ApiModelProperty("_data")
    private String exData;
    /** 状态 */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String innerStatus;
    /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private String createdBy;
    /** 修改人 */
    @Excel(name = "修改人")
    @ApiModelProperty("修改人")
    private String updatedBy;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private Date createdAt;
    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("修改时间")
    private Date updatedAt;
    /** 所属部门 */
    @Excel(name = "所属部门")
    @ApiModelProperty("所属部门")
    private String orgId;
    /** 所属租户 */
    @Excel(name = "所属租户")
    @ApiModelProperty("所属租户")
    private String tenantId;
    @Excel(name = "导入状态")
    private String importStatus;

    //排序列
    private String sortField;

    //排序方式
    private String sortOrder;

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
    public void setEmployee(String employee)
    {
        this.employee = employee;
    }

    public String getEmployee()
    {
        return employee;
    }
    public void setUserid(String userid)
    {
        this.userid = userid;
    }

    public String getUserid()
    {
        return userid;
    }
    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

    public String getFullname()
    {
        return fullname;
    }
    public void setOuid(String ouid)
    {
        this.ouid = ouid;
    }

    public String getOuid()
    {
        return ouid;
    }
    public void setOuguid(String ouguid)
    {
        this.ouguid = ouguid;
    }

    public String getOuguid()
    {
        return ouguid;
    }
    public void setWorkouguid(String workouguid)
    {
        this.workouguid = workouguid;
    }

    public String getWorkouguid()
    {
        return workouguid;
    }
    public void setRegion(String region)
    {
        this.region = region;
    }

    public String getRegion()
    {
        return region;
    }
    public void setRegionid(String regionid)
    {
        this.regionid = regionid;
    }

    public String getRegionid()
    {
        return regionid;
    }
    public void setCompany(String company)
    {
        this.company = company;
    }

    public String getCompany()
    {
        return company;
    }
    public void setCompanyid(String companyid)
    {
        this.companyid = companyid;
    }

    public String getCompanyid()
    {
        return companyid;
    }
    public void setBranch(String branch)
    {
        this.branch = branch;
    }

    public String getBranch()
    {
        return branch;
    }
    public void setBranchid(String branchid)
    {
        this.branchid = branchid;
    }

    public String getBranchid()
    {
        return branchid;
    }
    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getDepartment()
    {
        return department;
    }
    public void setDepartmentid(String departmentid)
    {
        this.departmentid = departmentid;
    }

    public String getDepartmentid()
    {
        return departmentid;
    }
    public void setUsergroup(String usergroup)
    {
        this.usergroup = usergroup;
    }

    public String getUsergroup()
    {
        return usergroup;
    }
    public void setUsergroupid(String usergroupid)
    {
        this.usergroupid = usergroupid;
    }

    public String getUsergroupid()
    {
        return usergroupid;
    }
    public void setHall(String hall)
    {
        this.hall = hall;
    }

    public String getHall()
    {
        return hall;
    }
    public void setHallid(String hallid)
    {
        this.hallid = hallid;
    }

    public String getHallid()
    {
        return hallid;
    }
    public void setSalepoint(String salepoint)
    {
        this.salepoint = salepoint;
    }

    public String getSalepoint()
    {
        return salepoint;
    }
    public void setSalepointid(String salepointid)
    {
        this.salepointid = salepointid;
    }

    public String getSalepointid()
    {
        return salepointid;
    }
    public void setTeams(String teams)
    {
        this.teams = teams;
    }

    public String getTeams()
    {
        return teams;
    }
    public void setJobtype(String jobtype)
    {
        this.jobtype = jobtype;
    }

    public String getJobtype()
    {
        return jobtype;
    }
    public void setUsertype(String usertype)
    {
        this.usertype = usertype;
    }

    public String getUsertype()
    {
        return usertype;
    }
    public void setWorkphone(String workphone)
    {
        this.workphone = workphone;
    }

    public String getWorkphone()
    {
        return workphone;
    }
    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getTelephone()
    {
        return telephone;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setUserbirthday(Date userbirthday)
    {
        this.userbirthday = userbirthday;
    }

    public Date getUserbirthday()
    {
        return userbirthday;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setShortmobile(String shortmobile)
    {
        this.shortmobile = shortmobile;
    }

    public String getShortmobile()
    {
        return shortmobile;
    }
    public void setUserposilevel(String userposilevel)
    {
        this.userposilevel = userposilevel;
    }

    public String getUserposilevel()
    {
        return userposilevel;
    }
    public void setCurrentlevel(String currentlevel)
    {
        this.currentlevel = currentlevel;
    }

    public String getCurrentlevel()
    {
        return currentlevel;
    }
    public void setEmployeeclass(String employeeclass)
    {
        this.employeeclass = employeeclass;
    }

    public String getEmployeeclass()
    {
        return employeeclass;
    }
    public void setUsergrade(String usergrade)
    {
        this.usergrade = usergrade;
    }

    public String getUsergrade()
    {
        return usergrade;
    }
    public void setCmccaccount(String cmccaccount)
    {
        this.cmccaccount = cmccaccount;
    }

    public String getCmccaccount()
    {
        return cmccaccount;
    }
    public void setOrderid(String orderid)
    {
        this.orderid = orderid;
    }

    public String getOrderid()
    {
        return orderid;
    }
    public void setUserjoinindate(Date userjoinindate)
    {
        this.userjoinindate = userjoinindate;
    }

    public Date getUserjoinindate()
    {
        return userjoinindate;
    }
    public void setUsernation(String usernation)
    {
        this.usernation = usernation;
    }

    public String getUsernation()
    {
        return usernation;
    }
    public void setUserreligion(String userreligion)
    {
        this.userreligion = userreligion;
    }

    public String getUserreligion()
    {
        return userreligion;
    }
    public void setUserquitdate(Date userquitdate)
    {
        this.userquitdate = userquitdate;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUserquitdate()
    {
        return userquitdate;
    }
    public void setUserdn(String userdn)
    {
        this.userdn = userdn;
    }

    public String getUserdn()
    {
        return userdn;
    }
    public void setDutydesc(String dutydesc)
    {
        this.dutydesc = dutydesc;
    }

    public String getDutydesc()
    {
        return dutydesc;
    }
    public void setChangetime(String changetime)
    {
        this.changetime = changetime;
    }

    public String getChangetime()
    {
        return changetime;
    }
    public void setLastmodifytime(Date lastmodifytime)
    {
        this.lastmodifytime = lastmodifytime;
    }

    public Date getLastmodifytime()
    {
        return lastmodifytime;
    }
    public void setPhone3g(String phone3g)
    {
        this.phone3g = phone3g;
    }

    public String getPhone3g()
    {
        return phone3g;
    }
    public void setEmpstarttime(String empstarttime)
    {
        this.empstarttime = empstarttime;
    }

    public String getEmpstarttime()
    {
        return empstarttime;
    }
    public void setPosttitle(String posttitle)
    {
        this.posttitle = posttitle;
    }

    public String getPosttitle()
    {
        return posttitle;
    }
    public void setHeadshipcode(String headshipcode)
    {
        this.headshipcode = headshipcode;
    }

    public String getHeadshipcode()
    {
        return headshipcode;
    }
    public void setCmccorgcode(String cmccorgcode)
    {
        this.cmccorgcode = cmccorgcode;
    }

    public String getCmccorgcode()
    {
        return cmccorgcode;
    }
    public void setCmccorgcodetwo(String cmccorgcodetwo)
    {
        this.cmccorgcodetwo = cmccorgcodetwo;
    }

    public String getCmccorgcodetwo()
    {
        return cmccorgcodetwo;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setInnerHandler(String innerHandler)
    {
        this.innerHandler = innerHandler;
    }

    public String getInnerHandler()
    {
        return innerHandler;
    }
    public void setExData(String exData)
    {
        this.exData = exData;
    }

    public String getExData()
    {
        return exData;
    }
    public void setInnerStatus(String innerStatus)
    {
        this.innerStatus = innerStatus;
    }

    public String getInnerStatus()
    {
        return innerStatus;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setUpdatedBy(String updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy()
    {
        return updatedBy;
    }
    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }
    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt()
    {
        return updatedAt;
    }
    public void setOrgId(String orgId)
    {
        this.orgId = orgId;
    }

    public String getOrgId()
    {
        return orgId;
    }
    public void setTenantId(String tenantId)
    {
        this.tenantId = tenantId;
    }

    public String getTenantId()
    {
        return tenantId;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("employee", getEmployee())
                .append("userid", getUserid())
                .append("fullname", getFullname())
                .append("ouid", getOuid())
                .append("ouguid", getOuguid())
                .append("workouguid", getWorkouguid())
                .append("region", getRegion())
                .append("regionid", getRegionid())
                .append("company", getCompany())
                .append("companyid", getCompanyid())
                .append("branch", getBranch())
                .append("branchid", getBranchid())
                .append("department", getDepartment())
                .append("departmentid", getDepartmentid())
                .append("usergroup", getUsergroup())
                .append("usergroupid", getUsergroupid())
                .append("hall", getHall())
                .append("hallid", getHallid())
                .append("salepoint", getSalepoint())
                .append("salepointid", getSalepointid())
                .append("teams", getTeams())
                .append("jobtype", getJobtype())
                .append("usertype", getUsertype())
                .append("workphone", getWorkphone())
                .append("telephone", getTelephone())
                .append("email", getEmail())
                .append("address", getAddress())
                .append("userbirthday", getUserbirthday())
                .append("sex", getSex())
                .append("title", getTitle())
                .append("shortmobile", getShortmobile())
                .append("userposilevel", getUserposilevel())
                .append("currentlevel", getCurrentlevel())
                .append("employeeclass", getEmployeeclass())
                .append("usergrade", getUsergrade())
                .append("cmccaccount", getCmccaccount())
                .append("orderid", getOrderid())
                .append("userjoinindate", getUserjoinindate())
                .append("usernation", getUsernation())
                .append("userreligion", getUserreligion())
                .append("userquitdate", getUserquitdate())
                .append("userdn", getUserdn())
                .append("dutydesc", getDutydesc())
                .append("changetime", getChangetime())
                .append("lastmodifytime", getLastmodifytime())
                .append("phone3g", getPhone3g())
                .append("empstarttime", getEmpstarttime())
                .append("posttitle", getPosttitle())
                .append("headshipcode", getHeadshipcode())
                .append("cmccorgcode", getCmccorgcode())
                .append("cmccorgcodetwo", getCmccorgcodetwo())
                .append("status", getStatus())
                .append("innerHandler", getInnerHandler())
                .append("exData", getExData())
                .append("innerStatus", getInnerStatus())
                .append("createdBy", getCreatedBy())
                .append("updatedBy", getUpdatedBy())
                .append("createdAt", getCreatedAt())
                .append("updatedAt", getUpdatedAt())
                .append("orgId", getOrgId())
                .append("tenantId", getTenantId())
                .toString();
    }
}
