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
 * 同步组织信息对象 uum_organizationinfo
 *
 * @author yabushan
 * @date 2022-04-27
 */

@ApiModel(value = "同步组织信息对象")
public class UumOrganizationinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** $column.columnComment */
    private String ouguid;
    private String parentouguid;
    private String parentouid;
    /** $column.columnComment */
    private String ouid;
    /** $column.columnComment */
    private String orgtypeid;
    /** $column.columnComment */
    private String orgstate;
    /** $column.columnComment */
    private String ouname;
    /** 区县 */
    @Excel(name = "区县")
    @ApiModelProperty("区县")
    private String oufullname;
    /** $column.columnComment */
    @Excel(name = "区县")
    private Long oulevel;
    /** $column.columnComment */
    @Excel(name = "区县")
    private String ouorder;
    /** $column.columnComment */
    @Excel(name = "区县")
    private String orgdn;
    /** $column.columnComment */
    @Excel(name = "区县")
    private String region;
    /** $column.columnComment */
    @Excel(name = "区县")
    private String regionid;
    /** 地市 */
    @Excel(name = "地市")
    @ApiModelProperty("地市")
    private String company;
    /** $column.columnComment */
    @Excel(name = "地市")
    private String companyid;
    /** $column.columnComment */
    @Excel(name = "地市")
    private String branch;
    /** $column.columnComment */
    @Excel(name = "地市")
    private String branchid;
    /** $column.columnComment */
    @Excel(name = "地市")
    private String department;
    /** $column.columnComment */
    @Excel(name = "地市")
    private String departmentid;
    /** $column.columnComment */
    @Excel(name = "地市")
    private String usergroup;
    /** $column.columnComment */
    @Excel(name = "地市")
    private String usergroupid;
    /** $column.columnComment */
    @Excel(name = "地市")
    private String hall;
    /** $column.columnComment */
    @Excel(name = "地市")
    private String hallid;
    /** $column.columnComment */
    @Excel(name = "地市")
    private String salepoint;
    /** $column.columnComment */
    @Excel(name = "地市")
    private String salepointid;
    /** $column.columnComment */
    @Excel(name = "地市")
    private String teams;
    /** $column.columnComment */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "地市", width = 30, format = "yyyy-MM-dd")
    private Date lastmodifytime;
    /** $column.columnComment */
    @Excel(name = "地市")
    private String orgfullname;
    /** $column.columnComment */
    @Excel(name = "地市")
    private String orgtypeidnum;
    /** $column.columnComment */
    @Excel(name = "地市")
    private String orgstarttime;
    /** $column.columnComment */
    @Excel(name = "地市")
    private String cmccorgcode;
    /** 组织负责人(主) */
    @Excel(name = "组织负责人(主)")
    @ApiModelProperty("组织负责人(主)")
    private String orgmainleader;
    /** 上级 */
    @Excel(name = "上级")
    @ApiModelProperty("上级")
    private String supervisor;
    /** 组织负责人(辅) */
    @Excel(name = "组织负责人(辅)")
    @ApiModelProperty("组织负责人(辅)")
    private String orgotherleader;
    /** $column.columnComment */
    @Excel(name = "组织负责人(辅)")
    private String parentcmccorgcode;
    /** 当前处理人 */
    @Excel(name = "当前处理人")
    @ApiModelProperty("当前处理人")
    private String innerHandler;
    /** _data */
    @Excel(name = "_data")
    @ApiModelProperty("_data")
    private String exData;
    /** 标题 */
    @Excel(name = "标题")
    @ApiModelProperty("标题")
    private String title;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, format = "yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private Date createtime;
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

    //条数
    private Integer limitSum;

    //相差条数
    private Integer onLimitSum;

    public UumOrganizationinfo(){}

    public UumOrganizationinfo(String company,String companyid){this.company = company;this.companyid = companyid;}

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
    public void setOuguid(String ouguid)
    {
        this.ouguid = ouguid;
    }

    public String getOuguid()
    {
        return ouguid;
    }
    public void setParentouguid(String parentouguid)
    {
        this.parentouguid = parentouguid;
    }

    public String getParentouguid()
    {
        return parentouguid;
    }
    public void setParentouid(String parentouid)
    {
        this.parentouid = parentouid;
    }

    public String getParentouid()
    {
        return parentouid;
    }
    public void setOuid(String ouid)
    {
        this.ouid = ouid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getOuid()
    {
        return ouid;
    }
    public void setOrgtypeid(String orgtypeid)
    {
        this.orgtypeid = orgtypeid;
    }

    public String getOrgtypeid()
    {
        return orgtypeid;
    }
    public void setOrgstate(String orgstate)
    {
        this.orgstate = orgstate;
    }

    public String getOrgstate()
    {
        return orgstate;
    }
    public void setOuname(String ouname)
    {
        this.ouname = ouname;
    }

    public String getOuname()
    {
        return ouname;
    }
    public void setOufullname(String oufullname)
    {
        this.oufullname = oufullname;
    }

    public String getOufullname()
    {
        return oufullname;
    }
    public void setOulevel(Long oulevel)
    {
        this.oulevel = oulevel;
    }

    public Long getOulevel()
    {
        return oulevel;
    }
    public void setOuorder(String ouorder)
    {
        this.ouorder = ouorder;
    }

    public String getOuorder()
    {
        return ouorder;
    }
    public void setOrgdn(String orgdn)
    {
        this.orgdn = orgdn;
    }

    public String getOrgdn()
    {
        return orgdn;
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
    public void setLastmodifytime(Date lastmodifytime)
    {
        this.lastmodifytime = lastmodifytime;
    }

    public Date getLastmodifytime()
    {
        return lastmodifytime;
    }
    public void setOrgfullname(String orgfullname)
    {
        this.orgfullname = orgfullname;
    }

    public String getOrgfullname()
    {
        return orgfullname;
    }
    public void setOrgtypeidnum(String orgtypeidnum)
    {
        this.orgtypeidnum = orgtypeidnum;
    }

    public String getOrgtypeidnum()
    {
        return orgtypeidnum;
    }
    public void setOrgstarttime(String orgstarttime)
    {
        this.orgstarttime = orgstarttime;
    }

    public String getOrgstarttime()
    {
        return orgstarttime;
    }
    public void setCmccorgcode(String cmccorgcode)
    {
        this.cmccorgcode = cmccorgcode;
    }

    public String getCmccorgcode()
    {
        return cmccorgcode;
    }
    public void setOrgmainleader(String orgmainleader)
    {
        this.orgmainleader = orgmainleader;
    }

    public String getOrgmainleader()
    {
        return orgmainleader;
    }
    public void setSupervisor(String supervisor)
    {
        this.supervisor = supervisor;
    }

    public String getSupervisor()
    {
        return supervisor;
    }
    public void setOrgotherleader(String orgotherleader)
    {
        this.orgotherleader = orgotherleader;
    }

    public String getOrgotherleader()
    {
        return orgotherleader;
    }
    public void setParentcmccorgcode(String parentcmccorgcode)
    {
        this.parentcmccorgcode = parentcmccorgcode;
    }

    public String getParentcmccorgcode()
    {
        return parentcmccorgcode;
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
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
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

    public Integer getLimitSum() {
        return limitSum;
    }

    public void setLimitSum(Integer limitSum) {
        this.limitSum = limitSum;
    }

    public Integer getOnLimitSum() {
        return onLimitSum;
    }

    public void setOnLimitSum(Integer onLimitSum) {
        this.onLimitSum = onLimitSum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("ouguid", getOuguid())
                .append("parentouguid", getParentouguid())
                .append("parentouid", getParentouid())
                .append("ouid", getOuid())
                .append("orgtypeid", getOrgtypeid())
                .append("orgstate", getOrgstate())
                .append("ouname", getOuname())
                .append("oufullname", getOufullname())
                .append("oulevel", getOulevel())
                .append("ouorder", getOuorder())
                .append("orgdn", getOrgdn())
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
                .append("lastmodifytime", getLastmodifytime())
                .append("orgfullname", getOrgfullname())
                .append("orgtypeidnum", getOrgtypeidnum())
                .append("orgstarttime", getOrgstarttime())
                .append("cmccorgcode", getCmccorgcode())
                .append("orgmainleader", getOrgmainleader())
                .append("supervisor", getSupervisor())
                .append("orgotherleader", getOrgotherleader())
                .append("parentcmccorgcode", getParentcmccorgcode())
                .append("innerHandler", getInnerHandler())
                .append("exData", getExData())
                .append("title", getTitle())
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
