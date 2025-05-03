package com.yabushan.system.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yabushan.system.domain.NgsOrg;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 *
 * @author ruoyi
 */
public class OrgTreeSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private String id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<OrgTreeSelect> children;

    public OrgTreeSelect()
    {

    }

    public OrgTreeSelect(UumOrgInfo dept)
    {
        this.id = dept.getOuguid();
        this.label = dept.getOuName();
        this.children = dept.getChildren().stream().map(OrgTreeSelect::new).collect(Collectors.toList());
    }

    public OrgTreeSelect(NgsOrg ngsOrg) {
        this.id = ngsOrg.getOrgId();
        this.label = ngsOrg.getOrgName();
        this.children = ngsOrg.getChildren().stream().map(OrgTreeSelect::new).collect(Collectors.toList());
    }



    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public List<OrgTreeSelect> getChildren()
    {
        return children;
    }

    public void setChildren(List<OrgTreeSelect> children)
    {
        this.children = children;
    }
}
