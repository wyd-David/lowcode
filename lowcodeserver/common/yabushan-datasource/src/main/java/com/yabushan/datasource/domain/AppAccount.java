package com.yabushan.datasource.domain;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yabushan.common.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 应用实例账号对象 app_account
 *
 * @author yabushan
 * @date 2023-11-19
 */

@ApiModel(value = "应用实例账号对象")
public class AppAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 账号ID */
    @ApiModelProperty("账号ID")
    private String accountId;
    /** 父账号Id */
    @Excel(name = "父账号Id")
    @ApiModelProperty("父账号Id")
    private String parentId;
    /** $column.columnComment */
    @Excel(name = "父账号Id")
    @ApiModelProperty("$column.columnComment")
    private String appId;
    /** 账号用户名 */
    @Excel(name = "账号用户名")
    @ApiModelProperty("账号用户名")
    private String username;
    /** 账号密码 */
    @Excel(name = "账号密码")
    @ApiModelProperty("账号密码")
    private String password;
    /** $column.columnComment */
    @Excel(name = "账号密码")
    @ApiModelProperty("accesskey")
    private String accesskey;
    @Excel(name = "账号密码")
    @ApiModelProperty("secretkey")
    private String secretkey;
    /** $column.columnComment */
    @Excel(name = "账号密码")
    @ApiModelProperty("defaultInstanceId")
    private String defaultInstanceId;
    /** 账号状态 */
    @Excel(name = "账号状态")
    @ApiModelProperty("账号状态")
    private String accountStatus;
    /** 账号说明 */
    @Excel(name = "账号说明")
    @ApiModelProperty("账号说明")
    private String accountDesc;
    /** 是否管理员账号 */
    @Excel(name = "是否管理员账号")
    @ApiModelProperty("是否管理员账号")
    private Long isAdmin;
    /** 创建人ID */
    @Excel(name = "创建人ID")
    @ApiModelProperty("创建人ID")
    private String createUserId;
    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    @ApiModelProperty("创建人姓名")
    private String createUserName;
    /** 修改人ID */
    @Excel(name = "修改人ID")
    @ApiModelProperty("修改人ID")
    private String lastUpdateUserId;
    /** 修改人名称 */
    @Excel(name = "修改人名称")
    @ApiModelProperty("修改人名称")
    private String lastUpdateUserName;
    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("修改时间")
    private Date lastUpdateTime;
    /** 归属空间编码 */
    @Excel(name = "归属空间编码")
    @ApiModelProperty("归属空间编码")
    private String belongSpaceId;
    /** 所有者类型 （User、Tenant、Space） */
    @Excel(name = "所有者类型 ", readConverterExp = "U=ser、Tenant、Space")
    @ApiModelProperty("所有者类型 （User、Tenant、Space）")
    private String ownerType;
    /** 所有者ID */
    @Excel(name = "所有者ID")
    @ApiModelProperty("所有者ID")
    private String ownerId;
    /** 所有者名称 */
    @Excel(name = "所有者名称")
    @ApiModelProperty("所有者名称")
    private String ownerName;
    /** 排序 */
    @Excel(name = "排序")
    @ApiModelProperty("排序")
    private Long sortId;
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
    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    public String getAccountId()
    {
        return accountId;
    }
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getParentId()
    {
        return parentId;
    }
    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getAppId()
    {
        return appId;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getUsername()
    {
        return username;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }
    public void setAccesskey(String accesskey)
    {
        this.accesskey = accesskey;
    }

    public String getAccesskey()
    {
        return accesskey;
    }
    public void setSecretkey(String secretkey)
    {
        this.secretkey = secretkey;
    }

    public String getSecretkey()
    {
        return secretkey;
    }
    public void setDefaultInstanceId(String defaultInstanceId)
    {
        this.defaultInstanceId = defaultInstanceId;
    }

    public String getDefaultInstanceId()
    {
        return defaultInstanceId;
    }
    public void setAccountStatus(String accountStatus)
    {
        this.accountStatus = accountStatus;
    }

    public String getAccountStatus()
    {
        return accountStatus;
    }
    public void setAccountDesc(String accountDesc)
    {
        this.accountDesc = accountDesc;
    }

    public String getAccountDesc()
    {
        return accountDesc;
    }
    public void setIsAdmin(Long isAdmin)
    {
        this.isAdmin = isAdmin;
    }

    public Long getIsAdmin()
    {
        return isAdmin;
    }
    public void setCreateUserId(String createUserId)
    {
        this.createUserId = createUserId;
    }

    public String getCreateUserId()
    {
        return createUserId;
    }
    public void setCreateUserName(String createUserName)
    {
        this.createUserName = createUserName;
    }

    public String getCreateUserName()
    {
        return createUserName;
    }
    public void setLastUpdateUserId(String lastUpdateUserId)
    {
        this.lastUpdateUserId = lastUpdateUserId;
    }

    public String getLastUpdateUserId()
    {
        return lastUpdateUserId;
    }
    public void setLastUpdateUserName(String lastUpdateUserName)
    {
        this.lastUpdateUserName = lastUpdateUserName;
    }

    public String getLastUpdateUserName()
    {
        return lastUpdateUserName;
    }
    public void setLastUpdateTime(Date lastUpdateTime)
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime()
    {
        return lastUpdateTime;
    }
    public void setBelongSpaceId(String belongSpaceId)
    {
        this.belongSpaceId = belongSpaceId;
    }

    public String getBelongSpaceId()
    {
        return belongSpaceId;
    }
    public void setOwnerType(String ownerType)
    {
        this.ownerType = ownerType;
    }

    public String getOwnerType()
    {
        return ownerType;
    }
    public void setOwnerId(String ownerId)
    {
        this.ownerId = ownerId;
    }

    public String getOwnerId()
    {
        return ownerId;
    }
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getOwnerName()
    {
        return ownerName;
    }
    public void setSortId(Long sortId)
    {
        this.sortId = sortId;
    }

    public Long getSortId()
    {
        return sortId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("accountId", getAccountId())
                .append("parentId", getParentId())
                .append("appId", getAppId())
                .append("username", getUsername())
                .append("password", getPassword())
                .append("accesskey", getAccesskey())
                .append("secretkey", getSecretkey())
                .append("defaultInstanceId", getDefaultInstanceId())
                .append("accountStatus", getAccountStatus())
                .append("accountDesc", getAccountDesc())
                .append("isAdmin", getIsAdmin())
                .append("createUserId", getCreateUserId())
                .append("createUserName", getCreateUserName())
                .append("createTime", getCreateTime())
                .append("lastUpdateUserId", getLastUpdateUserId())
                .append("lastUpdateUserName", getLastUpdateUserName())
                .append("lastUpdateTime", getLastUpdateTime())
                .append("belongSpaceId", getBelongSpaceId())
                .append("ownerType", getOwnerType())
                .append("ownerId", getOwnerId())
                .append("ownerName", getOwnerName())
                .append("sortId", getSortId())
                .toString();
    }
}
