package com.yabushan.system.domain;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *  角色授权控制对象 role_control
 *
 * @author zcm
 * @date 2021-09-22
 */

@ApiModel(value = " 角色授权控制对象")
public class RoleControl extends BaseEntity
        {
private static final long serialVersionUID = 1L;


        /** 表id */
    @ApiModelProperty("表id")
    private String roleControlId;
            /** 角色名 */
    @Excel(name = "角色名")
    @ApiModelProperty("角色名")
    private String roleName;
            /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, databaseFormat = "yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private Date createdTime;
            /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, databaseFormat = "yyyy-MM-dd")
    @ApiModelProperty("更新时间")
    private Date updatedTime;
            /** 删除标识 0删除1不删除 */
    @Excel(name = "删除标识 0删除1不删除")
    @ApiModelProperty("删除标识 0删除1不删除")
    private Integer delSign;
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
                        public void setRoleControlId(String roleControlId)
            {
            this.roleControlId = roleControlId;
            }

    public String getRoleControlId()
            {
            return roleControlId;
            }
                            public void setRoleName(String roleName)
            {
            this.roleName = roleName;
            }

    public String getRoleName()
            {
            return roleName;
            }
                            public void setCreatedTime(Date createdTime)
            {
            this.createdTime = createdTime;
            }

    public Date getCreatedTime()
            {
            return createdTime;
            }
                            public void setUpdatedTime(Date updatedTime)
            {
            this.updatedTime = updatedTime;
            }

    public Date getUpdatedTime()
            {
            return updatedTime;
            }
                            public void setDelSign(Integer delSign)
            {
            this.delSign = delSign;
            }

    public Integer getDelSign()
            {
            return delSign;
            }
    
@Override
public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                                .append("roleControlId", getRoleControlId())
                                .append("roleName", getRoleName())
                                .append("createdTime", getCreatedTime())
                                .append("updatedTime", getUpdatedTime())
                                .append("delSign", getDelSign())
            .toString();
        }
        }
