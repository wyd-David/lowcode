package com.yabushan.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 工作流模型对象 act_re_model
 *
 * @author yabushan
 * @date 2021-04-21
 */
public class ActReModel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;

    /** 序列号 */
    @Excel(name = "序列号")
    private Long rev;

    /** 流程名称 */
    @Excel(name = "流程名称")
    private String name;

    /** 流程KEY */
    @Excel(name = "流程KEY")
    private String key;

    /** 备注 */
    @Excel(name = "备注")
    private String category;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdateTime;

    /** 版本 */
    @Excel(name = "版本")
    private Long version;

    /** 元信息 */
    @Excel(name = "元信息")
    private String metaInfo;

    /** 部署ID */
    @Excel(name = "部署ID")
    private String deploymentId;

    /** EDITOR */
    @Excel(name = "EDITOR")
    private String editorSourceValueId;

    /** EDITOR_SORCE */
    @Excel(name = "EDITOR_SORCE")
    private String editorSourceExtraValueId;

    /** 租户ID */
    @Excel(name = "租户ID")
    private String tenantId;

    //流程节点实例ID
    private String pId;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setRev(Long rev)
    {
        this.rev = rev;
    }

    public Long getRev()
    {
        return rev;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setKey(String key)
    {
        this.key = key;
    }

    public String getKey()
    {
        return key;
    }
    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getCategory()
    {
        return category;
    }
    public void setLastUpdateTime(Date lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime()
    {
        return lastUpdateTime;
    }
    public void setVersion(Long version)
    {
        this.version = version;
    }

    public Long getVersion()
    {
        return version;
    }
    public void setMetaInfo(String metaInfo)
    {
        this.metaInfo = metaInfo;
    }

    public String getMetaInfo()
    {
        return metaInfo;
    }
    public void setDeploymentId(String deploymentId)
    {
        this.deploymentId = deploymentId;
    }

    public String getDeploymentId()
    {
        return deploymentId;
    }
    public void setEditorSourceValueId(String editorSourceValueId)
    {
        this.editorSourceValueId = editorSourceValueId;
    }

    public String getEditorSourceValueId()
    {
        return editorSourceValueId;
    }
    public void setEditorSourceExtraValueId(String editorSourceExtraValueId)
    {
        this.editorSourceExtraValueId = editorSourceExtraValueId;
    }

    public String getEditorSourceExtraValueId()
    {
        return editorSourceExtraValueId;
    }
    public void setTenantId(String tenantId)
    {
        this.tenantId = tenantId;
    }

    public String getTenantId()
    {
        return tenantId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("rev", getRev())
            .append("name", getName())
            .append("key", getKey())
            .append("category", getCategory())
            .append("createTime", getCreateTime())
            .append("lastUpdateTime", getLastUpdateTime())
            .append("version", getVersion())
            .append("metaInfo", getMetaInfo())
            .append("deploymentId", getDeploymentId())
            .append("editorSourceValueId", getEditorSourceValueId())
            .append("editorSourceExtraValueId", getEditorSourceExtraValueId())
            .append("tenantId", getTenantId())
            .toString();
    }
}
