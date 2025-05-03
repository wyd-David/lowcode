package com.yabushan.system.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.yabushan.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 同步时间记录表对象 uum_sync_log
 *
 * @author yabushan
 * @date 2022-04-27
 */

@ApiModel(value = "同步时间记录表对象")
public class UumSyncLog extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * $column.columnComment
     */
    @ApiModelProperty("$column.columnComment")
    private String id;
    /**
     * 最大更新时间
     */
    @Excel(name = "最大更新时间")
    @ApiModelProperty("最大更新时间")
    private String maxTime;
    /**
     * 结果
     */
    @Excel(name = "结果")
    @ApiModelProperty("结果")
    private String code;
    /**
     * 描述
     */
    @Excel(name = "描述")
    @ApiModelProperty("描述")
    private String msg;
    /**
     * 数量
     */
    @Excel(name = "数量")
    @ApiModelProperty("数量")
    private Long num;
    /**
     * 类型(1：用户信息  2：用户组织信息)
     */
    @Excel(name = "类型(1：用户信息  2：用户组织信息)")
    @ApiModelProperty("类型(1：用户信息  2：用户组织信息)")
    private String type;
    @Excel(name = "导入状态")
    private String importStatus;

    public UumSyncLog(){}

    public UumSyncLog(String id,String maxTime,String code,String msg,Long num,String type){
        this.id = id;this.maxTime = maxTime;this.code = code;this.msg = msg; this.num = num;this.type = type;
    }

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

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }

    public String getMaxTime() {
        return maxTime;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getNum() {
        return num;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("maxTime", getMaxTime())
                .append("code", getCode())
                .append("msg", getMsg())
                .append("num", getNum())
                .append("type", getType())
                .append("createTime", getCreateTime())
                .toString();
    }
}
