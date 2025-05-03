package com.yabushan.system.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 部门信息 对象 department_info
 *
 * @author yabushan
 * @date 2021-10-21
 */

@ApiModel(value = "部门信息 对象")
public class DepartmentInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 表Id
     */
    @ApiModelProperty("表Id")
    private Integer deptId;
    /**
     * 部门名称 部门名称
     */
    @Excel(name = "部门名称",width = 25)
    @ApiModelProperty("部门名称")
    private String deptName;
    /**
     * 部门名称英译 部门名称英译
     */
    @Excel(name = "部门名称英译",width = 25)
    @ApiModelProperty("部门名称英译")
    private String deptNameEn;
    /**
     * 删除标识 0删除1不删除
     */

    @ApiModelProperty("删除标识 0删除1不删除")
    private Integer delSign;
    /**
     * 类型 0公司1部门
     */
   // @Excel(name = "类型",width = 20)
    @ApiModelProperty("类型 0公司1部门")
    private Integer type;
    /** 部门ID */

    /**
     * 父级 父级id
     */
   // @Excel(name = "父级 父级id",width = 25)
    @ApiModelProperty("父级 父级id")
    private Integer parentId;

    @ApiModelProperty("sync表的部门id")
    private String syncDeptId;
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

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptNameEn(String deptNameEn) {
        this.deptNameEn = deptNameEn;
    }

    public String getDeptNameEn() {
        return deptNameEn;
    }

    public void setDelSign(Integer delSign) {
        this.delSign = delSign;
    }

    public Integer getDelSign() {
        return delSign;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public String getSyncDeptId() {
        return syncDeptId;
    }

    public void setSyncDeptId(String syncDeptId) {
        this.syncDeptId = syncDeptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("deptId", getDeptId())
                .append("deptName", getDeptName())
                .append("deptNameEn", getDeptNameEn())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("delSign", getDelSign())
                .append("type", getType())
                .append("parentId", getParentId())
                .append("syncDeptId", getSyncDeptId())
                .toString();
    }
}
