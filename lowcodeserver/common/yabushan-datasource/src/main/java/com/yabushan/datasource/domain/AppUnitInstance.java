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
 * 分配实例对象 app_unit_instance
 *
 * @author yabushan
 * @date 2023-11-19
 */

@ApiModel(value = "分配实例对象")
public class AppUnitInstance extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 单元实例ID */
    @ApiModelProperty("单元实例ID")
    private String instanceId;
            /** 父实例Id */
    @Excel(name = "父实例Id")
    @ApiModelProperty("父实例Id")
    private String parentId;
            /** 分配单元树状结构路径，体现层级关系，以“."号分隔 */
    private String path;
            /** 实例编码 */
    @Excel(name = "实例编码")
    @ApiModelProperty("实例编码")
    private String instanceCode;
            /** $column.columnComment */
    @Excel(name = "实例编码")
    @ApiModelProperty("$column.columnComment")
    private String appId;
            /** 账号Id */
    @Excel(name = "账号Id")
    @ApiModelProperty("账号Id")
    private String accountId;
            /** 分配单元编码 */
    @Excel(name = "分配单元编码")
    @ApiModelProperty("分配单元编码")
    private String quotaUnitCode;
            /** 实例名 */
    @Excel(name = "实例名")
    @ApiModelProperty("实例名")
    private String instanceName;
            /** 实例显示名 */
    @Excel(name = "实例显示名")
    @ApiModelProperty("实例显示名")
    private String instanceDisplayName;
            /** 实例描述 */
    @Excel(name = "实例描述")
    @ApiModelProperty("实例描述")
    private String instanceDesc;
            /** 引擎类型编码 */
    @Excel(name = "引擎类型编码")
    @ApiModelProperty("引擎类型编码")
    private String appClassCode;
            /** 引擎版本号 */
    @Excel(name = "引擎版本号")
    @ApiModelProperty("引擎版本号")
    private String versionCode;
            /** 实例类型 storage 存储，calculation 计算,resourcePool 资源池,resourceQueue 队列 */
    @Excel(name = "实例类型 storage 存储，calculation 计算,resourcePool 资源池,resourceQueue 队列")
    @ApiModelProperty("实例类型 storage 存储，calculation 计算,resourcePool 资源池,resourceQueue 队列")
    private String instanceType;
            /** 用途类型 */
    @Excel(name = "用途类型")
    @ApiModelProperty("用途类型")
    private String storageType;
            /** 创建人 */
    @Excel(name = "创建人")
    @ApiModelProperty("创建人")
    private String createUserId;
            /** $column.columnComment */
    @Excel(name = "创建人")
    @ApiModelProperty("$column.columnComment")
    private String createUserName;
                /** $column.columnComment */
    @Excel(name = "创建人")
    @ApiModelProperty("$column.columnComment")
    private String lastUpdateUserId;
            /** $column.columnComment */
    @Excel(name = "创建人")
    @ApiModelProperty("$column.columnComment")
    private String lastUpdateUserName;
            /** $column.columnComment */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("$column.columnComment")
    private Date lastUpdateTime;
            /** $column.columnComment */
    @Excel(name = "创建人")
    @ApiModelProperty("$column.columnComment")
    private String belongSpaceId;
            /** $column.columnComment */
    @Excel(name = "创建人")
    @ApiModelProperty("$column.columnComment")
    private String ownerType;
            /** $column.columnComment */
    @Excel(name = "创建人")
    @ApiModelProperty("$column.columnComment")
    private String ownerId;
            /** $column.columnComment */
    @Excel(name = "创建人")
    @ApiModelProperty("$column.columnComment")
    private String ownerName;
            /** $column.columnComment */
    @Excel(name = "创建人")
    @ApiModelProperty("$column.columnComment")
    private String securityGradeId;
            /** 排序 */
    @Excel(name = "排序")
    @ApiModelProperty("排序")
    private Long sortId;
            /** 是否已删除 */
    @Excel(name = "是否已删除")
    @ApiModelProperty("是否已删除")
    private Long isDeleted;
            /** 是否统计存储 */
    @Excel(name = "是否统计存储")
    @ApiModelProperty("是否统计存储")
    private Long storageStats;
            /** 是否交换通道0:否，1:是 */
    @Excel(name = "是否交换通道0:否，1:是")
    @ApiModelProperty("是否交换通道0:否，1:是")
    private String isExchangeArea;
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
                        public void setInstanceId(String instanceId)
            {
            this.instanceId = instanceId;
            }

    public String getInstanceId()
            {
            return instanceId;
            }
                            public void setParentId(String parentId)
            {
            this.parentId = parentId;
            }

    public String getParentId()
            {
            return parentId;
            }
                            public void setPath(String path)
            {
            this.path = path;
            }

    public String getPath()
            {
            return path;
            }
                            public void setInstanceCode(String instanceCode)
            {
            this.instanceCode = instanceCode;
            }

    public String getInstanceCode()
            {
            return instanceCode;
            }
                            public void setAppId(String appId)
            {
            this.appId = appId;
            }

    public String getAppId()
            {
            return appId;
            }
                            public void setAccountId(String accountId)
            {
            this.accountId = accountId;
            }

    public String getAccountId()
            {
            return accountId;
            }
                            public void setQuotaUnitCode(String quotaUnitCode)
            {
            this.quotaUnitCode = quotaUnitCode;
            }

    public String getQuotaUnitCode()
            {
            return quotaUnitCode;
            }
                            public void setInstanceName(String instanceName)
            {
            this.instanceName = instanceName;
            }

    public String getInstanceName()
            {
            return instanceName;
            }
                            public void setInstanceDisplayName(String instanceDisplayName)
            {
            this.instanceDisplayName = instanceDisplayName;
            }

    public String getInstanceDisplayName()
            {
            return instanceDisplayName;
            }
                            public void setInstanceDesc(String instanceDesc)
            {
            this.instanceDesc = instanceDesc;
            }

    public String getInstanceDesc()
            {
            return instanceDesc;
            }
                            public void setAppClassCode(String appClassCode)
            {
            this.appClassCode = appClassCode;
            }

    public String getAppClassCode()
            {
            return appClassCode;
            }
                            public void setVersionCode(String versionCode)
            {
            this.versionCode = versionCode;
            }

    public String getVersionCode()
            {
            return versionCode;
            }
                            public void setInstanceType(String instanceType)
            {
            this.instanceType = instanceType;
            }

    public String getInstanceType()
            {
            return instanceType;
            }
                            public void setStorageType(String storageType)
            {
            this.storageType = storageType;
            }

    public String getStorageType()
            {
            return storageType;
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
                            public void setSecurityGradeId(String securityGradeId)
            {
            this.securityGradeId = securityGradeId;
            }

    public String getSecurityGradeId()
            {
            return securityGradeId;
            }
                            public void setSortId(Long sortId)
            {
            this.sortId = sortId;
            }

    public Long getSortId()
            {
            return sortId;
            }
                            public void setIsDeleted(Long isDeleted)
            {
            this.isDeleted = isDeleted;
            }

    public Long getIsDeleted()
            {
            return isDeleted;
            }
                            public void setStorageStats(Long storageStats)
            {
            this.storageStats = storageStats;
            }

    public Long getStorageStats()
            {
            return storageStats;
            }
                            public void setIsExchangeArea(String isExchangeArea)
            {
            this.isExchangeArea = isExchangeArea;
            }

    public String getIsExchangeArea()
            {
            return isExchangeArea;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("instanceId", getInstanceId())
                                .append("parentId", getParentId())
                                .append("path", getPath())
                                .append("instanceCode", getInstanceCode())
                                .append("appId", getAppId())
                                .append("accountId", getAccountId())
                                .append("quotaUnitCode", getQuotaUnitCode())
                                .append("instanceName", getInstanceName())
                                .append("instanceDisplayName", getInstanceDisplayName())
                                .append("instanceDesc", getInstanceDesc())
                                .append("appClassCode", getAppClassCode())
                                .append("versionCode", getVersionCode())
                                .append("instanceType", getInstanceType())
                                .append("storageType", getStorageType())
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
                                .append("securityGradeId", getSecurityGradeId())
                                .append("sortId", getSortId())
                                .append("isDeleted", getIsDeleted())
                                .append("storageStats", getStorageStats())
                                .append("isExchangeArea", getIsExchangeArea())
            .toString();
        }
        }
