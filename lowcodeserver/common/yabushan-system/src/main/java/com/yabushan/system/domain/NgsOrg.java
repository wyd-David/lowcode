package com.yabushan.system.domain;

import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * 班级对象 ngs_org
 *
 * @author yabushan
 * @date 2021-06-05
 */
public class NgsOrg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 组织ID */
    private String orgId;

    /** 组织名称 */
    @Excel(name = "组织名称")
    private String orgName;

    /** 父组织id */
    @Excel(name = "父组织id")
    private String parentId;

    /** 是否已结课 */
    @Excel(name = "是否已结课")
    private String ortStatus;

    public void setOrgId(String orgId)
    {
        this.orgId = orgId;
    }

    public String getOrgId()
    {
        return orgId;
    }
    public void setOrgName(String orgName)
    {
        this.orgName = orgName;
    }

    public String getOrgName()
    {
        return orgName;
    }
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getParentId()
    {
        return parentId;
    }
    public void setOrtStatus(String ortStatus)
    {
        this.ortStatus = ortStatus;
    }

    public String getOrtStatus()
    {
        return ortStatus;
    }

    /** 子菜单 */
    private List<NgsOrg> children = new ArrayList<NgsOrg>();

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("orgId", getOrgId())
            .append("orgName", getOrgName())
            .append("parentId", getParentId())
            .append("ortStatus", getOrtStatus())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }

    public List<NgsOrg> getChildren() {
        return children;
    }

    public void setChildren(List<NgsOrg> children) {
        this.children = children;
    }
}
