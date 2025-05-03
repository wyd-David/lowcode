package com.yabushan.system.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 系统部门与对接部门中间对象 sys_sync_dep
 *
 * @author leij
 * @date 2021-09-02
 */

@ApiModel(value = "系统部门与对接部门中间对象")
public class SysSyncDep extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * id
     */


    @ApiModelProperty("id")
    private Long id;

    /**
     * 系统部门id
     */


    @ApiModelProperty("系统部门id")
    private Long sysDepId;

    /**
     * 对接部门id
     */


    @ApiModelProperty("对接部门id")
    private String syncDepId;

    /**
     * 部门账套
     */
    @Excel(name = "部门账套")
    @ApiModelProperty("部门账套")
    private String depAcset;
    /**
     * 部门英文名
     */
    @Excel(name = "部门英文名")
    @ApiModelProperty("部门英文名")
    private String depEnglishName;
    /**
     * 是否部门/群组
     */
    @Excel(name = "是否部门/群组")
    @ApiModelProperty("是否部门/群组")
    private String isDept;
    /**
     * 扩展字段1
     */
    @Excel(name = "扩展字段1")
    @ApiModelProperty("扩展字段1")
    private String
            extendedField1;
    /**
     * 扩展字段2
     */
    @Excel(name = "扩展字段2")
    @ApiModelProperty("扩展字段2")
    private String extendedField2;
    /**
     * 扩展字段3
     */
    @Excel(name = "扩展字段3")
    @ApiModelProperty("扩展字段3")
    private String extendedField3;

    @Excel(name = "导入状态")
    private String importStatus;
    /**
     * id
     */
    private String syscsdepid;
    public SysSyncDep() {

    }

    public SysSyncDep(Long sysDepId, String syncDepId) {
        this.syncDepId = syncDepId;
        this.sysDepId = sysDepId;
    }

    public SysSyncDep(Long sysDepId, String syncDepId,String isDept) {
        this.syncDepId = syncDepId;
        this.sysDepId = sysDepId;
        this.isDept = isDept;
    }


    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setSysDepId(Long sysDepId) {
        this.sysDepId = sysDepId;
    }

    public Long getSysDepId() {
        return sysDepId;
    }

    public void setSyncDepId(String syncDepId) {
        this.syncDepId = syncDepId;
    }

    public String getSyncDepId() {
        return syncDepId;
    }

    public String getDepAcset() {
        return depAcset;
    }

    public void setDepAcset(String depAcset) {
        this.depAcset = depAcset;
    }

    public String getDepEnglishName() {
        return depEnglishName;
    }

    public void setDepEnglishName(String depEnglishName) {
        this.depEnglishName = depEnglishName;
    }

    public String getIsDept() {
        return isDept;
    }

    public void setIsDept(String isDept) {
        this.isDept = isDept;
    }

    public String getExtendedField1() {
        return extendedField1;
    }

    public void setExtendedField1(String extendedField1) {
        this.extendedField1 = extendedField1;
    }

    public String getExtendedField2() {
        return extendedField2;
    }

    public void setExtendedField2(String extendedField2) {
        this.extendedField2 = extendedField2;
    }

    public String getExtendedField3() {
        return extendedField3;
    }

    public void setExtendedField3(String extendedField3) {
        this.extendedField3 = extendedField3;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("sysDepId", getSysDepId())
                .append("syncDepId", getSyncDepId())
                .toString();
    }

    public String getSyscsdepid() {
        return syscsdepid;
    }

    public void setSyscsdepid(String syscsdepid) {
        this.syscsdepid = syscsdepid;
    }
}
