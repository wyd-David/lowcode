package com.yabushan.system.utils;

import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * 租户组织对象 uum_organizationinfo
 *
 * @author yabushan
 * @date 2021-04-17
 */
public class UumOrgInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 组织ID */
    private String ouguid;

    /** 父组织ID */
    private String parentOuguid;

    /** 组织状态 */
    private String orgState;

    /** 组织名称 */
    private String ouName;

    /** 组织全名称 */
    private String ouFullName;

    /** 组织层级 */
    private Long ouLevel;

    /** 组织排序 */
    private Long ouOrder;

    /** 组织类型 */
    private String orgTypeId;

    /** 所属地区(租户)ID */
    private String regionKey;

    /** 所属地区(租户)名称 */
    private String regionName;

    /** 所属公司 */
    private String companyName;

    /** 公司ID */
    private String companyId;

    /** 分公司 */
    private String branchName;

    /** 分公司ID */
    private String branchId;

    /** 部门 */
    private String departmentName;

    /** 部门序号 */
    private String departmentId;

    /** 科室 */
    private String officeName;

    /** 科室ID */
    private String officeId;

    /** 团队 */
    private String teamName;

    /** 团队序号 */
    private String teamId;

    /** 小组 */
    private String groupName;

    /** 小组序号 */
    private String groupId;

    /** 开始生效时间 */
    private String orgStartTime;

    /** 组织编码 */
    private String orgCode;

    /** 组织主负责人 */
    private String orgMainLeader;

    /** 组织辅负责人 */
    private String orgOtherLeader;

    /** 上级组织负责人 */
    private String orgSupervisor;

    /** 上级组织编码 */
    private String orgParentCode;


    /** 子菜单 */
    private List<UumOrgInfo> children = new ArrayList<UumOrgInfo>();


    public void setOuguid(String ouguid)
    {
        this.ouguid = ouguid;
    }

    public String getOuguid()
    {
        return ouguid;
    }
    public void setParentOuguid(String parentOuguid)
    {
        this.parentOuguid = parentOuguid;
    }

    public String getParentOuguid()
    {
        return parentOuguid;
    }
    public void setOrgState(String orgState)
    {
        this.orgState = orgState;
    }

    public String getOrgState()
    {
        return orgState;
    }
    public void setOuName(String ouName)
    {
        this.ouName = ouName;
    }

    public String getOuName()
    {
        return ouName;
    }
    public void setOuFullName(String ouFullName)
    {
        this.ouFullName = ouFullName;
    }

    public String getOuFullName()
    {
        return ouFullName;
    }
    public void setOuLevel(Long ouLevel)
    {
        this.ouLevel = ouLevel;
    }

    public Long getOuLevel()
    {
        return ouLevel;
    }
    public void setOuOrder(Long ouOrder)
    {
        this.ouOrder = ouOrder;
    }

    public Long getOuOrder()
    {
        return ouOrder;
    }
    public void setOrgTypeId(String orgTypeId)
    {
        this.orgTypeId = orgTypeId;
    }

    public String getOrgTypeId()
    {
        return orgTypeId;
    }
    public void setRegionKey(String regionKey)
    {
        this.regionKey = regionKey;
    }

    public String getRegionKey()
    {
        return regionKey;
    }
    public void setRegionName(String regionName)
    {
        this.regionName = regionName;
    }

    public String getRegionName()
    {
        return regionName;
    }
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getCompanyName()
    {
        return companyName;
    }
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setBranchName(String branchName)
    {
        this.branchName = branchName;
    }

    public String getBranchName()
    {
        return branchName;
    }
    public void setBranchId(String branchId)
    {
        this.branchId = branchId;
    }

    public String getBranchId()
    {
        return branchId;
    }
    public void setDepartmentName(String departmentName)
    {
        this.departmentName = departmentName;
    }

    public String getDepartmentName()
    {
        return departmentName;
    }
    public void setDepartmentId(String departmentId)
    {
        this.departmentId = departmentId;
    }

    public String getDepartmentId()
    {
        return departmentId;
    }
    public void setOfficeName(String officeName)
    {
        this.officeName = officeName;
    }

    public String getOfficeName()
    {
        return officeName;
    }
    public void setOfficeId(String officeId)
    {
        this.officeId = officeId;
    }

    public String getOfficeId()
    {
        return officeId;
    }
    public void setTeamName(String teamName)
    {
        this.teamName = teamName;
    }

    public String getTeamName()
    {
        return teamName;
    }
    public void setTeamId(String teamId)
    {
        this.teamId = teamId;
    }

    public String getTeamId()
    {
        return teamId;
    }
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getGroupName()
    {
        return groupName;
    }
    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getGroupId()
    {
        return groupId;
    }
    public void setOrgStartTime(String orgStartTime)
    {
        this.orgStartTime = orgStartTime;
    }

    public String getOrgStartTime()
    {
        return orgStartTime;
    }
    public void setOrgCode(String orgCode)
    {
        this.orgCode = orgCode;
    }

    public String getOrgCode()
    {
        return orgCode;
    }
    public void setOrgMainLeader(String orgMainLeader)
    {
        this.orgMainLeader = orgMainLeader;
    }

    public String getOrgMainLeader()
    {
        return orgMainLeader;
    }
    public void setOrgOtherLeader(String orgOtherLeader)
    {
        this.orgOtherLeader = orgOtherLeader;
    }

    public String getOrgOtherLeader()
    {
        return orgOtherLeader;
    }
    public void setOrgSupervisor(String orgSupervisor)
    {
        this.orgSupervisor = orgSupervisor;
    }

    public String getOrgSupervisor()
    {
        return orgSupervisor;
    }
    public void setOrgParentCode(String orgParentCode)
    {
        this.orgParentCode = orgParentCode;
    }

    public String getOrgParentCode()
    {
        return orgParentCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("ouguid", getOuguid())
            .append("parentOuguid", getParentOuguid())
            .append("orgState", getOrgState())
            .append("ouName", getOuName())
            .append("ouFullName", getOuFullName())
            .append("ouLevel", getOuLevel())
            .append("ouOrder", getOuOrder())
            .append("orgTypeId", getOrgTypeId())
            .append("regionKey", getRegionKey())
            .append("regionName", getRegionName())
            .append("companyName", getCompanyName())
            .append("companyId", getCompanyId())
            .append("branchName", getBranchName())
            .append("branchId", getBranchId())
            .append("departmentName", getDepartmentName())
            .append("departmentId", getDepartmentId())
            .append("officeName", getOfficeName())
            .append("officeId", getOfficeId())
            .append("teamName", getTeamName())
            .append("teamId", getTeamId())
            .append("groupName", getGroupName())
            .append("groupId", getGroupId())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("orgStartTime", getOrgStartTime())
            .append("orgCode", getOrgCode())
            .append("orgMainLeader", getOrgMainLeader())
            .append("orgOtherLeader", getOrgOtherLeader())
            .append("orgSupervisor", getOrgSupervisor())
            .append("orgParentCode", getOrgParentCode())
            .toString();
    }

    public List<UumOrgInfo> getChildren() {
        return children;
    }

    public void setChildren(List<UumOrgInfo> children) {
        this.children = children;
    }
}
